package main;
import java.sql.*;
import java.util.ArrayList;

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
			String username = "root", password = "pass";
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database Connected!");
			return connection; 
		} catch(Exception e) {
			System.out.println(e);
		}
			return null;
	}
	
	/**
	 * Creates a flashcard table
	 * @param newSet
	 */
	public static void addFlashCardSet(String newSet) {
		
		try {
			
			Connection connect = getConnection();
			PreparedStatement add = connect.prepareStatement("CREATE TABLE " + newSet + "(flashcard_id INT AUTO_INCREMENT PRIMARY KEY, question TEXT, answer TEXT);");
			add.executeUpdate();
			System.out.println("Flashcard Set Added!!!");
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * Adds a flashcard to a specified flashcard set
	 * @param question
	 * @param answer
	 * @param flashcardSet
	 */
	public static void addFlashCard(String question, String answer, String flashcardSet) {
		
		try {
			Connection connect = getConnection();
			PreparedStatement add = connect.prepareStatement("INSERT INTO " + flashcardSet + " (question, answer) VALUES('" + question + "','" + answer + "');");
			add.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * Returns all flashcard sets from the flashcard database
	 * @return 
	 */
	public static ArrayList<String> getAllFlashCardSets(){
		
		//Temp flashcards array that gets returned
		ArrayList<String> flashcardSets = new ArrayList<>();
		
		try {
			
			//Gets all tables from Flashcards schema
			Connection connect = getConnection();
			PreparedStatement getAll = connect.prepareStatement("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = 'Flashcards';");
			ResultSet res = getAll.executeQuery();
			
			//Adds all flashcards sets to the array list
			while(res.next()) {
				flashcardSets.add(res.getString("TABLE_NAME"));
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return flashcardSets;
	}
	
	/**
	 * Checks if the flashcard set exists
	 * @param set
	 * @return
	 */
	public static boolean flashcardSetExists(String set) {
		
		try {
			
			Connection connect = getConnection();
			PreparedStatement get = connect.prepareStatement("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = 'Flashcards' AND TABLE_NAME = '" + set + "'");
			ResultSet res = get.executeQuery();
			
			//It is false when nothing is contained in the query
			if(res.next())
				return true;
		}catch(Exception e) {
			System.out.print(e);
		}
		
		return false;
	}
	
	/**
	 * Checks if the flashcard already exists in the set.
	 * NOTE: question is the name of the flashcard
	 * @param question, flashcardSet
	 * @return
	 */
	public static boolean flashcardExists(String question, String flashcardSet) {
		
		try {
			
			Connection connect = getConnection();
			PreparedStatement get = connect.prepareStatement("SELECT question FROM " + flashcardSet + " WHERE question = '" + question + "';");
			ResultSet res = get.executeQuery();
			if(res.next())
				return true;
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
}
