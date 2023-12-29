package com.hooxi.data.model.config;

import java.util.List;

public final class FindDestinationsResponseBuilder {
  List<DestinationMappingResponse> destinationMappings;

  private FindDestinationsResponseBuilder() {}

  public static FindDestinationsResponseBuilder aFindDestinationsResponse() {
    return new FindDestinationsResponseBuilder();
  }

  public FindDestinationsResponseBuilder withDestinationMappings(
      List<DestinationMappingResponse> destinationMappings) {
    this.destinationMappings = destinationMappings;
    return this;
  }

  public FindDestinationsResponse build() {
    FindDestinationsResponse findDestinationsResponse = new FindDestinationsResponse();
    findDestinationsResponse.setDestinationMappings(destinationMappings);
    return findDestinationsResponse;
  }
}
