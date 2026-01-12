package com.todo.application.controller;

import com.todo.dto.Todo;
import com.todo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {

  private final TodoService todoService;

  @GetMapping
  public List<Todo> getAllTodos() {
    return todoService.findAll();
  }

  @PostMapping
  public Todo createTodo(String todo, int userId) {
    return todoService.save(todo, userId);
  }
}
