package com.hooxi.config.exception;

public class CacheMissException extends RuntimeException {
  public CacheMissException(String message) {
    super(message);
  }

  public CacheMissException(String message, Throwable cause) {
    super(message, cause);
  }
}
