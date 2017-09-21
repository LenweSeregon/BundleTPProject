package pattern_observer;

import model.Boat;
import model.Cell;
import model.Grid;
import model.Winner;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observateur à un observable
	 * 
	 * @param observer
	 *            l'observateur à ajouter
	 */
	public void add_observer(Observer observer);

	/**
	 * Méthode permettant de supprimer tout les observateurs d'un observables
	 */
	public void remove_all();

	/**
	 * Méthode permettant de notifier la création d'une grille en donnat les
	 * grilles
	 * 
	 * @param player_grid
	 *            la grille du joueur qui vient d'etre crée
	 * @param enemy_grid
	 *            la grill de l'ennemi qui vient d'être crée
	 */
	public void notify_creation(Grid player_grid, Grid enemy_grid);

	/**
	 * Mtéthode permettant de notifier d'un changement d'une cellule d'une
	 * grille
	 * 
	 * @param grid
	 *            la grille ou une cellule a subit un changement
	 * @param cell
	 *            la cellule qui a subit un changement
	 */
	public void notify_cell_change(Grid grid, Cell cell);

	/**
	 * Méthode permettant de notifier d'un changement d'un bateau d'une grille
	 * 
	 * @param grid
	 *            la grilel ou la bateau à subit un changement
	 * @param boat
	 *            le bateau qui a subit un changement
	 */
	public void notify_boat_hitted(Grid grid, Boat boat);

	/**
	 * Méthode permettant de notifier qu'un adversaire a été touché
	 * 
	 * @param i
	 *            l'index i ou l'adversaire à été touché
	 * @param j
	 *            l'index j ou l'aversaire à été touché
	 */
	public void notify_enemy_hitted(int i, int j);

	/**
	 * Méthode permettant de notifier que le joueur courant est en train
	 * d'attendre ou non
	 * 
	 * @param message
	 *            est ce que le joueur est en train d'attendre
	 */
	public void notify_message(String message);

	/**
	 * Méthode permettant d'etre notifier d'un message splash
	 * 
	 * @param message
	 *            le message
	 */
	public void notify_message_splash(String message);

	/**
	 * Méthode permettant de notifier de la victoire d'un joueur sous forme
	 * d'enumeration
	 * 
	 * @param winner
	 *            le gagnant de la partie
	 */
	public void notify_winner(Winner winner);
}
