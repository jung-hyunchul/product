package com.homework.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "product")
@Builder(access = AccessLevel.PUBLIC)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ProductEntity {

  @Id
  @Column(name = "id", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private BrandEntity brand;

  @ManyToOne(fetch = FetchType.LAZY)
  private CategoryEntity category;

  @Column(name = "price", columnDefinition = "bigint", nullable = false)
  private Long price;

  @Column(name = "name", columnDefinition = "VARCHAR(20)", nullable = false)
  private String name;

}
