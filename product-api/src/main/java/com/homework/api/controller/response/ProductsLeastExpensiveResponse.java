package com.homework.api.controller.response;

import com.homework.core.dto.ProductDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsLeastExpensiveResponse {

  private List<ProductDto> products;

  private long sum;

}
