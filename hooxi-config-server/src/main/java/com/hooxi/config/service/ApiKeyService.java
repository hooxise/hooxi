package com.hooxi.config.service;

import com.hooxi.config.repository.ApiKeyRepository;
import com.hooxi.config.repository.entity.ApiKeyEntity;
import com.hooxi.data.model.apikey.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyService.class);
  private final ApiKeyRepository apiKeyRepository;
  private final MessageDigest messageDigest;

  public ApiKeyService(ApiKeyRepository apiKeyRepository) throws NoSuchAlgorithmException {
    this.apiKeyRepository = apiKeyRepository;
    this.messageDigest = MessageDigest.getInstance("MD5");
  }

  public Mono<CreateApiKeyResponse> createApiKey(Mono<CreateApiKeyRequest> apiKeyRequestMono) {
    String generatedApiKey = UUID.randomUUID().toString();
    return apiKeyRequestMono
        .map(
            req -> {
              ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
              apiKeyEntity.setApiKeyName(req.getApiKeyName());
              apiKeyEntity.setApiKeyPermissions(Strings.join(req.getApiKeyPermissions(), ','));
              apiKeyEntity.setApiKeyDescription(req.getApiKeyDescription());
              apiKeyEntity.setApiKeyExpirationTS(req.getExpirationTimestamp());
              apiKeyEntity.setActive(true);
              apiKeyEntity.setApiKeyCreateTS(System.currentTimeMillis());

              String apiKeyHash = new String(messageDigest.digest(generatedApiKey.getBytes()));
              apiKeyEntity.setApiKeyHash(apiKeyHash);
              return apiKeyEntity;
            })
        .flatMap(apiKeyRepository::save)
        .map(
            (apiKeyEntity) -> {
              CreateApiKeyResponse response =
                  CreateApiKeyResponseBuilder.aCreateApiKeyResponse()
                      .withApiKeyId(apiKeyEntity.getApiKeyId())
                      .withApiKey(generatedApiKey)
                      .withApiKeyDescription(apiKeyEntity.getApiKeyDescription())
                      .withApiKeyPermissions(
                          Arrays.asList(Strings.splitList(apiKeyEntity.getApiKeyPermissions())))
                      .withCreationTimestamp(apiKeyEntity.getApiKeyCreateTS())
                      .withExpirationTimestamp(apiKeyEntity.getApiKeyExpirationTS())
                      .build();
              return response;
            });
  }

  public Mono<ApiKeyEntity> inactivateApiKey(
      Mono<InactivateApiKeyRequest> inactivateApiKeyRequestMono) {
    return inactivateApiKeyRequestMono
        .flatMap(req -> apiKeyRepository.findById(req.getApiKeyId()))
        .flatMap(
            (apiKeyEntity) -> {
              apiKeyEntity.setActive(false);
              apiKeyEntity.setApiKeyUpdateTS(System.currentTimeMillis());
              return apiKeyRepository.save(apiKeyEntity);
            });
  }

  public Mono<Integer> validateApiKey(Mono<ValidateApiKeyRequest> validateApiKeyRequestMono) {
    return validateApiKeyRequestMono.flatMap(
        req ->
            apiKeyRepository
                .findByApiKeyHash(new String(messageDigest.digest(req.getApiKey().getBytes())))
                .map(
                    (apiKeyEntity) -> {
                      String permissions = apiKeyEntity.getApiKeyPermissions();
                      String permissionToCheck = req.getOperation().trim();
                      boolean checkRO = permissionToCheck.endsWith("RO");
                      if (permissions != null) {
                        String[] permissionsArr = permissions.split(",");
                        boolean hasPermission =
                            Arrays.stream(permissionsArr)
                                .anyMatch(
                                    perm ->
                                        perm.equals(permissionToCheck)
                                            || (checkRO && permissionToCheck.startsWith(perm)));
                        if (hasPermission) {
                          return 200;
                        } else {
                          return 403;
                        }
                      }
                      return 403;
                    })
                .switchIfEmpty(Mono.just(401)));
  }
}
