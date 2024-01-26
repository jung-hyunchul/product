package com.homework.core.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.homework.core.config.DatasourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@ContextConfiguration(classes = DatasourceConfig.class)
class ProductQuerydslRepositoryTest {

  @Autowired
  ProductQuerydslRepository productQuerydslRepository;

  @Test
  void injectedComponentsAreNotNull() {
    assertThat(productQuerydslRepository).isNotNull();
  }
}