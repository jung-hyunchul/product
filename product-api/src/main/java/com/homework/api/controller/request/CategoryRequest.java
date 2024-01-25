package com.homework.api.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryRequest {

  @Schema(
      description = "Category 이름",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "바지"
  )
  @NotNull
  private String name;
}
