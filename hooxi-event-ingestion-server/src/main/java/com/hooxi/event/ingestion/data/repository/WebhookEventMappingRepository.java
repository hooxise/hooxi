package com.hooxi.event.ingestion.data.repository;

import com.hooxi.event.ingestion.data.model.WebhookEventMapping;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WebhookEventMappingRepository
    extends ReactiveCrudRepository<WebhookEventMapping, Long> {

  Mono<WebhookEventMapping> findByInternalEventIdAndWebhookDestinationId(
      String internalEventId, Long webhookDestinationId);

  @Modifying
  @Query(
      "update webhook_event_mapping set event_status = :status where internal_event_id = :internalEventId and webhook_destination_id = :webhookDestinationId")
  Mono<Long> updateWebhookEventMappingStatus(
      String internalEventId, Long webhookDestinationId, String status);
}
