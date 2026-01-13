import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class StreamTask {

  public static void main(String[] args) {
    // TASK 1
    // 1. Get rid of duplicate transactions (all fields are the same)
    // 2. From the unique transactions, filter only COMPLETED transactions
    // 3. Calculate the total amount of the COMPLETED transactions per payerId
    Map<Integer, BigDecimal> totalAmountPerPayerId = null;
    System.out.println(totalAmountPerPayerId);

    // TASK 2
    // Find those transactions that do not chang their status from PENDING to REJECTED or COMPLETED
    List<Transaction> unchangedStatusTransactions = null;
    System.out.println(unchangedStatusTransactions);
  }

}

class Transaction {
  private Integer id;
  private BigDecimal amount;
  private Integer payerId;
  private TransactionStatus status;

  public Transaction(Integer id, BigDecimal amount, Integer payerId, TransactionStatus status) {
    this.id = id;
    this.amount = amount;
    this.payerId = payerId;
    this.status = status;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Integer getPayerId() {
    return payerId;
  }

  public void setPayerId(Integer payerId) {
    this.payerId = payerId;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "id=" + id +
        ", amount=" + amount +
        ", payerId=" + payerId +
        ", status=" + status +
        '}';
  }
}

enum TransactionStatus {
  PENDING, COMPLETED, REJECTED
}

interface Transactions {
  List<Transaction> TRANSACTIONS = List.of(
      new Transaction(4, BigDecimal.valueOf(400.004), 3, TransactionStatus.COMPLETED),
      new Transaction(6, BigDecimal.valueOf(600.006), 3, TransactionStatus.REJECTED),
      new Transaction(1, BigDecimal.valueOf(100.001), 1, TransactionStatus.PENDING),
      new Transaction(2, BigDecimal.valueOf(200.002), 2, TransactionStatus.COMPLETED),
      new Transaction(3, BigDecimal.valueOf(300.003), 1, TransactionStatus.PENDING),
      new Transaction(3, BigDecimal.valueOf(300.003), 1, TransactionStatus.PENDING),
      new Transaction(8, BigDecimal.valueOf(800.008), 2, TransactionStatus.COMPLETED),
      new Transaction(9, BigDecimal.valueOf(900.009), 3, TransactionStatus.PENDING),
      new Transaction(4, BigDecimal.valueOf(400.004), 3, TransactionStatus.PENDING),
      new Transaction(5, BigDecimal.valueOf(500.005), 2, TransactionStatus.PENDING),
      new Transaction(5, BigDecimal.valueOf(500.005), 2, TransactionStatus.PENDING),
      new Transaction(6, BigDecimal.valueOf(600.006), 3, TransactionStatus.REJECTED),
      new Transaction(7, BigDecimal.valueOf(700.007), 1, TransactionStatus.COMPLETED),
      new Transaction(8, BigDecimal.valueOf(800.008), 2, TransactionStatus.COMPLETED),
      new Transaction(3, BigDecimal.valueOf(300.003), 1, TransactionStatus.REJECTED)
      );
}
