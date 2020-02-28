package main;
import java.sql.Connection;
import java.sql.DriverManager;

/***
 * DatabaseHandler is responsible for adding flashcards
 * and their sets as well as deleting them
 * @author ninjagodivan
 *
 */
public class DatabaseHandler {
	
	/**
	 * Connects to MySQL server. You will
	 * need to enter your username and
	 * password.
	 * @return
	 */
	public static Connection getConnection() {
			
			try {
				String driver = "com.mysql.cj.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/Flashcards";
				String username = "root", password = "friendhomie9966";
				Class.forName(driver);
				Connection connection = DriverManager.getConnection(url, username, password);
				System.out.println("Database Connected!");
				return connection; 
			} catch(Exception e) {
				System.out.println(e);
			}
			
			return null;
		}
	
	public static void addFlashCardSet(String newSet) {
		
		try {
			
			Connection connect = getConnection();
			
		}catch(Exception e) {
			
		}
		
	}

}
