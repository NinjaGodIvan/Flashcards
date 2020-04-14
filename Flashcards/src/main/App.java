package main;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;

/*****************************************
 * The App is responsible for setting up *
 * graphical interfaces for the users to *
 * view									*
 *****************************************/

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
	JPanel deleteFlashcard(String flashcardSet, ArrayList <Flashcard> flashcard_list);
	JPanel editFlashcardStepOne(String flashcardSet, ArrayList <Flashcard> flashcard_list);
	JPanel editFlashcardStepTwo(String flashcardSet, String gotOldQuestion, String gotOldAnswer);
	JPanel successMessage(int key, String flashcardSet);
	JPanel failMessage();
	JPanel failMessage(int key, String flashcardSet);
	JPanel flashcardSetSelection(int key);
	JPanel flashcardGlossary();
}

public class App extends JFrame implements Menus{
	
	//Serial Version
	private static final long serialVersionUID = 1L;
	
	//Item that is currently being selected (Should be initialized to have no characters)
	private static String itemSelected = "";
	
	//[See in editFlashcardStepTwo() method]
	private static String oldQuestion = "";
	private static String oldAnswer = "";
	
	//FlashcardHandler
	private FlashcardHandler flashcardHandler;
	
	/**
	 * Program starts with the main menu
	 */
	public App() {
		
		//NOTE: When app starts, you can initially start up any GUI for debugging purposes
		add(mainMenu());
		setTitle("Flashcards");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setContentPane(mainMenu());
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
		setContentPane(newPanel);
		newPanel.revalidate();
		newPanel.repaint();
	}

	/**
	 * Main menu of the application
	 */
	@Override
	public JPanel mainMenu() {
		
		/*
		 * If a user clicks a flashcard (set) item and 
		 * returns back to main menu, reset it to nothing
		 */
		if(!itemSelected.equals("")) {
			System.out.println("[Resetting itemSelected]");
			itemSelected = "";
		}
			
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Flashcard Image
		try {
			URL url = this.getClass().getResource("/flashcards.png");
			ImageIcon flashcard_icon = new ImageIcon(url);
			JLabel flashcard_image = new JLabel("");
			flashcard_image.setIcon(flashcard_icon);
			c.gridx = 0;
			c.gridy = 0;
			main.add(flashcard_image,c);
		}catch(Exception e) {
			System.out.println(e);			
		}
	
		//Title of the app
		JLabel title = new JLabel("Flashcards");
		title.setFont(new Font("Helvetica", Font.BOLD,18));
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
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
					switchPanel(main, flashcardSetSelection(4));
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
		c.gridy = 2;
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
		
		JLabel note = new JLabel("Note: You may not use backticks");
		note.setFont(new Font("Helvetica", Font.PLAIN, 10));
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(2,0,0,0);
		main.add(note,c);
		
		//Success or fail message
		JLabel message = new JLabel("");
		message.setForeground(Color.RED);
		message.setFont(new Font("Helvetica", Font.PLAIN, 11));
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10,4,0,0);
		c.anchor = GridBagConstraints.WEST;
		main.add(message,c);
				
		//Field where a user enters the name of the flashcard set
		JTextField flashcardSetName = new JTextField();
		flashcardSetName.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		main.add(flashcardSetName,c);
		
		//Button panel for submit and back
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		
		JButton back = new JButton("Back"), submit = new JButton("Submit");
		
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(back)) {
					switchPanel(main, mainMenu());
				} else {
					//Will need to trim the string to get rid of trailing spaces that the user added
					String new_set = flashcardSetName.getText().trim();
					
					//False, if there are no back ticks in the set's name
					boolean backticksExisted = false;
					
					//Iterates through the set's characters 
					for(int i = 0; i < new_set.length(); i++)
						if(new_set.charAt(i) == '`') {
							backticksExisted = true;
							break;
						}
							
					try {
					
						//Will need to surround the flashcard set with brackets for searching
						if(backticksExisted) {
							message.setText("No backticks allowed!!");
						}else if(DatabaseHandler.flashcardSetExists("[" + new_set + "]")) {
							message.setText("This flashcard set has already existed.");
						}else if(new_set.toCharArray().length < 1) {
							message.setText("Textfield is blank :/");
						}else {
							DatabaseHandler.addFlashCardSet(new_set);
							switchPanel(main, successMessage(0, new_set));
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
		c.insets = new Insets(5,0,0,0);
		buttonPanel.add(back,c);
		
		submit.addActionListener(actionListener);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(5,10,0,0);
		buttonPanel.add(submit,c);
		
		//Adds button panel to main
		c.gridx = 0;
		c.gridy = 5;
		main.add(buttonPanel,c);
		
		return main;
	}

	/**
	 * Allows user to delete a flashcard 
	 */
	@Override
	public JPanel createFlashcard(String flashcardSet) {
		
		//Restores itemSelected back to nothing
		System.out.println("[Resetting itemSelected]");
		itemSelected = "";
		
		//Colorizes message if the message is successful
		Color green = new Color(34,112,48);
		
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
		c.insets = new Insets(5,0,0,0);
		main.add(instruction,c);
				
		//Success or fail message
		JLabel message = new JLabel("");
		message.setForeground(Color.RED);
		message.setFont(new Font("Helvetica", Font.PLAIN, 11));
		c.gridx = 0;
		c.gridy = 2;
		main.add(message,c);
		
		//Form panel including labels, textfields, and buttons
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		
		JLabel questionLabel = new JLabel("Question:");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.WEST;
		form.add(questionLabel,c);
		
		//Gets user to ask a question
		JTextArea question = new JTextArea();
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		
		//Applys scrollbar and adds question to the form
		JScrollPane scroll_one = new JScrollPane(question);
		scroll_one.setPreferredSize(new Dimension(200,80));
		c.gridx = 0;
		c.gridy = 1;
		form.add(scroll_one,c);
		
		JLabel answerLabel = new JLabel("Answer:");
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5,0,0,0);
		form.add(answerLabel,c);
		
		//Gets user to put an answer
		JTextArea answer = new JTextArea();
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);

		//Applys scrollbar and adds answer to the form
		JScrollPane scroll_two = new JScrollPane(answer);
		scroll_two.setPreferredSize(new Dimension(200,80));
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0,0,0,0);
		form.add(scroll_two,c);
		
		//Adds the form panel to the main
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		main.add(form,c);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		JButton back = new JButton("Back");
		JButton submit = new JButton("Submit");
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(back)) {
					switchPanel(main, flashcardSetSelection(0));
				} else {	
					
					try {
						
						if(DatabaseHandler.flashcardExists(question.getText().trim(), flashcardSet)) {
							
							//Changes the text to red if it's a different color
							if(message.getForeground() != Color.RED)
								message.setForeground(Color.RED);
							
							message.setText("This flashcard has already exists in this set.");
							
						} else if (question.getText().trim().length() == 0 || answer.getText().trim().length() == 0) {
							if(message.getForeground() != Color.RED)
								message.setForeground(Color.RED);
							
							message.setText("You must provide a question and answer.");
						} else {
							DatabaseHandler.addFlashCard(question.getText().trim(), answer.getText().trim(), flashcardSet);
							
							//Displays a successful message
							message.setText("Flashcard added successfully!");
							
							//Changes the text to green if it's a different color
							if(message.getForeground() != green)
								message.setForeground(green);
							
							//Resets question and answer
							question.setText("");
							answer.setText("");	
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
		System.out.println("[Resetting itemSelected]");
		itemSelected = "";
		
		/*
		 * This occurs when the user goes to the glossary,
		 * but left the flashcard to display the answer
		 */
		if(flashcardHandler.getFlashcardIndicator() == 1)
			flashcardHandler.switchIndicator();

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
		
		//Initializes previous and/or next button to be disabled
		if(flashcardHandler.getFlashcardTracker() == 0)
			previous.setEnabled(false);
		
		if(flashcardHandler.getFlashcardTracker() == flashcardHandler.getFlashcardSet().size() - 1)
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
					switchPanel(main, flashcardGlossary());
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
	public JPanel deleteFlashcard(String flashcardSet, ArrayList <Flashcard> flashcard_list) {
		
		//Restoring itemSelected to nothing
		System.out.println("[Resetting itemSelected]");
		itemSelected = "";
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
			
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
		
		JButton button1 = new JButton("My Sets"), button2 = new JButton("Main Menu"), button3 = new JButton("Submit");
		
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
		
		return main;
	}

	/**
	 * Edit Flashcard Step 1: Allows user to select a flashcard
	 * to edit
	 */
	public JPanel editFlashcardStepOne(String flashcardSet,  ArrayList <Flashcard> flashcard_list) {
		
		//Restores itemSelected back to nothing
		if(!itemSelected.equals("")) {
			System.out.println("[Resetting itemSelected]");
			itemSelected = "";
		}
				
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title 
		JLabel title = new JLabel("Select a flashcard to edit.");
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
		ButtonGroup buttons = new ButtonGroup();
		
		ItemListener itemListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				//Gets the button
				AbstractButton src = (AbstractButton) e.getSource();
				itemSelected = src.getText();
			}
		};
				
		//Displays all flashcard items
		for(int i = 0; i < flashcard_list.size(); i++) {
			JRadioButton button = new JRadioButton(flashcard_list.get(i).question);
			button.addItemListener(itemListener);
			flashcardView.add(button);
			buttons.add(button);
		}
		
		//Adds scroll and flashcard items to main
		c.gridx = 0;
		c.gridy = 2;
		main.add(scroll,c);
		
		//Button panel for flashcard set and main menu
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new GridBagLayout());
		
		//List of buttons from buttons_panel
		JButton button1 = new JButton("My Sets"), button2 = new JButton("Main Menu"), button3 = new JButton("Next");
		
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(button1)) {
					switchPanel(main,flashcardSetSelection(4));
				} else if(e.getSource().equals(button2)) {
					switchPanel(main, mainMenu());
				} else {
					
					if(itemSelected.equals("")) {
						err_message.setText("Please select a flashcard before proceeding.");
					} else {
						Flashcard temp = DatabaseHandler.getFlashcard(flashcardSet, itemSelected);
						switchPanel(main, editFlashcardStepTwo(flashcardSet, temp.question, temp.answer));
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
		
		return main;
	}
	
	/**
	 * Edit Flashcard Step Two: Have user edit the flashcard
	 */
	public JPanel editFlashcardStepTwo(String flashcardSet, String gotOldQuestion, String gotOldAnswer) {
		
		//Restores itemSelected back to nothing
		System.out.println("[Resetting itemSelected]");
		itemSelected = "";
		
		//Colorizes message if the message is successful
		Color green = new Color(34,112,48);
		
		/*
		 * Duplicating old question and answer (passed parameter) 
		 * This is to change reference an updated of both fields
		 */
		oldQuestion = gotOldQuestion;
		oldAnswer = gotOldAnswer;
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("Edit a Flashcard");
		title.setFont(new Font("Helvetica",Font.BOLD,16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		//Instruction for the user to follow
		JLabel instruction = new JLabel("Edit your flashcard to submit");
		instruction.setFont(new Font("Helvetica", Font.PLAIN, 12));
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5,0,0,0);
		main.add(instruction,c);
				
		//Success or fail message
		JLabel message = new JLabel("");
		message.setForeground(Color.RED);
		message.setFont(new Font("Helvetica", Font.PLAIN, 11));
		c.gridx = 0;
		c.gridy = 2;
		main.add(message,c);
		
		//Form panel including labels, textfields, and buttons
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		
		JLabel questionLabel = new JLabel("Question:");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0,0,0,0);
		form.add(questionLabel,c);
		
		//Gets user to ask a question
		JTextArea questionTextArea = new JTextArea();
		questionTextArea.setText(oldQuestion);
		questionTextArea.setLineWrap(true);
		questionTextArea.setWrapStyleWord(true);
		
		//Applys scrollbar and adds question to the form
		JScrollPane scroll_one = new JScrollPane(questionTextArea);
		scroll_one.setPreferredSize(new Dimension(200,80));
		c.gridx = 0;
		c.gridy = 1;
		form.add(scroll_one,c);
		
		JLabel answerLabel = new JLabel("Answer:");
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5,0,0,0);
		form.add(answerLabel,c);
		
		//Gets user to put an answer
		JTextArea answerTextArea = new JTextArea();
		answerTextArea.setText(oldAnswer);
		answerTextArea.setLineWrap(true);
		answerTextArea.setWrapStyleWord(true);

		//Applys scrollbar and adds answer to the form
		JScrollPane scroll_two = new JScrollPane(answerTextArea);
		scroll_two.setPreferredSize(new Dimension(200,80));
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0,0,0,0);
		form.add(scroll_two,c);
		
		//Adds the form panel to the main
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		main.add(form,c);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		JButton back = new JButton("Back");
		JButton submit = new JButton("Submit");
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(back)) {
					ArrayList <Flashcard> temp = DatabaseHandler.getAllFlashcards(flashcardSet);
					
					//Resets Old Question
					if(!oldQuestion.equals("")) {
						System.out.println("[Resetting oldQuestion]");
						oldQuestion = "";
					}
					
					//Resets Old Question
					if(!oldAnswer.equals("")) {
						System.out.println("[Resetting oldAnswer]");
						oldAnswer = "";
					}
					
					switchPanel(main, editFlashcardStepOne(flashcardSet, temp));
				} else {	
					
					//Trims question and answer 
					String newQuestion = questionTextArea.getText().trim();
					String newAnswer = answerTextArea.getText().trim();
							
					if(DatabaseHandler.flashcardExists(newQuestion, flashcardSet) && !newQuestion.equals(oldQuestion)) {
						
						//Changes the text to red if it's a different color
						if(message.getForeground() != Color.RED)
							message.setForeground(Color.RED);
						
						message.setText("Go back to flashcard selection to edit this flashcard.");
					} else if(newQuestion.length() == 0 || newAnswer.length() == 0) {
						
						if(message.getForeground() != Color.RED)
							message.setForeground(Color.RED);
						
						message.setText("You must provide a question and answer.");
					} else if(newQuestion.equals(oldQuestion)  && newAnswer.equals(oldAnswer)) {
						
						if(message.getForeground() != Color.RED)
							message.setForeground(Color.RED);
						
						message.setText("Either the question or the answer has to change.");
					} else {
						DatabaseHandler.editFlashcard(oldQuestion, oldAnswer, newQuestion, newAnswer, flashcardSet);
						
						//Updates oldQuestion if the user changed the question
						if(!newQuestion.equals(oldQuestion)) {
							System.out.println("Old Question: " + oldQuestion);
							oldQuestion = newQuestion;
							System.out.println("New Question: " + oldQuestion);
						}
						
						//Updates oldAnswer if the user changed the answer
						if(!newAnswer.equals(oldAnswer)) {
							System.out.println("Old Answer: " + oldAnswer);
							oldAnswer = newAnswer;
							System.out.println("New Answer: " + oldAnswer);
						}
						
						//Displays a successful message
						message.setText("Flashcard edited successfully!");
						
						//Changes the text to green if it's a different color
						if(message.getForeground() != green)
							message.setForeground(green);
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

		return main;	}

	/**
	 * Displays a success message before the user can go back.
	 * parameters: message_id (indicates unique message) 
	 * 
	 * 0: Flashcard Set is successfully added
	 * 1: Flashcard Set is successfully deleted
	 * 2: Flashcard is deleted from a flashcard set
	 */
	@Override
	public JPanel successMessage(int key, String flashcardSet) {
		
		System.out.println("[Resetting itemSelected]");
		itemSelected = "";

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Green Check Image
		try {
			URL url = this.getClass().getResource("/greencheck.png");
			ImageIcon checkmark_icon = new ImageIcon(url);
			JLabel checkmark_image = new JLabel("");
			checkmark_image.setIcon(checkmark_icon);
			c.gridx = 0;
			c.gridy = 0;
			main.add(checkmark_image,c);
		}catch(Exception e) {
			System.out.println(e);			
		}
		
		//Title
		JLabel title = new JLabel("Success!");
		title.setFont(new Font("Helvetica", Font.BOLD, 25));
		title.setForeground(new Color(34,112,48));
		c.insets = new Insets(20,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		main.add(title,c);
		
		JLabel suc_message = null;
		
		//Selects a message to display 
		if(key == 0)
			suc_message = new JLabel(flashcardSet + " is successfully added!");
		else if(key == 1)
			suc_message = new JLabel(flashcardSet + " is successfully deleted!");
		else
			suc_message = new JLabel("Your flashcard(s) are deleted from " + flashcardSet + "!");
		
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,0,0,0);
		main.add(suc_message,c);
		
		//Back Button
		JButton back = new JButton("Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, mainMenu());
			}
		});
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(20,0,0,0);
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
		
		//Red X Image
		try {
			URL url = this.getClass().getResource("/red-X.png");
			ImageIcon red_X_icon = new ImageIcon(url);
			JLabel red_X_image = new JLabel("");
			red_X_image.setIcon(red_X_icon);
			c.gridx = 0;
			c.gridy = 0;
			main.add(red_X_image,c);
		}catch(Exception e) {
			System.out.println(e);			
		}
		
		JLabel title = new JLabel("Error!");
		title.setFont(new Font("Helvetica", Font.BOLD,25));
		title.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20,0,0,0);
		main.add(title,c);
		
		JLabel message_one = new JLabel("You did not create any flashcard sets.");
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,0,0,0);
		main.add(message_one, c);
		
		JLabel message_two = new JLabel("Go back and make one.");
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(5,0,0,0);
		main.add(message_two,c);
		
		JButton button = new JButton("Main Menu");
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(20,0,0,0);
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
	 * 
	 * 1: Go back to delete flashcard settings
	 * 3: Go back to view flashcard settings
	 * 4: Go back to edit flashcard settings
	 */
	public JPanel failMessage(int key, String flashcardSet) {
		
		//Restores itemSelected back to nothing
		System.out.println("[Resetting itemSelected]");
		itemSelected = "";

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Red X Image
		try {
			URL url = this.getClass().getResource("/red-X.png");
			ImageIcon red_X_icon = new ImageIcon(url);
			JLabel red_X_image = new JLabel("");
			red_X_image.setIcon(red_X_icon);
			c.gridx = 0;
			c.gridy = 0;
			main.add(red_X_image,c);
		}catch(Exception e) {
			System.out.println(e);			
		}
		
		//Title
		JLabel title = new JLabel("Error!");
		title.setFont(new Font("Helvetica", Font.BOLD,25));
		title.setForeground(Color.RED);
		c.insets = new Insets(20,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		main.add(title,c);
		
		JLabel message_one = new JLabel("There are no flashcards in " + flashcardSet + " set.");
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,0,0,0);
		main.add(message_one, c);
		
		JLabel message_two = new JLabel("Go back and make one.");
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(5,0,0,0);
		main.add(message_two,c);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		
		//Buttons from this panel
		JButton 	flashCardSetButton = new JButton("My Sets"), mainMenu = new JButton("Main Menu");

		//Fires when either flashcard or main menu button is pressed
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource().equals(flashCardSetButton)) {
					
					if(key == 1)
						switchPanel(main,flashcardSetSelection(1));
					else if(key == 3)
						switchPanel(main,flashcardSetSelection(3));
					else
						switchPanel(main,flashcardSetSelection(4));

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
		c.gridy = 4;
		c.insets = new Insets(20,0,0,0);
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
	 * 4: Select a flashcard set to edit flashcard
	 */
	@Override
	public JPanel flashcardSetSelection(int key) {
		
		/*
		 * If a user clicks flashcard item(s) and 
		 * returns back to flashcard set selection, 
		 * reset it to nothing
		 */
		if(!itemSelected.equals("")) {
			System.out.println("[Resetting itemSelected]");
			itemSelected = "";
		}
		
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
			else if(key == 3)
				instruction = new JLabel("Select a Flashcard Set to view its flashcards");
			else
				instruction = new JLabel("Select a Flashcard Set to edit a flashcard");

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
			
			JButton submit;
			
			if(key == 0 || key == 1 || key == 4)
				submit = new JButton("Next");
			else
				submit = new JButton("Submit");
			
			submit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(itemSelected.equals("")) {
						err_message.setText("Please select a flashcard set before proceeding.");
					} else {
						
						//Goes to a different panel depending on the key
						if(key == 0)
							switchPanel(main,createFlashcard(itemSelected));
						else if(key == 1) {
							
							ArrayList <Flashcard> flashcard_list = DatabaseHandler.getAllFlashcards(itemSelected);
							
							if(flashcard_list.isEmpty())
								switchPanel(main,failMessage(1, itemSelected));
							else
								switchPanel(main,deleteFlashcard(itemSelected, flashcard_list));
						}
						else if(key == 2) {
							DatabaseHandler.deleteFlashCardSet(itemSelected);
							switchPanel(main,successMessage(1, itemSelected));
						}
						else if(key == 3){
							
							ArrayList <Flashcard> flashcard_list = DatabaseHandler.getAllFlashcards(itemSelected);
							
							if(flashcard_list.isEmpty())
								switchPanel(main,failMessage(3, itemSelected));
							else {
								flashcardHandler = new FlashcardHandler(itemSelected, flashcard_list);
								switchPanel(main, viewFlashcards());
							}
						} else {
														
							ArrayList <Flashcard> flashcard_list = DatabaseHandler.getAllFlashcards(itemSelected);
							if(flashcard_list.isEmpty())
								switchPanel(main,failMessage(4, itemSelected));
							else
								switchPanel(main,editFlashcardStepOne(itemSelected, flashcard_list));
							
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
	public JPanel flashcardGlossary() {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Adds instruction
		JLabel instruction = new JLabel("Select a flashcard");
		instruction.setFont(new Font("Helvetica",Font.BOLD,16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(instruction,c);
		
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
		for(int i = 0; i < flashcardHandler.getFlashcardSet().size(); i++) {
			button = new JRadioButton(flashcardHandler.getFlashcardSet().get(i).question);
			button.addItemListener(itemListener);
			
			//Selects the flashcard item that is currently used
			if(flashcardHandler.getQuestion() == flashcardHandler.getFlashcardSet().get(i).question)
				button.setSelected(true);
			
			flashCards_View.add(button);
			button_list.add(button);
		}
		
		//Adds scroll panel to the main
		c.gridx = 0;
		c.gridy = 1;
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
						
					int index = 0;
						
					//Searches through questions to get the corresponding index
					for(Flashcard flashcard: flashcardHandler.getFlashcardSet()) {
						if(itemSelected.equals(flashcard.question)) {
							break;
						}
						index++;
					}
					
					flashcardHandler.changeFlashcard(index);
					switchPanel(main, viewFlashcards());
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
		c.gridy = 2;
		c.insets = new Insets(10,50,0,0);
		main.add(buttons_group,c);
		
		return main;
	}
}
