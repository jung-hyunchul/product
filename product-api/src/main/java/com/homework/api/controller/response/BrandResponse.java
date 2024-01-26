package com.homework.api.controller.response;

import com.homework.core.dto.BrandDto;
import com.homework.core.entity.BrandEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponse {

  @Schema(
      description = "브랜드 아이디",
      example = "1"
  )
  @NotNull
  private Long id;

  @Schema(
      description = "브랜드 이름",
      example = "무신사"
  )
  @NotNull
  private String name;

  public static BrandResponse of(BrandDto brand) {
    return BrandResponse.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }

  public static BrandResponse of(BrandEntity brand) {
    return BrandResponse.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }
}
