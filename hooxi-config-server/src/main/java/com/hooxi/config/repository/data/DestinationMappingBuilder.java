package com.hooxi.config.repository.data;

import com.hooxi.data.model.dest.Destination;

public final class DestinationMappingBuilder {
    private Long id;
    private String tenantId;
    private String domainId;
    private String subdomainId;
    private String eventType;
    private Long destinationId;
    private Destination destination;
    private DestinationMappingStatus status;

    private DestinationMappingBuilder() {
    }

    public static DestinationMappingBuilder aDestinationMapping() {
        return new DestinationMappingBuilder();
    }

    public DestinationMappingBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DestinationMappingBuilder withTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public DestinationMappingBuilder withDomainId(String domainId) {
        this.domainId = domainId;
        return this;
    }

    public DestinationMappingBuilder withSubdomainId(String subdomainId) {
        this.subdomainId = subdomainId;
        return this;
    }

    public DestinationMappingBuilder withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public DestinationMappingBuilder withDestinationId(Long destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public DestinationMappingBuilder withDestination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public DestinationMappingBuilder withStatus(DestinationMappingStatus status) {
        this.status = status;
        return this;
    }

    public DestinationMapping build() {
        DestinationMapping destinationMapping = new DestinationMapping();
        destinationMapping.setDestinationMappingId(id);
        destinationMapping.setTenantId(tenantId);
        destinationMapping.setDomainId(domainId);
        destinationMapping.setSubdomainId(subdomainId);
        destinationMapping.setEventType(eventType);
        destinationMapping.setDestinationId(destinationId);
        destinationMapping.setDestination(destination);
        destinationMapping.setStatus(status);
        return destinationMapping;
    }
}
