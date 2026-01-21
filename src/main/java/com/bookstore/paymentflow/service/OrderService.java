package com.bookstore.paymentflow.service;

import com.bookstore.paymentflow.exception.OrderException;
import com.bookstore.paymentflow.model.Address;
import com.bookstore.paymentflow.model.Book;
import com.bookstore.paymentflow.model.Order;
import com.bookstore.paymentflow.model.OrderRequest;
import com.bookstore.paymentflow.model.OrderResponse;
import com.bookstore.paymentflow.model.PaymentProfile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

  private static final Integer USER_ID = 1;
  private final AtomicInteger orderId = new AtomicInteger(0);
  private final Map<Integer, Order> orders = new LinkedHashMap<>();

  private final BookService bookService;
  private final UserService userService;
  private final PaymentProfileService paymentProfileService;

  public OrderResponse createOrder(OrderRequest orderRequest) {
    log.info("Creating order: {}", orderRequest);

    Order order = new Order();
    order.setOrderId(orderId.incrementAndGet());
    order.setUserId(USER_ID);
    BigDecimal currentBonusBalance = paymentProfileService.getBonusBalance();

    try {
      Book book = Optional
          .ofNullable(bookService.getBook(orderRequest.getOrderedBookId()))
          .orElseThrow(() -> new IllegalArgumentException(
              "Book with id " + orderRequest.getOrderedBookId() + " not found"));
      order.setBookId(book.getId());

      Address address = Optional
          .ofNullable(userService.getAddress(orderRequest.getDeliveryAddressId()))
          .orElseThrow(() -> new IllegalArgumentException(
              "Address with id " + orderRequest.getDeliveryAddressId() + " not found"));
      order.setDeliveryAddressId(address.getName());

      if (orderRequest.getUseBonus()
          && paymentProfileService.reserveBonus(BigDecimal.valueOf(book.getPrice()))) {
        order.setPaid(true);
      } else if (!Boolean.TRUE.equals(orderRequest.getPayLater())) {
        payNow(orderRequest, book, order);
      }

      orders.put(order.getOrderId(), order);
      log.info("Order created successfully: {}", order);

      OrderResponse orderResponse = new OrderResponse();
      orderResponse.setBook(book);
      orderResponse.setDeliveryAddress(address);
      orderResponse.setBonusBalance(paymentProfileService.getBonusBalance());
      orderResponse.setOrder(order);
      orderResponse.setPaid(order.isPaid());
      orderResponse.setFuturePayment(
          order.getPaymentProfileId() == null
              && (order.isPaid() || orderRequest.getPayLater()));

      return orderResponse;
    } catch (Exception e) {
      if (orderRequest.getUseBonus()) {
        // reset bonus balance in case of any error
        paymentProfileService.setBonusBalance(currentBonusBalance);
      }
      log.error("Error while creating order {}", order, e);
      throw new OrderException(
          "Failed to place order for book id " + orderRequest.getOrderedBookId());
    }
  }

  private void payNow(OrderRequest orderRequest, Book book, Order order) {
    String paymentProfileId = orderRequest.getPaymentProfileId();
    PaymentProfile paymentProfile = paymentProfileService.getPaymentProfileByIdOrDefault(
        paymentProfileId);
    if (paymentProfile != null) {
      try {
        BigDecimal bookPrice = BigDecimal.valueOf(book.getPrice());
        paymentProfile.charge(bookPrice);
        order.setPaymentProfileId(paymentProfile.getId());
        order.setPaid(true);
        paymentProfileService.addBonusBalance(
            bookPrice.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP));
      } catch (IllegalArgumentException e) {
        log.info("Insufficient funds to pay {}", book.getPrice());
      }
    }
  }
}
