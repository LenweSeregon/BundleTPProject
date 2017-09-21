package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Word extends JPanel {

	private Vector<Letter> word;

	/**
	 * Constructeur de la classe Word, instanciant un Word comme une suite de
	 * lettre
	 * 
	 * @param s
	 *            String représentant la valeur du mot
	 * @see Letter
	 */
	public Word(String s) {
		this.word = new Vector<Letter>();

		for (int i = 0; i < s.length(); i++) {
			this.word.add(new Letter(s.toUpperCase().charAt(i)));
		}
	}

	/**
	 * Méthode permettant de réinitaliser le modéle logique du jeu en affectant
	 * à l'attribut word une nouvelle valeur
	 * 
	 * @param s
	 *            nouvelle valeur de word
	 */
	public void reinit(String s) {
		this.word = new Vector<Letter>();

		for (int i = 0; i < s.length(); i++) {
			this.word.add(new Letter(s.toUpperCase().charAt(i)));
		}
	}

	/**
	 * Méthode permettant d'afficher le mot dans le panel actuel
	 */
	public void paintComponent(Graphics g) {

		int pos_y = (this.getSize().height / 2);
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.black);
		int x = 40;
		for (Letter c : this.word) {
			g.drawLine(x, pos_y + 10, x + 20, pos_y + 10);
			if (c.is_found()) {
				g.drawString(Character.toString(c.get_letter()), x + 5, pos_y);
			} else {
				g.drawString("*", x + 5, pos_y);
			}
			x += 25;
		}

	}

	/**
	 * Méthode qui test sur une lettre existe dans le mot
	 * 
	 * @param c
	 *            la lettre dont l'on veut savoir si elle existe ou non
	 * @return retourne vrai si la lettre existe, faux sinon
	 */
	public boolean letter_exist(char c) {

		for (Letter l : this.word) {
			if (l.get_letter() == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de savoir si toutes les lettres du mot on était
	 * découverte
	 * 
	 * @return vrai si le mot est découvert entiérement, faux sinon
	 */
	public boolean all_revealed() {
		for (Letter l : this.word) {
			if (!l.is_found()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Cette méthode permet simplement de réveler toutes les lettres du mot
	 */
	public void reveal_all() {
		for (Letter l : this.word) {
			l.set_found(true);
		}
	}

	/**
	 * Méthode permettant de réveler tout les lettres d'un caractere dans un mot
	 * 
	 * @param c
	 *            le caractere que l'on souhaite révéler
	 */
	public void reveal_letter(char c) {
		for (Letter l : this.word) {
			if (l.get_letter() == Character.toUpperCase(c)) {
				l.set_found(true);
			}
		}
	}

}
