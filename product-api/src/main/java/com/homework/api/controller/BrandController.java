package com.homework.api.controller;

import com.homework.api.controller.request.BrandRequest;
import com.homework.api.controller.response.BrandResponse;
import com.homework.api.exception.ExceptionResponse;
import com.homework.core.dto.BrandDto;
import com.homework.core.exception.BusinessException;
import com.homework.core.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "3. brand")
@ApiResponses({
    @ApiResponse(
        responseCode = "400",
        description = "잘못된 요청입니다. 올바르지 않은 값을 전달하였습니다.",
        content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
})
@RequestMapping(path = "/api/v1/brands")
@RestController
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @Operation(
      summary = "브랜드 정보 조회",
      description = "브랜드 정보 조회"
  )
  @Parameters({
      @Parameter(name = "brandName", in = ParameterIn.PATH, example = "무신사")
  })
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "브랜드 정보 조회 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandResponse.class)
              )
          }
      )
  })
  @GetMapping(value = "/{brandName}")
  public ResponseEntity<BrandResponse> getBrand(@PathVariable String brandName) {
    BrandDto brand = brandService.getBrand(brandName)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST.value(),
                String.format("brand %s not found", brandName)));

    return ResponseEntity.ok(BrandResponse.of(brand));
  }

  @Operation(
      summary = "브랜드 정보 저장",
      description = "브랜드 정보 저장"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "브랜드 정보 저장 성공",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandResponse.class)
              )
          }
      )
  })
  @PostMapping(value = "")
  public ResponseEntity<BrandResponse> saveBrand(@RequestBody @Valid BrandRequest request) {
    BrandDto brandDto = BrandDto.builder()
        .name(request.getName())
        .build();
    return ResponseEntity.ok(BrandResponse.of(brandService.saveBrand(brandDto)));
  }
}
