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
}

public class Main extends JFrame implements Menus{
	
	private static final long serialVersionUID = 1L;
		
	/**
	 * Program starts with the main menu
	 */
	public Main() {
		
		add(mainMenu());
		
		setTitle("Flashcards");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}

	//Driver function
	public static void main(String[] args) throws Exception {

		new Main();
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
		return null;	
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
}
