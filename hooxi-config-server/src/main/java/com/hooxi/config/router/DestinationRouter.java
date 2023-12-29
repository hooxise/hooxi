package com.hooxi.config.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.hooxi.config.router.handler.ConfigServiceHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class DestinationRouter {

  @Bean
  @RouterOperations({
    @RouterOperation(
        method = RequestMethod.GET,
        path = "/tenants/{tenantId}/destinations",
        beanClass = ConfigServiceHandler.class,
        beanMethod = "findDestinationsForTenant"),
    @RouterOperation(
        method = RequestMethod.GET,
        path = "/tenants/{tenantId}/destinations/{destinationId}",
        beanClass = ConfigServiceHandler.class,
        beanMethod = "getDestination"),
    @RouterOperation(
        method = RequestMethod.POST,
        path = "/tenants/{tenantId}/destinations",
        beanClass = ConfigServiceHandler.class,
        beanMethod = "addDestination")
  })
  public RouterFunction<ServerResponse> destinationRoute(
      ConfigServiceHandler configServiceHandler) {

    return RouterFunctions.route(
            GET("/tenants/{tenantId}/destinations").and(accept(MediaType.APPLICATION_JSON)),
            configServiceHandler::findDestinationsForTenant)
        .andRoute(
            GET("/tenants/{tenantId}/destinations/{destinationId}")
                .and(accept(MediaType.APPLICATION_JSON)),
            configServiceHandler::getDestination)
        .andRoute(
            RequestPredicates.POST("/tenants/{tenantId}/destinations")
                .and(accept(MediaType.APPLICATION_JSON)),
            configServiceHandler::addDestination);
  }
}
