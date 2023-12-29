package com.hooxi.data.model.config;

public class DeleteDestinationMappingResponse {
  private String message;
  private DestinationMappingResponse deletedMapping;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public DestinationMappingResponse getDeletedMapping() {
    return deletedMapping;
  }

  public void setDeletedMapping(DestinationMappingResponse deletedMapping) {
    this.deletedMapping = deletedMapping;
  }
}
