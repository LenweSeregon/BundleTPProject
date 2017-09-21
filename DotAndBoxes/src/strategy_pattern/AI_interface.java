package strategy_pattern;

import models.Grid;

public interface AI_interface {

	/**
	 * Méthode utilisé par notre joueur IA pour jouers un tour dans notre grille
	 * et réaliser une action
	 *
	 * @param grid
	 *            la grille en paramétre
	 * @return retourne vrai si une action a été possible, faux sinon
	 */
	public boolean execute(Grid grid);

}
