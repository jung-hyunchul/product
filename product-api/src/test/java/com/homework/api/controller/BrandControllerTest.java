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
import com.homework.core.dto.BrandDto;
import com.homework.core.entity.BrandEntity;
import com.homework.core.service.BrandService;
import java.util.List;
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
class BrandControllerTest {

  @InjectMocks
  private BrandController controller;

  @Mock
  private BrandService brandService;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new CommonExceptionHandler())
        .build();
  }

  @Test
  @DisplayName("브랜드가 존재하지 않을 경우")
  public void getBrand_brandNotExist() throws Exception {
    when(brandService.getBrand(any())).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/brands/test")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("brand test not found"));
  }

  @Test
  @DisplayName("브랜드가 존재할 경우")
  public void getBrand() throws Exception {
    BrandDto brand = mock(BrandDto.class);
    when(brand.getId()).thenReturn(1L);
    when(brand.getName()).thenReturn("무신사");

    when(brandService.getBrand("무신사")).thenReturn(Optional.of(brand));

    mockMvc.perform(get("/api/v1/brands/무신사")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("무신사"));
  }

  @Test
  @DisplayName("브랜드 저장이 실패할 경우")
  public void saveBrand_fail() throws Exception {
    when(brandService.saveBrand(any())).thenThrow(new RuntimeException("서버 오류"));

    String requestContent = objectMapper.writeValueAsString(Map.of(
        "name", "무신사"
    ));

    mockMvc.perform(post("/api/v1/brands")
            .content(requestContent)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
        .andExpect(jsonPath("$.message").value("서버 오류"));
  }

  @Test
  public void getBrands() throws Exception {
    BrandDto brand1 = mock(BrandDto.class);
    when(brand1.getId()).thenReturn(1L);
    when(brand1.getName()).thenReturn("무신사");

    BrandDto brand2 = mock(BrandDto.class);
    when(brand2.getId()).thenReturn(2L);
    when(brand2.getName()).thenReturn("나이키");

    when(brandService.getBrands()).thenReturn(List.of(brand1, brand2));

    mockMvc.perform(get("/api/v1/brands")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[0].name").value("무신사"))
        .andExpect(jsonPath("$[1].id").value("2"))
        .andExpect(jsonPath("$[1].name").value("나이키"));
  }

  @Test
  @DisplayName("브랜드 저장 성공")
  public void saveBrand() throws Exception {
    BrandEntity entity = mock(BrandEntity.class);
    when(entity.getId()).thenReturn(1L);
    when(entity.getName()).thenReturn("무신사");

    when(brandService.saveBrand(any())).thenReturn(entity);

    String requestContent = objectMapper.writeValueAsString(Map.of(
        "name", "무신사r"
    ));

    mockMvc.perform(post("/api/v1/brands")
            .content(requestContent)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("무신사"));
  }
}