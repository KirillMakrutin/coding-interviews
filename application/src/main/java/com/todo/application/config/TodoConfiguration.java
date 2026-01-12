package com.todo.application.config;

import com.todo.config.TodoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class TodoConfiguration {

  @Bean
  public RestClient todoRestClient(TodoProperties todoProperties,
      RestClient.Builder builder) {
    return builder
        .baseUrl(todoProperties.getBaseUrl())
        .build();
  }
}
