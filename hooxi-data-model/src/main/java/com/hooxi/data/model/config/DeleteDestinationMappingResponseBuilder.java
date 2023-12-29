package com.hooxi.data.model.config;

public final class DeleteDestinationMappingResponseBuilder {
  private String message;
  private DestinationMappingResponse deletedMapping;

  private DeleteDestinationMappingResponseBuilder() {}

  public static DeleteDestinationMappingResponseBuilder aDeleteDestinationMappingResponse() {
    return new DeleteDestinationMappingResponseBuilder();
  }

  public DeleteDestinationMappingResponseBuilder withMessage(String message) {
    this.message = message;
    return this;
  }

  public DeleteDestinationMappingResponseBuilder withDeletedMapping(
      DestinationMappingResponse deletedMapping) {
    this.deletedMapping = deletedMapping;
    return this;
  }

  public DeleteDestinationMappingResponse build() {
    DeleteDestinationMappingResponse deleteDestinationMappingResponse =
        new DeleteDestinationMappingResponse();
    deleteDestinationMappingResponse.setMessage(message);
    deleteDestinationMappingResponse.setDeletedMapping(deletedMapping);
    return deleteDestinationMappingResponse;
  }
}
