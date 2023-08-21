package com.hooxi.config.router;

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

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class DestinationRouter {

    @Bean
    @RouterOperations({
        @RouterOperation(method = RequestMethod.GET,
                         path = "/destinations/tenant/{tenantId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "findDestinationsForTenant"),
        @RouterOperation(method = RequestMethod.GET,
                         path = "/destinations/tenant/{tenantId}/destination/{destinationId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "getDestination"),
        @RouterOperation(method = RequestMethod.POST,
                         path = "/destinations/tenant/{tenantId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "addDestination")
    })
    public RouterFunction<ServerResponse> destinationRoute(ConfigServiceHandler configServiceHandler) {

        return RouterFunctions.route(GET("/destinations/tenant/{tenantId}").and(accept(MediaType.APPLICATION_JSON)),
                configServiceHandler::findDestinationsForTenant)
            .andRoute(GET("/destinations/tenant/{tenantId}/destination/{destinationId}").and(accept(MediaType.APPLICATION_JSON)),
                configServiceHandler::getDestination)
            .andRoute(RequestPredicates.POST("/destinations/tenant/{tenantId}")
                .and(accept(MediaType.APPLICATION_JSON)), configServiceHandler::addDestination);
    }
}
