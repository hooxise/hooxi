package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.dest.security.AuthenticationConfig;
import com.hooxi.data.model.dest.security.TLSConfig;

public class AddDestinationRequest {

  private Destination destination;
  private TLSConfig tlsConfig;
  private AuthenticationConfig authConfig;

  public Destination getDestination() {
    return destination;
  }

  public void setDestination(Destination destination) {
    this.destination = destination;
  }

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
