package com.controller;

import com.menu.MenuAdmin;
import com.model.User;
import com.service.UserService;
import com.service.implement.InMemoryUserService;

import java.util.List;
import java.util.Scanner;

public class UserController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new InMemoryUserService();

    public static void getAllUsers() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("List of users:");
            for (User user : users) {
                System.out.println("Name: " + user.getName() + ", Email: " + user.getEmail() + ", Role: " + user.getRole());
            }
        }
    }

    public static void createUser() {
        System.out.println("Enter your name");
        String name = scanner.nextLine();

        System.out.println("Enter your email");
        String email = scanner.nextLine();

        System.out.println("Enter new role (1-ADMIN / 2-AUDITOR / 3-MANAGER / 4-TELLER)");
        int choixRole = scanner.nextInt();
        scanner.nextLine();

        User.Role role;
        switch (choixRole) {
            case 1:
                role = User.Role.ADMIN;
                break;
            case 2:
                role = User.Role.AUDITOR;
                break;
            case 3:
                role = User.Role.MANAGER;
                break;
            case 4:
                role = User.Role.TELLER;
                break;
            default:
                System.out.println("Invalid role. Please try again.");
                return;
        }

        System.out.println("Enter your password");
        String password = scanner.nextLine();

        User user = userService.createUser(name, email, password, role);
        if (user != null) {
            System.out.println("User created successfully! Welcome " + user.getName());
            MenuAdmin.showAdminMenu(user);
        } else {
            System.out.println("Error creating user. Please try again.");
            MenuAdmin.showAdminMenu(null);
        }
    }

    public static void updateUser() {
        System.out.println("Enter user email to update");
        String oldEmail = scanner.nextLine();

        System.out.println("Enter new name");
        String name = scanner.nextLine();

        System.out.println("Enter new email");
        String email = scanner.nextLine();

        System.out.println("Enter new role (1-ADMIN / 2-AUDITOR / 3-MANAGER / 4-TELLER)");
        int choixRole = scanner.nextInt();
        scanner.nextLine();

        User.Role role;
        switch (choixRole) {
            case 1:
                role = User.Role.ADMIN;
                break;
            case 2:
                role = User.Role.AUDITOR;
                break;
            case 3:
                role = User.Role.MANAGER;
                break;
            case 4:
                role = User.Role.TELLER;
                break;
            default:
                System.out.println("Invalid role. Please try again.");
                return;
        }

        System.out.println("Enter new password");
        String password = scanner.nextLine();

        try {
            User updatedUser = userService.updateUser(name, email, role, password, oldEmail);
            System.out.println("User updated successfully! Welcome " + updatedUser.getName());
            MenuAdmin.showAdminMenu(updatedUser);
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating user: " + e.getMessage());
            MenuAdmin.showAdminMenu(null);
        }
    }

    public static void deleteUser(User user) {
        System.out.println("Enter user email to delete");
        String email = scanner.nextLine();
        try {
            if(email.equals(user.getEmail())){
                System.out.println("You cannot delete this account because it is currently logged in (" + user.getRole() + ")");
                return;
            }
            userService.deleteUser(email);
            System.out.println("User deleted successfully!");
            MenuAdmin.showAdminMenu(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            MenuAdmin.showAdminMenu(null);
        }
    }
}
