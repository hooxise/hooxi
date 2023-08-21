package com.hooxi.data.model.config;

public final class DestinationMappingResponseBuilder {
    private Long destinationMappingId;
    private String tenantId;
    private String domainId;
    private String subdomainId;
    private String eventType;
    private DestinationResponse destinationInfo;
    private String status;

    private DestinationMappingResponseBuilder() {
    }

    public static DestinationMappingResponseBuilder aDestinationMappingResponse() {
        return new DestinationMappingResponseBuilder();
    }

    public DestinationMappingResponseBuilder withDestinationMappingId(Long destinationMappingId) {
        this.destinationMappingId = destinationMappingId;
        return this;
    }

    public DestinationMappingResponseBuilder withTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public DestinationMappingResponseBuilder withDomainId(String domainId) {
        this.domainId = domainId;
        return this;
    }

    public DestinationMappingResponseBuilder withSubdomainId(String subdomainId) {
        this.subdomainId = subdomainId;
        return this;
    }

    public DestinationMappingResponseBuilder withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public DestinationMappingResponseBuilder withDestinationInfo(DestinationResponse destinationInfo) {
        this.destinationInfo = destinationInfo;
        return this;
    }

    public DestinationMappingResponseBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public DestinationMappingResponse build() {
        DestinationMappingResponse destinationMappingResponse = new DestinationMappingResponse();
        destinationMappingResponse.setDestinationMappingId(destinationMappingId);
        destinationMappingResponse.setTenantId(tenantId);
        destinationMappingResponse.setDomainId(domainId);
        destinationMappingResponse.setSubdomainId(subdomainId);
        destinationMappingResponse.setEventType(eventType);
        destinationMappingResponse.setDestinationInfo(destinationInfo);
        destinationMappingResponse.setStatus(status);
        return destinationMappingResponse;
    }
}
