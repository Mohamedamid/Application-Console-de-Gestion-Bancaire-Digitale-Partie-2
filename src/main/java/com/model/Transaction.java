package com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private BigDecimal amount;
    private Currency currency;
    private Type type;
    private Status status;
    private LocalDateTime timestamp;
    private UUID accountId;
    private UUID source;
    private UUID target;
    private UUID tellerId;

    public enum Currency {
        MAD, USD, EUR
    }

    public enum Type {
        DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT, TRANSFER_EXTERNAL, FEE
    }

    public enum Status {
        PENDING, SETTLED, FAILED
    }

    public Transaction(BigDecimal amount, Type type,
                       Status status, UUID accountId,
                       UUID source, UUID target, UUID tellerId) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.currency = Currency.MAD;
        this.type = type;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.accountId = accountId;
        this.source = source;
        this.target = target;
        this.tellerId = tellerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getSource() {
        return source;
    }

    public void setSource(UUID source) {
        this.source = source;
    }

    public UUID getTarget() {
        return target;
    }

    public void setTarget(UUID target) {
        this.target = target;
    }

    public UUID getTellerId() {
        return tellerId;
    }

    public void setTellerId(UUID tellerId) {
        this.tellerId = tellerId;
    }
}

