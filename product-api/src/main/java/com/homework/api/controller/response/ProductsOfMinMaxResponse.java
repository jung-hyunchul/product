package com.homework.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.homework.core.dto.ProductDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsOfMinMaxResponse {

  @JsonProperty("카테고리")
  private String categoryName;

  @JsonProperty("최저가")
  private List<Product> lowest;

  @JsonProperty("최고가")
  private List<Product> highest;

  public static ProductsOfMinMaxResponse of(String categoryName, ProductDto highest,
      ProductDto lowest) {
    return ProductsOfMinMaxResponse.builder()
        .categoryName(categoryName)
        .lowest(List.of(Product.builder()
            .brandName(lowest.getBrandName())
            .price(String.format("%,d", lowest.getPrice()))
            .build()))
        .highest(List.of(Product.builder()
            .brandName(highest.getBrandName())
            .price(String.format("%,d", highest.getPrice()))
            .build()))
        .build();
  }

  @Getter
  @Builder
  private static class Product {

    @JsonProperty("브랜드")
    private String brandName;

    @JsonProperty("가격")
    private String price;
  }
}


