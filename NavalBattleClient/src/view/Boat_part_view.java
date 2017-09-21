package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import model.Boat_orientation;
import model.Boat_position;

public class Boat_part_view extends JComponent {

	private int pos_x;
	private int pos_y;
	private int size_cell;
	private boolean is_dead;

	private Boat_position pos;
	private Boat_orientation orientation;
	private boolean is_player;

	private final static Color color_boat = Color.red;
	private final static int size_reducer = 12;

	/**
	 * Constructeur de la classe représentant une partie de bateau
	 * graphiquement. La classe s'occupe de positionner et dessiner correctement
	 * la partie de bateau en fonction des parametres
	 * 
	 * @param pos_x
	 *            la position X de la partie de bateau
	 * @param pos_y
	 *            la position Y de la partie de bateau
	 * @param size_cell
	 *            la taille de la partie de bateau
	 * @param pos
	 *            la position sous forme d'énumération pour savoir comment
	 *            dessiner
	 * @param orientation
	 *            l'orientation de la partie du bateau
	 * @param is_player
	 *            peremt de savoir si le bateau appartient au joueur
	 * @param is_dead
	 *            permet de savoir la partie du bateau a été touché ou non
	 */
	public Boat_part_view(int pos_x, int pos_y, int size_cell,
			Boat_position pos, Boat_orientation orientation, boolean is_player,
			boolean is_dead) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.size_cell = size_cell;
		this.pos = pos;
		this.orientation = orientation;
		this.is_dead = is_dead;
		this.is_player = is_player;
	}

	/**
	 * Méthode permettant de choisir si on veut mettre la partie de bateau à
	 * mort ou non
	 * 
	 * @param b
	 *            est ce qu'on veut mettre la partie de bateau à mort
	 */
	public void set_is_dead(boolean b) {
		this.is_dead = b;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (is_player) {
			g.setColor(color_boat);
			if (orientation == Boat_orientation.VERTICAL) {
				if (pos == Boat_position.HEAD) {
					int x1 = pos_x + (size_cell / 2);
					int y1 = pos_y + size_reducer;
					int x2 = pos_x + size_cell - size_reducer;
					int y2 = pos_y + size_cell;
					int x3 = pos_x + size_reducer;
					int y3 = pos_y + size_cell;
					g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2,
							y3 }, 3);
				} else if (pos == Boat_position.MIDDLE) {
					g.fillRect(pos_x + size_reducer, pos_y, size_cell
							- (2 * size_reducer), size_cell);
				} else {
					int x1 = pos_x + size_reducer;
					int y1 = pos_y;
					int x2 = pos_x + size_cell - size_reducer;
					int y2 = pos_y;
					int x3 = pos_x + (size_cell / 2);
					int y3 = pos_y + size_cell - size_reducer;
					g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2,
							y3 }, 3);
				}
			} else {
				if (pos == Boat_position.HEAD) {
					int x1 = pos_x + size_reducer;
					int y1 = pos_y + (size_cell / 2);
					int x2 = pos_x + size_cell;
					int y2 = pos_y + size_reducer;
					int x3 = pos_x + size_cell;
					int y3 = pos_y + size_cell - size_reducer;
					g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2,
							y3 }, 3);
				} else if (pos == Boat_position.MIDDLE) {
					g.fillRect(pos_x, pos_y + size_reducer, size_cell,
							size_cell - (2 * size_reducer));
				} else {
					int x1 = pos_x;
					int y1 = pos_y + size_reducer;
					int x2 = pos_x + size_cell - size_reducer;
					int y2 = pos_y + (size_cell / 2);
					int x3 = pos_x;
					int y3 = pos_y + size_cell - size_reducer;
					g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2,
							y3 }, 3);
				}
			}
		}
		if (is_dead) {
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
