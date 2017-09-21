package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Racket_view extends JComponent {

	private int pos_x;
	private int pos_y;
	private int width;
	private int height;

	/**
	 * Constructeur de la raquette vue qui est une réprésentation graphique de
	 * la raquette du modele
	 * 
	 * @param pos_x
	 *            la position x de la raquette
	 * @param pos_y
	 *            la positon y de la raquette
	 * @param height
	 *            le hauteur de la raquette
	 * @param width
	 *            la largeur de la raquette
	 */
	public Racket_view(int pos_x, int pos_y, int height, int width) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Méthode permettant de choisir la position y de la raquette
	 * 
	 * @param pos_y
	 *            la position y
	 */
	public void set_pos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(pos_x - width / 2, pos_y - height / 2, width, height);
	}
}
