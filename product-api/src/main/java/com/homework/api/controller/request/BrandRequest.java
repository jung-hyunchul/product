package com.homework.api.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BrandRequest {

  @Schema(
      description = "Brand 이름",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "무신사"
  )
  @NotNull
  private String name;
}
