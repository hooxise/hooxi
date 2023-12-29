package com.hooxi.event.webhook.worker;

import com.hooxi.data.model.config.DestinationResponse;
import com.hooxi.data.model.config.DestinationSecurityConfigResponse;
import com.hooxi.event.ingestion.data.model.EventStatus;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.webhook.worker.data.EventWithWenhookEventMapping;
import com.hooxi.event.webhook.worker.exception.WebhookExecutionFailureException;
import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class EventPoller {

  private static final Logger logger = LoggerFactory.getLogger(EventPoller.class);
  private final RedisScript<List<String>> pendingEventsScript;
  private final RedisScript<?> updateWebhookStatusScript;

  private final HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService;
  private final ReactiveRedisOperations<String, String> redisTemplate;
  private final WebClient configServiceWebClient;
  private final URI configServerURI;

  @Autowired
  public EventPoller(
      ReactiveRedisOperations<String, String> redisTemplate,
      RedisScript<List<String>> pendingEventsScript,
      RedisScript<?> updateWebhookStatusScript,
      HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService,
      @Value("${hooxi.config.server.url}") String configServerUrl) {
    this.pendingEventsScript = pendingEventsScript;
    this.updateWebhookStatusScript = updateWebhookStatusScript;
    this.redisTemplate = redisTemplate;
    this.hooxiEventStatusUpdaterService = hooxiEventStatusUpdaterService;
    configServiceWebClient = WebClient.builder().build();
    configServerURI = URI.create(configServerUrl);
  }

  @Scheduled(fixedDelay = 100)
  public void pollEventsAndExecuteWebhook() {
    Mono<Pair<String, List<String>>> webhookEventsPairStream = readWebhookEventsFromRedis();
    webhookEventsPairStream
        .flatMap(
            webhookEventsPair -> {
              logger.debug("webhook events pair " + webhookEventsPair);
              // pending_webhook stores webhook id as {tenant_id}:destinationId
              String[] tenantWebhookId = webhookEventsPair.getFirst().split(":");
              String tenantId = tenantWebhookId[0].substring(1, tenantWebhookId[0].length() - 1);
              String destinationId = tenantWebhookId[1];
              List<String> eventIds = webhookEventsPair.getSecond();
              Mono<DestinationResponse> destResp =
                  configServiceWebClient
                      .get()
                      .uri(
                          uriBuilder ->
                              uriBuilder
                                  .host(configServerURI.getHost())
                                  .port(configServerURI.getPort())
                                  .scheme(configServerURI.getScheme())
                                  .path(
                                      "/config/api/v1/tenants/{tenantId}/destinations/{destinationId}")
                                  .build(tenantId, destinationId))
                      .retrieve()
                      .bodyToMono(DestinationResponse.class);
              return destResp
                  .zipWith(
                      configServiceWebClient
                          .get()
                          .uri(
                              uriBuilder ->
                                  uriBuilder
                                      .host(configServerURI.getHost())
                                      .port(configServerURI.getPort())
                                      .scheme(configServerURI.getScheme())
                                      .path(
                                          "/config/api/v1/tenants/{tenantId}/destinations/{destinationId}/securityconfig")
                                      .build(tenantId, destinationId))
                          .retrieve()
                          .bodyToMono(DestinationSecurityConfigResponse.class))
                  .flatMap(
                      tuple -> {
                        logger.debug("destination response " + tuple.getT1());
                        DestinationResponse dst = tuple.getT1();
                        DestinationSecurityConfigResponse dstSecConfig = tuple.getT2();
                        WebClient webClient = WebClient.builder().build();
                        return Flux.fromIterable(eventIds)
                            .flatMap(
                                eventId ->
                                    hooxiEventStatusUpdaterService.findHooxiEventEntity(eventId))
                            .flatMap(
                                he -> {
                                  try {
                                    return invokeWebhookWithRetry(he, webClient, dst, destinationId)
                                        .flatMap(
                                            str ->
                                                hooxiEventStatusUpdaterService
                                                    .updateHooxiEventStatus(
                                                        he.getInternalEventId(),
                                                        dst.getDestinationId(),
                                                        EventStatus.SUCCESS))
                                        .flatMap(
                                            eventWithWenhookEventMapping ->
                                                removeEventIdFromRedisList(
                                                    he,
                                                    eventWithWenhookEventMapping,
                                                    tenantId,
                                                    destinationId));
                                  } catch (WebhookExecutionFailureException wex) {
                                    logger.error("webhook execution failed", wex);
                                    redisTemplate
                                        .opsForSet()
                                        .add(
                                            "pending_webhooks_set_key",
                                            "{" + tenantId + "}:" + destinationId)
                                        .subscribe();
                                    throw wex;
                                  }
                                })
                            .onErrorComplete(WebhookExecutionFailureException.class)
                            .reduce(
                                new ArrayList<EventWithWenhookEventMapping>(),
                                (lst, eventWithWebhookEventMapping2) -> {
                                  lst.add(eventWithWebhookEventMapping2);
                                  return lst;
                                })
                            .map(
                                lst -> {
                                  logger.debug(
                                      "calling update webhook status script {} {}",
                                      tenantId,
                                      destinationId);
                                  return redisTemplate
                                      .execute(
                                          updateWebhookStatusScript,
                                          List.of(),
                                          List.of("{" + tenantId + "}:" + destinationId))
                                      .subscribe();
                                });
                      });
            })
        .subscribe();
  }

  private Mono<EventWithWenhookEventMapping> removeEventIdFromRedisList(
      HooxiEventEntity he,
      EventWithWenhookEventMapping eventWithWenhookEventMapping,
      String tenantId,
      String destinationId) {
    return redisTemplate
        .opsForList()
        .remove(
            "webhook_events" + ":{" + tenantId + "}:" + destinationId, 1, he.getInternalEventId())
        .map((b) -> eventWithWenhookEventMapping);
  }

  private Mono<String> invokeWebhookWithRetry(
      HooxiEventEntity he, WebClient webClient, DestinationResponse dst, String destinationId) {
    return webClient
        .post()
        .uri(dst.getDestination().getEndpoint())
        .bodyValue(he.getPayload())
        .retrieve()
        .bodyToMono(String.class)
        .onErrorResume(Mono::error)
        .retryWhen(
            Retry.backoff(3, Duration.of(2, ChronoUnit.SECONDS))
                .onRetryExhaustedThrow(
                    (retryBackoffSpec, retrySignal) -> {
                      hooxiEventStatusUpdaterService
                          .updateHooxiEventStatus(
                              he.getInternalEventId(), dst.getDestinationId(), EventStatus.FAILURE)
                          .subscribe();
                      throw new WebhookExecutionFailureException(
                          "webhook execution failed for " + destinationId);
                    }));
  }

  private Mono<Pair<String, List<String>>> readWebhookEventsFromRedis() {
    return redisTemplate
        .execute(pendingEventsScript)
        .reduce(
            new ArrayList<String>(),
            (lst, eventList) -> {
              lst.addAll(eventList);
              return lst;
            })
        .flatMap(
            result -> {
              if (result.size() > 0) {
                String webhookId = result.get(0);
                List<String> eventIds = result.stream().skip(1).collect(Collectors.toList());
                return Mono.just(Pair.of(webhookId, eventIds));
              } else {
                return Mono.empty();
              }
            });
  }
}
