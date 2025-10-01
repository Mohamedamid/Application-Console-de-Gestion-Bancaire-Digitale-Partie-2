package com.menu;

import java.util.Scanner;

import com.controller.UserController;
import com.model.User;

public class MenuAdmin {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAdminMenu(User user) {
        int choice;
        do {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View all users");
            System.out.println("2. Create a new user");
            System.out.println("3. Update a user");
            System.out.println("4. Delete a user");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    UserController.getAllUsers();
                    break;
                case 2:
                    UserController.createUser();
                    break;
                case 3:
                    UserController.updateUser();
                    break;
                case 4:
                    UserController.deleteUser(user);
                    break;
                case 0:
                    System.out.println("Exiting Admin Menu. Goodbye!");
                    MenuLogin.showLoginMenu();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
