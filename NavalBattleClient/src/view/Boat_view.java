package view;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JComponent;

import model.Boat_orientation;
import model.Boat_part;

public class Boat_view extends JComponent {

	private int pos_x_head;
	private int pos_y_head;

	private int pos_x_in_panel;
	private int pos_y_in_panel;

	private Boat_orientation orientation;
	private Vector<Boat_part_view> parts_view;

	private boolean is_player_boat;

	/**
	 * Constructeur de la classe représentant un bateau de manière graphique
	 * 
	 * @param pos_x_pan
	 *            la position X du plateau
	 * @param pos_y_pan
	 *            la position Y du plateau
	 * @param pos_x_head
	 *            la position X de la tete du bateau
	 * @param pos_y_head
	 *            la position Y de la tete du bateau
	 * @param is_player
	 *            est ce que le bateau est un bateau du joueur
	 * @param orientation
	 *            l'orientation du bateau
	 * @param parts
	 *            les différentes parties du bateau pour les initialiser
	 *            graphiquement
	 */
	public Boat_view(int pos_x_pan, int pos_y_pan, int pos_x_head,
			int pos_y_head, boolean is_player, Boat_orientation orientation,
			Vector<Boat_part> parts) {
		this.pos_x_head = pos_x_head;
		this.pos_y_head = pos_y_head;
		this.is_player_boat = is_player;
		this.pos_x_in_panel = pos_x_pan;
		this.pos_y_in_panel = pos_y_pan;

		this.orientation = orientation;
		this.parts_view = new Vector<Boat_part_view>();

		for (Boat_part b : parts) {
			parts_view.add(new Boat_part_view(pos_x_in_panel + b.get_pos_x(),
					pos_y_in_panel + b.get_pos_y(), b.get_size_cell(), b
							.get_position(), orientation, is_player_boat, b
							.get_touched()));
		}
	}

	/**
	 * Méthode permettant de récupérer la position X de la tete du bateau
	 * 
	 * @return la position x de la tete du bateau
	 */
	public int get_pos_x_head() {
		return pos_x_head;
	}

	/**
	 * Méthode permettant de récupérer la position Y de la tete du bateau
	 * 
	 * @return la position y de la tete du bateau
	 */
	public int get_pos_y_head() {
		return pos_y_head;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Boat_part_view bpw : parts_view) {
			bpw.paintComponent(g);
		}
	}
}
