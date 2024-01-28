package com.homework.core.config;

import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.homework.core.repository"},
    transactionManagerRef = "productTransactionManager",
    entityManagerFactoryRef = "productEntityManagerFactory")
@EntityScan(basePackages = {"com.homework.core.entity"})
@EnableTransactionManagement
@RequiredArgsConstructor
public class JpaConfig {

  private final EntityManagerFactoryBuilder builder;
  private final DataSource productDataSource;
  private final JpaVendorAdapter vendorAdapter;

  private final HibernateProperties properties;

  @Bean
  LocalContainerEntityManagerFactoryBean productEntityManagerFactory(
      ConfigurableListableBeanFactory beanFactory
  ) {
    LocalContainerEntityManagerFactoryBean factoryBean = builder
        .dataSource(productDataSource)
        .packages("com.homework.core.entity")
        .build();
    factoryBean.setJpaVendorAdapter(vendorAdapter);
    factoryBean.setJpaPropertyMap(Map.of(
        "hibernate.implicit_naming_strategy", properties.getNaming().getImplicitStrategy(),
        "hibernate.physical_naming_strategy", properties.getNaming().getPhysicalStrategy(),
        "hibernate.hbm2ddl.auto", properties.getDdlAuto()
    ));

    return factoryBean;
  }

  @Bean
  JpaTransactionManager productTransactionManager(ConfigurableListableBeanFactory beanFactory) {
    return new JpaTransactionManager(
        Objects.requireNonNull(productEntityManagerFactory(beanFactory).getObject()));
  }
}
