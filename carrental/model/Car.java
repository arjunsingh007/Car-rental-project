package com.carrental.model;

/**
 * Standard sedan/hatchback car. Applies a flat insurance surcharge per day.
 */
public class Car extends Vehicle {
    private static final long serialVersionUID = 1L;
    private static final double INSURANCE_PER_DAY = 150.0;

    public Car(String vehicleId, String brand, String model, double baseRentPerDay) {
        super(vehicleId, brand, model, baseRentPerDay);
    }

    @Override
    public double calculateRent(int days) {
        double rent = getBaseRentPerDay() * days;
        double insurance = INSURANCE_PER_DAY * days;
        // 5% discount for rentals of 7+ days
        double discount = days >= 7 ? rent * 0.05 : 0;
        return rent + insurance - discount;
    }
}
