package com.hooxi.event.ingestion.data.model.request;

import java.util.Map;

public class EventMetadata {

  private String tenant;
  private String eventType;
  private String eventSource;
  private String eventURI;
  private Long timestamp;

  private String domain;
  private String subdomain;
  private Map<String, String> headers;

  public String getTenant() {
    return tenant;
  }

  public void setTenant(String tenant) {
    this.tenant = tenant;
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

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getSubdomain() {
    return subdomain;
  }

  public void setSubdomain(String subdomain) {
    this.subdomain = subdomain;
  }

  public static final class EventMetadataBuilder {
    private String tenant;
    private String eventType;
    private String eventSource;
    private String eventURI;
    private Long timestamp;
    private String domain;
    private String subdomain;
    private Map<String, String> headers;

    private EventMetadataBuilder() {}

    public static EventMetadataBuilder anEventMetadata() {
      return new EventMetadataBuilder();
    }

    public EventMetadataBuilder withTenant(String tenant) {
      this.tenant = tenant;
      return this;
    }

    public EventMetadataBuilder withEventType(String eventType) {
      this.eventType = eventType;
      return this;
    }

    public EventMetadataBuilder withEventSource(String eventSource) {
      this.eventSource = eventSource;
      return this;
    }

    public EventMetadataBuilder withEventURI(String eventURI) {
      this.eventURI = eventURI;
      return this;
    }

    public EventMetadataBuilder withTimestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public EventMetadataBuilder withDomain(String domain) {
      this.domain = domain;
      return this;
    }

    public EventMetadataBuilder withSubdomain(String subdomain) {
      this.subdomain = subdomain;
      return this;
    }

    public EventMetadataBuilder withHeaders(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public EventMetadata build() {
      EventMetadata eventMetadata = new EventMetadata();
      eventMetadata.setTenant(tenant);
      eventMetadata.setEventType(eventType);
      eventMetadata.setEventSource(eventSource);
      eventMetadata.setEventURI(eventURI);
      eventMetadata.setTimestamp(timestamp);
      eventMetadata.setDomain(domain);
      eventMetadata.setSubdomain(subdomain);
      eventMetadata.setHeaders(headers);
      return eventMetadata;
    }
  }
}
