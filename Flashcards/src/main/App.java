package main;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Interface that consists all menus
 * of the application.
 * @author ninjagodivan
 *
 */
interface Menus {
	JPanel mainMenu();
	JPanel createFlashcardSet();
	JPanel createFlashcard(String flashcardSet);
	JPanel viewFlashcards();
	JPanel deleteFlashcard(String flashcardSet);
	JPanel editFlashcard();
	JPanel successMessage(int message_id, String flashcardSet);
	JPanel successMessage(int key, String question, String flashcardSet);
	JPanel failMessage();
	JPanel failMessage(String flashcardSet);
	JPanel flashcardSetSelection(int key);
	JPanel flashcardGlossary(ArrayList <Flashcard> flashcard_list);
}

/***
 * This is the Java application that
 * takes over all the functionalities
 * @author ninjagodivan
 *
 */
public class App extends JFrame implements Menus{
	
	//Serial Version
	private static final long serialVersionUID = 1L;
	//Item that is currently being selected (Should be initialized to have no characters)
	private static String itemSelected = "";
	//FlashcardHandler
	FlashcardHandler flashcardHandler;
	
	/**
	 * Program starts with the main menu
	 */
	public App() {
		
		//NOTE: When app starts, you can initially start up any GUI for debugging purposes
		add(mainMenu());
		//add(flashcardSetSelection(3));
		
		setTitle("Flashcards");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}
	
	/**
	 * Disposes old panel and create a new panel
	 * @param oldPanel
	 * @param newPanel
	 */
	public void switchPanel(JPanel oldPanel, JPanel newPanel) {
		oldPanel.removeAll();
		add(newPanel);
		newPanel.repaint();
		newPanel.revalidate();
	}

	/**
	 * Main menu of the application
	 */
	@Override
	public JPanel mainMenu() {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title of the app
		JLabel title = new JLabel("Flashcards");
		title.setFont(new Font("Helvetica", Font.BOLD,18));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		//Group of buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		//All buttons from the main menu
		JButton createFlashCardSet = new JButton("Create a Flashcard Set");
		JButton deleteFlashCardSet = new JButton("Delete a Flashcard Set");
		JButton createFlashCard = new JButton("Create a Flashcard");
		JButton editFlashcard = new JButton("Edit a Flashcard");
		JButton deleteFlashCard = new JButton("Delete a Flashcard");
		JButton 	viewFlashCards = new JButton("View Flashcards");

		//Fires when one of the main meny button gets clicked
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource().equals(createFlashCardSet)) {
					switchPanel(main, createFlashcardSet());
				} else if(e.getSource().equals(deleteFlashCardSet)) {
					switchPanel(main, flashcardSetSelection(2));
				} else if(e.getSource().equals(createFlashCard))	{
					switchPanel(main, flashcardSetSelection(0));
				} else if(e.getSource().equals(deleteFlashCard)) {
					switchPanel(main, flashcardSetSelection(1));
				} else if(e.getSource().equals(viewFlashCards)) {
					switchPanel(main, flashcardSetSelection(3));
				} else {
					switchPanel(main, editFlashcard());
				}
			}
		};
		
		//Adds "Create a Flashcard Set" button
		createFlashCardSet.addActionListener(actionListener);
		createFlashCardSet.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,0,0,0);
		buttons.add(createFlashCardSet,c);
		
		//Adds "Delete a Flashcard Set" button
		deleteFlashCardSet.addActionListener(actionListener);
		deleteFlashCardSet.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 1;
		buttons.add(deleteFlashCardSet,c);
		
		//Adds "Create a Flashcard" button
		createFlashCard.addActionListener(actionListener);
		createFlashCard.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 2;
		buttons.add(createFlashCard,c);
		
		//Adds "Edit a Flashcard Quiz" button
		editFlashcard.addActionListener(actionListener);
		editFlashcard.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 3;
		buttons.add(editFlashcard,c);
		
		//Adds "Delete a Flashcard" Menu
		deleteFlashCard.addActionListener(actionListener);
		deleteFlashCard.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 4;
		buttons.add(deleteFlashCard,c);
		
		//Adds "View Flashcards" button
		viewFlashCards.addActionListener(actionListener);
		viewFlashCards.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 5;
		buttons.add(viewFlashCards,c);
		
		
		//Adds button panel to main
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20,0,0,0);
		main.add(buttons,c);
				
		return main;
	}


	/**
	 * Allows user to create a flashcard set
	 * 
	 */
	@Override
	public JPanel createFlashcardSet() {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title of the interface
		JLabel title = new JLabel("Create a Flashcard Set");
		title.setFont(new Font("Helvetica", Font.BOLD, 16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		//Instruction for the user to follow
		JLabel instruction = new JLabel("Enter the name of the flashcard set");
		instruction.setFont(new Font("Helvetica", Font.PLAIN, 12));
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0);
		main.add(instruction,c);
		
		//Error message which is initialized to contain nothing
		JLabel errMessage = new JLabel("");
		errMessage.setForeground(Color.RED);
		errMessage.setFont(new Font("Helvetica", Font.PLAIN, 11));
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,4,0,0);
		c.anchor = GridBagConstraints.WEST;
		main.add(errMessage,c);
				
		//Field where a user enters the name of the flashcard set
		JTextField flashcardSetName = new JTextField();
		flashcardSetName.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		main.add(flashcardSetName,c);
		
		//Button panel for submit and back
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, mainMenu());
			}
		});
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,0,0,0);
		buttonPanel.add(back,c);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					//Will need to surround the flashcard set with brackets for searching
					if(DatabaseHandler.flashcardSetExists("[" +flashcardSetName.getText().trim() + "]")) {
						errMessage.setText("This flashcard set already exists!");
					}else if(flashcardSetName.getText().toCharArray().length < 1) {
						errMessage.setText("Flashcard set name needs at least 1 character!");
					}else {
						//Will need to trim the string to get rid of trailing spaces that the user added
						DatabaseHandler.addFlashCardSet(flashcardSetName.getText().trim());
						switchPanel(main, successMessage(0, flashcardSetName.getText().trim()));
					}
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
			
		});
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(5,10,0,0);
		buttonPanel.add(submit,c);
		
		//Adds button panel to main
		c.gridx = 0;
		c.gridy = 4;
		main.add(buttonPanel,c);
		
		return main;
	}

	/**
	 * Allows user to delete a flashcard 
	 */
	@Override
	public JPanel createFlashcard(String flashcardSet) {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("Create a Flashcard");
		title.setFont(new Font("Helvetica",Font.BOLD,16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		//Instruction for the user to follow
		JLabel instruction = new JLabel("Fill out the form to add to " + flashcardSet);
		instruction.setFont(new Font("Helvetica", Font.PLAIN, 12));
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0);
		main.add(instruction,c);
				
		//Error message which is initialized to contain nothing
		JLabel errMessage = new JLabel("");
		errMessage.setForeground(Color.RED);
		errMessage.setFont(new Font("Helvetica", Font.PLAIN, 11));
		c.gridx = 0;
		c.gridy = 2;
		main.add(errMessage,c);
		
		//Form panel including labels, textfields, and buttons
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		
		JLabel questionLabel = new JLabel("Question:");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		form.add(questionLabel,c);
		
		JLabel answerLabel = new JLabel("Answer:");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets (10,30,0,0);
		form.add(answerLabel,c);
		
		JTextField question = new JTextField();
		question.setPreferredSize(new Dimension(200,30));
		c.gridx = 1;
		c.gridy = 0;
		form.add(question,c);
		
		JTextField answer = new JTextField();
		answer.setPreferredSize(new Dimension(200,30));
		c.gridx = 1;
		c.gridy = 1;
		form.add(answer,c); 
		
		//Adds the form panel to the main
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10,0,0,0);
		main.add(form,c);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		JButton back = new JButton("Back");
		JButton submit = new JButton("Submit");
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(back)) {
					//Restores itemSelected back to nothing
					itemSelected = "";
					switchPanel(main, flashcardSetSelection(0));
				} else {	
					
					try {
						
						if(DatabaseHandler.flashcardExists(question.getText().trim(), flashcardSet)) {
							errMessage.setText("This flashcard has already exists in this set.");
						} else if (question.getText().trim().length() == 0 || answer.getText().trim().length() == 0) {
							errMessage.setText("You must provide a question and answer for the flashcard.");
						} else {
							DatabaseHandler.addFlashCard(question.getText().trim(), answer.getText().trim(), flashcardSet);
							switchPanel(main,successMessage(0,question.getText().trim(),flashcardSet));
						}
			
					}catch(Exception ex) {
						System.out.println(ex);
					}
				}
			}
			
		};
		
		back.addActionListener(actionListener);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		buttons.add(back,c);
		
		submit.addActionListener(actionListener);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(10,30,0,0);
		buttons.add(submit,c);
		
		//Adds buttons to main
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10,0,0,0);
		main.add(buttons,c);

		return main;
	}

	/**
	 * Allows user to view flashcards
	 */
	@Override
	public JPanel viewFlashcards() {
		
		//Restores back to nothing
		itemSelected = "";

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Flashcard Title
		JLabel title = new JLabel(flashcardHandler.getFlashcardSetName());
		title.setFont(new Font("Helvetica", Font.BOLD, 16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		//Scroll Bar Component
		JScrollPane scroll = new JScrollPane();
				
		//Scroll bars only appear when there are more flashcard items or if the flashcard name is longer
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(flashcardHandler.createFlashcardView());
		scroll.setPreferredSize(new Dimension(320,170));
		scroll.revalidate();
	
		//Adds flashcard structure to main
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0);
		main.add(scroll,c);
		
		//Button Panel
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		/*
		 * Previous: Goes to the previous flashcard
		 * Next: Goes to the next flashcard
		 * Switcher: Allows to change from question to answer (vice versa)
		 * Glossary: Goes to the list of flashcard from a selected set 
		 * Main Menu: Goes back to the main menu
		 * Flashcard Set Selection: Goes back to the flashcard set selection
		 */
		JButton previous = new JButton("Previous"), 
		next = new JButton("Next"), 
		glossary = new JButton("Glossary"),
		switcher = new JButton("Answer"),
		mainMenu = new JButton("Main Menu"),
		flashcardSetSelection = new JButton("My Sets");
		
		//Initializes previous button to be disabled
		previous.setEnabled(false);
		
		//If there is only one flashcard, then disable the next button
		if(flashcardHandler.getFlashcardSet().size() == 1)
			next.setEnabled(false);
		
		//Fires when a user presses either of the buttons
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(e.getSource().equals(previous) || e.getSource().equals(next)) {
				
					if(e.getSource().equals(previous)) { 
						flashcardHandler.prevFlashcard();
						//Disables previous button if the user is at the first flashcard
						if(flashcardHandler.getFlashcardTracker() == 0) {
							previous.setEnabled(false);
						}
						
						//Enables the next button if it is disabled
						if(!next.isEnabled()) {
							next.setEnabled(true);
						}
					}
					else {
						flashcardHandler.nextFlashcard();
						//Disables next button if the user is at the last flashcard
						if(flashcardHandler.getFlashcardTracker() == flashcardHandler.getFlashcardSet().size() - 1) { 
							next.setEnabled(false);
						}
						
						//Enables the previous button if it is disabled
						if(!previous.isEnabled()) {
							previous.setEnabled(true);
						}	
					}
					
					//Switches the flashcard to set for the scroll
					scroll.setViewportView(flashcardHandler.createFlashcardView());
			
				} else if(e.getSource().equals(glossary)) {
					switchPanel(main, flashcardGlossary(flashcardHandler.getFlashcardSet()));
				} else if(e.getSource().equals(switcher)) {
					
					//Switches from question to answer (vice-versa)
					flashcardHandler.switchIndicator();
					
					if(flashcardHandler.getFlashcardIndicator() == 0) {
						switcher.setText("Answer");
						if(!next.isEnabled() && flashcardHandler.getFlashcardTracker() != flashcardHandler.getFlashcardSet().size() - 1)
							next.setEnabled(true);
						if(!previous.isEnabled() && flashcardHandler.getFlashcardTracker() > 0)
							previous.setEnabled(true);
						
					} else {
						switcher.setText("Question");
						if(next.isEnabled())
							next.setEnabled(false);
						if(previous.isEnabled())
							previous.setEnabled(false);
					}
					
					scroll.setViewportView(flashcardHandler.createFlashcardView());
					
				} else if(e.getSource().equals(mainMenu)) {
					flashcardHandler = null;
					switchPanel(main, mainMenu());
				} else {
					flashcardHandler = null;
					switchPanel(main, flashcardSetSelection(3));
				}
			}
			
		};

		//Positions Previous button
		previous.addActionListener(actionListener);
		previous.setPreferredSize(new Dimension(100,30));
		c.gridx = 0;
		c.gridy = 0;
		buttons.add(previous,c);
		
		//Positions Question/Answer button
		switcher.addActionListener(actionListener);
		switcher.setPreferredSize(new Dimension(100,30));
		c.gridx = 1;
		c.gridy = 0;
		buttons.add(switcher,c);
		
		//Positions Next Button
		next.addActionListener(actionListener);
		next.setPreferredSize(new Dimension(100,30));
		c.gridx = 2;
		c.gridy = 0;
		buttons.add(next,c);
		
		//Positions Flashcard Set Button
		flashcardSetSelection.addActionListener(actionListener);
		flashcardSetSelection.setPreferredSize(new Dimension(100,30));
		c.gridx = 0;
		c.gridy = 1;
		buttons.add(flashcardSetSelection,c);
		
		//Positions Main Menu button
		mainMenu.addActionListener(actionListener);
		mainMenu.setPreferredSize(new Dimension(100,30));
		c.gridx = 1;
		c.gridy = 1;
		buttons.add(mainMenu,c);
		
		//Positions GLossary button
		glossary.addActionListener(actionListener);
		glossary.setPreferredSize(new Dimension(100,30));
		c.gridx = 2;
		c.gridy = 1;
		buttons.add(glossary,c);
		
		//Adds button panel to main
		c.gridx = 0;
		c.gridy = 2;
		main.add(buttons, c);
		
		return main;
	}

	/**
	 * Allows user to delete flashcard(s)
	 */
	@Override
	public JPanel deleteFlashcard(String flashcardSet) {
		
		//Restoring itemSelected to nothing
		itemSelected = "";
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Gets all flashcards from a specified flashcard list
		ArrayList <Flashcard> flashcard_list = DatabaseHandler.getAllFlashcards(flashcardSet);
		
		if(flashcard_list.isEmpty()) {
			switchPanel(main, failMessage(flashcardSet));
		} else {
			
			//Title 
			JLabel title = new JLabel("Select a flashcard(s) to delete.");
			title.setFont(new Font("Helvetica", Font.BOLD,16));
			c.gridx = 0;
			c.gridy = 0;
			main.add(title,c);
			
			//Error Message
			JLabel err_message = new JLabel("");
			err_message.setFont(new Font("Helvetica",Font.PLAIN, 12));
			err_message.setForeground(Color.RED);
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(10,0,0,0);
			main.add(err_message,c);
			
			//Displays all flashcards from a specified set
			JPanel flashcardView = new JPanel();
			flashcardView.setLayout(new BoxLayout(flashcardView, BoxLayout.Y_AXIS));
			
			//Sets up the scroll panel
			JScrollPane scroll = new JScrollPane();
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setViewportView(flashcardView);
			scroll.setPreferredSize(new Dimension(300,200));
			scroll.revalidate();
			
			//Group of flashcard buttons
			JCheckBox button;
			
			//List of flashcards to be removed
			ArrayList <String> flashcardRemover = new ArrayList<>();
									
			//Fires when a user clicks a flashcard
			ItemListener itemListener = new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					
					//Gets the button
					AbstractButton src = (AbstractButton) e.getSource();
					
					if(e.getStateChange() == ItemEvent.SELECTED) {
						flashcardRemover.add(src.getText());
					} else {

						for(int i = 0; i < flashcardRemover.size(); i++) {
							if(flashcardRemover.get(i) == src.getText()) {
								flashcardRemover.remove(i);
								break;
							}
						}
					}
				}				
			};
			
			//Adds buttons in the scroll panel
			for(int i = 0; i < flashcard_list.size(); i++) {
				
				button = new JCheckBox(flashcard_list.get(i).question);
				button.addItemListener(itemListener);
				flashcardView.add(button);
			}
			
			//Adds scroll panel to the main
			c.gridx = 0;
			c.gridy = 2;
			c.anchor = GridBagConstraints.CENTER;
			main.add(scroll,c);
			
			//Button panel for flashcard set and main menu
			JPanel buttons_panel = new JPanel();
			buttons_panel.setLayout(new GridBagLayout());
			
			JButton button1 = new JButton("Flashcard Set Selection"), button2 = new JButton("Main Menu"), button3 = new JButton("Submit");
			
			ActionListener actionListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource().equals(button1)) {
						switchPanel(main,flashcardSetSelection(1));
					} else if(e.getSource().equals(button2)) {
						switchPanel(main, mainMenu());
					} else {
						
						if(flashcardRemover.isEmpty()) {
							err_message.setText("Select at least one flashcard to remove.");
						} else {
							
							//Deletes all selected flashcards
							for(int i = 0; i < flashcardRemover.size(); i++) {
								DatabaseHandler.removeFlashCard(flashcardRemover.get(i), flashcardSet);
							}
							
							switchPanel(main, successMessage(2, flashcardSet));
						}
					}
				}
				
			};
			
			//Adds Submit button
			button3.addActionListener(actionListener);
			button3.setPreferredSize(new Dimension(200,30));
			c.gridx = 0;
			c.gridy = 0;
			buttons_panel.add(button3,c);
			
			//Adds Flashcard Set Selection button
			button1.addActionListener(actionListener);
			button1.setPreferredSize(new Dimension(200,30));
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(5,0,0,0);
			buttons_panel.add(button1,c);
			
			//Adds Main Menu button
			button2.addActionListener(actionListener);
			button2.setPreferredSize(new Dimension(200,30));
			c.gridx = 0;
			c.gridy = 2;
			buttons_panel.add(button2,c);
			
			//Adds button panel to main
			c.gridx = 0;
			c.gridy = 3;
			c.insets = new Insets(10,0,0,0);
			main.add(buttons_panel,c);
		}
		
		return main;
	}

	@Override
	public JPanel editFlashcard() {
		return null;
	}

	/**
	 * Displays a success message before the user can go back.
	 * parameters: message_id (indicates unique message) 
	 * 
	 * 0: Flashcard Set is successfully added
	 * 1: Flashcard Set is successfully deleted
	 * 2: Flashcard is deleted from a flashcard set
	 */
	@Override
	public JPanel successMessage(int message_id, String flashcardSet) {
		
		if(itemSelected != "")
			itemSelected = "";

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title
		JLabel title = new JLabel("Success!");
		title.setFont(new Font("Helvetica", Font.BOLD, 25));
		title.setForeground(new Color(34,112,48));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		JLabel suc_message = null;
		
		//Selects a message to display 
		if(message_id == 0)
			suc_message = new JLabel(flashcardSet + " is successfully added!");
		else if(message_id == 1)
			suc_message = new JLabel(flashcardSet + " is successfully deleted!");
		else
			suc_message = new JLabel("Your flashcard(s) are deleted from " + flashcardSet + "!");
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5,0,0,0);
		main.add(suc_message,c);
		
		//Back Button
		JButton back = new JButton("Return to Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, mainMenu());
			}
		});
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(20,0,0,0);
		main.add(back,c);
		
		return main;
	}
	
	/**
	 * Same message method except that it only displays
	 * a message informing to the user that the flashcard
	 * is added to its corresponsding set.
	 * 
	 * 0: Flashcard is successfully added
	 * 1: Flashcard is successfully deleted
	 */
	public JPanel successMessage(int key, String question, String flashcardSet) {
				
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Restores itemSelected back to nothing 
		if(itemSelected != "")
			itemSelected = "";
		
		//Title
		JLabel title = new JLabel("Success!");
		title.setFont(new Font("Helvetica", Font.BOLD, 25));
		title.setForeground(new Color(34,112,48));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		//Message
		JLabel message = null;
		
		//Displays different success messages 
		if(key == 0) 
			message = new JLabel("Flashcard is added into " + flashcardSet + "!");
		else
			message = new JLabel("Flashcard is deleted from " + flashcardSet + "!");
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0);
		main.add(message,c);
		
		JButton back = new JButton("Back to Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, mainMenu());
			}
		});
		c.gridx = 0;
		c.gridy = 2;
		main.add(back,c);

		return main;
	}

	/**
	 * Displays a failed message because the user does not
	 * have any flashcard sets
	 */
	@Override
	public JPanel failMessage() {

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("Error!");
		title.setFont(new Font("Helvetica", Font.BOLD,25));
		title.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,0,0);
		main.add(title,c);
		
		JLabel message_one = new JLabel("You did not create any flashcard sets.");
		message_one.setFont(new Font("Helvetica", Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 1;
		main.add(message_one, c);
		
		JLabel message_two = new JLabel("Go back and make one.");
		message_two.setFont(new Font("Helvetica", Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5,0,0,0);
		main.add(message_two,c);
		
		JButton button = new JButton("Return to Main Menu");
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(15,0,0,0);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, mainMenu());
			}
			
		});
		main.add(button,c);
		
		return main;
	}
	
	/**
	 * Displayed a fail message because the flashcard set
	 * is empty and the user needs to make some
	 */
	public JPanel failMessage(String flashcardSet) {

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title
		JLabel title = new JLabel("Error!");
		title.setFont(new Font("Helvetica", Font.BOLD,25));
		title.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		JLabel message_one = new JLabel("There are no flashcards in " + flashcardSet + " set.");
		message_one.setFont(new Font("Helvetica", Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0);
		main.add(message_one, c);
		
		JLabel message_two = new JLabel("Go back and make one.");
		message_two.setFont(new Font("Helvetica", Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5,0,0,0);
		main.add(message_two,c);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		//Buttons from this panel
		JButton 	flashCardSetButton = new JButton("Flashcard Set Selection"), mainMenu = new JButton("Main Menu");

		//Fires when either flashcard or main menu button is pressed
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource().equals(flashCardSetButton)) {
					switchPanel(main,flashcardSetSelection(1));
				} else {
					switchPanel(main,mainMenu());
				}
			}
		};
		
		//Adds flashcard button
		c.gridx = 0;
		c.gridy = 0;
		flashCardSetButton.addActionListener(actionListener);
		buttons.add(flashCardSetButton,c);
		
		//Adds main menu button
		c.gridx = 1;
		c.gridy = 0;
		mainMenu.addActionListener(actionListener);
		buttons.add(mainMenu,c);
		
		//Adds buttons panel to main
		c.gridx = 0;
		c.gridy = 3;
		main.add(buttons,c); 
		
		return main;
	}
	
	/**
	 * Displayed to have users select the flashcard set
	 * 
	 * 0: Select a flashcard set to add a flashcard
	 * 1: Select a flashcard set to delete a flashcard
	 * 2: Select a flashcard set to delete (Deleting flashcard set)
	 * 3: Select a flashcard set to view flashcards
	 */
	@Override
	public JPanel flashcardSetSelection(int key) {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Get all flashcard sets
		ArrayList<String> flashcard_sets = DatabaseHandler.getAllFlashCardSets();
		
		if(flashcard_sets.isEmpty()) {
			 return failMessage();
		} else {
			
			//Instruction
			JLabel instruction = null;
			
			//Displays different instructions depending on the key
			if(key == 0)
				instruction = new JLabel("Select a Flashcard Set to add a flashcard");
			else if(key == 1)
				instruction = new JLabel("Select a Flashcard Set to delete a flashcard");
			else if(key == 2)
				instruction = new JLabel("Select a Flashcard Set to delete");
			else
				instruction = new JLabel("Select a Flashcard Set to view its flashcards");
			
			//Positions the instruction component
			instruction.setFont(new Font("Helvetica", Font.BOLD,16));
			c.gridx = 0;
			c.gridy = 0;
			main.add(instruction,c);
			
			//Error message
			JLabel err_message = new JLabel("");
			err_message.setFont(new Font("Helvetica", Font.PLAIN, 12));
			err_message.setForeground(Color.RED);
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(10,0,0,0);
			main.add(err_message,c);
			
			//Flashcard sets container, which will be inside the scroll panel
			JPanel flashCardSets_View = new JPanel();
			flashCardSets_View.setLayout(new BoxLayout(flashCardSets_View, BoxLayout.Y_AXIS));
						
			//Scroll Bar Component
			JScrollPane scroll = new JScrollPane();
			
			//Scroll bars only appear when there are more flashcard set items or if the flashcard name is longer
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setViewportView(flashCardSets_View);
			scroll.setPreferredSize(new Dimension(300,200));
			scroll.revalidate();
			
			//Group of flashcard buttons
			ButtonGroup button_list = new ButtonGroup();
			JRadioButton button;
									
			//Fires when a user clicks a flashcard set
			ItemListener itemListener = new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					AbstractButton src = (AbstractButton) e.getSource();
					itemSelected = src.getText();
				} 
			};
			
			//Adds buttons in the scroll panel
			for(int i = 0; i < flashcard_sets.size(); i++) {
				button = new JRadioButton(flashcard_sets.get(i));
				button.addItemListener(itemListener);
				flashCardSets_View.add(button);
				button_list.add(button);
			}
			
			//Adds scroll panel to the main
			c.gridx = 0;
			c.gridy = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10,0,0,0);
			main.add(scroll,c);
			
			JPanel buttons_group = new JPanel();
			buttons_group.setLayout(new GridBagLayout());
			
			JButton back = new JButton("Back");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					switchPanel(main, mainMenu());
				}
				
			});
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(10,0,0,50);
			buttons_group.add(back,c);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(itemSelected == "") {
						err_message.setText("Please select a flashcard set before proceeding.");
					} else {
						
						//Goes to a different panel depending on the key
						if(key == 0)
							switchPanel(main,createFlashcard(itemSelected));
						else if(key == 1) {
							
							ArrayList <Flashcard> flashcard_list = DatabaseHandler.getAllFlashcards(itemSelected);
							
							if(flashcard_list.isEmpty())
								switchPanel(main,failMessage(itemSelected));
							else
								switchPanel(main,deleteFlashcard(itemSelected));
						}
						else if(key == 2) {
							DatabaseHandler.deleteFlashCardSet(itemSelected);
							switchPanel(main,successMessage(1, itemSelected));
						} else {
							
							ArrayList <Flashcard> flashcard_list = DatabaseHandler.getAllFlashcards(itemSelected);
							
							if(flashcard_list.isEmpty())
								switchPanel(main,failMessage(itemSelected));
							else {
								flashcardHandler = new FlashcardHandler(itemSelected);
								switchPanel(main, viewFlashcards());
							}
						}
					}
				}
				
			});
			c.gridx = 1;
			c.gridy = 0;
			buttons_group.add(submit,c);
			
			//Adds button group panel to the main
			c.gridx = 0;
			c.gridy = 3;
			c.insets = new Insets(10,50,0,0);
			main.add(buttons_group,c);
			return main;
		}
		
	}

	/**
	 * Lists all flashcards from a specified set for users
	 * to select one before returning to viewing flashcards
	 */
	@Override
	public JPanel flashcardGlossary(ArrayList <Flashcard> flashcard_list) {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Adds instruction
		JLabel instruction = new JLabel("Select a flashcard");
		instruction.setFont(new Font("Helvetica",Font.BOLD,16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(instruction,c);
		
		//Adds Error Message
		JLabel error_message = new JLabel("");
		error_message.setFont(new Font("Helvetica", Font.PLAIN,12));
		error_message.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 1;
		main.add(error_message,c);
		
		//Flashcards container, which will be inside the scroll panel
		JPanel flashCards_View = new JPanel();
		flashCards_View.setLayout(new BoxLayout(flashCards_View, BoxLayout.Y_AXIS));
		
		//Scroll Bar Component
		JScrollPane scroll = new JScrollPane();
		
		//Scroll bars only appear when there are more flashcard items or if the flashcard name is longer
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(flashCards_View);
		scroll.setPreferredSize(new Dimension(300,200));
		scroll.revalidate();
		
		//Group of flashcard buttons
		ButtonGroup button_list = new ButtonGroup();
		JRadioButton button;
		
		//Fires when a user clicks a flashcard set
		ItemListener itemListener = new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				AbstractButton src = (AbstractButton) e.getSource();
				itemSelected = src.getText();
			} 
		};
		
		//Adds buttons in the scroll panel
		for(int i = 0; i < flashcard_list.size(); i++) {
			button = new JRadioButton(flashcard_list.get(i).question);
			button.addItemListener(itemListener);
			flashCards_View.add(button);
			button_list.add(button);
		}
		
		//Adds scroll panel to the main
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,0,0,0);
		main.add(scroll,c);
		
		JPanel buttons_group = new JPanel();
		buttons_group.setLayout(new GridBagLayout());
		
		//Submit and Back Button
		JButton back = new JButton("Back"), submit = new JButton("Submit");
		
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource().equals(back)) {
					switchPanel(main, viewFlashcards());
				} else {
					
					if(itemSelected == "")
						error_message.setText("Please select a flashcard set before proceeding.");
					else {
						
						//Code this
						
						switchPanel(main, viewFlashcards());
					}
					
				}
			}
			
		};
		
		back.addActionListener(actionListener);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,0,50);
		buttons_group.add(back,c);
		
		submit.addActionListener(actionListener);
		c.gridx = 1;
		c.gridy = 0;
		buttons_group.add(submit,c);
		
		//Adds button group panel to the main
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10,50,0,0);
		main.add(buttons_group,c);
		
		return main;
	}
}