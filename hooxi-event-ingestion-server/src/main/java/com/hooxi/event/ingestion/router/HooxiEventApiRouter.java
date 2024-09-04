package com.hooxi.event.ingestion.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.hooxi.data.model.event.log.EventLogsResponse;
import com.hooxi.event.ingestion.data.model.request.EventIngestionRequest;
import com.hooxi.event.ingestion.data.model.response.EventIngestionResponse;
import com.hooxi.event.ingestion.data.model.response.EventIngestionResponseData;
import com.hooxi.event.ingestion.handler.HooxiEventHandler;
import com.hooxi.event.ingestion.handler.HooxiEventLogHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

@Configuration(proxyBeanMethods = false)
public class HooxiEventApiRouter {

  @Bean
  @RouterOperations({
    @RouterOperation(
        method = RequestMethod.POST,
        path = "/events",
        operation =
            @Operation(
                description = "Ingets events",
                operationId = "ingestEvent",
                tags = "Events",
                responses = {
                  @ApiResponse(
                      responseCode = "200",
                      content =
                          @Content(schema = @Schema(implementation = EventIngestionResponse.class)))
                },
                requestBody =
                    @RequestBody(
                        content =
                            @Content(
                                schema = @Schema(implementation = EventIngestionRequest.class))),
                parameters = {@Parameter(in = ParameterIn.PATH, name = "tenantId")})),
    @RouterOperation(
        method = RequestMethod.GET,
        path = "/events/{hooxiEventId}",
        beanClass = HooxiEventHandler.class,
        beanMethod = "getEvent",
        operation =
            @Operation(
                description = "Get Event Details using hooxi event id",
                operationId = "GetEvent",
                tags = "EventIngestion",
                responses =
                    @ApiResponse(
                        responseCode = "200",
                        content =
                            @Content(
                                schema =
                                    @Schema(implementation = EventIngestionResponseData.class))),
                parameters = {@Parameter(in = ParameterIn.PATH, name = "hooxiEventId")}))
  })
  public RouterFunction<ServerResponse> eventIngestionRoute(HooxiEventHandler hooxiEventHandler) {

    return RouterFunctions.route()
        .POST("/events", accept(MediaType.APPLICATION_JSON), hooxiEventHandler::ingestEvent)
        .GET("/events/{hooxiEventId}", hooxiEventHandler::getEvent)
        .build();
  }

  @Bean
  @RouterOperations({
    @RouterOperation(
        method = RequestMethod.GET,
        path = "/events/{hooxiEventId}/logs",
        beanClass = HooxiEventLogHandler.class,
        beanMethod = "findEventLogs",
        operation =
            @Operation(
                description = "Get Event Logs for hooxi event id",
                operationId = "GetEventLogs",
                tags = "EventLogs",
                responses =
                    @ApiResponse(
                        responseCode = "200",
                        content =
                            @Content(schema = @Schema(implementation = EventLogsResponse.class))),
                parameters = {@Parameter(in = ParameterIn.PATH, name = "hooxiEventId")}))
  })
  public RouterFunction<ServerResponse> eventLogs(HooxiEventLogHandler eventLogService) {
    return RouterFunctions.route()
        .GET("/events/{hooxiEventId}/logs", eventLogService::findEventLogs)
        .build();
  }
}
