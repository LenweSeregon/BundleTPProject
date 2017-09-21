package main;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawArea extends JPanel {

	private int level_drawing;

	/**
	 * Constructeur de la classe, il initialise le niveau de dessin à 0
	 */
	public DrawArea() {

		this.level_drawing = 0;

	}

	/**
	 * Méthode permettant de mettre un niveau de dessin à notre fenetre
	 * 
	 * @param val
	 *            la valeur que l'on souhaite mettre au niveau de dessin
	 */
	public void set_level_drawing(int val) {
		this.level_drawing = val;
	}

	/**
	 * Methode permettant de connaitre le niveau courant de dessin que réalise
	 * la classe
	 * 
	 * @return retourne le niveau courant de dessin
	 */
	public int get_level_drawing() {
		return this.level_drawing;
	}

	/**
	 * Méthode dessinant le pendu, elle utilise son niveau de dessin pour savoir
	 * quelle partie dessiner
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 */
	public void paintComponent(Graphics g) {

		if (this.level_drawing >= 1) {
			draw_level_1(g);
		}
		if (this.level_drawing >= 2) {
			draw_level_2(g);
		}
		if (this.level_drawing >= 3) {
			draw_level_3(g);
		}
		if (this.level_drawing >= 4) {
			draw_level_4(g);
		}
		if (this.level_drawing >= 5) {
			draw_level_5(g);
		}
		if (this.level_drawing >= 6) {
			draw_level_6(g);
		}
		if (this.level_drawing >= 7) {
			draw_level_7(g);
		}
		if (this.level_drawing >= 8) {
			draw_level_8(g);
		}
		if (this.level_drawing >= 9) {
			draw_level_9(g);
		}
	}

	/**
	 * Méthode permettant de dessiner l'étape 1 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_1(Graphics g) {
		int xStart = this.getWidth() / 3;
		int yStart = this.getHeight() / 2;
		int xEnd = (this.getWidth() / 3) + 100;
		int yEnd = this.getHeight() / 2;
		g.drawLine(xStart, yStart, xEnd, yEnd);
	}

	/**
	 * Méthode permettant de dessiner l'étape 2 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_2(Graphics g) {
		int xStart = (this.getWidth() / 3) + 50;
		int yStart = this.getHeight() / 2;
		int xEnd = (this.getWidth() / 3) + 50;
		int yEnd = (this.getHeight() / 2) - 150;
		g.drawLine(xStart, yStart, xEnd, yEnd);
	}

	/**
	 * Méthode permettant de dessiner l'étape 3 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_3(Graphics g) {
		int xStart = (this.getWidth() / 3) + 50;
		int yStart = this.getHeight() / 2 - 110;
		int xEnd = xStart + 30;
		int yEnd = yStart - 40;
		g.drawLine(xStart, yStart, xEnd, yEnd);
	}

	/**
	 * Méthode permettant de dessiner l'étape 4 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_4(Graphics g) {
		int xStart = (this.getWidth() / 3) + 50;
		int yStart = (this.getHeight() / 2) - 150;
		int xEnd = (this.getWidth() / 3) + 150;
		int yEnd = (this.getHeight() / 2) - 150;
		g.drawLine(xStart, yStart, xEnd, yEnd);
	}

	/**
	 * Méthode permettant de dessiner l'étape 5 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_5(Graphics g) {
		int xStart = (this.getWidth() / 3) + 150;
		int yStart = (this.getHeight() / 2) - 150;
		int xEnd = xStart;
		int yEnd = yStart + 20;
		g.drawLine(xStart, yStart, xEnd, yEnd);
	}

	/**
	 * Méthode permettant de dessiner l'étape 6 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_6(Graphics g) {
		int xStart = (this.getWidth() / 3) + 135;
		int yStart = (this.getHeight() / 2) - 130;
		g.drawOval(xStart, yStart, 30, 30);
	}

	/**
	 * Méthode permettant de dessiner l'étape 7 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_7(Graphics g) {

		int xStart = (this.getWidth() / 3) + 150;
		int yStart = (this.getHeight() / 2) - 100;
		int xEnd = (this.getWidth() / 3) + 150;
		int yEnd = (this.getHeight() / 2) - 50;
		g.drawLine(xStart, yStart, xEnd, yEnd);

	}

	/**
	 * Méthode permettant de dessiner l'étape 8 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_8(Graphics g) {
		int xStart = (this.getWidth() / 3) + 150;
		int yStart = (this.getHeight() / 2) - 100;
		int xEnd = (this.getWidth() / 3) + 150;
		int yEnd = (this.getHeight() / 2) - 50;

		g.drawLine(xStart, yStart, xEnd - 20, yEnd - 16);
		g.drawLine(xStart, yStart, xEnd + 20, yEnd - 16);
	}

	/**
	 * Méthode permettant de dessiner l'étape 9 du pendu
	 * 
	 * @param g
	 *            l'objet Graphics permettant de dessiner
	 * @see Graphics
	 */
	private void draw_level_9(Graphics g) {
		int xStart = (this.getWidth() / 3) + 150;
		int yStart = (this.getHeight() / 2) - 50;

		g.drawLine(xStart, yStart, xStart - 10, yStart + 35);
		g.drawLine(xStart, yStart, xStart + 10, yStart + 35);
	}

}
