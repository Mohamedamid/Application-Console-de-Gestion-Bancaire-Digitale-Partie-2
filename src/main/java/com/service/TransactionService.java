package com.service;

import com.model.Transaction;

public interface TransactionService {
    Transaction depositTransaction(Transaction transaction);
    Transaction withdrawTransaction(Transaction transaction);
    void processTransfer(Transaction debitTransaction, Transaction creditTransaction);
    Transaction save(Transaction transaction);
}