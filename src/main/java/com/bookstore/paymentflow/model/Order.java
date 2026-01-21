package com.bookstore.paymentflow.model;

import lombok.Data;

@Data
public class Order {
  private Integer orderId;
  private Integer userId;
  private Integer bookId;
  private String paymentProfileId;
  private String deliveryAddressId;
  private boolean paid;
}
