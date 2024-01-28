package com.homework.core.repository;

import com.homework.core.entity.BrandEntity;
import com.homework.core.entity.CategoryEntity;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductRepositoryTest extends BaseRepositoryTest {

  @Autowired
  ProductRepository productRepository;
  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  BrandRepository brandRepository;

  @Test
  void findByCategoryAndBrand() {
    Optional<CategoryEntity> category = categoryRepository.findById(1L);
    Optional<BrandEntity> brand = brandRepository.findById(3L);

    var result = productRepository.findByCategoryAndBrand(category.get(), brand.get());

    AssertionsForClassTypes.assertThat(result.isPresent()).isTrue();
    AssertionsForClassTypes.assertThat(result.get().getId()).isEqualTo(17);
    AssertionsForClassTypes.assertThat(result.get().getPrice()).isEqualTo(10_000);
  }

  @Test
  void findTopByCategoryOrderByPriceAsc() {
    Optional<CategoryEntity> category = categoryRepository.findById(3L);

    var result = productRepository.findTopByCategoryOrderByPriceAsc(category.get());

    AssertionsForClassTypes.assertThat(result.getId()).isEqualTo(27);
    AssertionsForClassTypes.assertThat(result.getPrice()).isEqualTo(3_000);
  }

  @Test
  void findLeastExpensiveBrandId() {
    var result = productRepository.findLeastExpensiveBrandId();

    AssertionsForClassTypes.assertThat(result).isEqualTo(4L);
  }
}