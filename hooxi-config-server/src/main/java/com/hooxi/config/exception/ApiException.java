package com.hooxi.config.exception;

public class ApiException extends RuntimeException {
  private String message;
  private String errorCode;
  private Throwable cause;

  public ApiException(String message, String errorCode) {
    super(message);
    this.message = message;
    this.errorCode = errorCode;
  }

  public ApiException(String message, String errorCode, Throwable cause) {
    super(message);
    this.message = message;
    this.errorCode = errorCode;
    this.cause = cause;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public Throwable getCause() {
    return cause;
  }

  public void setCause(Throwable cause) {
    this.cause = cause;
  }
}
