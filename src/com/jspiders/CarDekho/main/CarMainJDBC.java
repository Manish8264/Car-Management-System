package com.jspiders.CarDekho.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.jspiders.CarDekho.CarOperationJDBC;

public class CarMainJDBC {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weja4";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            CarOperationJDBC carOperationJDBC = new CarOperationJDBC(connection);

            boolean flag = true;
            while (flag) {
                System.out.println("Enter 1 to add a car"
                		+ "\nEnter 2 to search car by id"
                		+ "\nEnter 3 to fetch all cars"
                		+ "\nEnter 4 to delete a car"
                		+ "\nEnter 5 to update car price"
                		+ "\nEnter 6 to exit");
                System.out.println("Enter your choice");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        carOperationJDBC.addCar(scanner);
                        break;
                    case 2:
                        carOperationJDBC.findCarById(scanner);
                        break;
                    case 3:
                        carOperationJDBC.findAllCars();
                        break;
                    case 4:
                        carOperationJDBC.deleteCar(scanner);
                        break;
                    case 5:
                        carOperationJDBC.updateCar(scanner);
                        break;
                    case 6:
                        flag = false;
                        System.out.println("Thank You");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

