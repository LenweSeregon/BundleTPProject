package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Border_view extends JComponent {
	private int pos_x;
	private int pos_y;
	private int width;
	private int height;

	/**
	 * Le constructeur de la bordure vue qui est la représentation graphique de
	 * la bordure
	 * 
	 * @param pos_x
	 *            la position x de la bordure
	 * @param pos_y
	 *            la position y de la bordure
	 * @param width
	 *            la largeur de la bordure
	 * @param height
	 *            la hauteur de la bordure
	 */
	public Border_view(int pos_x, int pos_y, int width, int height) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(pos_x, pos_y, width, height);
	}
}
