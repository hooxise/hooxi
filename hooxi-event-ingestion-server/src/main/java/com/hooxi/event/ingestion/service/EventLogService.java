package com.hooxi.event.ingestion.service;

import com.hooxi.data.model.event.log.EventLogBuilder;
import com.hooxi.data.model.event.log.EventLogsResponse;
import com.hooxi.data.model.event.log.EventLogsResponseBuilder;
import com.hooxi.event.ingestion.data.repository.HooxiEventRepository;
import com.hooxi.event.ingestion.data.repository.WebhookLogRepository;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EventLogService {
  private static final Logger logger = LoggerFactory.getLogger(EventLogService.class);

  private final WebhookLogRepository webhookLogRepository;
  private final HooxiEventRepository hooxiEventRepository;

  public EventLogService(
      WebhookLogRepository webhookLogRepository, HooxiEventRepository hooxiEventRepository) {
    this.webhookLogRepository = webhookLogRepository;
    this.hooxiEventRepository = hooxiEventRepository;
  }

  public Mono<EventLogsResponse> getEventLogs(String eventId) {
    return hooxiEventRepository
        .findById(eventId)
        .flatMap(
            he ->
                webhookLogRepository
                    .findByInternalEventId(eventId)
                    .map(
                        webhookLogEntity ->
                            EventLogBuilder.anEventLog()
                                .withHttpStatus(webhookLogEntity.getHttpStatus())
                                .withTimestamp(webhookLogEntity.getTimestamp())
                                .withResponseHeaders(webhookLogEntity.getResponseHeaders())
                                .withResposnePayload(webhookLogEntity.getResponsePayload())
                                .build())
                    .collectList()
                    .switchIfEmpty(Mono.just(new ArrayList<>()))
                    .map(
                        lstEventLog ->
                            EventLogsResponseBuilder.anEventLogsResponse()
                                .withEventId(eventId)
                                .withEventLogs(lstEventLog)
                                .build()));
  }
}
