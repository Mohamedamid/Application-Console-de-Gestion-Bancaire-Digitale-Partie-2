package com.repository;

import com.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountRepository {

     List<Account> findAll();
     Account findById(UUID idAccount);
     Account findByAccountNumber(String accountNumber);
     Account createAccount(Account account);
     Account editAccount(Account account, UUID idAccount);
     void deleteByIdAccount(UUID idAccount);
     void editBalance(BigDecimal balance, UUID accountId);

}
