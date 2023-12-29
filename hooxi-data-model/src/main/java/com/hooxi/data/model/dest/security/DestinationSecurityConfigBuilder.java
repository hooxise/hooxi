package com.hooxi.data.model.dest.security;

public final class DestinationSecurityConfigBuilder {
  private TLSConfig tlsConfig;
  private AuthenticationConfig authConfig;

  private DestinationSecurityConfigBuilder() {}

  public static DestinationSecurityConfigBuilder aDestinationSecurityConfig() {
    return new DestinationSecurityConfigBuilder();
  }

  public DestinationSecurityConfigBuilder withTlsConfig(TLSConfig tlsConfig) {
    this.tlsConfig = tlsConfig;
    return this;
  }

  public DestinationSecurityConfigBuilder withAuthConfig(AuthenticationConfig authConfig) {
    this.authConfig = authConfig;
    return this;
  }

  public DestinationSecurityConfig build() {
    DestinationSecurityConfig destinationSecurityConfig = new DestinationSecurityConfig();
    destinationSecurityConfig.setTlsConfig(tlsConfig);
    destinationSecurityConfig.setAuthConfig(authConfig);
    return destinationSecurityConfig;
  }
}
