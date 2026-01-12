package com.todo.application.service;

import com.todo.dto.Todo;
import com.todo.service.TodoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("todoService")
public class InMemoryTodoService implements TodoService {

  private final A aService;
  private final TodoService remoteTodoService;

  private final Map<Integer, Todo> localTodos = new HashMap<>();
  private final AtomicInteger atomicInteger = new AtomicInteger(1);

  public InMemoryTodoService(A aService,
      TodoService remoteTodoService) {
    this.aService = aService;
    this.remoteTodoService = remoteTodoService;
  }

  @Override
  public Todo save(String description, int userId) {
    int id = atomicInteger.getAndIncrement();
    Todo todo = new Todo(id, description, false, userId);
    localTodos.put(id, todo);
    log.info("Saved todo: {}", todo);
    return todo;
  }

  @Override
  public List<Todo> findAll() {
    aService.doSomething1();

    List<Todo> remoteTodos = remoteTodoService.findAll();
    // keep unique todos by id
    Map<Integer, Todo> allTodos = remoteTodos.stream()
        .collect(Collectors.toMap(Todo::id, Function.identity()));
    allTodos.putAll(localTodos);

    return new ArrayList<>(allTodos.values());
  }
}
