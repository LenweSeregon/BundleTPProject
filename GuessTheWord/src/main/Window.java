package main;
import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private GameBoard board;

	/**
	 * Constructeur de la classe Window, il permet de creer une fenetre appelé
	 * pendu et ne pouvant pas être redimensionnée
	 * 
	 * @param width
	 *            Largeur de notre fenetre
	 * @param height
	 *            Hauteur de notre fenetre
	 */
	public Window(int width, int height) {

		this.setTitle("Pendu");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * Permet de lancer le pendu qui est contenu dans la fenetre
	 */
	public void launch() {
		this.board = null;
		this.board = new GameBoard(this);
		this.getContentPane().add(this.board, BorderLayout.CENTER);
		this.setVisible(true);
	}

	/**
	 * Permet de quitter l'application en fermant la fenetre
	 */
	public void exit() {
		this.setVisible(false);
		this.dispose(); // Destroy the JFrame object
	}
}
