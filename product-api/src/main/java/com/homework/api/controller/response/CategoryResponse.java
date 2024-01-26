package com.homework.api.controller.response;

import com.homework.core.dto.CategoryDto;
import com.homework.core.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

  @Schema(
      description = "카테고리 아이디",
      example = "1"
  )
  @NotNull
  private Long id;

  @Schema(
      description = "카테고리 이름",
      example = "양말"
  )
  @NotNull
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
