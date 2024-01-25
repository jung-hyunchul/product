package com.homework.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.api.exception.CommonExceptionHandler;
import com.homework.core.dto.CategoryDto;
import com.homework.core.entity.CategoryEntity;
import com.homework.core.service.CategoryService;
import java.util.Map;
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
class CategoryControllerTest {

  @InjectMocks
  private CategoryController controller;

  @Mock
  private CategoryService categoryService;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new CommonExceptionHandler())
        .build();
  }

  @Test
  @DisplayName("카테고리가 존재하지 않을 경우")
  public void getCategory_categoryNotExist() throws Exception {
    when(categoryService.getCategory(any())).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/categories/test")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("category test not found"));
  }

  @Test
  @DisplayName("브랜드가 존재할 경우")
  public void getBrand() throws Exception {
    CategoryDto category = mock(CategoryDto.class);
    when(category.getId()).thenReturn(1L);
    when(category.getName()).thenReturn("양말");

    when(categoryService.getCategory("양말")).thenReturn(Optional.of(category));

    mockMvc.perform(get("/api/v1/categories/양말")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("양말"));
  }

  @Test
  @DisplayName("브랜드 저장이 실패할 경우")
  public void saveBrand_fail() throws Exception {
    when(categoryService.saveCategory(any())).thenThrow(new RuntimeException("서버 오류"));

    String requestContent = objectMapper.writeValueAsString(Map.of(
        "name", "무신사r"
    ));

    mockMvc.perform(post("/api/v1/categories")
            .content(requestContent)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
        .andExpect(jsonPath("$.message").value("서버 오류"));
  }

  @Test
  @DisplayName("브랜드 저장 성공")
  public void saveBrand() throws Exception {
    CategoryEntity entity = mock(CategoryEntity.class);
    when(entity.getId()).thenReturn(1L);
    when(entity.getName()).thenReturn("양말");

    when(categoryService.saveCategory(any())).thenReturn(entity);

    String requestContent = objectMapper.writeValueAsString(Map.of(
        "name", "양말"
    ));

    mockMvc.perform(post("/api/v1/categories")
            .content(requestContent)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("양말"));
  }
}