package com.homework.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.homework.core.dto.BrandDto;
import com.homework.core.entity.BrandEntity;
import com.homework.core.repository.BrandRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class BrandServiceTest {

  @InjectMocks
  private BrandService service;

  @Mock
  private BrandRepository repository;

  @Test
  public void tetGetBrand() {
    BrandEntity brand = mock(BrandEntity.class);
    when(brand.getId()).thenReturn(1L);
    when(brand.getName()).thenReturn("무신사");

    when(repository.findByName("무신사")).thenReturn(Optional.of(brand));

    var result = service.getBrand("무신사");

    assertThat(result.isPresent()).isTrue();
    assertThat(result.get().getId()).isEqualTo(1L);
    assertThat(result.get().getName()).isEqualTo("무신사");
  }

  @Test
  public void testSave() {
    BrandEntity brand = mock(BrandEntity.class);
    when(brand.getName()).thenReturn("무신사");

    when(repository.findByName("무신사")).thenReturn(Optional.of(brand));

    var result = service.saveBrand(BrandDto.builder().name("무신사").build());

    verify(repository, times(1)).save(argThat(
        e -> e.getName().equals("무신사")
    ));
  }
}