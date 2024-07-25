package com.hooxi.config.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.hooxi.config.router.handler.ConfigServiceHandler;
import com.hooxi.data.model.config.FindDestinationsResponse;
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

@Configuration(proxyBeanMethods = false)
public class ConfigServiceRouter {
  @Bean
  @RouterOperations({
    @RouterOperation(
        method = RequestMethod.GET,
        path = "/tenants/{tenantId}/destmappings",
        beanClass = ConfigServiceHandler.class,
        beanMethod = "findDestinationMappings",
        operation =
            @Operation(
                description = "Find Destination Mappings",
                operationId = "findDestinationMappings",
                tags = "DestinationMappings",
                responses =
                    @ApiResponse(
                        responseCode = "200",
                        content =
                            @Content(
                                schema = @Schema(implementation = FindDestinationsResponse.class))),
                parameters = {
                  @Parameter(in = ParameterIn.PATH, name = "tenantId", schema = @Schema(type = "string")),
                  @Parameter(in = ParameterIn.QUERY, name = "domainId", schema = @Schema(type = "string")),
                  @Parameter(in = ParameterIn.QUERY, name = "subdomainId", schema = @Schema(type = "string")),
                  @Parameter(in = ParameterIn.QUERY, name = "eventType", schema = @Schema(type = "string"))
                })),
    @RouterOperation(
        method = RequestMethod.POST,
        path = "/tenants/{tenantId}/destmappings",
        beanClass = ConfigServiceHandler.class,
        beanMethod = "addDestinationMapping"),
    @RouterOperation(
        method = RequestMethod.DELETE,
        path = "/tenants/{tenantId}/destmappings/{destMappingId}",
        beanClass = ConfigServiceHandler.class,
        beanMethod = "deleteDestinationMapping")
  })
  public RouterFunction<ServerResponse> destinationMappingRoute(
      ConfigServiceHandler configServiceHandler) {

    return RouterFunctions.route()
        .GET(
            "/tenants/{tenantId}/destmappings",
            accept(MediaType.APPLICATION_JSON),
            configServiceHandler::findDestinationMappings)
        .POST(
            "/tenants/{tenantId}/destmappings",
            accept(MediaType.APPLICATION_JSON),
            configServiceHandler::addDestinationMapping)
        .DELETE(
            "/tenants/{tenantId}/destmappings/{destMappingId}",
            configServiceHandler::deleteDestinationMapping)
        .build();
  }
}
