package com.service.implement;

import com.model.Client;
import com.model.User;
import com.repository.ClientRepository;
import com.repository.implement.InMemoryClientRepository;
import com.service.ClientService;

import java.util.List;

public class InMemoryClientService implements ClientService {
    private final ClientRepository clientRepository = new InMemoryClientRepository();

    public List<Client> getAllClient() {
        List<Client> clients = clientRepository.getAll();
        return clients;
    }

    public Client createClient(String fullName,
                               String address,
                               String email,
                               double salary,
                               Client.Currency clientCurrency) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is null or empty");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address is null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        if (clientCurrency == null) {
            throw new IllegalArgumentException("Client currency is null");
        }

        Client client = new Client(fullName, address, email, salary, clientCurrency);
        clientRepository.createClient(client);
        return client;
    }


}
