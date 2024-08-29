package com.hooxi.event.ingestion.data.repository;

import com.hooxi.event.ingestion.data.model.WebhookFailureLogEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookFailureLogRepository
    extends ReactiveCrudRepository<WebhookFailureLogEntity, Long> {}
