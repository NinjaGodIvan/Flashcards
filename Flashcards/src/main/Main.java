package main;

import java.awt.*;
import javax.swing.*;

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
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}

	//Driver function
	public static void main(String[] args) {

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
		main.setLayout(new GridBagLayout());
				
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPanel takeQuiz() {
		// TODO Auto-generated method stub
		return null;
	}

}
