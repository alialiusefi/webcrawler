package com.softeq.webcrawler.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .pathMapping("/")
        .apiInfo(ApiInfo.DEFAULT)
        .forCodeGeneration(true)
        .genericModelSubstitutes(ResponseEntity.class)
        .ignoredParameterTypes(Pageable.class)
        .ignoredParameterTypes(java.sql.Date.class)
        .directModelSubstitute(LocalDate.class, java.sql.Date.class)
        .directModelSubstitute(ZonedDateTime.class, Date.class)
        .directModelSubstitute(LocalDateTime.class, Date.class)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

}
