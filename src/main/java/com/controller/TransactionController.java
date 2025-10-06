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
        String sourceAccountNumber = scanner.nextLine().trim();

        if (sourceAccountNumber.isEmpty()) {
            System.out.println("Source account number cannot be empty!");
            return;
        }

        Account source = accountService.findByAccountNumber(sourceAccountNumber);

        System.out.println("Enter target account number (to which to transfer): ");
        String targetAccountNumber = scanner.nextLine().trim();

        if (targetAccountNumber.isEmpty()) {
            System.out.println("Target account number cannot be empty!");
            return;
        }

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
        String amountInput = scanner.nextLine().trim();

        if (amountInput.isEmpty()) {
            System.out.println("Amount cannot be empty!");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(amountInput);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be greater than zero!");
                return;
            }

            if (!hasSufficientBalance(source, amount)) {
                System.out.println("Insufficient balance! Available: " + source.getBalance());
                return;
            }

            Transaction debitTx = new Transaction(
                    amount.negate(),
                    Transaction.Type.TRANSFER_OUT,
                    Transaction.Status.SETTLED,
                    source.getId(),
                    source.getId(),
                    target.getId(),
                    user.getId()
            );

            Transaction creditTx = new Transaction(
                    amount,
                    Transaction.Type.TRANSFER_IN,
                    Transaction.Status.SETTLED,
                    target.getId(),
                    source.getId(),
                    target.getId(),
                    user.getId()
            );

            transactionService.processTransfer(debitTx, creditTx);

            System.out.println("Transfer completed successfully!");
            System.out.println("New source balance: " + source.getBalance());
            System.out.println("New target balance: " + target.getBalance());

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format! Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error during transfer: " + e.getMessage());
        }
    }

    private static boolean hasSufficientBalance(Account account, BigDecimal amount) {

        if (account.getType() == Account.Type.CREDIT) {
            return account.getBalance().add(getCreditLimit(account)).compareTo(amount) >= 0;
        } else {
            return account.getBalance().compareTo(amount) >= 0;
        }
    }

    private static BigDecimal getCreditLimit(Account account) {
        return new BigDecimal("1000.00");
    }

    public static void transferOut(){
        System.out.println("External transfer functionality not yet implemented.");
    }
}