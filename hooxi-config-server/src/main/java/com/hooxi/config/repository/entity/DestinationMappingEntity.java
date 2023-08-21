package com.hooxi.config.repository.entity;

import com.hooxi.config.repository.data.DestinationMappingStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("hooxi_destination_config")
public class DestinationMappingEntity {
    @Id
    private Long id;
    @Column("tenant_id")
    private String tenantId;
    @Column("domain_id")
    private String domainId;
    @Column("subdomain_id")
    private String subdomainId;
    @Column("event_type")
    private String eventType;
    @Column("destination_id")
    private Long destinationId;
    @Column("status")
    private DestinationMappingStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }


    public DestinationMappingStatus getStatus() {
        return status;
    }

    public void setStatus(DestinationMappingStatus status) {
        this.status = status;
    }
}
