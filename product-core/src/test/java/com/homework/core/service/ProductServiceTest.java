package com.homework.core.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.homework.core.entity.BrandEntity;
import com.homework.core.entity.CategoryEntity;
import com.homework.core.entity.ProductEntity;
import com.homework.core.repository.CategoryRepository;
import com.homework.core.repository.ProductQuerydslRepository;
import com.homework.core.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class ProductServiceTest {

  @InjectMocks
  private ProductService service;

  @Mock
  private ProductRepository productRepository;
  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private ProductQuerydslRepository productQuerydslRepository;

  @Test
  public void testGetLeastExpensiveProducts() {
    CategoryEntity category1 = mock(CategoryEntity.class);

    CategoryEntity category2 = mock(CategoryEntity.class);

    CategoryEntity category3 = mock(CategoryEntity.class);

    when(categoryRepository.findAll()).thenReturn(List.of(category1, category2, category3));

    ProductEntity product1 = mock(ProductEntity.class);
    when(product1.getBrand()).thenReturn(mock(BrandEntity.class));
    when(product1.getCategory()).thenReturn(mock(CategoryEntity.class));

    when(productRepository.findTopByCategoryOrderByPriceAsc(any())).thenReturn(product1);

    var result = service.getLeastExpensiveProducts();

    verify(productRepository, times(3)).findTopByCategoryOrderByPriceAsc(any());
  }

  @Test
  public void testGetLeastExpensiveBrand() {
    service.getLeastExpensiveBrand();

    verify(productRepository, times(1)).findLeastExpensiveBrandId();
  }

  @Test
  public void testGetCategories() {
    service.getCategories(1L);

    verify(productQuerydslRepository, times(1)).findProductsByBrandId(1L);
  }

  @Test
  public void testGetMostExpensiveProductOfCategory() {
    service.getMostExpensiveProductOfCategory("양말");

    verify(productQuerydslRepository, times(1)).findMostExpensiveProduct("양말");
  }

  @Test
  public void getLeastExpensiveProductOfCategory() {
    service.getLeastExpensiveProductOfCategory("양말");

    verify(productQuerydslRepository, times(1)).findLeastExpensiveProduct("양말");
  }
}