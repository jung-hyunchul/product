package com.homework.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.homework.core.dto.CategoryDto;
import com.homework.core.entity.CategoryEntity;
import com.homework.core.repository.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class CategoryServiceTest {

  @InjectMocks
  private CategoryService service;

  @Mock
  private CategoryRepository repository;

  @Test
  public void tetGetCategory() {
    CategoryEntity category = mock(CategoryEntity.class);
    when(category.getId()).thenReturn(1L);
    when(category.getName()).thenReturn("양말");

    when(repository.findByName("양말")).thenReturn(Optional.of(category));

    var result = service.getCategory("양말");

    assertThat(result.isPresent()).isTrue();
    assertThat(result.get().getId()).isEqualTo(1L);
    assertThat(result.get().getName()).isEqualTo("양말");
  }

  @Test
  public void testSave() {
    CategoryEntity category = mock(CategoryEntity.class);
    when(category.getName()).thenReturn("상의");

    when(repository.findByName("상의")).thenReturn(Optional.of(category));

    var result = service.saveCategory(CategoryDto.builder().name("상의").build());

    verify(repository, times(1)).save(argThat(
        e -> e.getName().equals("상의")
    ));
  }
}