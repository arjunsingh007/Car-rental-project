package com.carrental.service;

import com.carrental.exception.InvalidOperationException;
import com.carrental.exception.VehicleNotAvailableException;
import com.carrental.model.Customer;
import com.carrental.model.Rental;
import com.carrental.model.Vehicle;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Core service layer: owns all in-memory data (vehicles, customers, rentals)
 * and handles business logic + persistence to disk via Java serialization.
 */
public class RentalSystem {

    private final Map<String, Vehicle> vehicles = new LinkedHashMap<>();
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final List<Rental> rentalHistory = new ArrayList<>();

    private static final String DATA_DIR = "data";
    private static final String VEHICLES_FILE = DATA_DIR + "/vehicles.dat";
    private static final String CUSTOMERS_FILE = DATA_DIR + "/customers.dat";
    private static final String RENTALS_FILE = DATA_DIR + "/rentals.dat";

    private int rentalCounter = 1;

    // ---------------------- Vehicle management ----------------------

    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getVehicleId(), vehicle);
    }

    public Collection<Vehicle> getAllVehicles() {
        return vehicles.values();
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.isAvailable()) result.add(v);
        }
        return result;
    }

    // ---------------------- Customer management ----------------------

    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    // ---------------------- Rental operations ----------------------

    public Rental rentVehicle(String vehicleId, String customerId, int days)
            throws VehicleNotAvailableException, InvalidOperationException {

        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle == null) {
            throw new VehicleNotAvailableException("No vehicle found with ID: " + vehicleId);
        }
        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException("Vehicle " + vehicleId + " is already rented.");
        }
        if (!customers.containsKey(customerId)) {
            throw new InvalidOperationException("No customer found with ID: " + customerId);
        }
        if (days <= 0) {
            throw new InvalidOperationException("Rental days must be greater than zero.");
        }

        double cost = vehicle.calculateRent(days);
        String rentalId = "R" + String.format("%03d", rentalCounter++);
        Rental rental = new Rental(rentalId, vehicleId, customerId, LocalDate.now(), days, cost);

        vehicle.setAvailable(false);
        rentalHistory.add(rental);
        return rental;
    }

    public Rental returnVehicle(String vehicleId) throws InvalidOperationException {
        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle == null) {
            throw new InvalidOperationException("No vehicle found with ID: " + vehicleId);
        }

        Rental activeRental = null;
        for (Rental r : rentalHistory) {
            if (r.getVehicleId().equals(vehicleId) && r.isActive()) {
                activeRental = r;
                break;
            }
        }

        if (activeRental == null) {
            throw new InvalidOperationException("Vehicle " + vehicleId + " has no active rental.");
        }

        activeRental.closeRental(LocalDate.now());
        vehicle.setAvailable(true);
        return activeRental;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public List<Rental> getActiveRentals() {
        List<Rental> active = new ArrayList<>();
        for (Rental r : rentalHistory) {
            if (r.isActive()) active.add(r);
        }
        return active;
    }

    // ---------------------- Persistence (Serialization) ----------------------

    @SuppressWarnings("unchecked")
    public void loadData() {
        new File(DATA_DIR).mkdirs();
        loadMap(VEHICLES_FILE, vehicles);
        loadMap(CUSTOMERS_FILE, customers);

        File rentalsFile = new File(RENTALS_FILE);
        if (rentalsFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rentalsFile))) {
                List<Rental> loaded = (List<Rental>) ois.readObject();
                rentalHistory.clear();
                rentalHistory.addAll(loaded);
                // Recompute rental counter so new IDs don't collide
                for (Rental r : rentalHistory) {
                    int num = Integer.parseInt(r.getRentalId().substring(1));
                    if (num >= rentalCounter) rentalCounter = num + 1;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Warning: could not load rental history (" + e.getMessage() + ")");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <K, V> void loadMap(String path, Map<K, V> target) {
        File file = new File(path);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<K, V> loaded = (Map<K, V>) ois.readObject();
            target.putAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: could not load " + path + " (" + e.getMessage() + ")");
        }
    }

    public void saveData() {
        new File(DATA_DIR).mkdirs();
        saveObject(VEHICLES_FILE, vehicles);
        saveObject(CUSTOMERS_FILE, customers);
        saveObject(RENTALS_FILE, rentalHistory);
    }

    private void saveObject(String path, Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Warning: could not save " + path + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Seeds the system with sample data on first run (when no saved data exists).
     */
    public void seedSampleDataIfEmpty() {
        if (!vehicles.isEmpty() || !customers.isEmpty()) return;

        addVehicle(new com.carrental.model.Car("V001", "Toyota", "Corolla", 1800));
        addVehicle(new com.carrental.model.Car("V002", "Honda", "Civic", 2000));
        addVehicle(new com.carrental.model.SUV("V003", "Toyota", "Fortuner", 3500));
        addVehicle(new com.carrental.model.SUV("V004", "Hyundai", "Creta", 2800));
        addVehicle(new com.carrental.model.Bike("V005", "Royal Enfield", "Classic 350", 700));

        addCustomer(new Customer("C001", "Aditya Sharma", "9876543210", "DL-1420110012345"));
        addCustomer(new Customer("C002", "Priya Verma", "9123456780", "DL-0420110054321"));
    }
}
