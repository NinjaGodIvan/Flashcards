package main;
import java.sql.*;
import java.util.*;

/********************************************
 * Database API which allows Java Eclipse   *
 * to manipulate MySQL Flashcard data.      *
 ********************************************/

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
			
			//You can change the name of the schema instead of Flashcards
			String url = "jdbc:mysql://localhost:3306/Flashcards";
			
			String username = "root", password = "pass";
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			//System.out.println("Connected to Database!");
			return connection; 
		} catch(Exception e) {
			System.out.println("Unable to connect to database:\n " + e);
		}
			return null;
	}
	
	/**
	 * Creates a flashcard set. All trailing spaces are to be removed
	 * from the name of that set.
	 * @param newSet
	 */
	public static void addFlashCardSet(String newSet) {
		
		try {
			Connection connect = getConnection();
			//Escapes single quotes
			PreparedStatement add = connect.prepareStatement("CREATE TABLE `[" + newSet + "]`(flashcard_id INT AUTO_INCREMENT PRIMARY KEY, question TEXT, answer TEXT);");
			add.executeUpdate();
			System.out.println("addFlashCardSet function success!!");
		}catch(Exception e) {
			System.out.println("\naddFlashCardSet failed:\n" + e);
		}
		
	}
	
	public static void deleteFlashCardSet(String goodByeSet) {
		
		try {
			Connection connect = getConnection();
			PreparedStatement remove = connect.prepareStatement("DROP TABLE `[" + goodByeSet + "]`;");
			remove.executeUpdate();
			System.out.println("deleteFlashCardSet function success!!");
		} catch(Exception e) {
			System.out.println("\ndeleteFlashCardSet failed:\n" + e);
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
			question = escapeCharacters(question);
			answer = escapeCharacters(answer);
			PreparedStatement add = connect.prepareStatement("INSERT INTO `[" + flashcardSet + "]` (question, answer) VALUES('" + question + "','" + answer + "');");
			add.executeUpdate();
			System.out.println("addFlashCard function success!!");
		}catch(Exception e) {
			System.out.println("\naddFlashCard failed:\n" + e);
		}
		
	}
	
	/**
	 * Updates the question, answer, or both.
	 */
	public static void editFlashcard(String oldQuestion, String oldAnswer, String newQuestion, String newAnswer, String flashcardSet) {
		
		try {
			
			Connection connect = getConnection();
			PreparedStatement edit;
			oldQuestion = escapeCharacters(oldQuestion);
			oldAnswer = escapeCharacters(oldAnswer);
			newQuestion = escapeCharacters(newQuestion);
			newAnswer = escapeCharacters(newAnswer);
			
			if(!oldQuestion.equals(newQuestion) && oldAnswer.equals(newAnswer)) {
				edit = connect.prepareStatement("UPDATE `[" + flashcardSet + "]` SET question = '" + newQuestion + "' WHERE question = '" + oldQuestion + "';");
				System.out.println("\nYou have changed the question!");
			}
			else if(oldQuestion.equals(newQuestion) && !oldAnswer.equals(newAnswer)) {
				edit = connect.prepareStatement("UPDATE `[" + flashcardSet + "]` SET answer = '" + newAnswer + "' WHERE question = '" + oldQuestion + "';");
				System.out.println("\nYou have changed the answer!");
			}
			else {
				edit = connect.prepareStatement("UPDATE `[" + flashcardSet + "]` SET question = '" + newQuestion + "', answer = '" + newAnswer + "' WHERE question = '" + oldQuestion + "';");
				System.out.println("\nYou have changed the question and the answer!");
			}
			edit.executeUpdate();

			System.out.println("editFlashcard success!!");
		}catch(Exception e) {
			System.out.println("\neditFlashcard failed\n" + e);
		}
	}
	
	/**
	 * Removes a flashcard from a specified flashcard set
	 * @param question
	 * @param flashcardSet
	 */
	public static void removeFlashCard(String question, String flashcardSet) {
		
		try {
			Connection connect = getConnection();
			question = escapeCharacters(question);
			PreparedStatement remove = connect.prepareStatement("DELETE FROM `[" + flashcardSet + "]` WHERE question = '" + question + "';");
			remove.executeUpdate();
			System.out.println("removeFlashCard function success!!");
		} catch(Exception e) {
			System.out.println("\nremoveFlashCard failed:\n" + e);
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
			
			//Adds all flashcards sets to the array list and trims the tables' names to get rid of spaces
			while(res.next()) {
				String new_string = bracketRemover(res.getString("TABLE_NAME").toCharArray());
				//Needs to be trimmed to get rid of spaces that replace the brackets
				flashcardSets.add(new_string.trim());
			}
			System.out.println("getAllFlashCardSets function success!!");
			return flashcardSets;

		}catch(Exception e) {
			System.out.println("\ngetAllFlashCardSets failed:\n" + e);
		}
		
		return null;
	}
	
	/**
	 * Gets one of the flashcards from a specified flashcard set.
	 * This is only used when a user wants to edit a flashcard.
	 * @param flashcardSet
	 * @param question
	 */
	public static Flashcard getFlashcard(String flashcardSet, String question) {
		
		//Temporary flashcard
		Flashcard temp = new Flashcard();
		
		try {
			
			Connection connect = getConnection();
			question = escapeCharacters(question);
			PreparedStatement get = connect.prepareStatement("SELECT question, answer FROM `[" + flashcardSet + "]` WHERE question = '" + question + "';");
			ResultSet res = get.executeQuery();
			res.next();
			temp.question = res.getString(1);
			temp.answer = res.getString(2);
			System.out.println("getFlashcard function success!!");
			return temp;
		} catch (Exception e){
			System.out.println("\ngetFlashcard failed:\n" + e);
		}
		
		return null;
	}
	
	/**
	 * Returns all flashcards from a specified flashcard set
	 * @param flashcardSet
	 * @return
	 */
	public static ArrayList <Flashcard> getAllFlashcards(String flashcardSet){
		
		ArrayList <Flashcard> flashcard_list = new ArrayList<>();
		
		try {
			Connection connect = getConnection();
			PreparedStatement getAll = connect.prepareStatement("SELECT question, answer FROM `[" + flashcardSet + "]`;");
			ResultSet res = getAll.executeQuery();
			
			//Adds a flashcard to the flashcard set
			while(res.next()) {
				Flashcard flashcard = new Flashcard();
				flashcard.question = res.getString(1);
				flashcard.answer = res.getString(2);
				flashcard_list.add(flashcard);
			}
			System.out.println("getAllFlashcards function success!!");
			return flashcard_list;
		} catch(Exception e) {
			System.out.println("\ngetAllFlashcards failed:\n" + e);
		}
		
		return null;
	}
	
	/**
	 * Checks if the flashcard set exists
	 * @param set
	 * @return
	 */
	public static boolean flashcardSetExists(String flashcardSet) {
				
		try {
			
			Connection connect = getConnection();
			flashcardSet = escapeCharacters(flashcardSet);
			PreparedStatement get = connect.prepareStatement("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = 'Flashcards' AND TABLE_NAME = '" + flashcardSet + "'");
			ResultSet res = get.executeQuery();
			System.out.println("flashcardSetExists function success!");

			//It is false when nothing is contained in the query
			if(res.next()) {
				return true;
			}
		}catch(Exception e) {
			System.out.println("\nflashcardSetExists failed:\n" + e);
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
			question = escapeCharacters(question);
			PreparedStatement get = connect.prepareStatement("SELECT question FROM `[" + flashcardSet + "]` WHERE question = '" + question + "';");
			ResultSet res = get.executeQuery();
			System.out.println("flashcardExists function success!");
			if(res.next()) {
				return true;
			}
			
		}catch(Exception e) {
			System.out.println("\nflashcardExists failed:\n" + e);
		}
		
		return false;
	}
	
	/**
	 * Removes brackets from the first and last index of the character array.
	 * For example: [Hello] will become Hello
	 * @param a
	 * @return
	 */
	public static String bracketRemover(char [] a) {
		a[0] = ' ';
		a[a.length - 1] = ' ';
		return String.valueOf(a);
	}
	
	/**
	 * Adds backslashes before the escaping characters.
	 * This is to prevent MySQL syntax errors
	 * from happening. Returns the updated string.
	 * 
	 * MySQL escaping characters: single quote and backslash
	 * @param new_string
	 * @return
	 */
	public static String escapeCharacters(String new_string) {
				
		for(int i = 0; i < new_string.length(); i++) {
			
			//If an index references an escaping character, add a backslash
			if(new_string.charAt(i) == '\'' || new_string.charAt(i) == '\\') {
				
				/*
				 * Substring that starts with the first character
				 * and ends with the a non-backslash character that's
				 * next to the escaping character
				 */
				String substring = new_string.substring(0, i);
				substring += "\\";
				new_string = substring + new_string.substring(i);
				
				//References "i" to the character after the escaping character
				i += 1;
			}
		}
		
		return new_string;
	}
}
