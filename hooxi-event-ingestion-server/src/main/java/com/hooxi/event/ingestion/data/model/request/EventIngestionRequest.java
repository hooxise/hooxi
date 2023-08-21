package com.hooxi.event.ingestion.data.model.request;

public class EventIngestionRequest {

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
}
