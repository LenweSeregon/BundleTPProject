package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JComponent;

public class Ball_view extends JComponent {

	private int id;
	private int pos_x;
	private int pos_y;
	private int radius;
	private Vector<Dimension> last_p;

	/**
	 * Constructeur de la classe balle vue qui est le composant graphique des
	 * balles
	 * 
	 * @param id
	 *            l'id de la balle
	 * @param pos_x
	 *            la position x de la balle
	 * @param pos_y
	 *            la position y de la balle
	 * @param radius
	 *            le rayon de la balle
	 */
	public Ball_view(int id, int pos_x, int pos_y, int radius) {
		this.id = id;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.radius = radius;

		this.last_p = new Vector<Dimension>();
	}

	/**
	 * Méthode permettant de récupérer l'id de la balle
	 * 
	 * @return l'id de la balle
	 */
	public int get_id() {
		return this.id;
	}

	/**
	 * Méthode permettant de mettre les derniéres positions de balle dans la
	 * classe
	 * 
	 * @param pos
	 *            les derniére positions de balle
	 */
	public void set_last_pos(Vector<Dimension> pos) {
		this.last_p = pos;
	}

	/**
	 * Méthode permettant de metter la position X de la balle
	 * 
	 * @param d
	 *            la position x
	 */
	public void set_pos_x(int d) {
		pos_x = d;
	}

	/**
	 * Méthode permettant de mettre la position y de la balle
	 * 
	 * @param y
	 *            la position y
	 */
	public void set_pos_y(int y) {
		pos_y = y;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(pos_x - radius, pos_y - radius, radius * 2, radius * 2);

		int it = 1;
		for (int i = last_p.size() - 1; i >= last_p.size() - 30; i--) {

			if (i != 0 && i % 5 == 0 && i >= 0 && i < last_p.size()) {
				int reducer = ((it++) * 35);

				double pos_x = last_p.elementAt(i).getWidth();
				double pos_y = last_p.elementAt(i).getHeight();

				Color color = new Color(255 - reducer, 255 - reducer,
						255 - reducer);
				g.setColor(color);

				g.fillOval((int) pos_x - radius, (int) pos_y - radius,
						(int) (radius * 2 - (it * 1.5)),
						(int) (radius * 2 - (it * 1.5)));

			}
		}
	}
}
