package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.dest.security.AuthenticationConfig;
import com.hooxi.data.model.dest.security.TLSConfig;

public final class AddDestinationRequestBuilder {
    private Destination destination;
    private TLSConfig tlsConfig;
    private AuthenticationConfig authConfig;

    private AddDestinationRequestBuilder() {
    }

    public static AddDestinationRequestBuilder anAddDestinationRequest() {
        return new AddDestinationRequestBuilder();
    }

    public AddDestinationRequestBuilder withDestination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public AddDestinationRequestBuilder withTlsConfig(TLSConfig tlsConfig) {
        this.tlsConfig = tlsConfig;
        return this;
    }

    public AddDestinationRequestBuilder withAuthConfig(AuthenticationConfig authConfig) {
        this.authConfig = authConfig;
        return this;
    }

    public AddDestinationRequest build() {
        AddDestinationRequest addDestinationRequest = new AddDestinationRequest();
        addDestinationRequest.setDestination(destination);
        addDestinationRequest.setTlsConfig(tlsConfig);
        addDestinationRequest.setAuthConfig(authConfig);
        return addDestinationRequest;
    }
}
