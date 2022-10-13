package com.assignments.PayrollService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class PayrollServiceDB {
	static Connection connection = null;

	// Main method
	public static void main(String[] args) throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "Aman";
		String password = "Aman";

		databaseConnection(jdbcURL, userName, password); // Creating Database Connection
		closeConnection(); // Closing The Connection After Execution
	}

	// Loading Drivers And Creating Database Connection
	private static void databaseConnection(String jdbcURL, String userName, String password) {
		// Driver Loading
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot Find The Driver In Classpath!", e);
		}
		listDrivers();

		// Connecting to DataBase
		try {
			System.out.println("Connecting to database: " + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection Successfull !!! -> " + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// List Drivers Method
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("-> " + driverClass.getClass().getName());
		}
	}

	// Close Connection Method
	private static void closeConnection() throws SQLException {
		connection.close();
	}
}