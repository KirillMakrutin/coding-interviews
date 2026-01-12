package com.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Todo(int id,
                   @JsonProperty("todo") String description,
                   boolean completed,
                   int userId) {

}
