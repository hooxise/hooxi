package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.Destination;

public final class DestinationResponseBuilder {
  private Long destinationId;
  private String tenantId;
  private Destination destination;

  private DestinationResponseBuilder() {}

  public static DestinationResponseBuilder aDestinationResponse() {
    return new DestinationResponseBuilder();
  }

  public DestinationResponseBuilder withDestinationId(Long destinationId) {
    this.destinationId = destinationId;
    return this;
  }

  public DestinationResponseBuilder withTenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public DestinationResponseBuilder withDestination(Destination destination) {
    this.destination = destination;
    return this;
  }

  public DestinationResponse build() {
    DestinationResponse destinationResponse = new DestinationResponse();
    destinationResponse.setDestinationId(destinationId);
    destinationResponse.setTenantId(tenantId);
    destinationResponse.setDestination(destination);
    return destinationResponse;
  }
}
