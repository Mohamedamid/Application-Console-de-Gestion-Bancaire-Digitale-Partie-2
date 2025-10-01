package com.controller;

import com.menu.MenuTeller;
import com.model.Client;
import com.service.ClientService;
import com.service.implement.InMemoryClientService;

import java.util.List;
import java.util.Scanner;

public class ClientController {
    private static final ClientService clientService = new InMemoryClientService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void InsertClient(){
        System.out.println("Enter full name:");
        String fullName = scanner.nextLine();
        System.out.println("Enter address:");
        String address = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter salary:");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Select currency (1-USD, 2-EUR, 3-MAD):");
        int currencyChoice = scanner.nextInt();
        scanner.nextLine();
        Client.Currency currency;
        switch (currencyChoice) {
            case 1:
                currency = Client.Currency.USD;
                break;
            case 2:
                currency = Client.Currency.EUR;
                break;
            case 3:
                currency = Client.Currency.MAD;
                break;
            default:
                System.out.println("Invalid currency choice. Defaulting to USD.");
                currency = Client.Currency.USD;
        }

        Client client = clientService.createClient(fullName, address, email, salary, currency);

        if (client != null) {
            System.out.println("Client created successfully! Welcome " + client.getFullName());
            MenuTeller.showTellerMenu(null);
        } else {
            System.out.println("Error creating client. Please try again.");
            MenuTeller.showTellerMenu(null);
        }
    }

    public static void ViewClient(){
        List<Client> clients = clientService.getAllClient();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            System.out.println("\n=== Client List ===");
            for (Client client : clients) {
                System.out.println("ID: " + client.getId());
                System.out.println("Full Name: " + client.getFullName());
                System.out.println("Address: " + client.getAddress());
                System.out.println("Email: " + client.getEmail());
                System.out.println("Salary: " + client.getSalary());
                System.out.println("Currency: " + client.getCurrency());
                System.out.println("-----------------------");
            }
        }
        MenuTeller.showTellerMenu(null);
    }
}
