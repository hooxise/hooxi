package com.hooxi.data.model.apikey;

import java.util.List;

public final class CreateApiKeyRequestBuilder {
  private String apiKeyId;
  private String apiKeyDescription;
  private Long expirationTimestamp;
  private List<String> apiKeyPermissions;

  private CreateApiKeyRequestBuilder() {}

  public static CreateApiKeyRequestBuilder aCreateApiKeyRequest() {
    return new CreateApiKeyRequestBuilder();
  }

  public CreateApiKeyRequestBuilder withApiKeyId(String apiKeyId) {
    this.apiKeyId = apiKeyId;
    return this;
  }

  public CreateApiKeyRequestBuilder withApiKeyDescription(String apiKeyDescription) {
    this.apiKeyDescription = apiKeyDescription;
    return this;
  }

  public CreateApiKeyRequestBuilder withExpirationTimestamp(Long expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
    return this;
  }

  public CreateApiKeyRequestBuilder withApiKeyPermissions(List<String> apiKeyPermissions) {
    this.apiKeyPermissions = apiKeyPermissions;
    return this;
  }

  public CreateApiKeyRequest build() {
    CreateApiKeyRequest createApiKeyRequest = new CreateApiKeyRequest();
    createApiKeyRequest.setApiKeyName(apiKeyId);
    createApiKeyRequest.setApiKeyDescription(apiKeyDescription);
    createApiKeyRequest.setExpirationTimestamp(expirationTimestamp);
    createApiKeyRequest.setApiKeyPermissions(apiKeyPermissions);
    return createApiKeyRequest;
  }
}
