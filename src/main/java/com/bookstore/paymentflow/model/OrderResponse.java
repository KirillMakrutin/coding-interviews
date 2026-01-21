package com.bookstore.paymentflow.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderResponse {

  private Order order;
  private Address deliveryAddress;
  private Book book;
  private boolean paid;
  private boolean futurePayment;
  private BigDecimal bonusBalance;
}
