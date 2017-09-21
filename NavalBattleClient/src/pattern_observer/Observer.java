package pattern_observer;

import model.Boat;
import model.Cell;
import model.Grid;
import model.Winner;

public interface Observer {

	/**
	 * Méthode permettant d'être notifier d'une création de plateau
	 * 
	 * @param player_grid
	 *            la grille du joueur qui vient d'être crée
	 * @param enemy_grid
	 *            la grille de l'adversaire qui vient d'être crée
	 */
	public void update_creation(Grid player_grid, Grid enemy_grid);

	/**
	 * Méthode permettant d'être notifier d'un changement d'état d'une cellule
	 * 
	 * @param grid
	 *            la grille ou la cellule a changé
	 * @param cell
	 *            la cellule qui a changé
	 */
	public void update_cell_change(Grid grid, Cell cell);

	/**
	 * Méthode permettant d'être notifier d'un changement d'état d'un bateau
	 * 
	 * @param grid
	 *            la grille ou un bateau a changé
	 * @param boat
	 *            le bateau qui a changé
	 */
	public void update_boat_hitted(Grid grid, Boat boat);

	/**
	 * Méthode permettant d'être notifier d'un changement de la grille de
	 * l'advesaire quand il a été touché
	 * 
	 * @param i
	 *            l'index I ou l'adversaire à été touché
	 * @param j
	 *            l'index J ou l'adversaire a été touché
	 */
	public void update_enemy_hitted(int i, int j);

	/**
	 * Méthode permettant d'être notifier d'un changement de statut d'attendre
	 * dans le joueur
	 * 
	 * @param message
	 *            vrai si le joueur doit attendre car il attend de recevoir un
	 *            message, faux si il doit envoyer un message
	 */
	public void update_message(String message);

	/**
	 * Méthode permettant d'etre notifier d'un message splash
	 * 
	 * @param message
	 *            le message
	 */
	public void update_message_splash(String message);

	/**
	 * Méthode permettant d'être notifier de la victorie d'un joueur
	 * 
	 * @param winner
	 *            le vainqueur de la partie
	 */
	public void update_winner(Winner winner);
}
