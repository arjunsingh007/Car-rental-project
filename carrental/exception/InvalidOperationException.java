package com.carrental.exception;

/**
 * Thrown for invalid operations such as returning a vehicle that was never
 * rented, or referencing a customer/vehicle ID that doesn't exist.
 */
public class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}
