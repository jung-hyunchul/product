package com.homework.api.controller.response;

import com.homework.core.entity.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

  @Schema(
      description = "상품 아이디",
      example = "1"
  )
  @NotNull
  private Long id;

  @Schema(
      description = "가격",
      example = "10000"
  )
  @NotNull
  private Long price;

  private CategoryResponse category;
  private BrandResponse brand;

  public static ProductResponse of(ProductEntity product) {
    return ProductResponse.builder()
        .id(product.getId())
        .price(product.getPrice())
        .category(CategoryResponse.of(product.getCategory()))
        .brand(BrandResponse.of(product.getBrand()))
        .build();
  }
}
