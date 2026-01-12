package com.todo;

import com.todo.config.TodoProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@ComponentScan
@Configuration
public class RemoteTodoConfiguration {

  @ConditionalOnMissingBean(name = "todoRestClient")
  @Bean
  RestClient todoRestClient(TodoProperties todoProperties,
      RestClient.Builder builder) {
    return builder
        .baseUrl(todoProperties.getBaseUrl())
        .build();
  }
}
