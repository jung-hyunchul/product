package com.homework.api.controller.response;

import com.homework.core.dto.CategoryDto;
import com.homework.core.entity.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

  private Long id;

  private String name;

  public static CategoryResponse of(CategoryDto category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .build();
  }

  public static CategoryResponse of(CategoryEntity category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .build();
  }
}
