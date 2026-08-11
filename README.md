# Car Rental Management System (Java)

A console-based Car Rental Management System built in core Java, designed to
demonstrate solid object-oriented design and practical software engineering
skills for a resume/portfolio project.

## Features

- **Vehicle inventory management** — add and list Cars, SUVs, and Bikes, each
  with their own pricing rules
- **Customer registration** — store customer name, phone, and license number
- **Rent / Return workflow** — rent a vehicle to a customer for N days, and
  return it later, with cost calculated automatically
- **Rental history & active rentals view**
- **Data persistence** — all data (vehicles, customers, rentals) is saved to
  disk using Java serialization, so it survives across program restarts
- **Custom exceptions** for invalid operations (e.g. renting an unavailable
  vehicle, referencing an unknown customer)

## Concepts demonstrated

| Concept                     | Where it shows up                                      |
|------------------------------|--------------------------------------------------------|
| Abstraction & Inheritance    | `Vehicle` (abstract) → `Car`, `SUV`, `Bike`             |
| Polymorphism                 | `calculateRent()` overridden per vehicle type           |
| Encapsulation                | Private fields with controlled getters/setters          |
| Collections Framework        | `Map`, `List` used for vehicles, customers, rentals      |
| Custom Exceptions            | `VehicleNotAvailableException`, `InvalidOperationException` |
| File I/O & Serialization     | `RentalSystem.saveData()` / `loadData()`                |
| Java Time API                | `LocalDate` for rent/return dates                        |
| Layered architecture         | `model` / `service` / entry-point (`Main`) packages      |

## Project structure

```
CarRentalSystem/
├── src/
│   └── com/carrental/
│       ├── Main.java                     # console UI / entry point
│       ├── model/
│       │   ├── Vehicle.java              # abstract base class
│       │   ├── Car.java
│       │   ├── SUV.java
│       │   ├── Bike.java
│       │   ├── Customer.java
│       │   └── Rental.java
│       ├── service/
│       │   └── RentalSystem.java         # business logic + persistence
│       └── exception/
│           ├── VehicleNotAvailableException.java
│           └── InvalidOperationException.java
├── data/                                  # auto-created; stores .dat files
└── README.md
```

## How to compile and run

Requires JDK 8 or later (any modern JDK works, e.g. JDK 17/21).

```bash
# From the CarRentalSystem/ directory:

# 1. Compile
javac -d out $(find src -name "*.java")

# 2. Run
java -cp out com.carrental.Main
```

On Windows (PowerShell), replace step 1 with:
```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java -Path src).FullName
```

The app seeds a few sample vehicles and customers on first run. Everything
you add or rent is saved to the `data/` folder automatically when you choose
"Save & Exit" from the menu.

## Sample session

```
1. View All Vehicles
2. View Available Vehicles
3. Add New Vehicle
4. Register New Customer
5. View All Customers
6. Rent a Vehicle
7. Return a Vehicle
8. View Active Rentals
9. View Rental History
0. Save & Exit
```

## Ideas for extending this project (good for follow-up interview talking points)

- Swap the file-based persistence for a real database (JDBC + MySQL/SQLite)
- Add a REST API layer with Spring Boot and a simple web front end
- Add unit tests with JUnit for the rent/return logic and pricing rules
- Add role-based access (admin vs customer) and login
- Add overdue-return penalty pricing based on `LocalDate` comparisons

## Suggested resume bullet points

- Built a Java console application implementing a car rental workflow with
  inheritance-based vehicle pricing (Car/SUV/Bike), custom exception
  handling, and object serialization for data persistence.
- Designed a layered architecture (model/service/UI) using core Java
  collections to manage 100s of vehicles, customers, and rental records with
  O(1) lookups via HashMap-backed storage.
