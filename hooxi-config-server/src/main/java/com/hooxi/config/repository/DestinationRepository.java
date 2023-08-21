package com.hooxi.config.repository;

import com.hooxi.config.repository.entity.DestinationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DestinationRepository extends ReactiveCrudRepository<DestinationEntity, Long> {
    Mono<DestinationEntity> findByIdAndTenantId(Long id, String tenantId);

    Flux<DestinationEntity> findByTenantId(String tenantId);
}
