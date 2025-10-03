package com.controller;

import java.util.Scanner;

public class CreditController {
    private static final Scanner scanner = new Scanner(System.in);

    public static void credit() {
        System.out.println("Enter your AccountNumber : ");
        String accountNumber = scanner.nextLine();
    }
}
