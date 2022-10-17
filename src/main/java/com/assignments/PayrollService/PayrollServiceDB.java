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
		readPayrollDataByName(); // Retrieving Payroll Data By Name Using Prepared Statement
		displayRecordsWithinGivenDateRange(); // Retrieving Data Within Given Date Range
		databaseFunctions(); // Using Database Functions(SUM, AVG, MIN, MAX,COUNT)
		insertNewPayrollIntoTable(); // Inserting New Employee To The Payroll
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

	// Retrieve The Employee Payroll Data From Database
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

	// Retrieve The Employee Payroll Data By Name
	private static void readPayrollDataByName() {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("Select * from Employee_payroll where Name=?");
			preparedStatement.setString(1, "Chaya");
			ResultSet result = preparedStatement.executeQuery();
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

	// Retrieve The Employee Payroll Data In Given Date Range
	public static void displayRecordsWithinGivenDateRange() {
		try {
			Statement statement = connection.createStatement();
			String query = "Select * from employee_payroll where StartDate between '2017-01-01' and DATE(now())";
			ResultSet result = statement.executeQuery(query);
			System.out.println("\n" + result + " records retrieved.");
			while (result.next()) {
				System.out.print("ID->" + result.getInt("ID") + " : ");
				System.out.print("Name->" + result.getString("Name") + " : ");
				System.out.print("Salary->" + result.getFloat("Salary") + " : ");
				System.out.print("StartDate->" + result.getDate("StartDate") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Use Database Functions(SUM, AVG, MIN, MAX,COUNT) To Do Analysis By Gender
	private static void databaseFunctions() {
		try {
			Statement statement = connection.createStatement();

			// SUM Function
			ResultSet result = statement
					.executeQuery("SELECT SUM(Salary) as TotalSUM, Gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records retrieved.");
			System.out.println("\n:: SUM ::");
			while (result.next()) {
				System.out.print("Sum->" + result.getString("TotalSUM") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			// AVG Function
			result = statement
					.executeQuery("SELECT AVG(Salary) as Average, Gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records retrieved.");
			System.out.println("\n:: AVERAGE ::");
			while (result.next()) {
				System.out.print("Average->" + result.getString("Average") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			// MIN Function
			result = statement
					.executeQuery("SELECT MIN(Salary) as Minimum, Gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records retrieved.");
			System.out.println("\n:: MINIMUM ::");
			while (result.next()) {
				System.out.print("Minimum->" + result.getString("Minimum") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			// MAX Function
			result = statement
					.executeQuery("SELECT MAX(Salary) as Maximum, Gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records retrieved.");
			System.out.println("\n:: MAXIMUM ::");
			while (result.next()) {
				System.out.print("Maximum->" + result.getString("Maximum") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			// COUNT Function
			result = statement
					.executeQuery("SELECT COUNT(Name) as Count, Gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records retrieved.");
			System.out.println("\n:: COUNT ::");
			while (result.next()) {
				System.out.print("Count->" + result.getString("Count") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Insert A New Employee To The Payroll
	private static void insertNewPayrollIntoTable() {
		try {
			String query = "Insert into employee_payroll values(?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, 5);
			preparedStatement.setString(2, "Gauravi");
			preparedStatement.setDouble(3, 35000.00);
			preparedStatement.setDate(4, java.sql.Date.valueOf("2018-02-09"));
			preparedStatement.setString(5, "F");
			Integer recordUpdated = preparedStatement.executeUpdate();
			System.out.println("\nRecords Updated(Statement) : " + recordUpdated);
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
