package com.homework.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.homework.core.dto.ProductDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

@Getter
@Builder
public class LeastExpensiveBrandResponse {

  @JsonProperty("최저가")
  private LowestPrice lowestPrice;

  public static LeastExpensiveBrandResponse of(List<ProductDto> products) {
    if (CollectionUtils.isEmpty(products)) {
      throw new RuntimeException("product 정보가 없습니다");
    }
    String brandName = products.get(0).getBrandName();
    List<Category> categories = products.stream()
        .map(e -> Category.builder()
            .categoryName(e.getCategoryName())
            .price(String.format("%,d", e.getPrice()))
            .build())
        .collect(Collectors.toList());

    long totalPrice = products.stream()
        .map(ProductDto::getPrice)
        .reduce(0L, Long::sum);

    LowestPrice lowestPrice = LowestPrice.builder()
        .brandName(brandName)
        .categories(categories)
        .totalPrice(String.format("%,d", totalPrice))
        .build();

    return LeastExpensiveBrandResponse.builder()
        .lowestPrice(lowestPrice)

        .build();
  }

  @Getter
  @Builder
  private static class LowestPrice {

    @JsonProperty("브랜드")
    private String brandName;

    @JsonProperty("카테고리")
    private List<Category> categories;

    @JsonProperty("총액")
    private String totalPrice;
  }

  @Getter
  @Builder
  private static class Category {

    @JsonProperty("카테고리")
    private String categoryName;

    @JsonProperty("가격")
    private String price;
  }
}


