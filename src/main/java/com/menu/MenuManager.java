package com.menu;

import com.controller.AuthController;
import com.model.User;

import java.util.Scanner;

public class MenuManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showManagerMenu(User user) {
        int choice;
        do {
            System.out.println("\n=== Manager MENU ===");
            System.out.println("1. View Clients");
            System.out.println("2. View Accounts");
            System.out.println("3. Approve Account Closure");
            System.out.println("4. Approve Credits");
            System.out.println("5. Approve External Transfers");
            System.out.println("6. Monitor Transactions");
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
                    System.out.println("Approving Account Closure...");
                    break;
                case 4:
                    System.out.println("Approving Credits...");
                    break;
                case 5:
                    System.out.println("Approving External Transfers...");
                    break;
                case 6:
                    System.out.println("Monitoring Transactions...");
                    break;
                case 0:
                    System.out.println("Exiting Manager Menu. Goodbye!");
                    MenuLogin.showLoginMenu();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
