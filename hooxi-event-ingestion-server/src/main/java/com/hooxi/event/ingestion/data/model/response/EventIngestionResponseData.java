package com.hooxi.event.ingestion.data.model.response;

public class EventIngestionResponseData {
  private String hooxiEventId;
  private String eventId;

  public String getHooxiEventId() {
    return hooxiEventId;
  }

  public void setHooxiEventId(String hooxiEventId) {
    this.hooxiEventId = hooxiEventId;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public static final class EventIngestionResponseDataBuilder {
    private String hooxiEventId;
    private String eventId;

    private EventIngestionResponseDataBuilder() {}

    public static EventIngestionResponseDataBuilder anEventIngestionResponse() {
      return new EventIngestionResponseDataBuilder();
    }

    public EventIngestionResponseDataBuilder withHooxiEventId(String hooxiEventId) {
      this.hooxiEventId = hooxiEventId;
      return this;
    }

    public EventIngestionResponseDataBuilder withEventId(String eventId) {
      this.eventId = eventId;
      return this;
    }

    public EventIngestionResponseData build() {
      EventIngestionResponseData eventIngestionResponse = new EventIngestionResponseData();
      eventIngestionResponse.setHooxiEventId(hooxiEventId);
      eventIngestionResponse.setEventId(eventId);
      return eventIngestionResponse;
    }
  }
}
