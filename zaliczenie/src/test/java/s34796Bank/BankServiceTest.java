package s34796Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {
    private ClientStorage storage;
    private BankService service;

    @BeforeEach
    void setup() {
        storage = new ClientStorage();
        service = new BankService(storage);
    }

    @Test
    void rejestracjaKlientaZaakceptowana() {
        var r = service.registerClient("c1", new BigDecimal("100.00"));
        assertEquals("REGISTERED", r.getMessage());
        assertEquals("ACCEPTED", r.getStatus().name());
        assertEquals(new BigDecimal("100.00"), r.getNewBalance());
        assertNotNull(service.getClient("c1"));
    }

    @Test
    void depozytOdrzuconyGdyKlientNieZarejestrowany() {
        var r = service.deposit("x", new BigDecimal("10.00"));
        assertEquals("DECLINED", r.getStatus().name());
        assertEquals("CLIENT_NOT_REGISTERED", r.getMessage());
        assertNull(r.getNewBalance());
    }

    @Test
    void depozytZaakceptowanyZamianaRachunku() {
        service.registerClient("c1", new BigDecimal("10.00"));
        var r = service.deposit("c1", new BigDecimal("5.00"));
        assertEquals("ACCEPTED", r.getStatus().name());
        assertEquals(new BigDecimal("15.00"), r.getNewBalance());
    }

    @Test
    void transferNiezaakceptowanyNiewylasciwaKwota() {
        service.registerClient("c1", new BigDecimal("10.00"));
        var r = service.transfer("c1", new BigDecimal("50.00"));
        assertEquals("DECLINED", r.getStatus().name());
        assertEquals("INSUFFICIENT_FUNDS", r.getMessage());
        assertEquals(new BigDecimal("10.00"), r.getNewBalance());
    }

    @Test
    void transferZaakceptowanyZmianaRachunku() {
        service.registerClient("c1", new BigDecimal("10.00"));
        var r = service.transfer("c1", new BigDecimal("3.00"));
        assertEquals("ACCEPTED", r.getStatus().name());
        assertEquals(new BigDecimal("7.00"), r.getNewBalance());
    }
}