package com.hooxi.config.router.handler;

import com.hooxi.config.service.ConfigService;
import com.hooxi.data.model.config.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ConfigServiceHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceHandler.class);

    private final ConfigService configService;

    @Autowired
    public ConfigServiceHandler(ConfigService configService) {
        this.configService = configService;
    }

    public Mono<ServerResponse> findDestinationMappings(ServerRequest serverRequest) {
        Map<String, String> pathVariables = serverRequest.pathVariables();
        String tenantId = pathVariables.get("tenantId");
        if (tenantId == null || tenantId.trim().isEmpty()) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                .bodyValue("missing tenantid in the path");
        }
        FindDestinationsRequest req = FindDestinationsRequestBuilder.aFindDestinationsRequest()
            .withDomain(pathVariables.getOrDefault("domainId", "")).withTenantId(pathVariables.get("tenantId"))
            .withSubDomain(pathVariables.getOrDefault("subdomainId", ""))
            .withEventType(pathVariables.getOrDefault("eventType", "")).build();
        Mono<FindDestinationsResponse> fdr = configService.findDestinations(Mono.just(req));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fdr, FindDestinationsResponse.class)
            .onErrorResume(e -> {
                logger.error("error in finding destinations for %s ", serverRequest, e);
                return Mono.just(String.format("error in finding destinations for %s ", serverRequest))
                    .flatMap(s -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.TEXT_PLAIN).bodyValue(s));
            });
    }

    @Operation(description = "Add Destination Mappings",
               requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AddDestinationMappingRequest.class))),
               operationId = "addDestinationMapping",
               tags = "DestinationMappings",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(schema = @Schema(implementation = DestinationMappingResponse.class))),
               parameters = {@Parameter(in = ParameterIn.PATH,
                                        name = "tenantId")})
    public Mono<ServerResponse> addDestinationMapping(ServerRequest serverRequest) {
        return configService.addDestinationMapping(serverRequest.bodyToMono(AddDestinationMappingRequest.class), serverRequest.pathVariable("tenantId"))
            .flatMap(dt -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dt));
    }

    @Operation(description = "Get Destination by tenantId and destinationId",
               operationId = "getDestinationByTenantIdAndDestinationId",
               tags = "Destinations",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = DestinationResponse.class))), @ApiResponse(responseCode = "404")},
               parameters = {@Parameter(in = ParameterIn.PATH,
                                        name = "tenantId"), @Parameter(in = ParameterIn.PATH,
                                                                       name = "destinationId")})
    public Mono<ServerResponse> getDestination(ServerRequest serverRequest) {
        Long destinationId = Long.valueOf(serverRequest.pathVariable("destinationId"));
        String tenantId = serverRequest.pathVariable("tenantId");
        return configService.findDestinationById(destinationId, tenantId)
            .flatMap(de -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(de))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Operation(description = "ADd Destination for tenant",
               operationId = "addDestination",
               tags = "Destinations",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = DestinationResponse.class)))},
               requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AddDestinationRequest.class))),
               parameters = {@Parameter(in = ParameterIn.PATH,
                                        name = "tenantId")})
    public Mono<ServerResponse> addDestination(ServerRequest serverRequest) {
        return configService.addDestination(serverRequest.bodyToMono(AddDestinationRequest.class), serverRequest.pathVariable("tenantId"))
            .flatMap(dt -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dt));
    }

    @Operation(description = "Get all destinations for tenant",
               operationId = "findDestinationsForTenant",
               tags = "Destinations",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(array = @ArraySchema(schema = @Schema(implementation = DestinationResponse.class))))},
               parameters = {@Parameter(in = ParameterIn.PATH,
                                        name = "tenantId")})
    public Mono<ServerResponse> findDestinationsForTenant(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(configService.findDestinationForTenants(serverRequest.pathVariable("tenantId")), DestinationResponse.class);
    }

    @Operation(description = "Delete Destination Mappings",
               operationId = "deleteDestinationMapping",
               tags = "DestinationMappings",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(schema = @Schema(implementation = DeleteDestinationMappingResponse.class))),
               parameters = {@Parameter(in = ParameterIn.PATH,
                                        name = "tenantId"), @Parameter(in = ParameterIn.PATH,
                                                                       name = "destMappingId")})
    public Mono<ServerResponse> deleteDestinationMapping(ServerRequest serverRequest) {
        return configService.deleteDestinationMapping(serverRequest.pathVariable("destMappingId"))
            .flatMap(dstM -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .bodyValue(DeleteDestinationMappingResponseBuilder.aDeleteDestinationMappingResponse()
                    .withMessage("Delete successful")
                    .withDeletedMapping(DestinationMappingResponseBuilder.aDestinationMappingResponse()
                        .withDestinationMappingId(dstM.getDestinationMappingId()).withEventType(dstM.getEventType())
                        .withTenantId(dstM.getTenantId()).withDomainId(dstM.getDomainId())
                        .withSubdomainId(dstM.getSubdomainId())
                        .withDestinationInfo(DestinationResponseBuilder.aDestinationResponse()
                            .withDestination(dstM.getDestination()).withDestinationId(dstM.getDestinationId())
                            .withTenantId(dstM.getTenantId()).build()).withStatus(dstM.getStatus().toString()).build())
                    .build())).switchIfEmpty(ServerResponse.notFound().build()).onErrorResume(e -> {
                logger.error("error in processing delete destination mapping request for destinationMappingId {}", serverRequest.pathVariable("destMappingId"), e);
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            });
    }
}
