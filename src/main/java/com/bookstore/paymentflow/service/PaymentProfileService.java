package com.bookstore.paymentflow.service;

import com.bookstore.paymentflow.model.PaymentProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentProfileService {

  private final static Map<String, BigDecimal> INITIAL_BALANCES = Map.of(
      "1", new BigDecimal("100.00"),
      "3", new BigDecimal("15.00")
  );

  private final Map<BonusStatus, BigDecimal> INITIAL_BONUSES = Map.of(
      BonusStatus.AVAILABLE, new BigDecimal("50.00"),
      BonusStatus.RESERVED, new BigDecimal("0.00"),
      BonusStatus.USED, new BigDecimal("10.00")
  );

  private final Map<String, PaymentProfile> paymentProfiles = Map.of(
      "1", new PaymentProfile("1", "MasterCard **** 5678", false, INITIAL_BALANCES.get("1")),
      "3", new PaymentProfile("3", "Visa **** 1234", true, INITIAL_BALANCES.get("3")));

  private final Map<BonusStatus, BigDecimal> bonusBalances = new HashMap<>(INITIAL_BONUSES);

  public void resetBalances() {
    log.info("Resetting balances");

    bonusBalances.putAll(INITIAL_BONUSES);
    paymentProfiles.get("1").setBalance(INITIAL_BALANCES.get("1"));
    paymentProfiles.get("3").setBalance(INITIAL_BALANCES.get("3"));
  }

  /**
   * Reserve bonus amount if available, and it will be charged later when book is delivered by
   * showing a QR code.
   */
  public boolean reserveBonus(BigDecimal amount) {
    BigDecimal availableBonus = bonusBalances.get(BonusStatus.AVAILABLE);
    if (availableBonus.compareTo(amount) >= 0) {
      bonusBalances.put(BonusStatus.AVAILABLE, availableBonus.subtract(amount));
      bonusBalances.compute(BonusStatus.RESERVED, (k, v) -> v == null ? amount : v.add(amount));
      return true;
    }

    return false;
  }

  public void addBonusBalance(BigDecimal amount) {
    bonusBalances.compute(BonusStatus.AVAILABLE, (k, v) -> v == null ? amount : v.add(amount));
  }

  public void setBonusBalance(BigDecimal amount) {
    bonusBalances.put(BonusStatus.AVAILABLE, amount);
  }

  public BigDecimal getBonusBalance() {
    return bonusBalances.get(BonusStatus.AVAILABLE);
  }

  public List<PaymentProfile> getPaymentProfiles() {
    return new ArrayList<>(paymentProfiles.values());
  }

  public PaymentProfile getPaymentProfileByIdOrDefault(String id) {
    return Optional.ofNullable(id)
        .map(paymentProfiles::get)
        .orElseGet(this::getDefaultPaymentProfile);
  }

  public PaymentProfile getDefaultPaymentProfile() {
    return paymentProfiles.values().stream()
        .filter(PaymentProfile::isDefault)
        .findFirst()
        .orElse(null);
  }

  enum BonusStatus {
    AVAILABLE,
    RESERVED,
    USED
  }
}
