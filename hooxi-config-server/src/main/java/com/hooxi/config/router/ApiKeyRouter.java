package com.hooxi.config.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.hooxi.config.router.handler.ApiKeyHandler;
import com.hooxi.data.model.apikey.CreateApiKeyRequest;
import com.hooxi.data.model.apikey.CreateApiKeyResponse;
import com.hooxi.data.model.apikey.ValidateApiKeyRequest;
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

@Configuration
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
                requestBody =
                    @RequestBody(
                        content =
                            @Content(schema = @Schema(implementation = CreateApiKeyRequest.class))),
                responses =
                    @ApiResponse(
                        responseCode = "200",
                        content =
                            @Content(
                                schema = @Schema(implementation = CreateApiKeyResponse.class))))),
    @RouterOperation(
        method = RequestMethod.POST,
        path = "/apikeys/validate",
        beanClass = ApiKeyHandler.class,
        beanMethod = "validateApiKey",
        operation =
            @Operation(
                description = "Validate Api Key",
                operationId = "validateApiKey",
                tags = "ApiKeys",
                requestBody =
                    @RequestBody(
                        content =
                            @Content(
                                schema = @Schema(implementation = ValidateApiKeyRequest.class))),
                responses = {
                  @ApiResponse(responseCode = "200"),
                  @ApiResponse(responseCode = "401"),
                  @ApiResponse(responseCode = "403")
                })),
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
                parameters = {
                  @Parameter(
                      in = ParameterIn.PATH,
                      name = "apiKeyId",
                      schema = @Schema(type = "string"))
                },
                responses = @ApiResponse(responseCode = "200")))
  })
  public RouterFunction<ServerResponse> createApiKey(ApiKeyHandler apiKeyHandler) {

    return RouterFunctions.route(
            POST("/apikeys").and(accept(MediaType.APPLICATION_JSON)), apiKeyHandler::createApiKey)
        .andRoute(
            POST("/apikeys/{apiKeyId}/inactivate").and(accept(MediaType.APPLICATION_JSON)),
            apiKeyHandler::inactivateApiKey)
        .andRoute(
            POST("/apikeys/validate").and(accept(MediaType.APPLICATION_JSON)),
            apiKeyHandler::validateApiKey);
  }
}
