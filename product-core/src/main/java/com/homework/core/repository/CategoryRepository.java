package com.homework.core.repository;

import com.homework.core.entity.CategoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  Optional<CategoryEntity> findByName(String name);
}
