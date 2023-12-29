package com.hooxi.data.model.config;

import java.util.StringJoiner;

public class FindDestinationsRequest {
  private String tenantId;
  private String eventType;
  private String domain;
  private String subDomain;

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getSubDomain() {
    return subDomain;
  }

  public void setSubDomain(String subDomain) {
    this.subDomain = subDomain;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FindDestinationsRequest.class.getSimpleName() + "[", "]")
        .add("tenantId='" + tenantId + "'")
        .add("eventType='" + eventType + "'")
        .add("domain='" + domain + "'")
        .add("subDomain='" + subDomain + "'")
        .toString();
  }
}
