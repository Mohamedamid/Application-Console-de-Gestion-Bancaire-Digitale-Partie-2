package com.repository;

import com.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {

    List<Client> getAll();
    Client findClientById(UUID idClient);
    Client createClient(Client client);
    Client editClient(Client client, UUID clientId);
    void deleteClient(UUID clientId);

}
