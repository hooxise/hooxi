package com.hooxi.data.model.dest;

import io.swagger.v3.oas.annotations.media.Schema;

public class  WebhookDestination extends Destination {
  @Schema(description = "destination type. currently only WEBHOOK is supported")
  private DestinationType destinationType;

  public WebhookDestination() {
    super();
    this.destinationType = DestinationType.WEBHOOK;
  }

  @Override
  public DestinationType getDestinationType() {
    return DestinationType.WEBHOOK;
  }

  public void setDestinationType(DestinationType destinationType) {
    this.destinationType = destinationType;
  }
}
