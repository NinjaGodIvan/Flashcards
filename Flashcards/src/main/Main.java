package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	JPanel createFlashcard();
	JPanel viewFlashcards();
	JPanel deleteFlashcard();
	JPanel takeQuiz();
	JPanel message(int message_id);
}

public class Main extends JFrame implements Menus{
	
	private static final long serialVersionUID = 1L;
		
	/**
	 * Program starts with the main menu
	 */
	public Main() {
		
		//add(mainMenu());
		add(message(0));
		
		setTitle("Flashcards");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}

	//Driver function
	public static void main(String[] args) throws Exception {
		new Main();
		//DatabaseHandler.getConnection();
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
		
		//Goes to Create Flashcard Set Menu
		JButton createFlashCardSet = new JButton ("Create a Flashcard Set");
		createFlashCardSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, createFlashcardSet());
			}
			
		});
		createFlashCardSet.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,0,0,0);
		buttons.add(createFlashCardSet,c);
		
		//Goes to Create Flashcard Menu
		JButton createFlashCard = new JButton ("Create a Flashcard");
		createFlashCard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, createFlashcard());
			}
		});
		createFlashCard.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 1;
		buttons.add(createFlashCard,c);
		
		//Goes to View Flashcards Menu
		JButton viewFlashCards = new JButton ("View Flashcards");
		viewFlashCards.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, viewFlashcards());
			}
		});
		viewFlashCards.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 2;
		buttons.add(viewFlashCards,c);
		
		//Goes to Delete Flashcard Menu
		JButton deleteFlashCards = new JButton("Delete a Flashcard");
		deleteFlashCards.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main,deleteFlashcard());
			}
			
		});
		deleteFlashCards.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 3;
		buttons.add(deleteFlashCards,c);
		
		//Goes to Take Quiz Menu
		JButton takeQuiz = new JButton("Take a Quiz");
		takeQuiz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(main, takeQuiz());
			}
		});
		takeQuiz.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 4;
		buttons.add(takeQuiz,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20,0,0,0);
		main.add(buttons,c);
				
		return main;
	}


	@Override
	public JPanel createFlashcardSet() {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("Create a Flashcard Set");
		title.setFont(new Font("Helvetica", Font.BOLD, 16));
		c.gridx = 0;
		c.gridy = 0;
		main.add(title,c);
		
		JLabel instruction = new JLabel("Enter the name of the flashcard set");
		instruction.setFont(new Font("Helvetica", Font.PLAIN, 12));
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0);
		main.add(instruction,c);
		
		JLabel err_message = new JLabel("Flashcard set already exists!");
		err_message.setForeground(Color.RED);
		err_message.setFont(new Font("Helvetica", Font.PLAIN, 11));
		err_message.setVisible(false); //Set false later
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,4,0,0);
		c.anchor = GridBagConstraints.WEST;
		main.add(err_message,c);
				
		JTextField flashcardName = new JTextField();
		flashcardName.setPreferredSize(new Dimension(200,30));
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		main.add(flashcardName,c);
		
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
					
					if(DatabaseHandler.flashcardSetExists(flashcardName.getText())) {
						err_message.setVisible(true);
					}else {
						DatabaseHandler.addFlashCardSet(flashcardName.getText());
						switchPanel(main, message(0));
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
	public JPanel createFlashcard() {
		return null;
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
	public JPanel takeQuiz() {
		return null;
	}

	/**
	 * Displays a message before the user can go back.
	 * parameters: message_id (indicates unique message) 
	 * 
	 * 0: Flashcard Set is successfully added
	 */
	@Override
	public JPanel message(int message_id) {
		
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel title;
		
		//Selects a message to display 
		if(message_id == 0) {
			title = new JLabel("Success!");
			title.setFont(new Font("Helvetica", Font.BOLD, 25));
			title.setForeground(new Color(34,112,48));
			c.gridx = 0;
			c.gridy = 0;
			main.add(title,c);
			
			JLabel suc_message = new JLabel("Your flashcard set is successfully added!");
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(5,0,0,0);
			main.add(suc_message,c);
		}
		
		//Back Button
		JButton back = new JButton("Main Menu");
		back.setPreferredSize(new Dimension(150,30));
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
}
