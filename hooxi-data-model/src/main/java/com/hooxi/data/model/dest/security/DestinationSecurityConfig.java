package com.hooxi.data.model.dest.security;

import io.swagger.v3.oas.annotations.media.Schema;

public class DestinationSecurityConfig {

  @Schema(description = "TLS used for invoking webhook")
  private TLSConfig tlsConfig;

  @Schema(description = "information used for authenticating webhook call")
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

  @Override
  public String toString() {
    return "DestinationSecurityConfig{"
        + "tlsConfig="
        + tlsConfig
        + ", authConfig="
        + authConfig
        + '}';
  }
}
