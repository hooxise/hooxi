package com.hooxi.config.repository.entity;

import com.hooxi.config.repository.data.DestinationMappingStatus;
import com.hooxi.data.model.dest.Destination;

public class DestinationCacheEntity {
  private Long destinationId;
  private Long destinationMappingId;
  private Destination destination;
  private DestinationMappingStatus status;
  private String tenantId;
  private String domainId;
  private String subdomainId;
  private String eventType;

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

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public Long getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(Long destinationId) {
    this.destinationId = destinationId;
  }

  public Destination getDestination() {
    return destination;
  }

  public void setDestination(Destination destination) {
    this.destination = destination;
  }

  public DestinationMappingStatus getStatus() {
    return status;
  }

  public void setStatus(DestinationMappingStatus status) {
    this.status = status;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public Long getDestinationMappingId() {
    return destinationMappingId;
  }

  public void setDestinationMappingId(Long destinationMappingId) {
    this.destinationMappingId = destinationMappingId;
  }
}
