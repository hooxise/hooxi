package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.dest.WebhookDestination;
import io.swagger.v3.oas.annotations.media.Schema;

public class DestinationResponse {
    private Long destinationId;
    private String tenantId;
    @Schema(oneOf = {WebhookDestination.class})
    private Destination destination;

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
