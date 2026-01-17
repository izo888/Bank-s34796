package s34796Bank;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
@Component
public class ClientStorage {
    private final ArrayList<Client> clients = new ArrayList<>();

    public ArrayList<Client> getClients() {
        return clients;
    }

    public Optional<Client> findById(String id) {
        return clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public boolean exists(String id) {
        return findById(id).isPresent();
    }

    public void add(Client client) {
        clients.add(client);
    }
}
