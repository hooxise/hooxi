package com.hooxi.config.repository;

import com.hooxi.config.repository.entity.ApiKeyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends ReactiveCrudRepository<ApiKeyEntity, Long> {}
