package com.service.implement;

import com.model.Account;
import com.model.Transaction;
import com.repository.AccountRepository;
import com.repository.RepositoryFactory;
import com.repository.TransactionRepository;
import com.service.TransactionService;

import java.math.BigDecimal;

public class InMemoryTransactionService implements TransactionService {
    private final AccountRepository accountRepository = RepositoryFactory.accountRepository;
    private final TransactionRepository transactionRepository = RepositoryFactory.transactionRepository;

    public Transaction depositTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId());

        if (account == null) {
            throw new RuntimeException("Account not found with ID " + transaction.getAccountId());
        }

        if (account.getType() != Account.Type.SAVINGS &&
                account.getType() != Account.Type.CURRENT) {
            throw new RuntimeException("Deposits are only allowed into SAVINGS or CURRENT accounts. This account type is: " + account.getType());
        }

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(transaction.getAmount());

        accountRepository.editBalance(newBalance, account.getId());
        transactionRepository.saveTransaction(transaction);

        return transaction;
    }

    public Transaction withdrawTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId());

        if (account == null) {
            throw new RuntimeException("Account not found with ID " + transaction.getAccountId());
        }

        if (account.getType() != Account.Type.CURRENT) {
            throw new RuntimeException("Withdrawals are only allowed from CURRENT accounts. This account type is: " + account.getType());
        }

        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(transaction.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance! Current balance: "
                    + currentBalance + ", Withdraw amount: "
                    + transaction.getAmount());
        }

        BigDecimal newBalance = currentBalance.subtract(transaction.getAmount());
        accountRepository.editBalance(newBalance, account.getId());
        transactionRepository.saveTransaction(transaction);

        return transaction;
    }

    public void processTransfer(Transaction debitTransaction, Transaction creditTransaction) {

        Account sourceAccount = accountRepository.findById(debitTransaction.getAccountId());
        if (sourceAccount == null) {
            throw new RuntimeException("Source account not found");
        }

        BigDecimal sourceNewBalance = sourceAccount.getBalance().add(debitTransaction.getAmount());
        accountRepository.editBalance(sourceNewBalance, sourceAccount.getId());

        Account targetAccount = accountRepository.findById(creditTransaction.getAccountId());
        if (targetAccount == null) {
            throw new RuntimeException("Target account not found");
        }

        BigDecimal targetNewBalance = targetAccount.getBalance().add(creditTransaction.getAmount());
        accountRepository.editBalance(targetNewBalance, targetAccount.getId());

        transactionRepository.saveTransaction(debitTransaction);
        transactionRepository.saveTransaction(creditTransaction);
    }

    public Transaction save(Transaction transaction) {

        Account account = accountRepository.findById(transaction.getAccountId());
        if (account == null) {
            throw new RuntimeException("Account not found with ID " + transaction.getAccountId());
        }

        BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
        accountRepository.editBalance(newBalance, account.getId());
        transactionRepository.saveTransaction(transaction);

        return transaction;
    }
}