package main;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/************************************************
 * FlashcardHandler library does the following: *
 * 	- Initializing a flashcard set to set up    *
 * 	  in creating flashcards.				   *
 * 	- Switches the flashcard's question to	   *
 * 	  answer or vice-versa.                     *
 * 	- Switches flashcards.                      *
 * 	- Keeps track of the user's current         *
 *    flashcard.                                *
 * 	- Creates a flashcard containing the        *
 *    question and the answer.                  *
 ************************************************/

/**
 * Flashcard which includes the question and its answer
 * @author ninjagodivan
 */
class Flashcard{
	String question, answer;
}

public class FlashcardHandler {
	
	/*
	 * Current index position of the flashcard list
	 * Constraints: 0 <= flashcardTracker < set size
	 */
	private int flashcardTracker;
	
	//Flashcard List that the user selected
	private ArrayList <Flashcard> flashcard_list;
	
	//0 = Question, 1 = Answer
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
		System.out.println("Current flashcard position at: " + flashcardTracker);
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
		flashcardTracker++;
		System.out.println("Current flashcard position at: " + flashcardTracker);
	}
	
	/**
	 * Goes to the previous flashcard
	 */
	public void prevFlashcard() {
		flashcardTracker--;
		System.out.println("Current flashcard position at: " + flashcardTracker);
	}
	
	/**
	 * Changes the flashcard
	 */
	public void changeFlashcard(int flashcardTracker) {
		this.flashcardTracker = flashcardTracker;
		System.out.println("Current flashcard position at: " + this.flashcardTracker);
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
	public int getFlashcardIndicator() {
		return flashcardIndicator;
	}
	
	/**
	 * Returns the current index of the
	 * flashcard
	 * @return
	 */
	public int getFlashcardTracker() {
		return flashcardTracker;
	}
	
	/**
	 * Creates a flashcard view whenever
	 * the user switches the flashcard or
	 * changes its question to answer
	 * (vice-versa).
	 * @return
	 */
	public JPanel createFlashcardView() {
		
		//Flashcard Structure
		JPanel flashcard_view = new JPanel(new GridBagLayout());
		flashcard_view.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		
		String description;
				
		//Gets either the question or answer of the flashcard
		if(getFlashcardIndicator() == 0)
			 description = getQuestion();
		else
			 description = getAnswer();

		//String size of the question or answer
		int descriptionSize = description.toCharArray().length;
		
		//Amount of characters per line
		int char_limit = 30;
		
		/*
		 * If the amount of characters of question or answer exceeds more than
		 * the character limit, then split the question/answer into substrings. 
		 */
		if(descriptionSize > char_limit) {
			for(int i = 0, j = 0; i < descriptionSize; i += char_limit, j++) {
								
				//Label that will be added in the flashcard
				JLabel descriptionText;
				//Substring of the string of question/answer
				String _substring = null;
				//False if the word hasn't move to the next line
				boolean hasMovedtoNextLine = false;
				
				/*
				 * Subtracts the value that references the last index of the substring.
				 * It's responsible for repositioning i before the next iteration to
				 * get the rest of the characters.
				 */
				int decrementor = 0;
				
				if(i + char_limit < descriptionSize) {
											
					//Takes 30 consecutive letters from the description
					_substring = description.substring(i, i + char_limit);
					
					
					/* 
					 * Must check if the last char doesn't contain
					 * any character besides a space AND that there
					 * is not a space after that letter. If so,
					 * then the word that is associated with that
					 * letter needs to be moved to the next line
					 * 
					 */
					if(description.charAt(i + (char_limit - 1)) != ' ' && description.charAt(i + char_limit) != ' ') {
						
						
						//Gets the last index of the substring
						int index = char_limit - 1;
						
						//Loops through the list backwards to find a space
						do {
							//System.out.println("Index: " + index);
							index--;
							decrementor++;
							if(index == -1)
								break;
							
						} while(_substring.charAt(index) != ' ');
						
						/*
						 * Updates the substring to where it ends with a space.
						 * Otherwise, no letters are to be moved to the next line.
						 */
						if(index > -1) {
							_substring = description.substring(i, (i + char_limit) - (decrementor + 1));
							hasMovedtoNextLine = true;
						}
					}
					
				}
				else
					_substring = description.substring(i, descriptionSize);
								
				descriptionText = new JLabel(_substring);
				descriptionText.setFont(new Font("Helvetica", Font.PLAIN, 14));
				c.gridx = 0;
				c.gridy = j;
				flashcard_view.add(descriptionText, c);
				
				//Moves 'i' to get all letters to the be the part of the next substring
				if(hasMovedtoNextLine) 
					i -= decrementor;
			}
		} else {
			JLabel descriptionText = new JLabel(description);
			descriptionText.setFont(new Font("Helvetica", Font.PLAIN, 14));
			c.gridx = 0;
			c.gridy = 0;
			flashcard_view.add(descriptionText, c);
		}

			
		return flashcard_view;
	}
}
