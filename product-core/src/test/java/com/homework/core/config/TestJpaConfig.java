package com.homework.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.homework.core.repository"}
)
@EntityScan(basePackages = {"com.homework.core.entity"})
@EnableTransactionManagement
@RequiredArgsConstructor
public class TestJpaConfig {

}
