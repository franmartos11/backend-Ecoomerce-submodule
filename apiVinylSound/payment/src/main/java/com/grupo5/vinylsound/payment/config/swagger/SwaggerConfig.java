package com.grupo5.vinylsound.payment.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
      .group("payment")
      .packagesToScan("com.grupo5.vinylSound.payment.controller")
      .build();
  }

  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
      .info(new Info().title("cloud API")
        .description("cloud API")
        .version("1.0")
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }

}