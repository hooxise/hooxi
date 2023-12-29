package com.hooxi.config.repository.data;

import com.hooxi.data.model.dest.Destination;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.relational.core.mapping.Column;

public class DestinationMapping {
  @Column("destmappingid")
  private Long destinationMappingId;

  private String tenantId;

  @Column("domain_id")
  private String domainId;

  @Column("subdomain_id")
  private String subdomainId;

  @Column("event_type")
  private String eventType;

  @Column("destination_id")
  private Long destinationId;

  @Column("destination")
  private Destination destination;

  @Column("status")
  private DestinationMappingStatus status;

  public Long getDestinationMappingId() {
    return destinationMappingId;
  }

  public void setDestinationMappingId(Long destinationMappingId) {
    this.destinationMappingId = destinationMappingId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
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

  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
