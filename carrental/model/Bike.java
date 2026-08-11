package com.carrental.model;

/**
 * Two-wheeler - cheapest option, no insurance surcharge, small daily discount
 * for longer rentals.
 */
public class Bike extends Vehicle {
    private static final long serialVersionUID = 1L;

    public Bike(String vehicleId, String brand, String model, double baseRentPerDay) {
        super(vehicleId, brand, model, baseRentPerDay);
    }

    @Override
    public double calculateRent(int days) {
        double rent = getBaseRentPerDay() * days;
        double discount = days >= 5 ? rent * 0.03 : 0;
        return rent - discount;
    }
}
