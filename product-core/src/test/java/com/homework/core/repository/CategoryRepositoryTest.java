package com.homework.core.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  CategoryRepository categoryRepository;

  @Test
  void findByName() {
    var result = categoryRepository.findByName("바지");

    AssertionsForClassTypes.assertThat(result.isPresent()).isTrue();
    AssertionsForClassTypes.assertThat(result.get().getId()).isEqualTo(3L);
    AssertionsForClassTypes.assertThat(result.get().getName()).isEqualTo("바지");
  }
}