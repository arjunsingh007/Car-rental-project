package com.carrental.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a single rental transaction linking a Customer to a Vehicle.
 */
public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String rentalId;
    private final String vehicleId;
    private final String customerId;
    private final LocalDate rentDate;
    private LocalDate returnDate;
    private final int expectedDays;
    private final double totalCost;
    private boolean active;

    public Rental(String rentalId, String vehicleId, String customerId,
                   LocalDate rentDate, int expectedDays, double totalCost) {
        this.rentalId = rentalId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.rentDate = rentDate;
        this.expectedDays = expectedDays;
        this.totalCost = totalCost;
        this.active = true;
    }

    public String getRentalId() {
        return rentalId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getExpectedDays() {
        return expectedDays;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isActive() {
        return active;
    }

    public void closeRental(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.active = false;
    }

    @Override
    public String toString() {
        return String.format("%-8s | Vehicle:%-8s | Customer:%-8s | Rented:%-10s | Days:%-3d | Rs.%-8.2f | %s",
                rentalId, vehicleId, customerId, rentDate, expectedDays, totalCost,
                active ? "ACTIVE" : ("RETURNED on " + returnDate));
    }
}
