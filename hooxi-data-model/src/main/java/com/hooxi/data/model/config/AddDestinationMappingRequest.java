package com.hooxi.data.model.config;

import io.swagger.v3.oas.annotations.media.Schema;

public class AddDestinationMappingRequest {
  private Long destinationId;

  @Schema(nullable = true)
  private String domainId;

  @Schema(nullable = true)
  private String subdomainId;

  @Schema(nullable = true)
  private String eventType;

  @Schema(allowableValues = {"ACTIVE", "INACTIVE"})
  private String status;

  public Long getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(Long destinationId) {
    this.destinationId = destinationId;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
