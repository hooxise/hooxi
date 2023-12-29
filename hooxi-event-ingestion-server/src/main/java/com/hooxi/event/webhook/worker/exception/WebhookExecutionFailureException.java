package com.hooxi.event.webhook.worker.exception;

public class WebhookExecutionFailureException extends RuntimeException {
  public WebhookExecutionFailureException(String message) {
    super(message);
  }

  public WebhookExecutionFailureException(String message, Throwable cause) {
    super(message, cause);
  }
}
