package s34796Bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class BankServiceSimpleIT {

    @Autowired
    BankService bankService;

    @Autowired
    ClientStorage clientStorage;

    @Test
    void flowRegisterDepositTransfer() {
        var r1 = bankService.registerClient("c1", new BigDecimal("100"));
        assertEquals("ACCEPTED", r1.getStatus().name());

        var r2 = bankService.deposit("c1", new BigDecimal("50"));
        assertEquals(new BigDecimal("150"), r2.getNewBalance());

        var r3 = bankService.transfer("c1", new BigDecimal("70"));
        assertEquals(new BigDecimal("80"), r3.getNewBalance());
    }
}
