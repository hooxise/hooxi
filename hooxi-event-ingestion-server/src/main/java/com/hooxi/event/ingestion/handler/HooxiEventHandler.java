package com.hooxi.event.ingestion.handler;

import com.hooxi.data.model.config.FindDestinationsResponse;
import com.hooxi.data.model.event.EventStatus;
import com.hooxi.data.model.event.HooxiEventBuilder;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.ingestion.data.model.WebhookEventMapping;
import com.hooxi.event.ingestion.data.model.request.EventIngestionData;
import com.hooxi.event.ingestion.data.model.request.EventIngestionRequest;
import com.hooxi.event.ingestion.data.model.request.EventMetadata;
import com.hooxi.event.ingestion.data.model.response.EventIngestionResponse;
import com.hooxi.event.ingestion.data.model.response.EventIngestionResponseData;
import com.hooxi.event.ingestion.data.model.response.HooxiEventResponse;
import com.hooxi.event.ingestion.data.repository.HooxiEventRepository;
import com.hooxi.event.ingestion.data.repository.WebhookEventMappingRepository;
import com.hooxi.event.ingestion.service.HooxiConfigServerService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HooxiEventHandler {

  private static final Logger logger = LoggerFactory.getLogger(HooxiEventHandler.class);

  private final HooxiEventRepository hooxiEventRepository;

  private final WebhookEventMappingRepository webhookEventMappingRepository;
  private final HooxiConfigServerService hooxiConfigServerService;
  private final RedisScript<?> queueEventsScript;
  private final ReactiveRedisOperations<String, String> redisTemplate;

  public HooxiEventHandler(
      HooxiEventRepository hooxiEventRepository,
      WebhookEventMappingRepository webhookEventMappingRepository,
      HooxiConfigServerService hooxiConfigServerService,
      RedisScript queueEventsScript,
      ReactiveRedisOperations<String, String> redisTemplate) {
    this.hooxiEventRepository = hooxiEventRepository;
    this.webhookEventMappingRepository = webhookEventMappingRepository;
    this.hooxiConfigServerService = hooxiConfigServerService;
    this.queueEventsScript = queueEventsScript;
    this.redisTemplate = redisTemplate;
  }

  @Transactional
  public Mono<ServerResponse> ingestEvent(ServerRequest serverRequest) {
    ParameterizedTypeReference<List<EventIngestionData>> typeRef =
        new ParameterizedTypeReference<>() {};
    Flux<HooxiEventEntity> hooxiEventEntityFlux =
        serverRequest
            .bodyToMono(EventIngestionRequest.class)
            .flatMapMany(
                er -> {
                  logger.debug("events from request " + er.getEvents());
                  return Flux.fromIterable(er.getEvents());
                })
            .log()
            .map(HooxiEventHandler::buildHooxiEventFromRequest)
            .collectList()
            .flatMapMany(hooxiEventRepository::saveAll);
    return buildEventIngestionDetailsFlux(hooxiEventEntityFlux)
        .delayUntil(queueEventsInRedis())
        .collectList()
        .log()
        .flatMap(HooxiEventHandler::buildResponse);
  }

  private static Mono<ServerResponse> buildResponse(List<EventIngestionDetails> lstEventDetails) {
    List<EventIngestionResponseData> eventIngestionResponseList =
        lstEventDetails.stream()
            .peek(System.out::println)
            .map(
                ed -> {
                  HooxiEventEntity he = ed.getHooxiEventEntity();
                  return EventIngestionResponseData.EventIngestionResponseDataBuilder
                      .anEventIngestionResponse()
                      .withEventId(he.getExternalEventId())
                      .withHooxiEventId(he.getInternalEventId())
                      .build();
                })
            .collect(Collectors.toList());
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(
            EventIngestionResponse.EventIngestionResponseBuilder.anEventIngestionResponse()
                .withEvents(eventIngestionResponseList)
                .build());
  }

  private static HooxiEventEntity buildHooxiEventFromRequest(EventIngestionData req) {
    String hooxiInternalEventId = UUID.randomUUID().toString();

    HooxiEventEntity he =
        new HooxiEventEntity(
            HooxiEventBuilder.aHooxiEvent()
                .withInternalEventId(hooxiInternalEventId)
                .withExternalEventId(req.getEventId())
                .withEventSource(req.getEventMetadata().getEventSource())
                .withEventType(req.getEventMetadata().getEventType())
                .withTenantId(req.getEventMetadata().getTenant())
                .withTimestamp(req.getEventMetadata().getTimestamp())
                .withPayload(req.getPayload())
                .withStatus(EventStatus.PENDING)
                .withHeaders(req.getEventMetadata().getHeaders())
                .withDomainId(req.getEventMetadata().getDomain())
                .withSubdomainId(req.getEventMetadata().getSubdomain())
                .build());
    return he;
  }

  private Function<EventIngestionDetails, Publisher<? extends EventIngestionDetails>>
      queueEventsInRedis() {
    return ed -> {
      logger.debug("ed " + ed.getWebhookEventMappings());
      String interalEventId = ed.getHooxiEventEntity().getInternalEventId();
      List<String> webhookIds =
          ed.getWebhookEventMappings().stream()
              .map(wem -> wem.getWebhookDestinationId().toString())
              .toList();
      List<String> argsList = new ArrayList<>();
      argsList.add(interalEventId);
      argsList.add(ed.getHooxiEventEntity().getTenantId());
      argsList.addAll(webhookIds);
      return redisTemplate.execute(queueEventsScript, List.of(), argsList).then(Mono.just(ed));
    };
  }

  private Flux<EventIngestionDetails> buildEventIngestionDetailsFlux(
      Flux<HooxiEventEntity> hooxiEventEntityFlux) {
    return hooxiEventEntityFlux
        .doOnNext(
            he ->
                logger.debug(
                    "hooxi event from db internal id {} external id {}",
                    he.getInternalEventId(),
                    he.getExternalEventId()))
        .flatMapSequential(
            he ->
                hooxiConfigServerService
                    .findDestinations(he)
                    .map(
                        fd -> {
                          EventIngestionDetails ed = new EventIngestionDetails();
                          ed.setHooxiEventEntity(he);
                          ed.setFindDestinationsResponse(fd);
                          return ed;
                        }))
        .doOnNext(
            ed ->
                logger.debug(
                    "ed before webhookEventMappingRepository internal id {} external id {}",
                    ed.getHooxiEventEntity().getInternalEventId(),
                    ed.getHooxiEventEntity().getExternalEventId()))
        .flatMapSequential(
            ed ->
                webhookEventMappingRepository
                    .saveAll(
                        buildWebhookEventMapping(
                            ed.getHooxiEventEntity(), ed.getFindDestinationsResponse()))
                    .collectList()
                    .map(
                        lstWebhookEventMappings -> {
                          ed.setWebhookEventMappings(lstWebhookEventMappings);
                          return ed;
                        }))
        .doOnNext(
            ed ->
                logger.debug(
                    "ed after webhookEventMappingRepository internal id {} external id {}",
                    ed.getHooxiEventEntity().getInternalEventId(),
                    ed.getHooxiEventEntity().getExternalEventId()));
  }

  public Mono<ServerResponse> getEvent(ServerRequest serverRequest) {
    String eventId = serverRequest.pathVariable("eventId");
    return hooxiEventRepository
        .findById(eventId)
        .flatMap(
            hooxiEventEntity ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(
                        HooxiEventResponse.HooxiEventResponseBuilder.aHooxiEventResponse()
                            .withEventId(hooxiEventEntity.getExternalEventId())
                            .withHooxiEventId(hooxiEventEntity.getInternalEventId())
                            .withPayload(hooxiEventEntity.getPayload())
                            .withEventMetadata(
                                EventMetadata.EventMetadataBuilder.anEventMetadata()
                                    .withEventSource(hooxiEventEntity.getEventSource())
                                    .withEventType(hooxiEventEntity.getEventType())
                                    .withSubdomain(hooxiEventEntity.getSubdomainId())
                                    .withDomain(hooxiEventEntity.getDomainId())
                                    .withTimestamp(hooxiEventEntity.getTimestamp())
                                    .withHeaders(hooxiEventEntity.getHeaders())
                                    .build())
                            .build()));
  }

  private Mono<HooxiEventEntity> buildHooxiEvent(Mono<EventIngestionData> reqMono) {
    /*String hooxiInternalEventId =
    UUID.nameUUIDFromBytes(req.getEventId().getBytes(StandardCharsets.UTF_8)).toString();*/
    return reqMono.map(HooxiEventHandler::buildHooxiEventFromRequest);
  }

  private List<WebhookEventMapping> buildWebhookEventMapping(
      HooxiEventEntity hooxiEventEntity, FindDestinationsResponse destinationsResponse) {
    List<WebhookEventMapping> webhookEventMappings = new ArrayList<>();
    if (destinationsResponse != null && destinationsResponse.getDestinationMappings() != null) {
      webhookEventMappings =
          destinationsResponse.getDestinationMappings().stream()
              .map(
                  d -> {
                    WebhookEventMapping webhookEventMapping = new WebhookEventMapping();
                    webhookEventMapping.setInternalEventId(hooxiEventEntity.getInternalEventId());
                    webhookEventMapping.setWebhookDestinationId(
                        d.getDestinationInfo().getDestinationId());
                    webhookEventMapping.setEventStatus(
                        com.hooxi.event.ingestion.data.model.EventStatus.PENDING);
                    return webhookEventMapping;
                  })
              .collect(Collectors.toList());
    }
    return webhookEventMappings;
  }

  private class EventIngestionDetails {
    private HooxiEventEntity hooxiEventEntity;
    private FindDestinationsResponse findDestinationsResponse;
    private List<WebhookEventMapping> webhookEventMappings;

    public EventIngestionDetails() {}

    public HooxiEventEntity getHooxiEventEntity() {
      return hooxiEventEntity;
    }

    public void setHooxiEventEntity(HooxiEventEntity hooxiEventEntity) {
      this.hooxiEventEntity = hooxiEventEntity;
    }

    public FindDestinationsResponse getFindDestinationsResponse() {
      return findDestinationsResponse;
    }

    public void setFindDestinationsResponse(FindDestinationsResponse findDestinationsResponse) {
      this.findDestinationsResponse = findDestinationsResponse;
    }

    public List<WebhookEventMapping> getWebhookEventMappings() {
      return webhookEventMappings;
    }

    public void setWebhookEventMappings(List<WebhookEventMapping> webhookEventMappings) {
      this.webhookEventMappings = webhookEventMappings;
    }
  }
}
