package com.hooxi.config.repository.entity;

import com.hooxi.config.repository.data.DestinationMappingStatus;
import com.hooxi.data.model.dest.Destination;

public final class DestinationCacheEntityBuilder {
  private Long destinationId;
  private Long destinationMappingId;
  private Destination destination;
  private DestinationMappingStatus status;
  private String tenantId;
  private String domainId;
  private String subdomainId;
  private String eventType;

  private DestinationCacheEntityBuilder() {}

  public static DestinationCacheEntityBuilder aDestinationCacheEntity() {
    return new DestinationCacheEntityBuilder();
  }

  public DestinationCacheEntityBuilder withDestinationId(Long destinationId) {
    this.destinationId = destinationId;
    return this;
  }

  public DestinationCacheEntityBuilder withDestinationMappingId(Long destinationMappingId) {
    this.destinationMappingId = destinationMappingId;
    return this;
  }

  public DestinationCacheEntityBuilder withDestination(Destination destination) {
    this.destination = destination;
    return this;
  }

  public DestinationCacheEntityBuilder withStatus(DestinationMappingStatus status) {
    this.status = status;
    return this;
  }

  public DestinationCacheEntityBuilder withTenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public DestinationCacheEntityBuilder withDomainId(String domainId) {
    this.domainId = domainId;
    return this;
  }

  public DestinationCacheEntityBuilder withSubdomainId(String subdomainId) {
    this.subdomainId = subdomainId;
    return this;
  }

  public DestinationCacheEntityBuilder withEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public DestinationCacheEntity build() {
    DestinationCacheEntity destinationCacheEntity = new DestinationCacheEntity();
    destinationCacheEntity.setDestinationId(destinationId);
    destinationCacheEntity.setDestinationMappingId(destinationMappingId);
    destinationCacheEntity.setDestination(destination);
    destinationCacheEntity.setStatus(status);
    destinationCacheEntity.setTenantId(tenantId);
    destinationCacheEntity.setDomainId(domainId);
    destinationCacheEntity.setSubdomainId(subdomainId);
    destinationCacheEntity.setEventType(eventType);
    return destinationCacheEntity;
  }
}
