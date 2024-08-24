package com.hooxi.config.router;

import com.hooxi.config.router.handler.ApiKeyHandler;
import com.hooxi.config.router.handler.ConfigServiceHandler;
import com.hooxi.data.model.apikey.CreateApiKeyResponse;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class ApiKeyRouter {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    method = RequestMethod.POST,
                    path = "/apikeys",
                    beanClass = ApiKeyHandler.class,
                    beanMethod = "createApiKey",
                    operation =
                    @Operation(
                            description = "Create Api Key",
                            operationId = "createApiKey",
                            tags = "ApiKeys",
                            responses =
                            @ApiResponse(
                                    responseCode = "200",
                                    content =
                                    @Content(
                                            schema = @Schema(implementation = CreateApiKeyResponse.class))))),
            @RouterOperation(
                    method = RequestMethod.POST,
                    path = "/apikeys/{apiKeyId}/inactivate",
                    beanClass = ApiKeyHandler.class,
                    beanMethod = "inactivateApiKey",
                    operation =
                    @Operation(
                            description = "Inactivate Api Key",
                            operationId = "inactivateApiKey",
                            tags = "ApiKeys",
                            responses =
                            @ApiResponse(
                                    responseCode = "200")))
    })
    public RouterFunction<ServerResponse> createApiKey(
            ApiKeyHandler apiKeyHandler) {

        return RouterFunctions.route(
                POST("/apikeys").and(accept(MediaType.APPLICATION_JSON)),
                        apiKeyHandler::createApiKey).andRoute(
                POST("/apikeys/{apiKeyId}/inactivate").and(accept(MediaType.APPLICATION_JSON)),
                apiKeyHandler::inactivateApiKey);

    }
}
