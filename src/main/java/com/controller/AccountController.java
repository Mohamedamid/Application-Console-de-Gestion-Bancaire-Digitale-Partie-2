package com.controller;

import com.menu.MenuTeller;
import com.model.Account;
import com.service.AccountService;
import com.service.implement.InMemoryAccountService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AccountController {
        private static final AccountService accountService = new InMemoryAccountService();
        private static final Scanner scanner = new Scanner(System.in);

    public static void InsertAccount(){
        System.out.println("Enter id client select account:");
        String idClientInput = scanner.nextLine();
        UUID IdClient;
        try {
            IdClient = idClientInput.isEmpty() ? null : UUID.fromString(idClientInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Please try again.");
            MenuTeller.showTellerMenu(null);
            return;
        }

        System.out.println("Select account type (1-SAVINGS, 2-CURRENT, 3-CREDIT):");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();
        Account.Type type;
        switch (typeChoice) {
            case 1: type = Account.Type.SAVINGS; break;
            case 2: type = Account.Type.CURRENT; break;
            case 3: type = Account.Type.CREDIT; break;
            default:
                System.out.println("Invalid account type choice. Defaulting to SAVINGS.");
                type = Account.Type.SAVINGS;
        }

        try {
            Account account = accountService.createAccount(type, IdClient);
            if (account != null) {
                System.out.println("Account created successfully! Account Number: " + account.getAccountNumber());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }

        MenuTeller.showTellerMenu(null);
    }

    public static void ViewAccount(){
        List<Account> accounts = accountService.getAllAccount();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("\n=== Account List ===");
            for (Account account : accounts) {
                System.out.println("ID: " + account.getId());
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Type: " + account.getType());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Client ID: " + account.getClientId());
                System.out.println("Status: " + (account.isStatus() ? "Active" : "Inactive"));
                System.out.println("-----------------------");
            }
        }
        MenuTeller.showTellerMenu(null);
    }

    public static void activeAccount() {

    }

    public static void desactiveAccount() {

    }

}
