package com.repository;

import com.model.Transaction;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction);
}
