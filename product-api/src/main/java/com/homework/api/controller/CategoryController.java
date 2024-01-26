package com.homework.api.controller;

import com.homework.api.controller.request.CategoryRequest;
import com.homework.api.controller.response.CategoryResponse;
import com.homework.api.exception.ExceptionResponse;
import com.homework.core.dto.CategoryDto;
import com.homework.core.exception.BusinessException;
import com.homework.core.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2. category")
@ApiResponses({
    @ApiResponse(
        responseCode = "400",
        description = "잘못된 요청입니다. 올바르지 않은 값을 전달하였습니다.",
        content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
})
@RequestMapping(path = "/api/v1/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @Operation(
      summary = "카테고리 정보 조회",
      description = "카테고리 정보 조회"
  )
  @Parameters({
      @Parameter(name = "categoryName", in = ParameterIn.PATH, example = "양말")
  })
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "카테고리 정보 조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryResponse.class)
              )
          }
      )
  })
  @GetMapping(value = "/{categoryName}")
  public ResponseEntity<CategoryResponse> getCategory(
      @PathVariable String categoryName) {
    CategoryDto category = categoryService.getCategory(categoryName)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST.value(),
                String.format("category %s not found", categoryName)));

    return ResponseEntity.ok(CategoryResponse.of(category));
  }

  @Operation(
      summary = "카테고리 리스트 조회",
      description = "카테고리 리스트 조회"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "카테고리 리스트 조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))
              )
          }
      )
  })
  @GetMapping(value = "")
  public ResponseEntity<List<CategoryResponse>> getCategories() {
    return ResponseEntity.ok(categoryService.getCategories().stream()
        .map(CategoryResponse::of)
        .collect(Collectors.toList()));
  }

  @Operation(
      summary = "카테고리 정보 저장",
      description = "카테고리 정보 저장"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "카테고리 정보 저장 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryResponse.class)
              )
          }
      )
  })
  @PostMapping(value = "")
  public ResponseEntity<CategoryResponse> saveCategory(
      @RequestBody @Valid CategoryRequest request) {
    CategoryDto categoryDto = CategoryDto.builder()
        .name(request.getName())
        .build();
    return ResponseEntity.ok(CategoryResponse.of(categoryService.saveCategory(categoryDto)));
  }
}
