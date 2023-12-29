package com.hooxi.data.model.config;

public class DestinationMappingResponse {
  private Long destinationMappingId;
  private String tenantId;
  private String domainId;
  private String subdomainId;
  private String eventType;
  private DestinationResponse destinationInfo;
  private String status;

  public Long getDestinationMappingId() {
    return destinationMappingId;
  }

  public void setDestinationMappingId(Long destinationMappingId) {
    this.destinationMappingId = destinationMappingId;
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

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public DestinationResponse getDestinationInfo() {
    return destinationInfo;
  }

  public void setDestinationInfo(DestinationResponse destinationInfo) {
    this.destinationInfo = destinationInfo;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
