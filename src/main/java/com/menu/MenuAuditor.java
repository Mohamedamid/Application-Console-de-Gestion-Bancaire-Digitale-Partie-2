package com.menu;

import java.util.Scanner;

import com.controller.AuthController;
import com.model.User;

public class MenuAuditor{
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAuditorMenu(User user) {
        int choice;
        do {
            System.out.println("\n=== Auditor MENU ===");
            System.out.println("1. View Clients");
            System.out.println("2. View Accounts");
            System.out.println("3. View Transactions");
            System.out.println("4. View Credits");
            System.out.println("5. Generate Audit Reports");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Viewing Clients...");
                    break;
                case 2:
                    System.out.println("Viewing Accounts...");
                    break;
                case 3:
                    System.out.println("Viewing Transactions...");
                    break;
                case 4:
                    System.out.println("Viewing Credits...");
                    break;
                case 5:
                    System.out.println("Generating Audit Reports...");
                    break;
                case 0:
                    System.out.println("Exiting Auditor Menu. Goodbye!");
                    MenuLogin.showLoginMenu();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
