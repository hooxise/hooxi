package com.hooxi.data.model.config;

import com.hooxi.data.model.dest.security.DestinationSecurityConfig;
import io.swagger.v3.oas.annotations.media.Schema;

public class DestinationSecurityConfigResponse {
  @Schema(description = "destination identifier")
  private Long destinationId;
  @Schema(description = "security information used while invoking webhook")
  private DestinationSecurityConfig destinationSecurityConfig;

  public Long getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(Long destinationId) {
    this.destinationId = destinationId;
  }

  public DestinationSecurityConfig getDestinationSecurityConfig() {
    return destinationSecurityConfig;
  }

  public void setDestinationSecurityConfig(DestinationSecurityConfig destinationSecurityConfig) {
    this.destinationSecurityConfig = destinationSecurityConfig;
  }

  @Override
  public String toString() {
    return "DestinationSecurityConfigResponse{" +
            "destinationId=" + destinationId +
            ", destinationSecurityConfig=" + destinationSecurityConfig +
            '}';
  }
}
