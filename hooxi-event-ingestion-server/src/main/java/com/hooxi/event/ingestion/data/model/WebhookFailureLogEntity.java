package com.hooxi.event.ingestion.data.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class WebhookFailureLogEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("internal_event_id")
  private String internalEventId;

  @Column("external_event_id")
  private String externalEventId;

  @Column("timestamp")
  private Long timestamp;

  @Column("http_status")
  private Integer httpStatus;

  @Column("response_payload")
  private String responsePayload;

  @Column("response_headers")
  private String responseHeaders;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getInternalEventId() {
    return internalEventId;
  }

  public void setInternalEventId(String internalEventId) {
    this.internalEventId = internalEventId;
  }

  public String getExternalEventId() {
    return externalEventId;
  }

  public void setExternalEventId(String externalEventId) {
    this.externalEventId = externalEventId;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getResponsePayload() {
    return responsePayload;
  }

  public void setResponsePayload(String responsePayload) {
    this.responsePayload = responsePayload;
  }

  public String getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(String responseHeaders) {
    this.responseHeaders = responseHeaders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WebhookFailureLogEntity that = (WebhookFailureLogEntity) o;
    return Objects.equals(id, that.id)
        && Objects.equals(internalEventId, that.internalEventId)
        && Objects.equals(externalEventId, that.externalEventId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, internalEventId, externalEventId);
  }
}
