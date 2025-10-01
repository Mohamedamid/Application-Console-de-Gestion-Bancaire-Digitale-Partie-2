package com.service;

import com.model.Client;
import com.model.User;

import java.util.List;

public interface ClientService {
    List<Client> getAllClient();
    Client createClient(String fullName, String address, String email, double salary, Client.Currency currencyClient);

}
