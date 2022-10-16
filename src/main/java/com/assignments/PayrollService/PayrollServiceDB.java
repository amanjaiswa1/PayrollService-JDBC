package com.assignments.PayrollService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class PayrollServiceDB {
	static Connection connection = null;

	// Main method
	public static void main(String[] args) throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "Aman";
		String password = "Aman";

		databaseConnection(jdbcURL, userName, password); // Creating Database Connection
		readEmployeePayrollDataFromDB(); // Retrieving Data From Database
		updateSalaryUsingStatement(); // Updating Salary Using Statement
		updateSalaryUsingPreparedStatement(); // Updating Salary Using Prepared Statement
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

	// Retrieve The Employee Payroll Data From Data
	private static void readEmployeePayrollDataFromDB() {
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from Employee_payroll");
			System.out.println("\n" + result + " records retrieved.");
			while (result.next()) {
				System.out.print("ID->" + result.getInt("ID") + " : ");
				System.out.print("Name->" + result.getString("Name") + " : ");
				System.out.print("Salary->" + result.getString("Salary") + " : ");
				System.out.print("StartDate->" + result.getString("StartDate") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update Employee Salary By Using Employee Name (Statement)
	private static void updateSalaryUsingStatement() {
		try {
			Statement statement = connection.createStatement();
			String query = "update employee_payroll set Salary=40000.00 where Name='Aarya' ";
			Integer recordUpdated = statement.executeUpdate(query);
			System.out.println("\nRecords Updated(Statement) : " + recordUpdated);
			readEmployeePayrollDataFromDB(); // Retrieving Data From Database
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update Employee Salary By Using Employee Name (Prepared Statement)
	private static void updateSalaryUsingPreparedStatement() {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPdate employee_payroll set Salary=? where Name=? ");
			preparedStatement.setDouble(1, 60000.00);
			preparedStatement.setString(2, "Chaya");
			Integer recordUpdated = preparedStatement.executeUpdate();
			System.out.println("\nRecords Updated(Prepared Statement): " + recordUpdated);
			readEmployeePayrollDataFromDB(); // Retrieving Data From Database
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Close Connection Method
	private static void closeConnection() throws SQLException {
		connection.close();
	}
}
