package strategy;

import java.awt.Dimension;

import main.Direction;
import main.Grid;

public class OptimumIA implements IA_interface {

	public OptimumIA() {

	}

	/**
	 * Méthode permettant d'avoir une estimation par évaluation dans une
	 * profondeur de 1
	 * 
	 * @param grid
	 *            la référence vers la grille départ
	 * @param fst
	 *            La première direction à privilégier
	 * @param snd
	 *            La seconde direction à privilégier
	 * @return
	 */
	private Direction profondeur_1(Grid grid, Direction fst, Direction snd) {

		long max_valuation = 0;
		Direction better_dir = null;

		for (Direction dir : Direction.values()) {
			Grid copy = grid.get_copy();
			long valuation = 0;
			if (copy.action_possible_bis(dir)) {
				copy.move_to_direction(dir, true);
				valuation = copy.get_valuation(dir, fst, snd);
			} else {
				continue;
			}

			/* CHECK VALUATION */
			if (valuation > max_valuation) {
				max_valuation = valuation;
				better_dir = dir;
			}
		}
		return better_dir;
	}

	/**
	 * Méthode permettant d'avoir une estimation par évaluation dans une
	 * profondeur de 2
	 * 
	 * @param grid
	 *            la référence vers la grille départ
	 * @param fst
	 *            La première direction à privilégier
	 * @param snd
	 *            La seconde direction à privilégier
	 * @return
	 */
	private Direction profondeur_2(Grid grid, Direction fst, Direction snd) {

		Direction better_dir = null;
		long max_somme = Long.MIN_VALUE;
		for (Direction dirRoot : Direction.values()) {
			long somme = 0;
			Grid copy = grid.get_copy();
			/* APPLY DIRECTION TO ROOT DIRECTION */
			if (copy.action_possible_bis(dirRoot)) {
				copy.move_to_direction(dirRoot, true);
			} else {
				continue;
			}

			/* PROFONDEUR */
			for (int i = 0; i < 1; i++) {

				/* RANDOM CELL GENERATION */
				for (Dimension free_pos : copy.get_all_free_position()) {
					Grid copy2 = copy.get_copy();
					copy2.generate_cell_at(free_pos.width, free_pos.height, true);

					/* TEST ALL DIRECTION */
					for (Direction dirStep2 : Direction.values()) {
						Grid copy3 = copy2.get_copy();
						if (copy3.action_possible_bis(dirRoot)) {
							copy3.move_to_direction(dirRoot, true);
						} else {
							continue;
						}

						long valuation = copy3.get_valuation(dirRoot, fst, snd);
						somme += valuation;
					}
				}
			}

			if (somme > max_somme) {
				max_somme = somme;
				better_dir = dirRoot;
			}

		}
		return better_dir;
	}

	/**
	 * Méthode permettant d'avoir une estimation par évaluation dans une
	 * profondeur de 3
	 * 
	 * @param grid
	 *            la référence vers la grille départ
	 * @param fst
	 *            La première direction à privilégier
	 * @param snd
	 *            La seconde direction à privilégier
	 * @return
	 */
	private Direction profondeur_3(Grid grid, Direction fst, Direction snd) {
		Direction better_dir = null;
		long max_somme = Long.MIN_VALUE;
		for (Direction dirRoot : Direction.values()) {
			long somme = 0;
			Grid copy = grid.get_copy();
			/* APPLY DIRECTION TO ROOT DIRECTION */
			if (copy.action_possible_bis(dirRoot)) {
				copy.move_to_direction(dirRoot, true);
			} else {
				continue;
			}

			/* PROFONDEUR */
			/* RANDOM CELL GENERATION */
			for (Dimension free_pos : copy.get_all_free_position()) {
				Grid copy2 = copy.get_copy();
				copy2.generate_cell_at(free_pos.width, free_pos.height, true);

				/* TEST ALL DIRECTION */
				for (Direction dirStep2 : Direction.values()) {
					Grid copy3 = copy2.get_copy();
					if (copy3.action_possible_bis(dirStep2)) {
						copy3.move_to_direction(dirStep2, true);
					} else {
						continue;
					}

					for (Dimension free_pos_2 : copy3.get_all_free_position()) {
						Grid copy4 = copy3.get_copy();
						copy4.generate_cell_at(free_pos_2.width, free_pos_2.height, true);

						/* TEST ALL DIRECTION */
						for (Direction dirStep3 : Direction.values()) {
							Grid copy5 = copy4.get_copy();
							if (copy5.action_possible_bis(dirStep3)) {
								copy5.move_to_direction(dirStep3, true);
							} else {
								continue;
							}

							long valuation = copy5.get_valuation(dirRoot, fst, snd);
							somme += valuation;
						}
					}
				}
			}

			if (somme > max_somme) {
				max_somme = somme;
				better_dir = dirRoot;
			}

		}
		return better_dir;
	}

	/**
	 * Méthode permettant d'avoir une estimation par évaluation dans une
	 * profondeur de 4
	 * 
	 * @param grid
	 *            la référence vers la grille départ
	 * @param fst
	 *            La première direction à privilégier
	 * @param snd
	 *            La seconde direction à privilégier
	 * @return
	 */
	private Direction profondeur_4(Grid grid, Direction fst, Direction snd) {
		Direction better_dir = null;
		long max_somme = Long.MIN_VALUE;
		for (Direction dirRoot : Direction.values()) {
			long somme = 0;
			Grid copy = grid.get_copy();
			/* APPLY DIRECTION TO ROOT DIRECTION */
			if (copy.action_possible_bis(dirRoot)) {
				copy.move_to_direction(dirRoot, true);
			} else {
				continue;
			}

			/* RANDOM CELL GENERATION */
			for (Dimension free_pos : copy.get_all_free_position()) {
				Grid copy2 = copy.get_copy();
				copy2.generate_cell_at(free_pos.width, free_pos.height, true);

				/* TEST ALL DIRECTION */
				for (Direction dirStep2 : Direction.values()) {
					Grid copy3 = copy2.get_copy();
					if (copy3.action_possible_bis(dirStep2)) {
						copy3.move_to_direction(dirStep2, true);
					} else {
						continue;
					}

					for (Dimension free_pos_2 : copy3.get_all_free_position()) {
						Grid copy4 = copy3.get_copy();
						copy4.generate_cell_at(free_pos_2.width, free_pos_2.height, true);

						/* TEST ALL DIRECTION */
						for (Direction dirStep3 : Direction.values()) {
							Grid copy5 = copy4.get_copy();
							if (copy5.action_possible_bis(dirStep3)) {
								copy5.move_to_direction(dirStep3, true);
							} else {
								continue;
							}

							for (Dimension free_pos_3 : copy5.get_all_free_position()) {
								Grid copy6 = copy5.get_copy();
								copy6.generate_cell_at(free_pos_3.width, free_pos_3.height, true);

								/* TEST ALL DIRECTION */
								for (Direction dirStep4 : Direction.values()) {
									Grid copy7 = copy6.get_copy();
									if (copy7.action_possible_bis(dirStep4)) {
										copy7.move_to_direction(dirStep4, true);
									} else {
										continue;
									}

									long valuation = copy7.get_valuation(dirRoot, fst, snd);
									somme += valuation;
								}
							}
						}
					}
				}
			}

			if (somme > max_somme) {
				max_somme = somme;
				better_dir = dirRoot;
			}

		}
		return better_dir;
	}

	/**
	 * Méthode utilisé par notre joueur IA pour réaliser un tour dans notre
	 * grille et réaliser une action
	 */
	public boolean execute(Grid grid) {

		Direction horizontal_chosen = Direction.RIGHT;
		Direction vertical_chosen = Direction.UP;
		Dimension target_tile = new Dimension(0, 3);

		Grid copy = grid.get_copy();

		Direction dir = profondeur_3(copy, horizontal_chosen, vertical_chosen);

		if (dir != null) {
			grid.move_to_direction(dir, false);
		}

		return false;
	}
}
