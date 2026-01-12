package com.todo.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BImpl implements B {
  private final A aService;

  public BImpl(A aService) {
    this.aService = aService;
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
