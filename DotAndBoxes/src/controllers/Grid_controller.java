package controllers;

import java.awt.event.MouseEvent;

import models.Border_position;
import models.Cell;
import models.Cell_owner;
import models.Grid;

public class Grid_controller {

	private Grid model;

	/**
	 * Constructeur de la classe Grid_controller, elle initialise le modele
	 * contenu par le controlleur
	 * 
	 * @param model
	 *            la réféerence vers le model
	 */
	public Grid_controller(Grid model) {
		this.model = model;
	}

	/**
	 * Méthode permettant de donner l'autre au model de se réinitialiser
	 */
	public void re_init() {
		this.model.init();
	}

	/**
	 * Méthode permettant de gérer un click de souris réalisé par l'utilisateur.
	 * Cette méthode va regarder si le click permet d'activer une bordure de
	 * carrer et s'occuper d'appeler les vérifications annexes telles qu'une
	 * case complétée
	 * 
	 * @param e
	 *            evenement de la souris contenant entre autre sa position
	 * @see MouseEvent
	 */
	public void mouse_click_manager(MouseEvent e) {

		int target_x = e.getY() / model.get_size_cell();
		int target_y = e.getX() / model.get_size_cell();

		if (target_x < 0 || target_x > this.model.get_nb_column() - 1) {
			return;
		}
		if (target_y < 0 || target_x > this.model.get_nb_line() - 1) {
			return;
		}

		Cell target = model.get_grid()[target_x][target_y];

		int size = target.get_size_cell();
		boolean done = false;

		// Up Border
		if (target_x != 0 && !done) {
			if (is_mouse_on_line(target_y * size, (target_x * size) - 3,
					(target_y * size) + size, (target_x * size) + 3, e.getX(),
					e.getY())
					&& this.model.get_grid()[target_x][target_y].get_up_owner() == Cell_owner.NONE) {
				this.model.set_cell_border_to_owner(target_x, target_y,
						Border_position.UP, model.get_player_turn(), false);
				done = true;
			}
		}

		// Right border
		if (target_y != this.model.get_nb_column() - 1 && !done) {
			if (is_mouse_on_line((target_y * size) + size - 3, target_x * size,
					(target_y * size) + size + 3, (target_x * size) + size,
					e.getX(), e.getY())
					&& this.model.get_grid()[target_x][target_y]
							.get_right_owner() == Cell_owner.NONE) {

				this.model.set_cell_border_to_owner(target_x, target_y,
						Border_position.RIGHT, model.get_player_turn(), false);
				done = true;
			}
		}

		// Down border
		if (target_x != this.model.get_nb_line() - 1 && !done) {

			if (is_mouse_on_line((target_y * size), (target_x * size) + size
					- 3, (target_y * size) + size,
					(target_x * size) + size + 3, e.getX(), e.getY())
					&& this.model.get_grid()[target_x][target_y]
							.get_down_owner() == Cell_owner.NONE) {
				this.model.set_cell_border_to_owner(target_x, target_y,
						Border_position.DOWN, model.get_player_turn(), false);
				done = true;
			}
		}

		// Left border
		if (target_y != 0 && !done) {
			if (is_mouse_on_line((target_y * size) - 3, (target_x * size)
					+ size, (target_y * size) + 3, (target_x * size), e.getX(),
					e.getY())
					&& this.model.get_grid()[target_x][target_y]
							.get_left_owner() == Cell_owner.NONE) {
				this.model.set_cell_border_to_owner(target_x, target_y,
						Border_position.LEFT, model.get_player_turn(), false);
				done = true;
			}
		}

		if (done) {
			this.model.set_next_player(false);
			if (this.model.is_map_full()
					|| this.model.anticipated_win_player() != null
					&& this.model.get_winner() != Cell_owner.NONE) {
				this.model.set_winner(this.model.get_winner());
			}
		}
	}

	/**
	 * Méthode permettant de savoir si notre clic de souris se trouve dans la
	 * boite englobante d'un coté de carré
	 * 
	 * @param x1
	 *            position x supérieur gauche de la boite
	 * @param y1
	 *            position y supérieur gauche de la boite
	 * @param x2
	 *            position x inférieur droite de la boite
	 * @param y2
	 *            position y inférieur droite de la boite
	 * @param pos_x_mouse
	 *            position en x de la souris
	 * @param pos_y_mouse
	 *            position en y de la souris
	 * @return returne vrai si la souris est dans la boite englobante, faux
	 *         sinon
	 */
	public boolean is_mouse_on_line(int x1, int y1, int x2, int y2,
			int pos_x_mouse, int pos_y_mouse) {

		boolean x = false;
		boolean y = false;

		if ((pos_x_mouse >= x1 && pos_x_mouse <= x2)
				|| (pos_x_mouse <= x1 && pos_x_mouse >= x2)) {
			x = true;
		}
		if ((pos_y_mouse >= y1 && pos_y_mouse <= y2)
				|| (pos_y_mouse >= y2 && pos_y_mouse <= y1)) {
			y = true;
		}

		return x && y;
	}
}
