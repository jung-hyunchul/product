package com.homework.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.homework.core.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {

  private Long id;

  private Long brandId;

  private String brandName;

  private Long categoryId;

  private String categoryName;

  private Long price;

  private String name;

  public static ProductDto from(ProductEntity product) {
    return ProductDto.builder()
        .id(product.getId())
        .brandId(product.getBrand().getId())
        .brandName(product.getBrand().getName())
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .price(product.getPrice())
        .name(product.getName())
        .build();
  }

}
