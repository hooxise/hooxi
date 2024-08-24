package com.hooxi.data.model.apikey;

public final class InactivateApiKeyRequestBuilder {
  private Long apiKeyId;

  private InactivateApiKeyRequestBuilder() {}

  public static InactivateApiKeyRequestBuilder anInactivateApiKeyRequest() {
    return new InactivateApiKeyRequestBuilder();
  }

  public InactivateApiKeyRequestBuilder withApiKeyId(Long apiKeyId) {
    this.apiKeyId = apiKeyId;
    return this;
  }

  public InactivateApiKeyRequest build() {
    InactivateApiKeyRequest inactivateApiKeyRequest = new InactivateApiKeyRequest();
    inactivateApiKeyRequest.setApiKeyId(apiKeyId);
    return inactivateApiKeyRequest;
  }
}
