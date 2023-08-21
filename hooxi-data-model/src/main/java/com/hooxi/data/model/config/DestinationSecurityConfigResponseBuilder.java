package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.security.DestinationSecurityConfig;

public final class DestinationSecurityConfigResponseBuilder {
    private Long destinationId;
    private DestinationSecurityConfig destinationSecurityConfig;

    private DestinationSecurityConfigResponseBuilder() {
    }

    public static DestinationSecurityConfigResponseBuilder aDestinationSecurityConfigResponse() {
        return new DestinationSecurityConfigResponseBuilder();
    }

    public DestinationSecurityConfigResponseBuilder withDestinationId(Long destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public DestinationSecurityConfigResponseBuilder withDestinationSecurityConfig(
            DestinationSecurityConfig destinationSecurityConfig) {
        this.destinationSecurityConfig = destinationSecurityConfig;
        return this;
    }

    public DestinationSecurityConfigResponse build() {
        DestinationSecurityConfigResponse destinationSecurityConfigResponse = new DestinationSecurityConfigResponse();
        destinationSecurityConfigResponse.setDestinationId(destinationId);
        destinationSecurityConfigResponse.setDestinationSecurityConfig(destinationSecurityConfig);
        return destinationSecurityConfigResponse;
    }
}
