package com.todo.service;

import com.todo.dto.Todo;
import java.util.List;
import org.springframework.web.client.RestClient;

public class RemoteTodoService implements TodoService {

  private final RestClient restClient;

  public RemoteTodoService(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public List<Todo> findAll() {
    return restClient.get().uri("/todos")
        .retrieve()
        .body(Todos.class)
        .getTodos();
  }

  @Override
  public Todo save(String todo, int userId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public static class Todos {
    private List<Todo> todos;

    public List<Todo> getTodos() {
      return todos;
    }

    public void setTodos(List<Todo> todos) {
      this.todos = todos;
    }
  }
}
