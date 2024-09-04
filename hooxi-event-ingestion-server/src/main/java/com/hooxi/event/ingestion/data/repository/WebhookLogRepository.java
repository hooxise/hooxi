package com.hooxi.event.ingestion.data.repository;

import com.hooxi.event.ingestion.data.model.WebhookLogEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WebhookLogRepository extends ReactiveCrudRepository<WebhookLogEntity, Long> {
  Flux<WebhookLogEntity> findByInternalEventId(String internalEventId);
}
