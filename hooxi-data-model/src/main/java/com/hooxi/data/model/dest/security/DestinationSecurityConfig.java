package com.hooxi.data.model.dest.security;

public class DestinationSecurityConfig {
  private TLSConfig tlsConfig;
  private AuthenticationConfig authConfig;

  public TLSConfig getTlsConfig() {
    return tlsConfig;
  }

  public void setTlsConfig(TLSConfig tlsConfig) {
    this.tlsConfig = tlsConfig;
  }

  public AuthenticationConfig getAuthConfig() {
    return authConfig;
  }

  public void setAuthConfig(AuthenticationConfig authConfig) {
    this.authConfig = authConfig;
  }
}
