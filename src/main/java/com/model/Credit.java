package com.model;

import java.time.LocalDate;
import java.util.UUID;

public class Credit {
    private UUID id;
    private double amount;
    private Currency currency;
    private double interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private CreditType creditType;
    private Status status;
    private UUID accountId;

    public enum Currency {
        MAD, USD, EUR
    }

    public enum CreditType {
        SIMPLE, COMPOSED
    }

    public enum Status {
        PENDING, ACTIVE, LATE, CLOSED
    }

    public Credit(double amount, Currency currency,
                  double interestRate, LocalDate startDate,
                  LocalDate endDate, CreditType creditType,
                  Status status, UUID accountId) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.currency = currency;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creditType = creditType;
        this.status = status;
        this.accountId = accountId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

