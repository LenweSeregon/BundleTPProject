package pattern_observer;

import model.Grid;

public interface Observer_game {

	/**
	 * Méthode permettant d'etre notifier de la création d'une partie. La grille
	 * en paramètre détient tous les informations nécessaire à transmettre à la
	 * vue pour que celui ci construise la grille
	 * 
	 * @param grid
	 *            la grille que l'on vient de créer
	 */
	public void update_game_creation(Grid grid);

	/**
	 * Méthode permettant d'être notifié d'un changement d'état d'une cellule
	 * dans le modèle du jeu.
	 * 
	 * @param i
	 *            la case se trouve à l'index I sur l'axe X
	 * @param j
	 *            la case se trouve à l'index J sur l'axe Y
	 * @param val
	 *            la nouvelle valeure booléenne de la case
	 */
	public void update_tile_change(int i, int j, boolean val);

	/**
	 * Méthode permettant d'être notifié la vue de la victoire du joueur. C'est
	 * à dire qu'il a réussit à completer la grille et qu'il faut afficher un
	 * message de fin
	 */
	public void update_victory();
}
