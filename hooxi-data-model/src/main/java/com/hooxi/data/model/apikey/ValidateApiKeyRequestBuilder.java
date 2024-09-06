package com.hooxi.data.model.apikey;

public final class ValidateApiKeyRequestBuilder {
  private String apiKey;
  private String operation;

  private ValidateApiKeyRequestBuilder() {}

  public static ValidateApiKeyRequestBuilder aValidateApiKeyRequest() {
    return new ValidateApiKeyRequestBuilder();
  }

  public ValidateApiKeyRequestBuilder withApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public ValidateApiKeyRequestBuilder withOperation(String operation) {
    this.operation = operation;
    return this;
  }

  public ValidateApiKeyRequest build() {
    ValidateApiKeyRequest validateApiKeyRequest = new ValidateApiKeyRequest();
    validateApiKeyRequest.setApiKey(apiKey);
    validateApiKeyRequest.setOperation(operation);
    return validateApiKeyRequest;
  }
}
