package observer_pattern;

import models.Cell;
import models.Cell_owner;
import models.Player;

public interface Observer_grid {

	/**
	 * Méthhode permettant de recevoir un message spécifiant une victoire sur la
	 * partie
	 * 
	 * @param winner
	 *            le joueur qui a remporté la partie
	 */
	public void receive_winner_update(Cell_owner winner);

	/**
	 * Méthode permettant de recevoir un message spécifiant un changement d'état
	 * sur le plateau de jeu
	 * 
	 * @param tab
	 *            le plateau de jeu modifié
	 */
	public void receive_update(Cell[][] tab);

	/**
	 * Méthode permettant de recevoir un message spécifiant un changement d'état
	 * dans le plateau de jeu
	 * 
	 * @param player
	 *            le joueur dont le score est modifié
	 */
	public void receive_score_update(Player player);
}
