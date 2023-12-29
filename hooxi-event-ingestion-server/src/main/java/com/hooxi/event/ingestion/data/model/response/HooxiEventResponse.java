package com.hooxi.event.ingestion.data.model.response;

import com.hooxi.event.ingestion.data.model.request.EventMetadata;

public class HooxiEventResponse {
  private String eventId;
  private String payload;
  private String hooxiEventId;
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

  public String getHooxiEventId() {
    return hooxiEventId;
  }

  public void setHooxiEventId(String hooxiEventId) {
    this.hooxiEventId = hooxiEventId;
  }

  public EventMetadata getEventMetadata() {
    return eventMetadata;
  }

  public void setEventMetadata(EventMetadata eventMetadata) {
    this.eventMetadata = eventMetadata;
  }

  public static final class HooxiEventResponseBuilder {
    private String eventId;
    private String payload;
    private String hooxiEventId;
    private EventMetadata eventMetadata;

    private HooxiEventResponseBuilder() {}

    public static HooxiEventResponseBuilder aHooxiEventResponse() {
      return new HooxiEventResponseBuilder();
    }

    public HooxiEventResponseBuilder withEventId(String eventId) {
      this.eventId = eventId;
      return this;
    }

    public HooxiEventResponseBuilder withPayload(String payload) {
      this.payload = payload;
      return this;
    }

    public HooxiEventResponseBuilder withHooxiEventId(String hooxiEventId) {
      this.hooxiEventId = hooxiEventId;
      return this;
    }

    public HooxiEventResponseBuilder withEventMetadata(EventMetadata eventMetadata) {
      this.eventMetadata = eventMetadata;
      return this;
    }

    public HooxiEventResponse build() {
      HooxiEventResponse hooxiEventResponse = new HooxiEventResponse();
      hooxiEventResponse.setEventId(eventId);
      hooxiEventResponse.setPayload(payload);
      hooxiEventResponse.setHooxiEventId(hooxiEventId);
      hooxiEventResponse.setEventMetadata(eventMetadata);
      return hooxiEventResponse;
    }
  }
}
