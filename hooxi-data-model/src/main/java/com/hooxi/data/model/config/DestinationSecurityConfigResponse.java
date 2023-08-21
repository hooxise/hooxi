package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.security.DestinationSecurityConfig;

public class DestinationSecurityConfigResponse {
    private Long destinationId;
    private DestinationSecurityConfig destinationSecurityConfig;

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public DestinationSecurityConfig getDestinationSecurityConfig() {
        return destinationSecurityConfig;
    }

    public void setDestinationSecurityConfig(DestinationSecurityConfig destinationSecurityConfig) {
        this.destinationSecurityConfig = destinationSecurityConfig;
    }
}
