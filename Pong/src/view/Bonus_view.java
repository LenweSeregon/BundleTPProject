package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import model.Bonus_mode;

public class Bonus_view extends JComponent {
	private int pos_x;
	private int pos_y;
	private int radius;
	private Bonus_mode mode;

	/**
	 * Constructeur de la classe bonus vue qui est la représetation graphique
	 * des bonus
	 * 
	 * @param pos_x
	 *            la position x du bonus
	 * @param pos_y
	 *            la position y du bonus
	 * @param radius
	 *            le rayon du bonus
	 * @param mode
	 *            le mode du bonus
	 */
	public Bonus_view(int pos_x, int pos_y, int radius, Bonus_mode mode) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.radius = radius;
		this.mode = mode;
	}

	@Override
	public void paintComponent(Graphics g2) {
		g2.setColor(Color.WHITE);
		g2.drawOval(pos_x - radius, pos_y - radius, radius * 2, radius * 2);

		if (mode == Bonus_mode.NEW_BALL) {
			g2.setFont(g2.getFont().deriveFont(Float.valueOf(radius)));
			g2.drawString("B", pos_x - radius / 10, pos_y + radius / 4);
		}
	}
}
