package com.homework.core.service;

import com.homework.core.dto.BrandDto;
import com.homework.core.entity.BrandEntity;
import com.homework.core.repository.BrandRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepository;

  @Transactional(readOnly = true)
  public Optional<BrandDto> getBrand(String name) {
    return brandRepository.findByName(name)
        .map(e -> BrandDto.builder()
            .id(e.getId())
            .name(e.getName())
            .build());
  }

  @Transactional(readOnly = true)
  public List<BrandDto> getBrands() {
    return brandRepository.findAll().stream()
        .map(e -> BrandDto.builder()
            .id(e.getId())
            .name(e.getName())
            .build())
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<BrandDto> getBrandById(Long id) {
    return brandRepository.findById(id)
        .map(e -> BrandDto.builder()
            .id(e.getId())
            .name(e.getName())
            .build());
  }

  @Transactional
  public BrandEntity saveBrand(BrandDto request) {
    BrandEntity brand = brandRepository.findByName(request.getName())
        .orElse(BrandEntity.builder()
            .name(request.getName())
            .build());

    return brandRepository.save(brand);
  }
}
