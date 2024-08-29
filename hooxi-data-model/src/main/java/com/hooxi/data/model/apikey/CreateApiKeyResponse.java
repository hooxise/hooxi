package com.hooxi.data.model.apikey;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class CreateApiKeyResponse {

  private String apiKey;

  private Long apiKeyId;

  private String apiKeyDescription;

  private Long creationTimestamp;
  private Long expirationTimestamp;

  @Schema(allowableValues = {"CONFIG", "CONFIG_RO", "EVENTS", "EVENT_RO"})
  private List<String> apiKeyPermissions;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public Long getApiKeyId() {
    return apiKeyId;
  }

  public void setApiKeyId(Long apiKeyId) {
    this.apiKeyId = apiKeyId;
  }

  public String getApiKeyDescription() {
    return apiKeyDescription;
  }

  public void setApiKeyDescription(String apiKeyDescription) {
    this.apiKeyDescription = apiKeyDescription;
  }

  public Long getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Long creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public Long getExpirationTimestamp() {
    return expirationTimestamp;
  }

  public void setExpirationTimestamp(Long expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
  }

  public List<String> getApiKeyPermissions() {
    return apiKeyPermissions;
  }

  public void setApiKeyPermissions(List<String> apiKeyPermissions) {
    this.apiKeyPermissions = apiKeyPermissions;
  }
}
