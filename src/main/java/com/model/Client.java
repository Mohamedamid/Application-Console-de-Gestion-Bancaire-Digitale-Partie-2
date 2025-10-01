package com.model;

import java.util.UUID;

public class Client {
    private UUID id;
    private String fullName;
    private String address;
    private String email;
    private double salary;
    private Currency currency;

    public enum Currency {
        MAD, USD, EUR
    }

    public Client(String fullName, String address, String email, double salary, Currency currency) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.salary = salary;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

