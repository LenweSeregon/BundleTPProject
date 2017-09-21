package strategy;

import main.Grid;

public interface IA_interface {

	/**
	 * Méthode utilisé par notre joueur IA pour réaliser un tour dans notre
	 * grille et réaliser une action
	 */
	public boolean execute(Grid grid);

}
