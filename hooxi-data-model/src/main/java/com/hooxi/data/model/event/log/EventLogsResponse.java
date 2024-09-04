package com.hooxi.data.model.event.log;

import java.util.List;

public class EventLogsResponse {
  private String eventId;
  private String externalEventId;
  private List<EventLog> eventLogs;

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getExternalEventId() {
    return externalEventId;
  }

  public void setExternalEventId(String externalEventId) {
    this.externalEventId = externalEventId;
  }

  public List<EventLog> getEventLogs() {
    return eventLogs;
  }

  public void setEventLogs(List<EventLog> eventLogs) {
    this.eventLogs = eventLogs;
  }
}
