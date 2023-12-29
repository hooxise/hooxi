package com.hooxi.config.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.hooxi.config.router.handler.DestinationSecurityConfigHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class DestinationSecurityConfigRouter {

  @Bean
  public RouterFunction<ServerResponse> destinationSecurityRoute(
      DestinationSecurityConfigHandler destinationSecurityConfigHandler) {

    return RouterFunctions.route(
            GET("/tenants/{tenantId}/destinations/{destinationId}/securityconfig")
                .and(accept(MediaType.APPLICATION_JSON)),
            destinationSecurityConfigHandler::findDestinationSeurityConfig)
        .andRoute(
            RequestPredicates.POST(
                    "/tenants/{tenantId}/destinations/{destinationId}/securityconfig")
                .and(accept(MediaType.APPLICATION_JSON)),
            destinationSecurityConfigHandler::addDestinationSeurityConfig);
  }
}
