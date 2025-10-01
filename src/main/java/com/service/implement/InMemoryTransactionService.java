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

    public Transaction saveTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId());
        if (account == null) {
            throw new RuntimeException("Account not found with ID " + transaction.getAccountId());
        }

        BigDecimal currentBalance = BigDecimal.valueOf(account.getBalance());
        BigDecimal newBalance = currentBalance.add(transaction.getAmount());
        accountRepository.editBalance(newBalance, account.getId());

        transactionRepository.saveTransaction(transaction);

        return transaction;
    }
}
