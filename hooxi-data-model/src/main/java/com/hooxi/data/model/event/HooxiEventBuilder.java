package com.hooxi.data.model.event;

import java.util.Map;

public final class HooxiEventBuilder {
  private String tenantId;
  private String domainId;
  private String subdomainId;
  private String internalEventId;
  private String externalEventId;
  private String eventType;
  private String eventSource;
  private Long timestamp;
  private String payload;
  private Map<String, String> headers;
  private EventStatus status;

  private HooxiEventBuilder() {}

  public static HooxiEventBuilder aHooxiEvent() {
    return new HooxiEventBuilder();
  }

  public HooxiEventBuilder withTenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public HooxiEventBuilder withDomainId(String domainId) {
    this.domainId = domainId;
    return this;
  }

  public HooxiEventBuilder withSubdomainId(String subdomainId) {
    this.subdomainId = subdomainId;
    return this;
  }

  public HooxiEventBuilder withInternalEventId(String internalEventId) {
    this.internalEventId = internalEventId;
    return this;
  }

  public HooxiEventBuilder withExternalEventId(String externalEventId) {
    this.externalEventId = externalEventId;
    return this;
  }

  public HooxiEventBuilder withEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public HooxiEventBuilder withEventSource(String eventSource) {
    this.eventSource = eventSource;
    return this;
  }

  public HooxiEventBuilder withTimestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public HooxiEventBuilder withPayload(String payload) {
    this.payload = payload;
    return this;
  }

  public HooxiEventBuilder withHeaders(Map<String, String> headers) {
    this.headers = headers;
    return this;
  }

  public HooxiEventBuilder withStatus(EventStatus status) {
    this.status = status;
    return this;
  }

  public HooxiEvent build() {
    HooxiEvent hooxiEvent = new HooxiEvent();
    hooxiEvent.setTenantId(tenantId);
    hooxiEvent.setDomainId(domainId);
    hooxiEvent.setSubdomainId(subdomainId);
    hooxiEvent.setInternalEventId(internalEventId);
    hooxiEvent.setExternalEventId(externalEventId);
    hooxiEvent.setEventType(eventType);
    hooxiEvent.setEventSource(eventSource);
    hooxiEvent.setTimestamp(timestamp);
    hooxiEvent.setPayload(payload);
    hooxiEvent.setHeaders(headers);
    hooxiEvent.setStatus(status);
    return hooxiEvent;
  }
}
