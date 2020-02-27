package main;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHandler {
	
	public static Connection getConnection() throws Exception {
			
			try {
				String driver = "com.mysql.cj.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/Flashcards";
				String username = "user", password = "pass";
				Class.forName(driver);
				
				Connection connection = DriverManager.getConnection(url, username, password);
				System.out.println("Database Connected!");
				return connection;
			} catch(Exception e) {
				System.out.println(e);
			}
			
			return null;
		}

}
