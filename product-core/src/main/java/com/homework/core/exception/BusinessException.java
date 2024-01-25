package com.homework.core.exception;

public class BusinessException extends RuntimeException {

  private int statusCode;

  public BusinessException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return this.statusCode;
  }
}
