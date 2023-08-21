package com.hooxi.data.model.config;

public final class AddDestinationMappingRequestBuilder {
    private Long destinationId;
    private String domainId;
    private String subdomainId;
    private String eventType;
    private String status;

    private AddDestinationMappingRequestBuilder() {
    }

    public static AddDestinationMappingRequestBuilder anAddDestinationMappingRequest() {
        return new AddDestinationMappingRequestBuilder();
    }

    public AddDestinationMappingRequestBuilder withDestinationId(Long destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public AddDestinationMappingRequestBuilder withDomainId(String domainId) {
        this.domainId = domainId;
        return this;
    }

    public AddDestinationMappingRequestBuilder withSubdomainId(String subdomainId) {
        this.subdomainId = subdomainId;
        return this;
    }

    public AddDestinationMappingRequestBuilder withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public AddDestinationMappingRequestBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public AddDestinationMappingRequest build() {
        AddDestinationMappingRequest addDestinationMappingRequest = new AddDestinationMappingRequest();
        addDestinationMappingRequest.setDestinationId(destinationId);
        addDestinationMappingRequest.setDomainId(domainId);
        addDestinationMappingRequest.setSubdomainId(subdomainId);
        addDestinationMappingRequest.setEventType(eventType);
        addDestinationMappingRequest.setStatus(status);
        return addDestinationMappingRequest;
    }
}
