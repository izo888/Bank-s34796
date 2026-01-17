package s34796Bank;

import java.math.BigDecimal;

public class TransactionResult {
    private final TransactionStatus status;
    private final BigDecimal newBalance;
    private final String message;

    public TransactionResult(TransactionStatus status, BigDecimal newBalance, String message) {
        this.status = status;
        this.newBalance = newBalance;
        this.message = message;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public String getMessage() {
        return message;
    }
}
