package com.hooxi.config.service;

import com.hooxi.config.exception.ApiException;
import com.hooxi.config.exception.ErrorCodes;
import com.hooxi.config.repository.ApiKeyRepository;
import com.hooxi.config.repository.entity.ApiKeyEntity;
import com.hooxi.data.model.apikey.CreateApiKeyRequest;
import com.hooxi.data.model.apikey.CreateApiKeyResponse;
import com.hooxi.data.model.apikey.CreateApiKeyResponseBuilder;
import com.hooxi.data.model.apikey.InactivateApiKeyRequest;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

@Component
public class ApiKeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyService.class);
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public Mono<CreateApiKeyResponse> createApiKey(Mono<CreateApiKeyRequest> apiKeyRequestMono) {
        String generatedApiKey = UUID.randomUUID().toString();
        return apiKeyRequestMono.map( req -> {
            ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
            apiKeyEntity.setApiKeyName(req.getApiKeyName());
            apiKeyEntity.setApiKeyPermissions(Strings.join(req.getApiKeyPermissions(), ','));
            apiKeyEntity.setApiKeyDescription(req.getApiKeyDescription());
            apiKeyEntity.setApiKeyExpirationTS(req.getExpirationTimestamp());
            apiKeyEntity.setActive(true);
            apiKeyEntity.setApiKeyCreateTS(System.currentTimeMillis());

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
                String apiKeyHash = new String(md.digest(generatedApiKey.getBytes()));
                apiKeyEntity.setApiKeyHash(apiKeyHash);
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("failed to generate api key", e);
                throw new ApiException("failed to generate api key", ErrorCodes.ERR_KEY_GENERATION_000, e);
            }
            return apiKeyEntity;
        }).flatMap(apiKeyRepository::save).map((apiKeyEntity) -> {
            CreateApiKeyResponse response = CreateApiKeyResponseBuilder.aCreateApiKeyResponse().withApiKeyId(apiKeyEntity.getApiKeyId())
                    .withApiKey(generatedApiKey).withApiKeyDescription(apiKeyEntity.getApiKeyDescription())
                    .withApiKeyPermissions(Arrays.asList(Strings.splitList(apiKeyEntity.getApiKeyPermissions())))
                    .withCreationTimestamp(apiKeyEntity.getApiKeyCreateTS())
                    .withExpirationTimestamp(apiKeyEntity.getApiKeyExpirationTS())
                    .build();
            return response;
        });
    }

    public Mono<ApiKeyEntity> inactivateApiKey(Mono<InactivateApiKeyRequest> inactivateApiKeyRequestMono) {
        return inactivateApiKeyRequestMono.flatMap(req -> apiKeyRepository.findById(req.getApiKeyId()))
                .flatMap( (apiKeyEntity) -> {
                        apiKeyEntity.setActive(false);
                        apiKeyEntity.setApiKeyUpdateTS(System.currentTimeMillis());
                        return apiKeyRepository.save(apiKeyEntity);
                    }
            );
    }
}
