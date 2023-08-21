package com.hooxi.config.router;

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

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class ConfigServiceRouter {
    @Bean
    @RouterOperations({@RouterOperation(method = RequestMethod.GET,
                                        path = "/destmappings/tenant/{tenantId}/domain/{domainId}/subdomain/{subdomainId}/eventtype/{eventType}",
                                        beanClass = ConfigServiceHandler.class,
                                        beanMethod = "findDestinationMappings",
                                        operation = @Operation(description = "Find Destination Mappings",
                                                               operationId = "findDestinationMappings",
                                                               tags = "DestinationMappings",
                                                               responses = @ApiResponse(responseCode = "200",
                                                                                        content = @Content(
                                                                                            schema = @Schema(
                                                                                                implementation = FindDestinationsResponse.class))),
                                                               parameters = {
                                                                   @Parameter(in = ParameterIn.PATH,
                                                                              name = "eventType"),
                                                                   @Parameter(in = ParameterIn.PATH,
                                                                              name = "subdomainId"),
                                                                   @Parameter(in = ParameterIn.PATH,
                                                                              name = "domainId"),
                                                                   @Parameter(in = ParameterIn.PATH,
                                                                              name = "tenantId")})),
        @RouterOperation(method = RequestMethod.GET,
                         path = "/destmappings/tenant/{tenantId}/domain/{domainId}/subdomain/{subdomainId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "findDestinationMappings",
                         operation = @Operation(description = "Find Destination Mappings",
                                                operationId = "findDestinationMappings",
                                                tags = "DestinationMappings",
                                                responses = @ApiResponse(responseCode = "200",
                                                                         content = @Content(
                                                                             schema = @Schema(
                                                                                 implementation = FindDestinationsResponse.class))),
                                                parameters = {
                                                    @Parameter(in = ParameterIn.PATH,
                                                               name = "subdomainId"),
                                                    @Parameter(in = ParameterIn.PATH,
                                                               name = "domainId"),
                                                    @Parameter(in = ParameterIn.PATH,
                                                               name = "tenantId")})),
        @RouterOperation(method = RequestMethod.GET,
                         path = "/destmappings/tenant/{tenantId}/domain/{domainId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "findDestinationMappings",
                         operation = @Operation(description = "Find Destination Mappings",
                                                operationId = "findDestinationMappings",
                                                tags = "DestinationMappings",
                                                responses = @ApiResponse(responseCode = "200",
                                                                         content = @Content(
                                                                             schema = @Schema(
                                                                                 implementation = FindDestinationsResponse.class))),
                                                parameters = {
                                                    @Parameter(in = ParameterIn.PATH,
                                                               name = "domainId"),
                                                    @Parameter(in = ParameterIn.PATH,
                                                               name = "tenantId")})),
        @RouterOperation(method = RequestMethod.GET,
                         path = "/destmappings/tenant/{tenantId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "findDestinationMappings",
                         operation = @Operation(description = "Find Destination Mappings",
                                                operationId = "findDestinationMappings",
                                                tags = "DestinationMappings",
                                                responses = @ApiResponse(responseCode = "200",
                                                                         content = @Content(
                                                                             schema = @Schema(
                                                                                 implementation = FindDestinationsResponse.class))),
                                                parameters = {
                                                    @Parameter(in = ParameterIn.PATH,
                                                               name = "tenantId")})),
        @RouterOperation(method = RequestMethod.POST,
                         path = "/destmappings/tenant/{tenantId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "addDestinationMapping"),
        @RouterOperation(method = RequestMethod.DELETE,
                         path = "/destmappings/tenant/{tenantId}/mapping/{destMappingId}",
                         beanClass = ConfigServiceHandler.class,
                         beanMethod = "deleteDestinationMapping")
    })
    public RouterFunction<ServerResponse> destinationMappingRoute(ConfigServiceHandler configServiceHandler) {

        return RouterFunctions.route()
            .GET("/destmappings/tenant/{tenantId}/domain/{domainId}/subdomain/{subdomainId}/eventtype/{eventType}",
                accept(MediaType.APPLICATION_JSON), configServiceHandler::findDestinationMappings)
            .GET("/destmappings/tenant/{tenantId}/domain/{domainId}/subdomain/{subdomainId}",
                accept(MediaType.APPLICATION_JSON), configServiceHandler::findDestinationMappings)
            .GET("/destmappings/tenant/{tenantId}/domain/{domainId}", accept(MediaType.APPLICATION_JSON),
                configServiceHandler::findDestinationMappings)
            .GET("/destmappings/tenant/{tenantId}", accept(MediaType.APPLICATION_JSON),
                configServiceHandler::findDestinationMappings)
            .POST("/destmappings/tenant/{tenantId}", accept(MediaType.APPLICATION_JSON),
                configServiceHandler::addDestinationMapping)
            .DELETE("/destmappings/tenant/{tenantId}/mapping/{destMappingId}",
                configServiceHandler::deleteDestinationMapping)
            .build();

    }

}
