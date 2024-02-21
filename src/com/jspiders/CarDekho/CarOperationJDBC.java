package com.jspiders.CarDekho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CarOperationJDBC {

    private static final String INSERT_CAR_SQL = "INSERT INTO car (id, name, price) VALUES (?, ?, ?)";
    private static final String FIND_CAR_BY_ID_SQL = "SELECT * FROM car WHERE id = ?";
    private static final String FIND_ALL_CARS_SQL = "SELECT * FROM car";
    private static final String DELETE_CAR_BY_ID_SQL = "DELETE FROM car WHERE id = ?";
    private static final String UPDATE_CAR_PRICE_SQL = "UPDATE car SET price = ? WHERE id = ?";

    private Connection connection;

    public CarOperationJDBC(Connection connection) {
        this.connection = connection;
    }

    public void addCar(Scanner scanner) throws SQLException {
        System.out.println("Enter car id");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter car name");
        String name = scanner.nextLine();
        System.out.println("Enter car price");
        double price = scanner.nextDouble();

        try (PreparedStatement statement = connection.prepareStatement(INSERT_CAR_SQL)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setDouble(3, price);
            statement.executeUpdate();
            System.out.println("Car added");
        }
    }

    public void findCarById(Scanner scanner) throws SQLException {
        System.out.println("Enter car id");
        int id = scanner.nextInt();

        try (PreparedStatement statement = connection.prepareStatement(FIND_CAR_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Car car = new Car();
                    car.setId(resultSet.getInt("id"));
                    car.setName(resultSet.getString("name"));
                    car.setPrice(resultSet.getDouble("price"));
                    System.out.println(car);
                } else {
                    System.out.println("Car not found");
                }
            }
        }
    }

    public void findAllCars() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_CARS_SQL)) {
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setName(resultSet.getString("name"));
                car.setPrice(resultSet.getDouble("price"));
                System.out.println(car);
            }
        }
    }

    public void deleteCar(Scanner scanner) throws SQLException {
        System.out.println("Enter car id");
        int id = scanner.nextInt();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID_SQL)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Car deleted");
            } else {
                System.out.println("Car not found");
            }
        }
    }

    public void updateCar(Scanner scanner) throws SQLException {
        System.out.println("Enter car id");
        int id = scanner.nextInt();
        System.out.println("Enter new price");
        double price = scanner.nextDouble();

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_PRICE_SQL)) {
            statement.setDouble(1, price);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Car price updated");
            } else {
                System.out.println("Car not found");
            }
        }
    }
}
