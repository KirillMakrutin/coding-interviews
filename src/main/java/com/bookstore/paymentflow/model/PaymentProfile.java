package com.bookstore.paymentflow.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentProfile {

  private String id;
  private String cardNumberLast4;
  private boolean isDefault;
  private BigDecimal balance;

  public void charge(BigDecimal amount) {
    if (balance.compareTo(amount) >= 0) {
      balance = balance.subtract(amount);
    } else {
      throw new IllegalArgumentException("Insufficient balance");
    }
  }
}
