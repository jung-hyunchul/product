package com.homework.api.controller;

import com.homework.api.controller.request.ProductRequest;
import com.homework.api.controller.response.LeastExpensiveBrandResponse;
import com.homework.api.controller.response.ProductResponse;
import com.homework.api.controller.response.ProductsLeastExpensiveResponse;
import com.homework.api.controller.response.ProductsOfMinMaxResponse;
import com.homework.api.exception.ExceptionResponse;
import com.homework.core.dto.ProductDto;
import com.homework.core.exception.BusinessException;
import com.homework.core.service.CategoryService;
import com.homework.core.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. product")
@ApiResponses({
    @ApiResponse(
        responseCode = "400",
        description = "잘못된 요청입니다. 올바르지 않은 값을 전달하였습니다.",
        content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
})
@RequestMapping(path = "/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final CategoryService categoryService;

  @Operation(
      summary = "카테고리 별 최저가격 조회",
      description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductsLeastExpensiveResponse.class)
              )
          }
      )
  })
  @GetMapping(value = "/categories/least-expensive")
  public ProductsLeastExpensiveResponse getLeastExpensiveProductsPerCategory() {
    List<ProductDto> products = productService.getLeastExpensiveProducts();
    return ProductsLeastExpensiveResponse.builder()
        .products(products)
        .sum(products.stream()
            .map(ProductDto::getPrice)
            .reduce(0L, Long::sum)
        )
        .build();
  }

  @Operation(
      summary = "최저가 판매 브랜드 조회",
      description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액 조회"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LeastExpensiveBrandResponse.class)
              )
          }
      )
  })
  @GetMapping(value = "/brands/least-expensive")
  public LeastExpensiveBrandResponse getLeasExpensiveBrand() {
    long brandId = productService.getLeastExpensiveBrand();
    List<ProductDto> products = productService.getCategories(brandId);

    return LeastExpensiveBrandResponse.of(products);
  }

  @Operation(
      summary = "특정 카테고리의 브랜드 조회",
      description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductsOfMinMaxResponse.class)
              )
          }
      )
  })
  @GetMapping(value = "/categories/{categoryName}/brand")
  public ProductsOfMinMaxResponse getBrand(@PathVariable String categoryName) {
    categoryService.getCategory(categoryName)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST.value(),
                String.format("category %s not found", categoryName)));

    ProductDto highest = productService.getMostExpensiveProductOfCategory(categoryName);
    ProductDto lowest = productService.getLeastExpensiveProductOfCategory(categoryName);

    return ProductsOfMinMaxResponse.of(categoryName, highest, lowest);
  }

  @Operation(
      summary = "product 저장",
      description =
          "product 저장. 동일한 category, brand 에는 하나의 product만 저장할 수 있음. 이미 존재하는 product 이면 price 만 수정해서 저장."
              + "존재하지 않는 product 이면 새로 생성"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductResponse.class)
              )
          }
      )
  })
  @PostMapping
  public ProductResponse saveBrand(@RequestBody @Valid ProductRequest request) {
    ProductDto product = ProductDto.builder()
        .categoryId(request.getCategoryId())
        .brandId(request.getBrandId())
        .price(request.getPrice())
        .build();

    return ProductResponse.of(productService.saveProduct(product));
  }
}
