package com.carrental.model;

/**
 * SUV - higher base rate plus a per-day off-road/premium surcharge.
 */
public class SUV extends Vehicle {
    private static final long serialVersionUID = 1L;
    private static final double PREMIUM_SURCHARGE_PER_DAY = 300.0;

    public SUV(String vehicleId, String brand, String model, double baseRentPerDay) {
        super(vehicleId, brand, model, baseRentPerDay);
    }

    @Override
    public double calculateRent(int days) {
        double rent = getBaseRentPerDay() * days;
        double surcharge = PREMIUM_SURCHARGE_PER_DAY * days;
        double discount = days >= 7 ? rent * 0.08 : 0;
        return rent + surcharge - discount;
    }
}
