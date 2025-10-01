package com.service.implement;

import com.model.Account;
import com.model.Transaction;
import com.repository.AccountRepository;
import com.repository.TransactionRepository;
import com.repository.implement.InMemoryAccountRepository;
import com.repository.implement.InMemoryTransactionRepository;
import com.service.TransactionService;

import java.math.BigDecimal;

public class InMemoryTransactionService implements TransactionService {
    private static final TransactionRepository transactionRepository = new InMemoryTransactionRepository();
    private static final AccountRepository accountRepository = new InMemoryAccountRepository();

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

}
