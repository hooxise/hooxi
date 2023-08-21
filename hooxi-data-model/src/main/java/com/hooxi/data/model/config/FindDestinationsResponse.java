package com.hooxi.data.model.config;

import java.util.List;

public class FindDestinationsResponse {
    List<DestinationMappingResponse> destinationMappings;

    public List<DestinationMappingResponse> getDestinationMappings() {
        return destinationMappings;
    }

    public void setDestinationMappings(List<DestinationMappingResponse> destinationMappings) {
        this.destinationMappings = destinationMappings;
    }
}