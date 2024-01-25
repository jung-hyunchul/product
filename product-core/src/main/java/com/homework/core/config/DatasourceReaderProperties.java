package com.homework.core.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.datasource.reader.hikari")
public class DatasourceReaderProperties extends HikariDataSource {}
