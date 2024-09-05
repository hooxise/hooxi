package com.hooxi.event.ingestion.handler;

import com.hooxi.event.ingestion.service.EventLogService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HooxiEventLogHandler {
  private final EventLogService eventLogService;

  public HooxiEventLogHandler(EventLogService eventLogService) {
    this.eventLogService = eventLogService;
  }

  public Mono<ServerResponse> findEventLogs(ServerRequest serverRequest) {
    String eventId = serverRequest.pathVariable("hooxiEventId");
    return eventLogService
        .getEventLogs(eventId)
        .flatMap(de -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(de))
        .switchIfEmpty(ServerResponse.notFound().build());
  }
}
