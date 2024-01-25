package com.homework.core.repository;

import com.homework.core.entity.BrandEntity;
import com.homework.core.entity.CategoryEntity;
import com.homework.core.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByCategoryAndBrand(CategoryEntity category, BrandEntity brand);

  ProductEntity findTopByCategoryOrderByPriceAsc(CategoryEntity category);

  @Query(value = "select sub.brand_id \n"
      + "from ( \n"
      + "select brand_id, sum(price) as sumOfPrice \n"
      + "from product \n"
      + "group by brand_id \n"
      + ") sub \n"
      + "order by sumOfPrice asc \n"
      + "limit 1;", nativeQuery = true)
  long findLeastExpensiveBrandId();
}
