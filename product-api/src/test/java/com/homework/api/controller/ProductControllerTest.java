package com.homework.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.api.exception.CommonExceptionHandler;
import com.homework.core.dto.CategoryDto;
import com.homework.core.dto.ProductDto;
import com.homework.core.service.CategoryService;
import com.homework.core.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@MockitoSettings
class ProductControllerTest {

  @InjectMocks
  private ProductController controller;

  @Mock
  private CategoryService categoryService;
  @Mock
  private ProductService productService;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new CommonExceptionHandler())
        .build();
  }

  @Test
  public void getLeastExpensiveProductsPerCategory() throws Exception {
    ProductDto product1 = mock(ProductDto.class);
    when(product1.getPrice()).thenReturn(1_000L);
    when(product1.getCategoryName()).thenReturn("양말");
    when(product1.getBrandName()).thenReturn("나이키");

    ProductDto product2 = mock(ProductDto.class);
    when(product2.getPrice()).thenReturn(2_000L);
    when(product2.getCategoryName()).thenReturn("상의");
    when(product2.getBrandName()).thenReturn("무신사");

    ProductDto product3 = mock(ProductDto.class);
    when(product3.getPrice()).thenReturn(3_000L);
    when(product3.getCategoryName()).thenReturn("가방");
    when(product3.getBrandName()).thenReturn("아디다스");

    when(productService.getLeastExpensiveProducts()).thenReturn(
        List.of(product1, product2, product3));

    mockMvc.perform(get("/api/v1/products/categories/least-expensive")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sum").value("6000"))
        .andExpect(jsonPath("$.products[0].categoryName").value("양말"))
        .andExpect(jsonPath("$.products[0].brandName").value("나이키"))
        .andExpect(jsonPath("$.products[1].categoryName").value("상의"))
        .andExpect(jsonPath("$.products[1].brandName").value("무신사"))
        .andExpect(jsonPath("$.products[2].categoryName").value("가방"))
        .andExpect(jsonPath("$.products[2].brandName").value("아디다스"));
  }

  @Test
  public void getLeasExpensiveBrand() throws Exception {
    when(productService.getLeastExpensiveBrand()).thenReturn(1L);

    ProductDto product1 = mock(ProductDto.class);
    when(product1.getPrice()).thenReturn(1_000L);
    when(product1.getCategoryName()).thenReturn("양말");
    when(product1.getBrandName()).thenReturn("무신사");

    ProductDto product2 = mock(ProductDto.class);
    when(product2.getPrice()).thenReturn(2_000L);
    when(product2.getCategoryName()).thenReturn("상의");

    ProductDto product3 = mock(ProductDto.class);
    when(product3.getPrice()).thenReturn(3_000L);
    when(product3.getCategoryName()).thenReturn("가방");

    when(productService.getCategories(1L)).thenReturn(List.of(product1, product2, product3));

    mockMvc.perform(get("/api/v1/products/brands/least-expensive")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.최저가.브랜드").value("무신사"))
        .andExpect(jsonPath("$.최저가.총액").value("6,000"))
        .andExpect(jsonPath("$.최저가.카테고리[0].카테고리").value("양말"))
        .andExpect(jsonPath("$.최저가.카테고리[0].가격").value("1,000"))
        .andExpect(jsonPath("$.최저가.카테고리[1].카테고리").value("상의"))
        .andExpect(jsonPath("$.최저가.카테고리[1].가격").value("2,000"))
        .andExpect(jsonPath("$.최저가.카테고리[2].카테고리").value("가방"))
        .andExpect(jsonPath("$.최저가.카테고리[2].가격").value("3,000"));
  }

  @Test
  @DisplayName("해당 카테고리에 속한 브랜드가 없을 경우")
  public void getBrand_not_exist() throws Exception {
    when(categoryService.getCategory(any())).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/products/categories/test/brand")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("category test not found"));
  }

  @Test
  @DisplayName("해당 카테고리에 속한 브랜드가 있을 경우")
  public void getBrand() throws Exception {
    CategoryDto category = mock(CategoryDto.class);
    when(categoryService.getCategory(any())).thenReturn(Optional.of(category));

    ProductDto product1 = mock(ProductDto.class);
    when(product1.getPrice()).thenReturn(1_000L);
    when(product1.getBrandName()).thenReturn("nike");

    when(productService.getLeastExpensiveProductOfCategory("상의")).thenReturn(product1);

    ProductDto product2 = mock(ProductDto.class);
    when(product2.getPrice()).thenReturn(10_000L);
    when(product2.getBrandName()).thenReturn("무신사");

    when(productService.getMostExpensiveProductOfCategory("상의")).thenReturn(product2);

    mockMvc.perform(get("/api/v1/products/categories/상의/brand")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.카테고리").value("상의"))
        .andExpect(jsonPath("$.최저가[0].브랜드").value("nike"))
        .andExpect(jsonPath("$.최저가[0].가격").value("1,000"))
        .andExpect(jsonPath("$.최고가[0].브랜드").value("무신사"))
        .andExpect(jsonPath("$.최고가[0].가격").value("10,000"));
  }
}