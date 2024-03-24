package com.hooxi.event.ingestion.data.model;

import com.hooxi.data.model.config.DestinationResponse;
import com.hooxi.data.model.config.DestinationSecurityConfigResponse;

public class DestinationDetails {

    private DestinationResponse destinationResponse;
    private DestinationSecurityConfigResponse destinationSecurityConfigResponse;

    public DestinationResponse getDestinationResponse() {
        return destinationResponse;
    }

    public void setDestinationResponse(DestinationResponse destinationResponse) {
        this.destinationResponse = destinationResponse;
    }

    public DestinationSecurityConfigResponse getDestinationSecurityConfigResponse() {
        return destinationSecurityConfigResponse;
    }

    public void setDestinationSecurityConfigResponse(DestinationSecurityConfigResponse destinationSecurityConfigResponse) {
        this.destinationSecurityConfigResponse = destinationSecurityConfigResponse;
    }

    public Long getDestinationId() {
        return destinationResponse.getDestinationId();
    }

    public static final class DestinationDetailsBuilder {
        private DestinationResponse destinationResponse;
        private DestinationSecurityConfigResponse destinationSecurityConfigResponse;

        private DestinationDetailsBuilder() {
        }

        public static DestinationDetailsBuilder aDestinationDetails() {
            return new DestinationDetailsBuilder();
        }

        public DestinationDetailsBuilder withDestinationResponse(DestinationResponse destinationResponse) {
            this.destinationResponse = destinationResponse;
            return this;
        }

        public DestinationDetailsBuilder withDestinationSecurityConfigResponse(DestinationSecurityConfigResponse destinationSecurityConfigResponse) {
            this.destinationSecurityConfigResponse = destinationSecurityConfigResponse;
            return this;
        }

        public DestinationDetails build() {
            DestinationDetails destinationDetails = new DestinationDetails();
            destinationDetails.setDestinationResponse(destinationResponse);
            destinationDetails.setDestinationSecurityConfigResponse(destinationSecurityConfigResponse);
            return destinationDetails;
        }
    }
}
