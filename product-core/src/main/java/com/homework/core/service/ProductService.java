package com.homework.core.service;

import com.homework.core.dto.ProductDto;
import com.homework.core.entity.CategoryEntity;
import com.homework.core.entity.ProductEntity;
import com.homework.core.repository.CategoryRepository;
import com.homework.core.repository.ProductQuerydslRepository;
import com.homework.core.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductQuerydslRepository productQuerydslRepository;

  @Transactional(readOnly = true)
  public List<ProductDto> getLeastExpensiveProducts() {
    List<CategoryEntity> categories = categoryRepository.findAll();

    List<ProductEntity> results = new ArrayList<>();
    for (CategoryEntity category : categories) {
      ProductEntity product = productRepository.findTopByCategoryOrderByPriceAsc(category);
      results.add(product);
    }

    return results.stream()
        .map(ProductDto::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public long getLeastExpensiveBrand() {
    return productRepository.findLeastExpensiveBrandId();
  }

  @Transactional(readOnly = true)
  public List<ProductDto> getCategories(long brandId) {
    return productQuerydslRepository.findProductsByBrandId(brandId);
  }

  @Transactional(readOnly = true)
  public ProductDto getMostExpensiveProductOfCategory(String categoryName) {
    return productQuerydslRepository.findMostExpensiveProduct(categoryName);
  }

  @Transactional(readOnly = true)
  public ProductDto getLeastExpensiveProductOfCategory(String categoryName) {
    return productQuerydslRepository.findLeastExpensiveProduct(categoryName);
  }
}
