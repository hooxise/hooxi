package com.hooxi.data.model.config;

public final class FindDestinationsRequestBuilder {
  private String tenantId;
  private String eventType;
  private String domain;
  private String subDomain;

  private FindDestinationsRequestBuilder() {}

  public static FindDestinationsRequestBuilder aFindDestinationsRequest() {
    return new FindDestinationsRequestBuilder();
  }

  public FindDestinationsRequestBuilder withTenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public FindDestinationsRequestBuilder withEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public FindDestinationsRequestBuilder withDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public FindDestinationsRequestBuilder withSubDomain(String subDomain) {
    this.subDomain = subDomain;
    return this;
  }

  public FindDestinationsRequest build() {
    FindDestinationsRequest findDestinationsRequest = new FindDestinationsRequest();
    findDestinationsRequest.setTenantId(tenantId);
    findDestinationsRequest.setEventType(eventType);
    findDestinationsRequest.setDomain(domain);
    findDestinationsRequest.setSubDomain(subDomain);
    return findDestinationsRequest;
  }
}
