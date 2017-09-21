package pattern_observer;

import model.Grid;

public interface Observable_game {

	/**
	 * Méthode permettant d'ajouter un observeur de menu sur notre classe
	 * observable de menu
	 * 
	 * @param ob_m
	 *            l'observeur de menu que l'on souhaite ajouter
	 */
	public void add_observer_menu(Observer_game ob_g);

	/**
	 * Méthode permettant de supprimer tout les observeurs de menu sur notre
	 * classe observable de menu
	 */
	public void remove_all_observers_menu();

	/**
	 * Méthode permettant de notifier de la création d'un plateau de jeu lorsque
	 * celui ci a été choisi par le joueur lors du menu de selection des niveaux
	 * 
	 * @param grid
	 *            la grille qui a été construire et que l'on souhaite notifier
	 */
	public void notify_game_creation(Grid grid);

	/**
	 * Méthode permettant de notifier un changement dans une cellule de la
	 * grille que l'on vient de cliquer
	 * 
	 * @param i
	 *            la case se trouve en I sur l'axe X
	 * @param j
	 *            la case se trouve en J sur l'axe Y
	 * @param val
	 *            la valeur booléenne a laquelle est passé la case
	 */
	public void notify_tile_change(int i, int j, boolean val);

	/**
	 * Méthode permettant de notifier la vue de la victoire du joueur. C'est à
	 * dire qu'il a réussit à completer la grille
	 */
	public void notify_victory();

}
