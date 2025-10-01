package com.menu;

import com.controller.AccountController;
import com.controller.ClientController;
import com.controller.TransactionController;
import com.model.User;

import java.util.Scanner;

public class MenuTeller {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showTellerMenu(User user) {
        int choice;
        do {
            System.out.println("\n=== Teller MENU ===");
            System.out.println("1. Create Client");
            System.out.println("2. Create Account");
            System.out.println("3. View Client");
            System.out.println("4. View Account");
            System.out.println("5. Deposit Money");
            System.out.println("6. Withdraw Money");
            System.out.println("7. Internal Transfer");
            System.out.println("8. Credit Request");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println("\n");
            switch (choice) {
                case 1:
                    ClientController.InsertClient();
                    break;
                case 2:
                    AccountController.InsertAccount();
                    break;
                case 3:
                    ClientController.ViewClient();
                    break;
                case 4:
                    AccountController.ViewAccount();
                    break;
                case 5:
                    TransactionController.depodit();
                    break;
                case 6:
                    TransactionController.withdraw();
                    break;
                case 7:
                    System.out.println("Processing Internal Transfer...");
                    break;
                case 8:
                    System.out.println("Processing Credit Request...");
                    break;
                case 0:
                    System.out.println("Exiting Teller Menu. Goodbye!");
                    MenuLogin.showLoginMenu();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
