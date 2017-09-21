package controller;

import model.Grid;
import model.Owner;

public class Grid_controller {

	private Grid model;

	/**
	 * Constructeur de la classe Grid_controller, elle initialise le controller
	 * tel un mvc avec un modele en argument
	 * 
	 * @param grid
	 *            le modele que l'on associe a notre controleur
	 */
	public Grid_controller(Grid grid) {
		this.model = grid;
	}

	/**
	 * Méthode permettant de demander un placement de piece, le placement de
	 * pièce est réalisé par rapport à la position de la piéce de référence qui
	 * se trouve dans notre modéle
	 */
	public void ask_piece_placement() {
		if (this.model.get_column_empty_space(this.model
				.get_placemeent_next_piece()) > 0) {
			this.model.set_piece_to_column(this.model
					.get_placemeent_next_piece());
			this.model.notify_observer_grid_change(this.model.get_grid());

			if (this.model.game_win()) {
				if (this.model.is_suicid_mode()) {
					if (this.model.get_player_turn() == Owner.YELLOW) {
						this.model.notify_victory(Owner.RED);
					} else {
						this.model.notify_victory(Owner.YELLOW);
					}
				} else {
					this.model.notify_victory(this.model.get_player_turn());
				}
			} else if (this.model.is_grid_fill()) {
				this.model.notify_victory(Owner.NONE);
			} else {

				this.model.next_player();
				this.model.notify_next_piece_move(this.model.get_next_piece(),
						this.model.get_placemeent_next_piece());
				this.model.notify_message_box("C'est au tour de "
						+ this.model.get_player_turn().toString());
				if (this.model.AI_activated()) {
					this.model.make_ai_play();
				}
			}

		} else {
			this.model
					.notify_message_box("<html> Mouvement impossible, recommencer !<br>"
							+ "C'est au tour de "
							+ this.model.get_player_turn().toString()
							+ "<br></html>");
		}
	}

	/**
	 * Méthode permettant de demander de bouger la piece de référence de
	 * position, le parametre de la fonction permet simplement de dire si l'on
	 * souhaite aller de +1 ou de -1 au niveau index
	 * 
	 * @param value
	 *            la valeur représentant la direction
	 */
	public void move_next_piece_to(int value) {
		if (this.model.get_placemeent_next_piece() + value >= 0
				&& this.model.get_placemeent_next_piece() + value <= this.model
						.get_nb_column() - 1) {
			// Action
			this.model.move_placement_next_piece(this.model
					.get_placemeent_next_piece() + value);
		}
	}
}
