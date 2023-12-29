package com.hooxi.event.ingestion.data.model.response;

import java.util.List;

public class EventIngestionResponse {
  List<EventIngestionResponseData> events;

  public List<EventIngestionResponseData> getEvents() {
    return events;
  }

  public void setEvents(List<EventIngestionResponseData> events) {
    this.events = events;
  }

  public static final class EventIngestionResponseBuilder {
    private List<EventIngestionResponseData> events;

    private EventIngestionResponseBuilder() {}

    public static EventIngestionResponseBuilder anEventIngestionResponse() {
      return new EventIngestionResponseBuilder();
    }

    public EventIngestionResponseBuilder withEvents(List<EventIngestionResponseData> events) {
      this.events = events;
      return this;
    }

    public EventIngestionResponse build() {
      EventIngestionResponse eventIngestionResponse = new EventIngestionResponse();
      eventIngestionResponse.setEvents(events);
      return eventIngestionResponse;
    }
  }
}
