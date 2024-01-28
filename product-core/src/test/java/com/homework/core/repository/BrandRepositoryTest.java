package com.homework.core.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BrandRepositoryTest extends BaseRepositoryTest {

  @Autowired
  BrandRepository brandRepository;

  @Test
  void findByName() {
    var result = brandRepository.findByName("A");

    Assertions.assertThat(result.isPresent()).isTrue();
    Assertions.assertThat(result.get().getId()).isEqualTo(1L);
    Assertions.assertThat(result.get().getName()).isEqualTo("A");
  }
}