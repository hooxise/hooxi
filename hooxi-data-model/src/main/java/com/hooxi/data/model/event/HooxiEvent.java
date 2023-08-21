package com.hooxi.data.model.event;

import java.io.Serializable;
import java.util.Map;

public class HooxiEvent implements Serializable {
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

    public String getInternalEventId() {
        return internalEventId;
    }

    public void setInternalEventId(String internalEventId) {
        this.internalEventId = internalEventId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getExternalEventId() {
        return externalEventId;
    }

    public void setExternalEventId(String externalEventId) {
        this.externalEventId = externalEventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventURI() {
        return eventURI;
    }

    public void setEventURI(String eventURI) {
        this.eventURI = eventURI;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public boolean isCreateCloudEvent() {
        return createCloudEvent;
    }

    public void setCreateCloudEvent(boolean createCloudEvent) {
        this.createCloudEvent = createCloudEvent;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "HooxiEvent{" + "tenant='" + tenant + '\'' + ", internalEventId='" + internalEventId + '\'' +
                ", externalEventId='" + externalEventId + '\'' + ", eventType='" + eventType + '\'' +
                ", eventSource='" + eventSource + '\'' + ", eventURI='" + eventURI + '\'' + ", timestamp='" +
                timestamp + '\'' + ", payload='" + payload + '\'' + ", headers=" + headers + '\'' + '}';
    }
}
