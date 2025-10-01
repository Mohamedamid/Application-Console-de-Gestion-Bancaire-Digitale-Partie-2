package com.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private UUID id;
    private String accountNumber;
    private BigDecimal balance;
    private Type type;
    private UUID clientId;
    private boolean status;

    public enum Type {
        CURRENT, SAVINGS, CREDIT
    }

    public Account(Type type,UUID clientId) {
        this.id = UUID.randomUUID();
        this.accountNumber = "MA64" + (int)(Math.random() * 1000000);
        this.balance = BigDecimal.ZERO;
        this.type = type;
        this.clientId = clientId;
        this.status = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}

