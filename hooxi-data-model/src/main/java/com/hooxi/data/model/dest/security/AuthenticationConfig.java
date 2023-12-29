package com.hooxi.data.model.dest.security;

import java.util.Map;

public class AuthenticationConfig {
  AuthenticationType authType;
  Map<String, String> authHeaders;

  public AuthenticationType getAuthType() {
    return authType;
  }

  public void setAuthType(AuthenticationType authType) {
    this.authType = authType;
  }

  public Map<String, String> getAuthHeaders() {
    return authHeaders;
  }

  public void setAuthHeaders(Map<String, String> authHeaders) {
    this.authHeaders = authHeaders;
  }
}
