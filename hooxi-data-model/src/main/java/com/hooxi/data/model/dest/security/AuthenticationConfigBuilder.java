package com.hooxi.data.model.dest.security;

import java.util.Map;

public final class AuthenticationConfigBuilder {
    AuthenticationType authType;
    Map<String, String> authHeaders;

    private AuthenticationConfigBuilder() {
    }

    public static AuthenticationConfigBuilder anAuthenticationConfig() {
        return new AuthenticationConfigBuilder();
    }

    public AuthenticationConfigBuilder withAuthType(AuthenticationType authType) {
        this.authType = authType;
        return this;
    }

    public AuthenticationConfigBuilder withAuthHeaders(Map<String, String> authHeaders) {
        this.authHeaders = authHeaders;
        return this;
    }

    public AuthenticationConfig build() {
        AuthenticationConfig authenticationConfig = new AuthenticationConfig();
        authenticationConfig.setAuthType(authType);
        authenticationConfig.setAuthHeaders(authHeaders);
        return authenticationConfig;
    }
}
