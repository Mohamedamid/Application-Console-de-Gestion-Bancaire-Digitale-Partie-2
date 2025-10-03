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

    public static void transferIn() {
        User user = AuthController.getSessionUser();

        if (user == null) {
            System.out.println("No user logged in!");
            return;
        }

        System.out.println("Enter source account number (from which to transfer): ");
        String sourceAccountNumber = scanner.nextLine();
        Account source = accountService.findByAccountNumber(sourceAccountNumber);

        System.out.println("Enter target account number (to which to transfer): ");
        String targetAccountNumber = scanner.nextLine();
        Account target = accountService.findByAccountNumber(targetAccountNumber);

        if (source == null || target == null) {
            System.out.println("One or both accounts not found!");
            return;
        }

        if (source.getId().equals(target.getId())) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }

        System.out.println("Enter transfer amount: ");
        String amountInput = scanner.nextLine();

        try {
            BigDecimal amount = new BigDecimal(amountInput);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be greater than zero!");
                return;
            }

            if (amount.compareTo(source.getBalance()) > 0) {
                System.out.println("Insufficient balance!");
                return;
            }

            Transaction debitTx = new Transaction(
                    amount.negate(), // negative for debit
                    Transaction.Type.TRANSFER_OUT,
                    Transaction.Status.SETTLED,
                    null,
                    source.getId(),
                    target.getId(),
                    user.getId()
            );

            Transaction creditTx = new Transaction(
                    amount,
                    Transaction.Type.TRANSFER_IN,
                    Transaction.Status.SETTLED,
                    null,
                    source.getId(),
                    target.getId(),
                    user.getId()
            );

            transactionService.save(debitTx);
            // transactionService.save(creditTx);

            source.setBalance(source.getBalance().subtract(amount));
            target.setBalance(target.getBalance().add(amount));

            // accountService.update(source);
            // accountService.update(target);

            System.out.println("Transfer completed successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered!");
        }
    }

    public static void transferOut(){

    }

}
