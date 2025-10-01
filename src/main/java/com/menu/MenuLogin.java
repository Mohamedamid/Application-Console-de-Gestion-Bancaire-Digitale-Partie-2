package com.menu;

import com.controller.AuthController;

import java.util.Scanner;


public class MenuLogin {

    public static void showLoginMenu() {
    Scanner scanner = new java.util.Scanner(System.in);
        String choix;
        do {
            System.out.println("Please select an option:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Your choice: ");
            choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    AuthController.login();
                    break;
                case "2":
                    AuthController.logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choix != "2");
    }
}
