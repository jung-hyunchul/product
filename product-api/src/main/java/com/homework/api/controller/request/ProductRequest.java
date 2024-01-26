package com.homework.api.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequest {

  @Schema(
      description = "브랜드 아이디",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "1"
  )
  @NotNull
  private Long brandId;

  @Schema(
      description = "카테고리 아이디",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "2"
  )
  @NotNull
  private Long categoryId;

  @Schema(
      description = "가격",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "2"
  )
  @NotNull
  private Long price;
}
