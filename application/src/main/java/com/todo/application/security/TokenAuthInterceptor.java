package com.todo.application.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenAuthInterceptor implements HandlerInterceptor {

  private final String authorizationToken;

  public TokenAuthInterceptor(@Value("todo.authorization.token") String authorizationToken) {
    this.authorizationToken = authorizationToken;
  }

  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response,
      Object handler) {
    String method = request.getMethod();
    if (!method.equals("POST") && !method.equals("PUT") && !method.equals("DELETE")) {
      return true;
    }

    String authHeader = request.getHeader("X-TOKEN");
    if (authorizationToken == null || !authorizationToken.equals(authHeader)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

    return true;
  }
}
