package com.homework.core.repository;

import static com.homework.core.entity.QProductEntity.productEntity;

import com.homework.core.dto.ProductDto;
import com.homework.core.entity.ProductEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProductQuerydslRepository extends QuerydslRepositorySupport {

  private final JPAQueryFactory jpaQueryFactory;

  public ProductQuerydslRepository(JPAQueryFactory jpaQueryFactory) {
    super(ProductEntity.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  public List<ProductDto> findProductsByBrandId(Long brandId) {
    return jpaQueryFactory.select(
            Projections.fields(ProductDto.class, productEntity.category.id.as("categoryId"),
                productEntity.category.name.as("categoryName"),
                productEntity.brand.id.as("brandId"),
                productEntity.brand.name.as("brandName"),
                productEntity.price.as("price")))
        .from(productEntity)
        .innerJoin(productEntity.category)
        .innerJoin((productEntity.brand))
        .where(
            productEntity.brand.id.eq(brandId)
        )
        .fetch();
  }

  public ProductDto findMostExpensiveProduct(String categoryName) {
    return jpaQueryFactory.select(
            Projections.fields(ProductDto.class, productEntity.brand.id.as("brandId"),
                productEntity.brand.name.as("brandName"),
                productEntity.price.max().as("price")))
        .from(productEntity)
        .innerJoin(productEntity.category)
        .innerJoin((productEntity.brand))
        .where(
            productEntity.category.name.eq(categoryName)
        )
        .orderBy(productEntity.price.max().desc())
        .groupBy(productEntity.brand.id)
        .limit(1)
        .fetchOne();
  }

  public ProductDto findLeastExpensiveProduct(String categoryName) {
    return jpaQueryFactory.select(
            Projections.fields(ProductDto.class, productEntity.brand.id.as("brandId"),
                productEntity.brand.name.as("brandName"),
                productEntity.price.max().as("price")))
        .from(productEntity)
        .innerJoin(productEntity.category)
        .innerJoin((productEntity.brand))
        .where(
            productEntity.category.name.eq(categoryName)
        )
        .orderBy(productEntity.price.max().asc())
        .groupBy(productEntity.brand.id)
        .limit(1)
        .fetchOne();
  }

//
//  public Optional<SettlementSchedule> findByTargetMonthAndBaselineDate(
//      YearMonth targetMonth, LocalDate baselineDate, SettlementSchedule.Type type,
//      List<SettlementSchedule.Status> statuses
//  ) {
//    SettlementSchedule schedule = jpaQueryFactory.selectFrom(settlementSchedule)
//        .where(
//            settlementSchedule.targetMonth.eq(targetMonth),
//            settlementSchedule.baselineDate.eq(baselineDate),
//            settlementSchedule.type.eq(type),
//            settlementSchedule.status.in(statuses)
//        )
//        .distinct()
//        .fetchOne();
//
//    return Optional.ofNullable(schedule);
//  }
//
//  public Optional<Long> fetchIdsByTargetMonthAndBaselineDate(YearMonth targetMonth,
//      LocalDate baselineDate) {
//    return Optional.ofNullable(
//        jpaQueryFactory.select(settlementSchedule.id)
//            .from(settlementSchedule)
//            .where(
//                settlementSchedule.targetMonth.eq(targetMonth),
//                settlementSchedule.baselineDate.eq(baselineDate),
//                settlementSchedule.status.in(Status.getCompletedStatuses())
//            )
//            .distinct()
//            .fetchOne()
//    );
//  }
//
//  public List<SettlementSchedule> findAllByTargetMonthAndType(YearMonth targetMonth,
//      SettlementSchedule.Type type) {
//
//    return jpaQueryFactory.selectFrom(settlementSchedule)
//        .where(
//            settlementSchedule.targetMonth.eq(targetMonth),
//            settlementSchedule.type.eq(type),
//            settlementSchedule.status.in(Status.getCompletedStatuses())
//        )
//        .orderBy(settlementSchedule.baselineDate.desc())
//        .fetch();
//  }
}
