package com.hooxi.data.model.event.log;

public final class EventLogBuilder {
  private Long timestamp;
  private Integer httpStatus;
  private String responseHeaders;
  private String resposnePayload;

  private EventLogBuilder() {}

  public static EventLogBuilder anEventLog() {
    return new EventLogBuilder();
  }

  public EventLogBuilder withTimestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public EventLogBuilder withHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
    return this;
  }

  public EventLogBuilder withResponseHeaders(String responseHeaders) {
    this.responseHeaders = responseHeaders;
    return this;
  }

  public EventLogBuilder withResposnePayload(String resposnePayload) {
    this.resposnePayload = resposnePayload;
    return this;
  }

  public EventLog build() {
    EventLog eventLog = new EventLog();
    eventLog.setTimestamp(timestamp);
    eventLog.setHttpStatus(httpStatus);
    eventLog.setResponseHeaders(responseHeaders);
    eventLog.setResposnePayload(resposnePayload);
    return eventLog;
  }
}
