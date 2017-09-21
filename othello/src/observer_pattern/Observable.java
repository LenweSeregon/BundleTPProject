package observer_pattern;

import mvc_model.Grid;
import mvc_model.Tile;
import enums.Owner;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observeur à un observable
	 * 
	 * @param ob
	 *            l'observeur que l'on souhaite ajouter
	 */
	public void add_observer(Observer ob);

	/**
	 * Méthode permettant de supprimer sans exception tout les observeurs de
	 * l'observable
	 */
	public void remove_observers();

	/**
	 * Méthode permettant de notifier de la création de la grille
	 * 
	 * @param grid
	 *            la grille nouvellement créée
	 */
	public void notify_creation(Grid grid);

	/**
	 * Méthode permettant de notifier d'un changement général dans la grille
	 * 
	 * @param grid
	 *            la grille ayant subit un changement
	 */
	public void notify_grid_change(Tile[][] grid);

	/**
	 * Méthode permettant de notifier d'un changement de joueur courant dans la
	 * grille
	 * 
	 * @param owner
	 */
	public void notify_change_player(Owner owner);

	/**
	 * Méthode permettant de notifier du message de "pas de case disponible"
	 * pour un joueur
	 * 
	 * @param display
	 *            vrai si le message doit etre affiché, faux sinon
	 */
	public void notify_no_hit(boolean display);

	/**
	 * Méthode permettant de notifier du vainqueur de la partie
	 * 
	 * @param owner
	 *            le vainqueur de la partie
	 */
	public void notify_winner(Owner owner);
}
