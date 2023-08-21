package com.hooxi.config.router.handler;

import com.hooxi.config.service.DestinationSecurityConfigService;
import com.hooxi.data.model.config.DestinationSecurityConfigResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class DestinationSecurityConfigHandler {

    private final DestinationSecurityConfigService destinationSecurityConfigService;

    @Autowired
    public DestinationSecurityConfigHandler(DestinationSecurityConfigService destinationSecurityConfigService) {
        this.destinationSecurityConfigService = destinationSecurityConfigService;
    }

    public Mono<ServerResponse> findDestinationSeurityConfig(ServerRequest serverRequest) {
        Long destinationId = Long.valueOf(serverRequest.pathVariable("destinationId"));
        String tenantId = serverRequest.pathVariable("tenantId");
        return destinationSecurityConfigService.findDestinationSecurityConfig(tenantId, destinationId)
            .flatMap(dsc -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(DestinationSecurityConfigResponseBuilder.aDestinationSecurityConfigResponse()
                    .withDestinationId(destinationId)
                    .withDestinationSecurityConfig(dsc)
                    .build()));
    }

    public Mono<ServerResponse> addDestinationSeurityConfig(ServerRequest serverRequest) {
        return Mono.empty();
    }
}
