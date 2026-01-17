package s34796Bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class S34796BankApplication {
    private final BankService bankService;

    public S34796BankApplication(BankService bankService) {
        this.bankService = bankService;

        var r1 = bankService.registerClient("c1", new BigDecimal("100"));
        System.out.println(r1.getStatus() + " " + r1.getNewBalance() + " " + r1.getMessage());

        var r2 = bankService.deposit("c1", new BigDecimal("50"));
        System.out.println(r2.getStatus() + " " + r2.getNewBalance() + " " + r2.getMessage());

        var r3 = bankService.transfer("c1", new BigDecimal("70"));
        System.out.println(r3.getStatus() + " " + r3.getNewBalance() + " " + r3.getMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(S34796BankApplication.class, args);
    }
}

