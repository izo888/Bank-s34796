package s34796Bank;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankServiceMockTest {

    @Test
    void rejestracjaNiezaakceptowanaGdyKlientJuzIstnieje() {

        ClientStorage storage = Mockito.mock(ClientStorage.class);
        BankService service = new BankService(storage);

        Mockito.when(storage.exists("c1")).thenReturn(true);
        Mockito.when(storage.findById("c1"))
                .thenReturn(Optional.of(new Client("c1", new BigDecimal("200"))));

        TransactionResult result = service.registerClient("c1", new BigDecimal("100"));

        assertEquals("DECLINED", result.getStatus().name());
        assertEquals("CLIENT_ALREADY_REGISTERED", result.getMessage());
        assertEquals(new BigDecimal("200"), result.getNewBalance());

        Mockito.verify(storage).exists("c1");
        Mockito.verify(storage).findById("c1");
    }
}
