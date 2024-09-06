package com.hooxi.config.repository;

import com.hooxi.config.repository.entity.ApiKeyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ApiKeyRepository extends ReactiveCrudRepository<ApiKeyEntity, Long> {
  Mono<ApiKeyEntity> findByApiKeyHash(String apiKeyHash);

  Mono<ApiKeyEntity> findByApiKeyHashAndApiKeyPermissionsContaining(String s, String operation);
}
