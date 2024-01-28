package com.homework.core.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductQuerydslRepositoryTest extends BaseRepositoryTest {

  @Autowired
  ProductQuerydslRepository productQuerydslRepository;

  @Test
  void findProductsByBrandId() {
    var result = productQuerydslRepository.findProductsByBrandId(1L);

    assertThat(result.size()).isEqualTo(8);
    assertThat(result.get(0).getBrandName()).isEqualTo("A");
    assertThat(result.get(0).getCategoryName()).isEqualTo("상의");
    assertThat(result.get(0).getPrice()).isEqualTo(11_200L);
    assertThat(result.get(0).getBrandName()).isEqualTo("A");
    assertThat(result.get(7).getCategoryName()).isEqualTo("액세서리");
    assertThat(result.get(7).getPrice()).isEqualTo(2_300L);
  }

  @Test
  void findMostExpensiveProduct() {
    var result = productQuerydslRepository.findMostExpensiveProduct("상의");

    assertThat(result.getBrandName()).isEqualTo("I");
    assertThat(result.getPrice()).isEqualTo(11_400L);
  }

  @Test
  void findLeastExpensiveProduct() {
    var result = productQuerydslRepository.findLeastExpensiveProduct("상의");

    assertThat(result.getBrandName()).isEqualTo("C");
    assertThat(result.getPrice()).isEqualTo(10_000L);
  }
}