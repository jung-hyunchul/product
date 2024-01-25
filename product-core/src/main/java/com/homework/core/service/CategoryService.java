package com.homework.core.service;

import com.homework.core.dto.CategoryDto;
import com.homework.core.entity.CategoryEntity;
import com.homework.core.repository.CategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public Optional<CategoryDto> getCategory(String name) {
    return categoryRepository.findByName(name)
        .map(e -> CategoryDto.builder()
            .id(e.getId())
            .name(e.getName())
            .build());
  }

  @Transactional
  public CategoryEntity saveCategory(CategoryDto request) {
    CategoryEntity category = categoryRepository.findByName(request.getName())
        .orElse(CategoryEntity.builder()
            .name(request.getName())
            .build());

    return categoryRepository.save(category);
  }
}
