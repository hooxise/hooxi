package com.hooxi.event.ingestion.router.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HooxiEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(HooxiEventHandler.class);

    public Mono<ServerResponse> ingestEvent(ServerRequest serverRequest) {
        return Mono.empty();
    }

    public Mono<ServerResponse> getEvent(ServerRequest serverRequest) {
        return Mono.empty();
    }
}
