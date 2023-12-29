package com.hooxi.data.model.workflow;

import java.util.HashMap;
import java.util.Map;

public class EventDeliveryActivityResponse {
  private String code;
  private String responsePayload;
  private Map<String, String> headers;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getResponsePayload() {
    return responsePayload;
  }

  public void setResponsePayload(String responsePayload) {
    this.responsePayload = responsePayload;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public void addHeader(String key, String value) {
    if (headers == null) {
      headers = new HashMap<>();
    }
    this.headers.put(key, value);
  }
}
