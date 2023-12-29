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
}
