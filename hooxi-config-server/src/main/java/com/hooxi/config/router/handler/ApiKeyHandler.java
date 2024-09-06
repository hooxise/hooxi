package com.hooxi.config.router.handler;

import com.hooxi.config.service.ApiKeyService;
import com.hooxi.data.model.apikey.CreateApiKeyRequest;
import com.hooxi.data.model.apikey.InactivateApiKeyRequestBuilder;
import com.hooxi.data.model.apikey.ValidateApiKeyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyHandler.class);

  private final ApiKeyService apiKeyService;

  @Autowired
  public ApiKeyHandler(ApiKeyService apiKeyService) {
    this.apiKeyService = apiKeyService;
  }

  public Mono<ServerResponse> createApiKey(ServerRequest serverRequest) {
    return apiKeyService
        .createApiKey(serverRequest.bodyToMono(CreateApiKeyRequest.class))
        .flatMap(
            apiKeyResponse ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(apiKeyResponse));
  }

  public Mono<ServerResponse> inactivateApiKey(ServerRequest serverRequest) {
    return apiKeyService
        .inactivateApiKey(
            Mono.just(
                InactivateApiKeyRequestBuilder.anInactivateApiKeyRequest()
                    .withApiKeyId(Long.valueOf(serverRequest.pathVariable("apiKeyId")))
                    .build()))
        .flatMap(apiKeyEntity -> ServerResponse.ok().build())
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> validateApiKey(ServerRequest serverRequest) {
    return apiKeyService
        .validateApiKey(serverRequest.bodyToMono(ValidateApiKeyRequest.class))
        .flatMap(httpStatus -> ServerResponse.status(httpStatus).build())
        .switchIfEmpty(ServerResponse.status(401).build());
  }
}
