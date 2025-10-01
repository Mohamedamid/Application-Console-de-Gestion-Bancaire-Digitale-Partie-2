package com.service;

import com.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createAccount(Account.Type type, UUID IdClient);
    List<Account> getAllAccount();
    Account findByAccountNumber(String accountNumber);

}
