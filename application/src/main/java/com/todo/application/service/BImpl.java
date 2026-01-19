package com.todo.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * A->B->A: These are just sample services to demonstrate circular dependency resolution
 */
@Slf4j
@Service
public class BImpl implements B {
  private final A aService;

  public BImpl(A aService) {
    this.aService = aService;
    log.info("BImpl created");
  }

  @Override
  public void doSomething1() {
    log.info("B.doSomething1");
  }

  @Override
  public void doSomething2() {
    log.info("B.doSomething2");
    aService.doSomething2();
  }
}
