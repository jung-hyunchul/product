package com.homework.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {"com.homework.api", "com.homework.core"})
//@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"com.homework.core.config", "com.homework.api.config"})
public class ProductApiApplication {

  public static void main(String[] args) {
    System.setProperty("spring.devtools.restart.enabled", "false");
    System.setProperty("user.timezone", "UTC");

    SpringApplication.run(ProductApiApplication.class, args);
  }
}