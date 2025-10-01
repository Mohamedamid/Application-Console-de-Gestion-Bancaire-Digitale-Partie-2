package com.controller;

import com.menu.*;
import com.model.User;
import com.service.implement.InMemoryAuthService;
import com.service.AuthService;

import java.util.Scanner;

import static java.lang.System.exit;

public class AuthController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new InMemoryAuthService();
    private static User sessionUser = null;

    public static User getSessionUser() {
        return sessionUser;
    }

    public static void setSessionUser(User user) {
        sessionUser = user;
    }

    public static void login() {
        System.out.println("Enter your email");
        String userEmail = scanner.nextLine();
        System.out.println("Enter your password");
        String userPassword = scanner.nextLine();

        User user = authService.login(userEmail, userPassword);

        if (user != null && user.getRole() == User.Role.ADMIN) {
            setSessionUser(user);
            System.out.println("Login successful! Welcome " + user.getName() + " (ADMIN)");
            MenuAdmin.showAdminMenu(user);

        } else if (user != null && user.getRole() == User.Role.MANAGER) {
            setSessionUser(user);
            System.out.println("Login successful! Welcome " + user.getName() + " (MANAGER)");
            MenuManager.showManagerMenu(user);

        } else if (user != null && user.getRole() == User.Role.AUDITOR) {
            setSessionUser(user);
            System.out.println("Login successful! Welcome " + user.getName() + " (AUDITOR)");
            MenuAuditor.showAuditorMenu(user);

        } else if (user != null && user.getRole() == User.Role.TELLER) {
            setSessionUser(user);
            System.out.println("Login successful! Welcome " + user.getName() + " (TELLER)");
            MenuTeller.showTellerMenu(user);

        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    public static void logout() {
        setSessionUser(null);
        System.out.println("You have been logged out.");
        exit(0);
    }
}
