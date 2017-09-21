package strategy_pattern;

import java.awt.Dimension;
import java.util.Random;
import java.util.Vector;

import models.Border_position;
import models.Cell;
import models.Cell_owner;
import models.Grid;

public class OptimumAI implements AI_interface {

	public void make_player_turn_from_start(Grid copy, Cell_owner player,
			int i, int j, Border_position border) {

		// Base move
		copy.set_cell_border_to_owner(i, j, border, copy.get_player_turn(),
				true);
		copy.set_next_player(true);

		// Possible move replay
		while (copy.get_player_turn() == player) {
			Vector<Dimension> closable = copy.get_all_closable_cell();
			if (closable.size() == 0) {

				// Random play
				Vector<Cell> free_cells = copy.get_all_free_cell();
				Random intRand = new Random();

				int cell_selection = intRand.nextInt(free_cells.size());
				Cell target = free_cells.elementAt(cell_selection);

				Vector<Border_position> borders = copy
						.get_all_free_border_from_cell(target.get_line_index(),
								target.get_column_index());
				int border_selection = intRand.nextInt(borders.size());
				Border_position target_border = borders
						.elementAt(border_selection);

				copy.set_cell_border_to_owner(target.get_line_index(),
						target.get_column_index(), target_border,
						copy.get_player_turn(), true);
				copy.set_next_player(true);

			} else {
				for (Dimension dim : closable) {
					Border_position to_close = copy.get_unique_free_border(
							dim.width, dim.height);
					copy.set_cell_border_to_owner(dim.width, dim.height,
							to_close, copy.get_player_turn(), true);
					copy.set_next_player(true);
				}
			}
		}
	}

	@Override
	public boolean execute(Grid grid) {
		/*
		 * Le but de l'algorithme va etre de : Essayer tout les coups possibles
		 * et regarder ce qu'il advient de la grille. Pour vérifier l'etat de la
		 * grille après son coup, il va regarder le score potentile qu'il peut
		 * obtenir (IE : les case qu'il peut fermer) et le score potentiel de
		 * son adversaire suivant (IE : les case que l'adversaire peut fermer)
		 */

		Cell_owner player_turn = grid.get_player_turn();
		Cell_owner next_player_turn;
		int player_score = 0;
		int enemy_score = 0;
		int min_diff = Integer.MIN_VALUE;

		int i_selected = 0;
		int j_selected = 0;
		Border_position border_selected = Border_position.UP;

		for (int i = 0; i < grid.get_nb_line(); i++) {
			for (int j = 0; j < grid.get_nb_column(); j++) {
				for (Border_position border : grid
						.get_all_free_border_from_cell(i, j)) {
					Grid copy = grid.get_copy();

					// AI turn
					make_player_turn_from_start(copy, copy.get_player_turn(),
							i, j, border);

					player_score = copy.get_player_score(player_turn);

					// Next player turn
					next_player_turn = copy.get_player_turn();
					Vector<Dimension> closable = copy.get_all_closable_cell();
					Random intRand = new Random();
					if (closable.size() == 0) {

						// Random play
						Vector<Cell> free_cells = copy.get_all_free_cell();
						Random intRand2 = new Random();

						int cell_selection = intRand2
								.nextInt(free_cells.size());
						Cell target = free_cells.elementAt(cell_selection);

						Vector<Border_position> borders = copy
								.get_all_free_border_from_cell(
										target.get_line_index(),
										target.get_column_index());
						int border_selection = intRand2.nextInt(borders.size());
						Border_position target_border = borders
								.elementAt(border_selection);

						copy.set_cell_border_to_owner(target.get_line_index(),
								target.get_column_index(), target_border,
								copy.get_player_turn(), true);
						copy.set_next_player(true);

					} else {
						Dimension selected = closable.elementAt(intRand
								.nextInt(closable.size()));
						Border_position border_versus = copy
								.get_unique_free_border(selected.width,
										selected.height);
						make_player_turn_from_start(copy, next_player_turn,
								selected.width, selected.height, border_versus);
					}

					enemy_score = copy.get_player_score(next_player_turn);

					if ((player_score - enemy_score) > min_diff) {
						min_diff = player_score - enemy_score;
						i_selected = i;
						j_selected = j;
						border_selected = border;
					}
				}
			}
		}

		grid.set_cell_border_to_owner(i_selected, j_selected, border_selected,
				grid.get_player_turn(), false);

		return false;
	}
}
