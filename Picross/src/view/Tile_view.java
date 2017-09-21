package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import utils.Resources;

@SuppressWarnings("serial")
public class Tile_view extends JLabel {

	private int pos_x;
	private int pos_y;
	private int size_x;
	private int size_y;
	private boolean has_been_pressed;

	private static Color color_pressed = new Color(255, 255, 255, 200);

	/**
	 * Constructeur de la classe qui représente graphiquement les différentes
	 * cellules du plateau
	 * 
	 * @param pos_x
	 *            la position X de la cellule
	 * @param pos_y
	 *            la position Y de la cellule
	 * 
	 * @param has_been_pressed
	 *            est ce que la case a été appuyé
	 */
	public Tile_view(int pos_x, int pos_y, int size_x, int size_y,
			boolean has_been_pressed) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.size_x = size_x;
		this.size_y = size_y;
		this.has_been_pressed = has_been_pressed;
	}

	/**
	 * Méthode permettant de choisir une valeur pour le fait d'avoir ou non la
	 * case qui est dasn une position appuyé
	 * 
	 * @param b
	 *            la case est t elle en position appuyé
	 */
	public void set_has_been_pressed(boolean b) {
		this.has_been_pressed = b;
	}

	/**
	 * Méthode permettant de savoir si le clique de souris se trouve dans une
	 * tile graphique. Ici la fonction va simplement regarder si le point de
	 * clic de la souris se trouve dans le rectangle qui forme la cellule
	 * 
	 * @param mouse_x
	 *            la position X de la souris
	 * @param mouse_y
	 *            la position Y de la souris
	 * @return vrai si la souris est à l'intérieur, faux sinon
	 */
	public boolean mouse_in(int mouse_x, int mouse_y) {
		return mouse_x >= pos_x && mouse_x <= pos_x + size_x
				&& mouse_y >= pos_y && mouse_y <= pos_y + size_y;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (has_been_pressed) {
			g2.setColor(color_pressed);
			g2.fillRect(pos_x, pos_y, size_x, size_y);
		} else {

			g2.setColor(new Color(155, 91, 0, 200));
			g2.fillRect(pos_x, pos_y, size_x, size_y);
			g2.drawImage(Resources.ring_1, pos_x, pos_y, size_x, size_y, null);
		}

	}
}
