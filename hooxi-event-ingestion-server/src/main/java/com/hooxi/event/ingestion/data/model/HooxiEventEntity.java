package com.hooxi.event.ingestion.data.model;

import com.hooxi.data.model.event.HooxiEvent;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("hooxi_event")
public class HooxiEventEntity {
  @Column("tenant_id")
  private String tenantId;

  @Column("domain_id")
  private String domainId;

  @Column("subdomain_id")
  private String subdomainId;

  @Id
  @Column("internal_event_id")
  private String internalEventId;

  @Column("external_event_id")
  private String externalEventId;

  @Column("event_type")
  private String eventType;

  @Column("event_source")
  private String eventSource;

  @Column("event_timestamp")
  private Long timestamp;

  @Column("payload")
  private String payload;

  @Column("headers")
  private Map<String, String> headers;

  @Column("event_status")
  private EventStatus status;

  @Version private Integer version;

  public HooxiEventEntity() {}

  public HooxiEventEntity(HooxiEvent he) {
    setInternalEventId(he.getInternalEventId());
    setEventSource(he.getEventSource());
    setEventType(he.getEventType());
    setExternalEventId(he.getExternalEventId());
    setPayload(he.getPayload());
    setStatus(EventStatus.valueOf(he.getStatus().toString()));
    setTenantId(he.getTenantId());
    setTimestamp(he.getTimestamp());
    setHeaders(he.getHeaders());
    setDomainId(he.getDomainId());
    setSubdomainId(he.getSubdomainId());
  }

  @Id
  public String getInternalEventId() {
    return this.internalEventId;
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

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public EventStatus getStatus() {
    return status;
  }

  public void setStatus(EventStatus status) {
    this.status = status;
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

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
