package com.homework.api.controller.response;

import com.homework.core.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

  private Long id;
  private Long price;
  private String name;
  private CategoryResponse category;
  private BrandResponse brand;

  public static ProductResponse of(ProductEntity product) {
    return ProductResponse.builder()
        .id(product.getId())
        .price(product.getPrice())
        .name(product.getName())
        .category(CategoryResponse.of(product.getCategory()))
        .brand(BrandResponse.of(product.getBrand()))
        .build();
  }
}
