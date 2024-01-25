package com.homework.api.exception;

import com.homework.core.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> handleBusinessException(BusinessException exception) {
    return ResponseEntity.status(exception.getStatusCode())
        .body(ExceptionResponse.builder()
            .status(HttpStatus.valueOf(exception.getStatusCode()))
            .message(exception.getMessage())
            .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    return ResponseEntity.status(exception.getStatusCode())
        .body(ExceptionResponse.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(exception.getMessage())
            .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(exception.getMessage())
            .build());
  }
}
