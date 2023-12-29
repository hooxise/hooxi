package com.hooxi.data.model.event;

import java.io.Serializable;
import java.util.Map;

public class HooxiEvent implements Serializable {
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

  public String getInternalEventId() {
    return internalEventId;
  }

  public void setInternalEventId(String internalEventId) {
    this.internalEventId = internalEventId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getExternalEventId() {
    return externalEventId;
  }

  public void setExternalEventId(String externalEventId) {
    this.externalEventId = externalEventId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getEventSource() {
    return eventSource;
  }

  public void setEventSource(String eventSource) {
    this.eventSource = eventSource;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public EventStatus getStatus() {
    return status;
  }

  public void setStatus(EventStatus status) {
    this.status = status;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getSubdomainId() {
    return subdomainId;
  }

  public void setSubdomainId(String subdomainId) {
    this.subdomainId = subdomainId;
  }

  @Override
  public String toString() {
    return "HooxiEvent{"
        + "tenant='"
        + tenantId
        + '\''
        + ", internalEventId='"
        + internalEventId
        + '\''
        + ", externalEventId='"
        + externalEventId
        + '\''
        + ", eventType='"
        + eventType
        + '\''
        + ", eventSource='"
        + eventSource
        + '\''
        + '\''
        + ", timestamp='"
        + timestamp
        + '\''
        + ", payload='"
        + payload
        + '\''
        + ", headers="
        + headers
        + '\''
        + '}';
  }
}
