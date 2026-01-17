package s34796Bank;

import java.math.BigDecimal;
import java.util.Objects;

public class Client {
    private  String id;
    private BigDecimal balance;

    public Client(String id, BigDecimal balance) {
        this.id = Objects.requireNonNull(id, "id");
        this.balance = Objects.requireNonNull(balance, "Stan rachunku");
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = Objects.requireNonNull(balance, "Stan rachunku");
    }
    }

