package com.controller;

import com.model.Account;
import com.model.Transaction;
import com.model.User;
import com.service.AccountService;
import com.service.TransactionService;
import com.service.implement.InMemoryAccountService;
import com.service.implement.InMemoryTransactionService;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransactionController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountService accountService = new InMemoryAccountService();
    private static final TransactionService transactionService = new InMemoryTransactionService();

    public static void depodit() {
        User user = AuthController.getSessionUser();

        if (user == null) {
            System.out.println("No user logged in!");
            return;
        }

        System.out.println("Enter your AccountNumber : ");
        String accountNumber = scanner.nextLine();
        Account account = accountService.findByAccountNumber(accountNumber);

        if (account != null) {
            System.out.println("Enter your balance deposit : ");
            String balanceInput = scanner.nextLine();
            BigDecimal amount = new BigDecimal(balanceInput);

            Transaction.Type type = Transaction.Type.DEPOSIT;
            Transaction.Status status = Transaction.Status.SETTLED;

            Transaction transaction = new Transaction(
                    amount,
                    type,
                    status,
                    account.getId(),
                    null,
                    null,
                    user.getId()
            );

            transactionService.depositTransaction(transaction);
            System.out.println("Deposit completed successfully!");
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void withdraw() {
        User user = AuthController.getSessionUser();

        if (user == null) {
            System.out.println("No user logged in!");
            return;
        }

        System.out.println("Enter your AccountNumber : ");
        String accountNumber = scanner.nextLine();
        Account account = accountService.findByAccountNumber(accountNumber);

        if (account != null) {
            System.out.println("Enter your balance withdraw : ");
            String balanceInput = scanner.nextLine();
            BigDecimal amount = new BigDecimal(balanceInput);

            Transaction.Type type = Transaction.Type.WITHDRAW;
            Transaction.Status status = Transaction.Status.SETTLED;

            Transaction transaction = new Transaction(
                    amount,
                    type,
                    status,
                    account.getId(),
                    null,
                    null,
                    user.getId()
            );

            transactionService.withdrawTransaction(transaction);
            System.out.println("Withdraw completed successfully!");
        } else {
            System.out.println("Account not found!");
        }

    }
}
