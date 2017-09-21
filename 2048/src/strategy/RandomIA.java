package strategy;

import java.util.Random;

import main.Direction;
import main.Grid;

public class RandomIA implements IA_interface {

	public RandomIA() {

	}

	/**
	 * Méthode utilisé par notre joueur IA pour réaliser un tour dans notre
	 * grille et réaliser une action
	 */
	public boolean execute(Grid grid) {

		Random val = new Random();
		int dir = val.nextInt(4);
		if (dir == 0) {
			if (grid.action_possible_bis(Direction.UP)) {
				grid.move_to_direction(Direction.UP, true);
			}
		} else if (dir == 1) {
			if (grid.action_possible_bis(Direction.DOWN)) {
				grid.move_to_direction(Direction.DOWN, true);
			}
		} else if (dir == 2) {
			if (grid.action_possible_bis(Direction.LEFT)) {
				grid.move_to_direction(Direction.LEFT, true);
			}
		} else if (dir == 3) {
			if (grid.action_possible_bis(Direction.RIGHT)) {
				grid.move_to_direction(Direction.RIGHT, true);
			}
		}

		return false;
	}
}
