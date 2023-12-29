package com.hooxi.data.model.dest;

public class WebhookDestination extends Destination {
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
