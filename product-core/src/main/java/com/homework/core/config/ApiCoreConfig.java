package com.homework.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>해당하는 도메인의 비즈니스 로직부를 로드합니다. Domain, Infrastructure 에 관한 것은 모두 Core 패키지에 작성하세요.
 */
@Configuration
@ComponentScan(basePackages = {"com.homework.core"})
public class ApiCoreConfig {

}
