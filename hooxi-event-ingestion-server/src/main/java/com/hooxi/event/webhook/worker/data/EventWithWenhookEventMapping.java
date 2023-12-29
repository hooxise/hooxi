package com.hooxi.event.webhook.worker.data;

import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.ingestion.data.model.WebhookEventMapping;

public class EventWithWenhookEventMapping {
  private HooxiEventEntity hooxiEventEntity;
  private WebhookEventMapping webhookEventMapping;

  public EventWithWenhookEventMapping(
      HooxiEventEntity hooxiEventEntity, WebhookEventMapping webhookEventMapping) {
    this.hooxiEventEntity = hooxiEventEntity;
    this.webhookEventMapping = webhookEventMapping;
  }

  public HooxiEventEntity getHooxiEventEntity() {
    return hooxiEventEntity;
  }

  public void setHooxiEventEntity(HooxiEventEntity hooxiEventEntity) {
    this.hooxiEventEntity = hooxiEventEntity;
  }

  public WebhookEventMapping getWebhookEventMapping() {
    return webhookEventMapping;
  }

  public void setWebhookEventMapping(WebhookEventMapping webhookEventMapping) {
    this.webhookEventMapping = webhookEventMapping;
  }
}
