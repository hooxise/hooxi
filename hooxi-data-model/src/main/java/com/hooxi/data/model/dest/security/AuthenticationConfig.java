package com.hooxi.data.model.dest.security;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

public class AuthenticationConfig {

  @Schema(
      description = "Authentication Type. Currently bearer or username/password auth is supported")
  AuthenticationType authType;

  @Schema(description = "Authentication header used as http header when invoking webhook")
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
