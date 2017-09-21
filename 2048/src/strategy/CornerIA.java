package strategy;

import java.util.Random;

import main.Direction;
import main.Grid;

public class CornerIA implements IA_interface {

	public CornerIA() {
	}

	/**
	 * Méthode utilisé par notre joueur IA pour réaliser un tour dans notre
	 * grille et réaliser une action
	 */
	public boolean execute(Grid grid) {
		Random val = new Random();
		int dir = val.nextInt(2);
		if (dir == 0) {
			if (grid.action_possible_bis(Direction.UP)) {
				grid.move_to_direction(Direction.UP, true);
			} else {
				if (grid.action_possible_bis(Direction.LEFT)) {
					grid.move_to_direction(Direction.LEFT, true);
				} else {
					Direction[] dir_possible = new Direction[] { Direction.DOWN, Direction.RIGHT };
					Random rand = new Random();
					grid.move_to_direction(dir_possible[rand.nextInt(2)], true);
				}

			}

		} else if (dir == 1) {
			if (grid.action_possible_bis(Direction.LEFT)) {
				grid.move_to_direction(Direction.LEFT, true);
			} else {
				if (grid.action_possible_bis(Direction.UP)) {
					grid.move_to_direction(Direction.UP, true);
				} else {
					Direction[] dir_possible = new Direction[] { Direction.DOWN, Direction.RIGHT };
					Random rand = new Random();
					grid.move_to_direction(dir_possible[rand.nextInt(2)], true);
				}
			}

		}
		return false;
	}
}
