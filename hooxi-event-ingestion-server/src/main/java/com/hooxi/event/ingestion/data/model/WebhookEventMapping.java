package com.hooxi.event.ingestion.data.model;

import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("webhook_event_mapping")
public class WebhookEventMapping {
  @Column("internal_event_id")
  private String internalEventId;

  @Column("webhook_destination_id")
  private Long webhookDestinationId;

  @Column("event_status")
  private EventStatus eventStatus;

  @Version private Integer version;

  public String getInternalEventId() {
    return internalEventId;
  }

  public void setInternalEventId(String internalEventId) {
    this.internalEventId = internalEventId;
  }

  public Long getWebhookDestinationId() {
    return webhookDestinationId;
  }

  public void setWebhookDestinationId(Long webhookDestinationId) {
    this.webhookDestinationId = webhookDestinationId;
  }

  public EventStatus getEventStatus() {
    return eventStatus;
  }

  public void setEventStatus(EventStatus eventStatus) {
    this.eventStatus = eventStatus;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String toString() {
    return "internalEventId " + internalEventId + " webhook id " + webhookDestinationId;
  }
}
