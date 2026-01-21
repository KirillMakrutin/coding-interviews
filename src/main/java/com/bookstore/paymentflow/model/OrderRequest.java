package com.bookstore.paymentflow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderRequest {
  private Integer orderedBookId;
  private Boolean payLater;
  private Boolean useBonus;
  private String paymentProfileId;
  private String deliveryAddressId;
}
