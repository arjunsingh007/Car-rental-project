package com.carrental;

import com.carrental.exception.InvalidOperationException;
import com.carrental.exception.VehicleNotAvailableException;
import com.carrental.model.*;
import com.carrental.service.RentalSystem;

import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Car Rental Management System.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final RentalSystem system = new RentalSystem();

    public static void main(String[] args) {
        system.loadData();
        system.seedSampleDataIfEmpty();

        System.out.println("=================================================");
        System.out.println("      WELCOME TO THE CAR RENTAL SYSTEM");
        System.out.println("=================================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": viewAllVehicles(); break;
                case "2": viewAvailableVehicles(); break;
                case "3": addVehicle(); break;
                case "4": addCustomer(); break;
                case "5": viewAllCustomers(); break;
                case "6": rentVehicle(); break;
                case "7": returnVehicle(); break;
                case "8": viewActiveRentals(); break;
                case "9": viewRentalHistory(); break;
                case "0":
                    system.saveData();
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n------------------- MENU -------------------");
        System.out.println("1. View All Vehicles");
        System.out.println("2. View Available Vehicles");
        System.out.println("3. Add New Vehicle");
        System.out.println("4. Register New Customer");
        System.out.println("5. View All Customers");
        System.out.println("6. Rent a Vehicle");
        System.out.println("7. Return a Vehicle");
        System.out.println("8. View Active Rentals");
        System.out.println("9. View Rental History");
        System.out.println("0. Save & Exit");
        System.out.print("Enter your choice: ");
    }

    private static void viewAllVehicles() {
        System.out.println("\nID       | Type   | Brand      | Model      | Rate/day       | Status");
        System.out.println("-------------------------------------------------------------------------");
        for (Vehicle v : system.getAllVehicles()) {
            System.out.println(v);
        }
    }

    private static void viewAvailableVehicles() {
        List<Vehicle> available = system.getAvailableVehicles();
        if (available.isEmpty()) {
            System.out.println("No vehicles currently available.");
            return;
        }
        System.out.println("\nID       | Type   | Brand      | Model      | Rate/day       | Status");
        System.out.println("-------------------------------------------------------------------------");
        for (Vehicle v : available) {
            System.out.println(v);
        }
    }

    private static void addVehicle() {
        System.out.print("Vehicle ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Type (car/suv/bike): ");
        String type = scanner.nextLine().trim().toLowerCase();
        System.out.print("Brand: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Base rent per day: ");

        double rate;
        try {
            rate = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid rate. Vehicle not added.");
            return;
        }

        Vehicle vehicle;
        switch (type) {
            case "car": vehicle = new Car(id, brand, model, rate); break;
            case "suv": vehicle = new SUV(id, brand, model, rate); break;
            case "bike": vehicle = new Bike(id, brand, model, rate); break;
            default:
                System.out.println("Unknown vehicle type. Use car, suv, or bike.");
                return;
        }
        system.addVehicle(vehicle);
        System.out.println("Vehicle added successfully.");
    }

    private static void addCustomer() {
        System.out.print("Customer ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Driving License Number: ");
        String license = scanner.nextLine().trim();

        system.addCustomer(new Customer(id, name, phone, license));
        System.out.println("Customer registered successfully.");
    }

    private static void viewAllCustomers() {
        System.out.println("\nID       | Name            | Phone        | License");
        System.out.println("-------------------------------------------------------------");
        for (Customer c : system.getAllCustomers()) {
            System.out.println(c);
        }
    }

    private static void rentVehicle() {
        System.out.print("Vehicle ID to rent: ");
        String vehicleId = scanner.nextLine().trim();
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine().trim();
        System.out.print("Number of days: ");

        int days;
        try {
            days = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of days.");
            return;
        }

        try {
            Rental rental = system.rentVehicle(vehicleId, customerId, days);
            System.out.println("\nRental confirmed!");
            System.out.println(rental);
        } catch (VehicleNotAvailableException | InvalidOperationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnVehicle() {
        System.out.print("Vehicle ID to return: ");
        String vehicleId = scanner.nextLine().trim();
        try {
            Rental rental = system.returnVehicle(vehicleId);
            System.out.println("\nVehicle returned successfully!");
            System.out.println(rental);
        } catch (InvalidOperationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewActiveRentals() {
        List<Rental> active = system.getActiveRentals();
        if (active.isEmpty()) {
            System.out.println("No active rentals.");
            return;
        }
        for (Rental r : active) {
            System.out.println(r);
        }
    }

    private static void viewRentalHistory() {
        List<Rental> history = system.getRentalHistory();
        if (history.isEmpty()) {
            System.out.println("No rental history yet.");
            return;
        }
        for (Rental r : history) {
            System.out.println(r);
        }
    }
}
