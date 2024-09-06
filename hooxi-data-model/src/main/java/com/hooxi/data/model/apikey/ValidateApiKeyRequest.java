package com.hooxi.data.model.apikey;

public class ValidateApiKeyRequest {
  String apiKey;
  String operation;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }
}
