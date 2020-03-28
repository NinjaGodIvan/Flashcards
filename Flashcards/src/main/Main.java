package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

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
	JPanel deleteFlashcard();
	JPanel deleteFlashcardSet();
	JPanel takeQuiz();
	JPanel message(int message_id, String flashcardSet);
	JPanel message(int key, String question, String flashcardSet);
	JPanel flashcardSetSelection(int key);
}

public class Main extends JFrame implements Menus{
	
	//Serial Version
	private static final long serialVersionUID = 1L;
	//Item that is currently being selected (Should be initialized to have no characters)
	static String itemSelected = "";
		
	/**
	 * Program starts with the main menu
	 */
	public Main() {
		
		//NOTE: When app starts, you can initially start up a GUI for debugging purposes
		add(mainMenu());
		
		setTitle("Flashcards");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}

	//Driver function
	public static void main(String[] args) throws Exception {
		
		if(DatabaseHandler.getConnection() == null) {
			System.out.println("Unable to connect to database. System timed out.");
		} else {
			new Main();
		}
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
		JButton deleteFlashCard = new JButton("Delete a Flashcard");
		JButton 	viewFlashCards = new JButton("View Flashcards");
		JButton takeQuiz = new JButton("Take a Quiz");

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
					switchPanel(main, takeQuiz());
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
		
		//Adds "Delete a Flashcard" Menu
		deleteFlashCard.addActionListener(actionListener);
		deleteFlashCard.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 3;
		buttons.add(deleteFlashCard,c);
		
		//Adds "View Flashcards" button
		viewFlashCards.addActionListener(actionListener);
		viewFlashCards.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 4;
		buttons.add(viewFlashCards,c);
		
		//Adds "Take Quiz" button
		takeQuiz.addActionListener(actionListener);
		takeQuiz.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 5;
		buttons.add(takeQuiz,c);
		
		//Adds button panel to main
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20,0,0,0);
		main.add(buttons,c);
				
		return main;
	}


	/**
	 * Interface that allows user to create a flashcard 
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
					
					//Parses the string into sequence of characters
					char [] charParser = flashcardSetName.getText().toCharArray();
					
					if(DatabaseHandler.flashcardSetExists(flashcardSetName.getText())) {
						errMessage.setText("This flashcard set already exists!");
					}else if(charParser.length < 1) {
						errMessage.setText("Flashcard set name needs at least 1 character!");
					}else {
						
						//We must parse the string into sequence of characters to check for spaces
						//Space checker
						boolean hasSpace = false;
						
						//Loops through characters
						for(int i = 0; i < charParser.length; i++) {
							
							if(charParser[i] == ' ') {
								hasSpace = true;
								break;
							}
						}
						
						if(hasSpace) {
							errMessage.setText("Your flashcard set name should not have spaces!");
						} else {
							DatabaseHandler.addFlashCardSet(flashcardSetName.getText());
							switchPanel(main, message(0, flashcardSetName.getText()));
						}
						
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
						
						if(DatabaseHandler.flashcardExists(question.getText(), flashcardSet)) {
							errMessage.setText("This flashcard has already exists in this set.");
						} else if (question.getText().length() == 0 || answer.getText().length() == 0) {
							errMessage.setText("You must provide a question and answer for the flashcard.");
						} else {
							DatabaseHandler.addFlashCard(question.getText(), answer.getText(), flashcardSet);
							switchPanel(main,message(0,question.getText(),flashcardSet));
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

	@Override
	public JPanel viewFlashcards() {
		return null;
	}

	@Override
	public JPanel deleteFlashcard() {
		return null;
	}
	
	@Override
	public JPanel deleteFlashcardSet() {
		return null;
		
	}

	@Override
	public JPanel takeQuiz() {
		return null;
	}

	/**
	 * Displays a message before the user can go back.
	 * parameters: message_id (indicates unique message) 
	 * 
	 * 0: Flashcard Set is successfully added
	 * 1: Flashcard Set is successfully deleted
	 */
	@Override
	public JPanel message(int message_id, String flashcardSet) {
		
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
			suc_message = new JLabel("Your flashcard set is successfully added!");
		
		
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
	public JPanel message(int key, String question, String flashcard) {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Restores itemSelected back to nothing 
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
			message = new JLabel(question + " is added into " + flashcard + "!");
		else
			message = new JLabel(question + " is deleted from " + flashcard + "!");
		
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
			
			JLabel message_one = new JLabel("You did not create any flashcard sets.");
			message_one.setFont(new Font("Helvetica", Font.BOLD, 15));
			c.gridx = 0;
			c.gridy = 0;
			main.add(message_one, c);
			
			JLabel message_two = new JLabel("Go back and make one.");
			message_two.setFont(new Font("Helvetica", Font.BOLD, 15));
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(5,0,0,0);
			main.add(message_two,c);
			
			JButton button = new JButton("Return to Main Menu");
			c.gridx = 0;
			c.gridy = 2;
			c.insets = new Insets(15,0,0,0);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					switchPanel(main, mainMenu());
				}
				
			});
			main.add(button,c);
			
		} else {
			
			//Instruction
			JLabel instruction = null;
			
			//Displays different instructions depending on the key
			if(key == 0)
				instruction = new JLabel("Select a Flashcard Set to add a flashcard");
			
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
		}
		
		return main;
	}
}
