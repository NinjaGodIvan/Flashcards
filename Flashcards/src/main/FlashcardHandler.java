package main;
import java.util.*;

/**
 * Flashcard which includes the question and its answer
 * @author ninjagodivan
 */
class Flashcard{
	String question, answer;
}

/***
 * Flashcard Handler API 
 * @author ninjagodivan
 *
 */
public class FlashcardHandler {
	
	/*
	 * Current index position of the flashcard list
	 * Constraints: -1 < flashcardTracker < set size
	 */
	private int flashcardTracker;
	
	//Flashcard List that the user selected
	private ArrayList <Flashcard> flashcard_list;
	
	/*
	 * 0: Question
	 * 1: Answer
	 */
	private int flashcardIndicator;
	
	//Name of the flashcard set
	private String flashcardSet;

	/**
	 * Initializes flashcardTracker to reference the
	 * first flashcard and flashcard list to be the
	 * set that the user selected
	 * @param flashcardSet
	 */
	public FlashcardHandler(String flashcardSet) {
		flashcardTracker = 0;
		flashcardIndicator = 0;
		this.flashcardSet = flashcardSet;
		flashcard_list = DatabaseHandler.getAllFlashcards(flashcardSet);
	}
	
	/**
	 * Gets the name of the flashcard set
	 * @return
	 */
	public String getFlashcardSetName() {
		return this.flashcardSet;
	}
	
	/**
	 * Gets the array of flashcards
	 * @return
	 */
	public ArrayList<Flashcard> getFlashcardSet(){
		return this.flashcard_list;
	}
	
	/**
	 * Goes to the next flashcard
	 */
	public void nextFlashcard() {
		if(flashcardTracker + 1 != flashcard_list.size())
			flashcardTracker++;
		System.out.println("New flashcard position at: " + flashcardTracker);
	}
	
	/**
	 * Goes to the previous flashcard
	 */
	public void prevFlashcard() {
		
		if(flashcardTracker - 1 != -1)
			flashcardTracker--;
		
		System.out.println("New flashcard position at: " + flashcardTracker);
	}
	
	/**
	 * Changes the flashcard
	 */
	public void changeFlashcard(String question) {
	//	flashcardTracker = flashcard_list
	}
	
	/**
	 * Gets the current flashcard
	 * @return
	 */
	public Flashcard getFlashcard() {
		return flashcard_list.get(flashcardTracker);
	}
	
	/**
	 * Gets the question of the current flashcard
	 * @return
	 */
	public String getQuestion() {
		return flashcard_list.get(flashcardTracker).question;
	}
	
	/**
	 * Gets the answer of the current flashcard
	 * @return
	 */
	public String getAnswer() {
		return flashcard_list.get(flashcardTracker).answer;
	}
	
	/**
	 * Switches from question to answer (vice-versa)
	 */
	public void switchIndicator() {
		
		if(flashcardIndicator == 0)
			flashcardIndicator = 1;
		else
			flashcardIndicator = 0;
	}
	
	/**
	 * Returns the flashcard indicator
	 * @return
	 */
	public int getIndicator() {
		return flashcardIndicator;
	}
}
