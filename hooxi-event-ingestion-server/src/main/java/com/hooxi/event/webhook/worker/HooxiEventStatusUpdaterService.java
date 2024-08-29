package com.hooxi.event.webhook.worker;

import com.hooxi.event.ingestion.data.model.EventStatus;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.ingestion.data.model.WebhookEventMapping;
import com.hooxi.event.ingestion.data.model.WebhookLogEntity;
import com.hooxi.event.ingestion.data.repository.HooxiEventRepository;
import com.hooxi.event.ingestion.data.repository.WebhookEventMappingRepository;
import com.hooxi.event.ingestion.data.repository.WebhookFailureLogRepository;
import com.hooxi.event.webhook.worker.data.EventWithWenhookEventMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
public class HooxiEventStatusUpdaterService {

  private static final Logger logger =
      LoggerFactory.getLogger(HooxiEventStatusUpdaterService.class);

  private final HooxiEventRepository hooxiEventRepository;

  private final WebhookEventMappingRepository webhookEventMappingRepository;
  private final WebhookFailureLogRepository webhookFailureLogRepository;

  public HooxiEventStatusUpdaterService(
      HooxiEventRepository hooxiEventRepository,
      WebhookEventMappingRepository webhookEventMappingRepository,
      WebhookFailureLogRepository webhookFailureLogRepository) {
    this.hooxiEventRepository = hooxiEventRepository;
    this.webhookEventMappingRepository = webhookEventMappingRepository;
    this.webhookFailureLogRepository = webhookFailureLogRepository;
  }

  @Transactional
  public Mono<EventWithWenhookEventMapping> updateHooxiEventStatus(
      String eventId, Long destinationId, EventStatus status) {
    return hooxiEventRepository
        .findById(eventId)
        .zipWith(
            webhookEventMappingRepository.findByInternalEventIdAndWebhookDestinationId(
                eventId, destinationId),
            EventWithWenhookEventMapping::new)
        .flatMap(
            eventWithWenhookEventMapping -> {
              HooxiEventEntity he = eventWithWenhookEventMapping.getHooxiEventEntity();
              WebhookEventMapping wem = eventWithWenhookEventMapping.getWebhookEventMapping();
              he.setStatus(status);
              wem.setEventStatus(status);
              return hooxiEventRepository
                  .save(he)
                  .zipWith(
                      webhookEventMappingRepository.updateWebhookEventMappingStatus(
                          wem.getInternalEventId(),
                          wem.getWebhookDestinationId(),
                          status.toString()),
                      (t1, t2) -> new EventWithWenhookEventMapping(he, wem));
            });
  }

  public Mono<HooxiEventEntity> findHooxiEventEntity(String eventId) {
    logger.debug("fetching event from db " + eventId);
    return hooxiEventRepository.findById(eventId);
  }

  public Mono<WebhookLogEntity> saveWebhookLog(
      WebhookLogEntity webhookLogEntity) {
    return webhookFailureLogRepository.save(webhookLogEntity);
  }
}
