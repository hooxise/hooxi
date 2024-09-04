package com.hooxi.data.model.event.log;

import java.util.List;

public final class EventLogsResponseBuilder {
  private String eventId;
  private String externalEventId;
  private List<EventLog> eventLogs;

  private EventLogsResponseBuilder() {}

  public static EventLogsResponseBuilder anEventLogsResponse() {
    return new EventLogsResponseBuilder();
  }

  public EventLogsResponseBuilder withEventId(String eventId) {
    this.eventId = eventId;
    return this;
  }

  public EventLogsResponseBuilder withExternalEventId(String externalEventId) {
    this.externalEventId = externalEventId;
    return this;
  }

  public EventLogsResponseBuilder withEventLogs(List<EventLog> eventLogs) {
    this.eventLogs = eventLogs;
    return this;
  }

  public EventLogsResponse build() {
    EventLogsResponse eventLogsResponse = new EventLogsResponse();
    eventLogsResponse.setEventId(eventId);
    eventLogsResponse.setExternalEventId(externalEventId);
    eventLogsResponse.setEventLogs(eventLogs);
    return eventLogsResponse;
  }
}
