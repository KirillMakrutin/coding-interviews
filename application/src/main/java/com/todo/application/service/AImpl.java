package com.todo.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * A->B->A: These are just sample services to demonstrate circular dependency resolution
 */
@Service
@Slf4j
public class AImpl implements A {
  private final B bService;

  public AImpl(B bService) {
    this.bService = bService;
    log.info("AImpl created");
  }

  @Override
  public void doSomething1() {
    log.info("A.doSomething1");
    bService.doSomething1();
  }

  @Override
  public void doSomething2() {
    log.info("A.doSomething2");
  }
}
