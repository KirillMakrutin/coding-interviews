package com.bookstore.paymentflow.controller;

import com.bookstore.paymentflow.exception.OrderException;
import com.bookstore.paymentflow.model.OrderRequest;
import com.bookstore.paymentflow.model.OrderResponse;
import com.bookstore.paymentflow.service.BookService;
import com.bookstore.paymentflow.service.OrderService;
import com.bookstore.paymentflow.service.PaymentProfileService;
import com.bookstore.paymentflow.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookStoreController {

  private final BookService bookService;
  private final UserService userService;
  private final PaymentProfileService paymentProfileService;
  private final OrderService orderService;

  @GetMapping({"", "/", "/bookstore"})
  public String getBookstoreHomePage() {
    return "index";
  }

  @GetMapping("/bookstore/order")
  public String getBooksPage(Model model) {
    // reset payment method balances every time we load the bookstore page for testing purposes
    paymentProfileService.resetBalances();

    model.addAttribute("books", bookService.getBooks());
    model.addAttribute("addresses", userService.getAddresses());
    model.addAttribute("paymentProfiles", paymentProfileService.getPaymentProfiles());
    model.addAttribute("bonusBalance", paymentProfileService.getBonusBalance());
    return "order";
  }

  @ResponseBody
  @PostMapping(value = "/bookstore/order", produces = "application/json", consumes = "application/json")
  public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
    return orderService.createOrder(orderRequest);
  }

  @ExceptionHandler
  public ResponseEntity<?> handleOrderException(OrderException e) {
    return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleException(Exception e, HttpServletRequest request) {
    String path = request.getRequestURI();
    String method = request.getMethod();
    log.error("Failed to handle a request for {} {}", method, path, e);
    return ResponseEntity.internalServerError().build();
  }

}
