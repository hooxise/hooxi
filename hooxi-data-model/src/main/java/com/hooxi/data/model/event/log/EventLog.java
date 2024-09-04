package com.hooxi.data.model.event.log;

public class EventLog {
  private Long timestamp;
  private Integer httpStatus;
  private String responseHeaders;
  private String resposnePayload;

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(String responseHeaders) {
    this.responseHeaders = responseHeaders;
  }

  public String getResposnePayload() {
    return resposnePayload;
  }

  public void setResposnePayload(String resposnePayload) {
    this.resposnePayload = resposnePayload;
  }
}
