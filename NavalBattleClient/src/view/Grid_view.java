package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JComponent;

import model.Boat;
import model.Cell;
import model.Grid;

public class Grid_view extends JComponent {

	private Cell_view[][] cells;
	private Vector<Boat_view> boats;

	private int nb_line;
	private int nb_column;
	private int size_cell;

	private int pos_x_in_panel;
	private int pos_y_in_panel;

	/**
	 * Constructeur de la classe représentant la grille de manière graphique.
	 * Elle encapsule toutes les entités qui vont avoir un rendu à l'écran sur
	 * chaque grille
	 * 
	 * @param grid
	 *            la grille que l'on veut transformer en graphique
	 * @param pos_x_in_panel
	 *            la position X dans le panel de la grille
	 * @param pos_y_in_panel
	 *            la position Y dans le panel de la grille
	 */
	public Grid_view(Grid grid, int pos_x_in_panel, int pos_y_in_panel) {
		nb_line = grid.get_nb_line();
		nb_column = grid.get_nb_column();
		size_cell = grid.get_size_cell();

		this.pos_x_in_panel = pos_x_in_panel;
		this.pos_y_in_panel = pos_y_in_panel;

		this.cells = new Cell_view[grid.get_nb_line()][grid.get_nb_column()];
		for (int i = 0; i < grid.get_nb_line(); i++) {
			for (int j = 0; j < grid.get_nb_column(); j++) {
				Cell c = grid.get_cell(i, j);
				cells[i][j] = new Cell_view(pos_x_in_panel + c.get_pos_x(),
						pos_y_in_panel + c.get_pos_y(), c.get_size_cell(),
						grid.get_is_player(), false);
			}
		}

		this.boats = new Vector<Boat_view>();
		for (Boat b : grid.get_boats()) {
			boats.add(new Boat_view(pos_x_in_panel, pos_y_in_panel, b
					.get_pos_x(), b.get_pos_y(), b.is_player_boat(), b
					.get_orientation(), b.get_boat_parts()));
		}
	}

	/**
	 * Méthode permettant de savoir si un bateau est présent à une position
	 * donnée
	 * 
	 * @param i_head
	 *            la position X dans la grille
	 * @param j_head
	 *            la position Y dans la grille
	 * @return vrai si un bateau est présent, faux sinon
	 */
	public boolean boat_present(int i_head, int j_head) {
		for (Boat_view b : boats) {
			if (b.get_pos_x_head() == i_head && b.get_pos_y_head() == j_head) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de mettre un bateau à une position donnée
	 * 
	 * @param i_head
	 *            la position X ou l'on veut ajouter le bateau
	 * @param j_head
	 *            la position Y ou l'on veut ajouter le bateau
	 * @param b
	 *            le bateau que l'on souhaite ajouter
	 */
	public void set_boat(int i_head, int j_head, Boat b) {
		int position_to_change = -1;
		int i = 0;
		for (Boat_view boat : boats) {
			if (boat.get_pos_x_head() == i_head
					&& boat.get_pos_y_head() == j_head) {
				position_to_change = i;
			}
			i++;
		}

		boats.set(
				position_to_change,
				new Boat_view(pos_x_in_panel, pos_y_in_panel, b.get_pos_x(), b
						.get_pos_y(), b.is_player_boat(), b.get_orientation(),
						b.get_boat_parts()));
	}

	/**
	 * Méthode permettant de mettre une cellule à visiter dans la grille
	 * 
	 * @param i
	 *            la position en X de la case que l'on souhaite mettre à visité
	 * @param j
	 *            la position en Y de la case que l'on souhaite mettre à visité
	 */
	public void set_cell_has_been_visited(int i, int j) {
		cells[i][j].set_has_been_visited(true);
	}

	/**
	 * Méthode permettant de mettre la cellule en tant qu'avec un bateau mort
	 * 
	 * @param i
	 *            la position X de la cellule ou l'on souhaite mettre notre
	 *            bateau mort
	 * @param j
	 *            la position Y de la cellule ou l'on souhaite mettre notre
	 *            bateau mort
	 */
	public void set_cell_boat_dead(int i, int j) {
		cells[i][j].set_boat_dead(true);
	}

	/**
	 * Méthode permettant de savoir si une cellule dans notre grille a été
	 * cliqué
	 * 
	 * @param pos_x_mouse
	 *            la position en X de la souris
	 * @param pos_y_mouse
	 *            la position en Y de la souris
	 * @return une Dimension contenant la position(x,j) si une cellule a été
	 *         cliqué, null sinon
	 */
	public Dimension is_cell_click(int pos_x_mouse, int pos_y_mouse) {
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (cells[i][j].mouse_in(pos_x_mouse, pos_y_mouse)) {
					return new Dimension(i, j);
				}
			}
		}
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				cells[i][j].paintComponent(g);
			}
		}

		for (Boat_view b : boats) {
			b.paintComponent(g);
		}

		// Draw grid
		g.setColor(Color.WHITE);
		for (int i = 0; i <= nb_line; i++) {
			g.drawLine(pos_x_in_panel + 0, pos_y_in_panel + i * size_cell,
					pos_x_in_panel + nb_column * size_cell, pos_y_in_panel + i
							* size_cell);
		}

		for (int i = 0; i <= nb_column; i++) {
			g.drawLine(pos_x_in_panel + i * size_cell, pos_y_in_panel + 0,
					pos_x_in_panel + i * size_cell, pos_y_in_panel + nb_line
							* size_cell);
		}
	}
}
