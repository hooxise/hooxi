package com.hooxi.event.webhook.worker;

import com.hooxi.data.model.config.DestinationResponse;
import com.hooxi.event.ingestion.data.model.EventStatus;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.webhook.worker.exception.WebhookExecutionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class WebhookInvoker {

    private final HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService;

    public WebhookInvoker(HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService) {
        this.hooxiEventStatusUpdaterService = hooxiEventStatusUpdaterService;
    }

    public Mono<String> invokeWebhookWithRetry(
            HooxiEventEntity he, DestinationResponse dst) {
        WebClient webClient = WebClient.builder().build();
      return webClient
          .post()
          .uri(dst.getDestination().getEndpoint())
          .bodyValue(he.getPayload())
          .retrieve()
          .bodyToMono(String.class)
          .onErrorResume(Mono::error)
          .retryWhen(
              Retry.backoff(2, Duration.of(2, ChronoUnit.SECONDS))
                  .onRetryExhaustedThrow(
                      (retryBackoffSpec, retrySignal) -> {
                        throw new WebhookExecutionFailureException(
                            "webhook execution failed for " + dst.getDestinationId());
                      }));
    }
}
