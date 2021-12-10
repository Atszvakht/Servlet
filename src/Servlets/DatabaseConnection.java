package Servlets;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 

// This class can be used to initialize the database connection 
public class DatabaseConnection { 
	protected static Connection initializeDatabase() 
		throws SQLException, ClassNotFoundException 
	{ 
		// Initialize all the information regarding 
		// Database Connection 
		String dbDriver = "com.mysql.jdbc.Driver"; 
		String dbURL = "jdbc:mysql://localhost:3306/"; 
		// Database name to access 
		String dbName = "emails"; 
		String dbUsername = "root"; 
		String dbPassword = ""; 

		Class.forName(dbDriver); 
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbURL + dbName+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", 
					dbUsername, 
					dbPassword); 
	         System.out.println("Connected to MySQL database");
	         return con;
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return con;
	} 
} 
