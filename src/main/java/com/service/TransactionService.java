package com.service;

import com.model.Transaction;

public interface TransactionService {
    Transaction depositTransaction(Transaction transaction);
    Transaction withdrawTransaction(Transaction transaction);
    Transaction save(Transaction transaction);

}
