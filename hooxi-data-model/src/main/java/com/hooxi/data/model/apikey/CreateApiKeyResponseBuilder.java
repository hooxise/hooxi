package com.hooxi.data.model.apikey;

import java.util.List;

public final class CreateApiKeyResponseBuilder {
  private String apiKey;
  private Long apiKeyId;
  private String apiKeyDescription;
  private Long creationTimestamp;
  private Long expirationTimestamp;
  private List<String> apiKeyPermissions;

  private CreateApiKeyResponseBuilder() {}

  public static CreateApiKeyResponseBuilder aCreateApiKeyResponse() {
    return new CreateApiKeyResponseBuilder();
  }

  public CreateApiKeyResponseBuilder withApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public CreateApiKeyResponseBuilder withApiKeyId(Long apiKeyId) {
    this.apiKeyId = apiKeyId;
    return this;
  }

  public CreateApiKeyResponseBuilder withApiKeyDescription(String apiKeyDescription) {
    this.apiKeyDescription = apiKeyDescription;
    return this;
  }

  public CreateApiKeyResponseBuilder withCreationTimestamp(Long creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
    return this;
  }

  public CreateApiKeyResponseBuilder withExpirationTimestamp(Long expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
    return this;
  }

  public CreateApiKeyResponseBuilder withApiKeyPermissions(List<String> apiKeyPermissions) {
    this.apiKeyPermissions = apiKeyPermissions;
    return this;
  }

  public CreateApiKeyResponse build() {
    CreateApiKeyResponse createApiKeyResponse = new CreateApiKeyResponse();
    createApiKeyResponse.setApiKey(apiKey);
    createApiKeyResponse.setApiKeyId(apiKeyId);
    createApiKeyResponse.setApiKeyDescription(apiKeyDescription);
    createApiKeyResponse.setCreationTimestamp(creationTimestamp);
    createApiKeyResponse.setExpirationTimestamp(expirationTimestamp);
    createApiKeyResponse.setApiKeyPermissions(apiKeyPermissions);
    return createApiKeyResponse;
  }
}
