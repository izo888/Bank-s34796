package s34796Bank;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class BankService {

    private final ClientStorage clientStorage;

    public BankService(ClientStorage clientStorage) {
        this.clientStorage = clientStorage;
    }

    public TransactionResult registerClient(String clientId, BigDecimal initialBalance) {
        if (clientId == null || clientId.isBlank()) {
            return new TransactionResult(TransactionStatus.DECLINED, null, "CLIENT_ID_REQUIRED");
        }
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            return new TransactionResult(TransactionStatus.DECLINED, null, "INVALID_INITIAL_BALANCE");
        }
        if (clientStorage.exists(clientId)) {
            BigDecimal bal = clientStorage.findById(clientId).map(Client::getBalance).orElse(null);
            return new TransactionResult(TransactionStatus.DECLINED, bal, "CLIENT_ALREADY_REGISTERED");
        }

        Client client = new Client(clientId, initialBalance);
        clientStorage.add(client);
        return new TransactionResult(TransactionStatus.ACCEPTED, client.getBalance(), "REGISTERED");
    }

    public Client getClient(String clientId) {
        return clientStorage.findById(clientId).orElse(null);
    }

    public TransactionResult deposit(String clientId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new TransactionResult(TransactionStatus.DECLINED, null, "INVALID_AMOUNT");
        }

        Client client = getClient(clientId);
        if (client == null) {
            return new TransactionResult(TransactionStatus.DECLINED, null, "CLIENT_NOT_REGISTERED");
        }

        client.setBalance(client.getBalance().add(amount));
        return new TransactionResult(TransactionStatus.ACCEPTED, client.getBalance(), "DEPOSIT_ACCEPTED");
    }

    public TransactionResult transfer(String clientId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new TransactionResult(TransactionStatus.DECLINED, null, "INVALID_AMOUNT");
        }

        Client client = getClient(clientId);
        if (client == null) {
            return new TransactionResult(TransactionStatus.DECLINED, null, "CLIENT_NOT_REGISTERED");
        }

        if (client.getBalance().compareTo(amount) < 0) {
            return new TransactionResult(TransactionStatus.DECLINED, client.getBalance(), "INSUFFICIENT_FUNDS");
        }

        client.setBalance(client.getBalance().subtract(amount));
        return new TransactionResult(TransactionStatus.ACCEPTED, client.getBalance(), "TRANSFER_ACCEPTED");
    }
}