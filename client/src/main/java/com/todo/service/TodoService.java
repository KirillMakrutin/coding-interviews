package com.todo.service;

import com.todo.dto.Todo;
import java.util.List;

public interface TodoService {
  List<Todo> findAll();

  Todo save(String todo, int userId);
}
