package com.hooxi.event.ingestion.router;

import com.hooxi.event.ingestion.data.model.response.EventIngestionResponse;
import com.hooxi.event.ingestion.router.handler.HooxiEventHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class HooxiEventApiRouter {

    @Bean
    @RouterOperations({@RouterOperation(method = RequestMethod.POST,
            path = "/event",
            beanClass = HooxiEventHandler.class,
            beanMethod = "postEvent",
            operation = @Operation(description = "Post an event",
                    operationId = "postEvent",
                    tags = "EventIngestion",
                    responses = @ApiResponse(responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = EventIngestionResponse.class)))
                   )),
            @RouterOperation(method = RequestMethod.GET,
                    path = "/event/{hooxiEventId}}",
                    beanClass = HooxiEventHandler.class,
                    beanMethod = "getEvent",
                    operation = @Operation(description = "Get Event Details using hooxi event id",
                            operationId = "GetEvent",
                            tags = "EventIngestion",
                            responses = @ApiResponse(responseCode = "200",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = EventIngestionResponse.class))),
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH,
                                            name = "hooxiEventId")}))
    })
    public RouterFunction<ServerResponse> eventIngestionRoute(HooxiEventHandler hooxiEventHandler) {

        return RouterFunctions.route()
                .POST("/events", accept(MediaType.APPLICATION_JSON),
                        hooxiEventHandler::ingestEvent)
                .GET("/events/{hooxiEventId}",
                        hooxiEventHandler::getEvent)
                .build();

    }
}
