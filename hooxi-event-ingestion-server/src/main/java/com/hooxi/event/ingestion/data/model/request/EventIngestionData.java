package com.hooxi.event.ingestion.data.model.request;

public class EventIngestionData {

  private String eventId;
  private String payload;
  private EventMetadata eventMetadata;

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public EventMetadata getEventMetadata() {
    return eventMetadata;
  }

  public void setEventMetadata(EventMetadata eventMetadata) {
    this.eventMetadata = eventMetadata;
  }

  public String toString() {
    return eventId;
  }

  public static final class EventIngestionDataBuilder {
    private String eventId;
    private String payload;
    private EventMetadata eventMetadata;

    private EventIngestionDataBuilder() {
    }

    public static EventIngestionDataBuilder anEventIngestionData() {
      return new EventIngestionDataBuilder();
    }

    public EventIngestionDataBuilder withEventId(String eventId) {
      this.eventId = eventId;
      return this;
    }

    public EventIngestionDataBuilder withPayload(String payload) {
      this.payload = payload;
      return this;
    }

    public EventIngestionDataBuilder withEventMetadata(EventMetadata eventMetadata) {
      this.eventMetadata = eventMetadata;
      return this;
    }

    public EventIngestionData build() {
      EventIngestionData eventIngestionData = new EventIngestionData();
      eventIngestionData.setEventId(eventId);
      eventIngestionData.setPayload(payload);
      eventIngestionData.setEventMetadata(eventMetadata);
      return eventIngestionData;
    }
  }
}
