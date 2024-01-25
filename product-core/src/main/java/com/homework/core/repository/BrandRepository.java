package com.homework.core.repository;

import com.homework.core.entity.BrandEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

  Optional<BrandEntity> findByName(String name);
}
