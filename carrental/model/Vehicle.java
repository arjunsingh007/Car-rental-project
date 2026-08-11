package com.carrental.model;

import java.io.Serializable;

/**
 * Abstract base class representing a rentable vehicle.
 * Demonstrates abstraction and encapsulation.
 */
public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String vehicleId;
    private final String brand;
    private final String model;
    private final double baseRentPerDay;
    private boolean available;

    public Vehicle(String vehicleId, String brand, String model, double baseRentPerDay) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.baseRentPerDay = baseRentPerDay;
        this.available = true;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRentPerDay() {
        return baseRentPerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Polymorphic rent calculation - each vehicle type applies its own
     * pricing rules (surcharges, discounts, etc).
     */
    public abstract double calculateRent(int days);

    public String getVehicleType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-6s | %-10s | %-10s | Rs.%-8.2f/day | %s",
                vehicleId, getVehicleType(), brand, model, baseRentPerDay,
                available ? "Available" : "Rented");
    }
}
