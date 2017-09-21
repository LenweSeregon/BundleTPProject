package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Cell_view extends JComponent {

	private int pos_x;
	private int pos_y;
	private int size_cell;

	private boolean has_been_visited;
	private boolean is_player;
	private boolean is_boat_dead;

	private static Color unvisited = new Color(20, 45, 76, 255);
	private static Color visited = new Color(4, 25, 57, 120);

	/**
	 * Constructeur de la classe représentant les cellules de manière graphique
	 * 
	 * @param pos_x
	 *            la position X de la cellule
	 * @param pos_y
	 *            la position Y de la cellule
	 * @param size_cell
	 *            la taille de la cellule
	 * @param is_player
	 *            est ce que la cellule appartient au joueur
	 * @param is_boat_dead
	 *            est ce que la cellule est touché
	 */
	public Cell_view(int pos_x, int pos_y, int size_cell, boolean is_player,
			boolean is_boat_dead) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.size_cell = size_cell;
		this.has_been_visited = false;
		this.is_player = is_player;
		this.is_boat_dead = is_boat_dead;
		this.setBounds(pos_x, pos_y, size_cell, size_cell);

	}

	/**
	 * Méthode permettant de savoir si la souris se trouve dans la cellule
	 * 
	 * @param mouse_x
	 *            la position en X de la souris
	 * @param mouse_y
	 *            la position en Y de la souris
	 * @return vrai si la souris est dans la souris, faux sinon
	 */
	public boolean mouse_in(int mouse_x, int mouse_y) {
		return mouse_x >= pos_x && mouse_x <= pos_x + size_cell
				&& mouse_y >= pos_y && mouse_y <= pos_y + size_cell;
	}

	/**
	 * Méthode permettant de savoir si une cellule a été visité ou non
	 * 
	 * @param b
	 *            la valeur a mettre à la visite de la case
	 */
	public void set_has_been_visited(boolean b) {
		this.has_been_visited = b;
	}

	/**
	 * Méthode permettant de choisir la valeur au fait d'avoir un bateau mort
	 * 
	 * @param b
	 *            la valeur booléenne a assigner
	 */
	public void set_boat_dead(boolean b) {
		this.is_boat_dead = b;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (has_been_visited) {
			g.setColor(visited);
			g.fillRect(pos_x, pos_y, size_cell, size_cell);
		} else {
			g.setColor(unvisited);
			g.fillRect(pos_x, pos_y, size_cell, size_cell);
		}

		if (is_boat_dead) {
			int stroke = 3;
			g2.setStroke(new BasicStroke(stroke));
			g2.setColor(Color.RED);
			g2.drawLine(pos_x + stroke, pos_y + stroke, pos_x + size_cell
					- stroke, pos_y + size_cell - stroke);
			g2.drawLine(pos_x + size_cell - stroke, pos_y + stroke, pos_x
					+ stroke, pos_y + size_cell - stroke);
			g2.setStroke(new BasicStroke(1));
		}

	}
}
