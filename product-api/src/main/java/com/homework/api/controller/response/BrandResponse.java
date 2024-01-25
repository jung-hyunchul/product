package com.homework.api.controller.response;

import com.homework.core.dto.BrandDto;
import com.homework.core.entity.BrandEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponse {

  private Long id;

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
