package com.homework.api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ExceptionResponse {

  @Schema(
      description = "에러 메세지 (예외 메세지 또는 Custom message로서, 사용자에게 표시할 수 있음).",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "BAD_REQUEST"
  )
  private HttpStatus status;

  @Schema(
      description = "에러 메세지 (예외 메세지 또는 Custom message로서, 사용자에게 표시할 수 있음).",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "brand 유니클로 not found"
  )
  private String message;


}
