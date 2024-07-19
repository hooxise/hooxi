package com.hooxi.event.ingestion.data.model.request;

import java.util.List;

public class EventIngestionRequest {
  private List<EventIngestionData> events;

  public List<EventIngestionData> getEvents() {
    return events;
  }

  public void setEvents(List<EventIngestionData> events) {
    this.events = events;
  }

  public static final class EventIngestionRequestBuilder {
    private List<EventIngestionData> events;

    private EventIngestionRequestBuilder() {}

    public static EventIngestionRequestBuilder anEventIngestionRequest() {
      return new EventIngestionRequestBuilder();
    }

    public EventIngestionRequestBuilder withEvents(List<EventIngestionData> events) {
      this.events = events;
      return this;
    }

    public EventIngestionRequest build() {
      EventIngestionRequest eventIngestionRequest = new EventIngestionRequest();
      eventIngestionRequest.setEvents(events);
      return eventIngestionRequest;
    }
  }
}
