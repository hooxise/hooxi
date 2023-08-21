package com.hooxi.data.model.event;

import java.util.Map;

public final class HooxiEventBuilder {
    private String tenant;
    private String internalEventId;
    private String externalEventId;
    private String eventType;
    private String eventSource;
    private String eventURI;
    private String timestamp;
    private String payload;
    private Map<String, String> headers;
    private EventStatus status;
    private boolean createCloudEvent;

    private HooxiEventBuilder() {
    }

    public static HooxiEventBuilder aHooxiEvent() {
        return new HooxiEventBuilder();
    }

    public HooxiEventBuilder withTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public HooxiEventBuilder withInternalEventId(String internalEventId) {
        this.internalEventId = internalEventId;
        return this;
    }

    public HooxiEventBuilder withExternalEventId(String externalEventId) {
        this.externalEventId = externalEventId;
        return this;
    }

    public HooxiEventBuilder withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public HooxiEventBuilder withEventSource(String eventSource) {
        this.eventSource = eventSource;
        return this;
    }

    public HooxiEventBuilder withEventURI(String eventURI) {
        this.eventURI = eventURI;
        return this;
    }

    public HooxiEventBuilder withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public HooxiEventBuilder withPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public HooxiEventBuilder withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HooxiEventBuilder withStatus(EventStatus status) {
        this.status = status;
        return this;
    }

    public HooxiEventBuilder withCreateCloudEvent(boolean createCloudEvent) {
        this.createCloudEvent = createCloudEvent;
        return this;
    }

    public HooxiEvent build() {
        HooxiEvent hooxiEvent = new HooxiEvent();
        hooxiEvent.setTenant(tenant);
        hooxiEvent.setInternalEventId(internalEventId);
        hooxiEvent.setExternalEventId(externalEventId);
        hooxiEvent.setEventType(eventType);
        hooxiEvent.setEventSource(eventSource);
        hooxiEvent.setEventURI(eventURI);
        hooxiEvent.setTimestamp(timestamp);
        hooxiEvent.setPayload(payload);
        hooxiEvent.setHeaders(headers);
        hooxiEvent.setStatus(status);
        hooxiEvent.setCreateCloudEvent(createCloudEvent);
        return hooxiEvent;
    }
}
