package com.service.implement;

import com.model.Account;
import com.model.Client;
import com.repository.AccountRepository;
import com.repository.implement.InMemoryAccountRepository;
import com.service.AccountService;

import java.util.List;
import java.util.UUID;

public class InMemoryAccountService implements AccountService {
    private final AccountRepository accountRepository = new InMemoryAccountRepository();


    public Account createAccount(Account.Type type, UUID clientId) {

        List<Account> accounts = accountRepository.findAll();
        boolean alreadyExists = accounts.stream()
                .anyMatch(account -> account.getClientId().equals(clientId) && account.getType().equals(type));

        if (alreadyExists) {
            throw new IllegalArgumentException("Client already has an account of type " + type);
        }

        Account account = new Account(type, clientId);
        accountRepository.createAccount(account);
        System.out.println("New account created [" + type + "] for client " + clientId);
        return account;
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public Account findByAccountNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
