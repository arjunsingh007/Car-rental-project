package com.carrental.model;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String customerId;
    private final String name;
    private final String phone;
    private final String licenseNumber;

    public Customer(String customerId, String name, String phone, String licenseNumber) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-15s | %-12s | %s", customerId, name, phone, licenseNumber);
    }
}
