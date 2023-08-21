package com.hooxi.event.ingestion.data.model.response;

public class EventIngestionResponse {
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

    public static final class EventIngestionResponseBuilder {
        private String hooxiEventId;
        private String eventId;

        private EventIngestionResponseBuilder() {
        }

        public static EventIngestionResponseBuilder anEventIngestionResponse() {
            return new EventIngestionResponseBuilder();
        }

        public EventIngestionResponseBuilder withHooxiEventId(String hooxiEventId) {
            this.hooxiEventId = hooxiEventId;
            return this;
        }

        public EventIngestionResponseBuilder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public EventIngestionResponse build() {
            EventIngestionResponse eventIngestionResponse = new EventIngestionResponse();
            eventIngestionResponse.setHooxiEventId(hooxiEventId);
            eventIngestionResponse.setEventId(eventId);
            return eventIngestionResponse;
        }
    }
}
