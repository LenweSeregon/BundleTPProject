package observer_pattern;

import mvc_model.Grid;
import mvc_model.Tile;
import enums.Owner;

public interface Observer {

	/**
	 * Méthode permettant de recevoir une notification quand la grille vient
	 * d'etre crée
	 * 
	 * @param grid
	 *            la grille nouvellement créée.
	 */
	public void update_creation(Grid grid);

	/**
	 * Méthode permettant de recevoir une notification quand la grille
	 * changement de manière générale
	 * 
	 * @param grid
	 *            la grille qui vient de subir un changement
	 */
	public void update_grid_change(Tile[][] grid);

	/**
	 * Méthode permettant de recevoir une notification lors d'un changement de
	 * joueur courant dans la grille
	 * 
	 * @param owner
	 *            le nouveau joueur qui va devoir jouer
	 */
	public void update_change_player(Owner owner);

	/**
	 * Méthode permettant de recevoir une notification lors de la non
	 * possibilité pour un joueur de jouer
	 * 
	 * @param display
	 *            vrai si le joueur ne peut pas jouer, faux sinon
	 */
	public void update_no_hit(boolean display);

	/**
	 * Méthode permettant de recevoir une notification lors de la victoire d'un
	 * joueur
	 * 
	 * @param owner
	 *            le vainqueur de la partie
	 */
	public void update_winner(Owner owner);
}
