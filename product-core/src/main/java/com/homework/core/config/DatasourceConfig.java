package com.homework.core.config;

import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

  private final DatasourceWriterProperties writerProperties;

  private final DatasourceReaderProperties readerProperties;

  @Bean
  DataSource writerDataSource() {
    return DataSourceBuilder.derivedFrom(writerProperties).build();
  }

  @Bean
  DataSource readerDataSource() {
    return DataSourceBuilder.derivedFrom(readerProperties).build();
  }

  @Bean
  DataSource dynamicDataSource() {
    DatasourceRouter router = new DatasourceRouter();
    router.setTargetDataSources(
        Map.of(
            DatasourceType.WRITER, writerDataSource(),
            DatasourceType.READER, readerDataSource()));
    router.setDefaultTargetDataSource(writerDataSource());

    return router;
  }

  @Bean
  @Primary
  DataSource dataSource() {
    return new LazyConnectionDataSourceProxy(dynamicDataSource());
  }
}
