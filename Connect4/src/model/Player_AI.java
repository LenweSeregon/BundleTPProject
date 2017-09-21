package model;

public class Player_AI {

	private Owner owner;

	/**
	 * Constructeur de la classe Player_AI qui permet de construire un joueur
	 * avec un attribut propriétaire
	 * 
	 * @param owner
	 *            le propriétaire qui est dans le joueur
	 */
	public Player_AI(Owner owner) {
		this.owner = owner;
	}

	/**
	 * Méthode permettant d'obtenir la minimisation via l'algorithme min max
	 * 
	 * @param grid
	 *            la grille que l'on veut appliquer minmax
	 * @param owner
	 *            le propriétaire qui appelle minmax
	 * @param depth
	 *            la profondeur actuelle de recherche
	 * @return une évaluation de la grille
	 */
	private int min_max(Grid grid, Owner owner, int depth) {
		return this.min(grid, owner, depth);
	}

	/**
	 * Méthode permettant d'obtenir la minimisation via l'algorithme min
	 * 
	 * @param grid
	 *            la grille que l'on veut appliquer min
	 * @param owner
	 *            le propriétaire qui appelle min
	 * @param depth
	 *            la profondeur actuelle de recherche
	 * @return une évaluation de la grille
	 */
	private int min(Grid grid, Owner owner, int depth) {
		if (depth != 0) {
			int game_value = Integer.MAX_VALUE;
			for (int i = 0; i < grid.get_nb_column(); i++) {
				if (grid.get_column_empty_space(i) > 0) {
					Grid copy = grid.get_copy();
					copy.set_piece_to_column(i);
					copy.next_player();
					game_value = Math.min(game_value,
							this.max(copy, copy.get_player_turn(), depth - 1));
				}
			}
			return game_value;
		} else {
			return grid.valuate_grid(owner);
		}
	}

	/**
	 * Méthode permettant d'obtenir la minimisation via l'algorithme max
	 * 
	 * @param grid
	 *            la grille que l'on veut appliquer min
	 * @param owner
	 *            le propriétaire qui appelle min
	 * @param depth
	 *            la profondeur actuelle de recherche
	 * @return une évaluation de la grille
	 */
	private int max(Grid grid, Owner owner, int depth) {
		if (depth != 0) {
			int game_value = Integer.MIN_VALUE;
			for (int i = 0; i < grid.get_nb_column(); i++) {
				if (grid.get_column_empty_space(i) > 0) {
					Grid copy = grid.get_copy();
					copy.set_piece_to_column(i);
					copy.next_player();
					game_value = Math.max(game_value,
							this.min(copy, copy.get_player_turn(), depth - 1));
				}
			}
			return game_value;
		} else {
			return grid.valuate_grid(owner);
		}
	}

	/**
	 * Méthode de jouer pour un joueur AI en choississant la meilleur position
	 * de colonne
	 * 
	 * @param grid
	 *            la grille ou l'on fait les tests
	 * @param owner
	 *            le propriétaire de la grille
	 */
	public void play(Grid grid, Owner owner) {
		int column_to_play = 0;
		double game_value = Integer.MIN_VALUE;
		for (int i = 0; i < grid.get_nb_column(); i++) {
			if (grid.get_column_empty_space(i) > 0) {
				Grid copy = grid.get_copy();
				copy.set_piece_to_column(i);
				copy.next_player();
				int cur_val = this.min_max(copy, copy.get_player_turn(), 4);
				if (cur_val >= game_value) {
					game_value = cur_val;
					column_to_play = i;
				}
			}
		}

		grid.set_piece_to_column(column_to_play);
		if (grid.game_win()) {
			grid.notify_victory(grid.get_player_turn());
		} else if (grid.is_grid_fill()) {
			grid.notify_victory(Owner.NONE);
		}
		grid.next_player();
	}
}
