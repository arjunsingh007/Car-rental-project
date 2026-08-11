package com.carrental.exception;

/**
 * Thrown when a customer tries to rent a vehicle that is already rented
 * or does not exist in the system.
 */
public class VehicleNotAvailableException extends Exception {
    public VehicleNotAvailableException(String message) {
        super(message);
    }
}
