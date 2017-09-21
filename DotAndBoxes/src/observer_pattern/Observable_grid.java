package observer_pattern;

import models.Cell;
import models.Cell_owner;
import models.Player;

public interface Observable_grid {

	/**
	 * Méthode permettant d'ajouter un observer à notre objet observable
	 * 
	 * @param obs
	 *            l'observer que l'on souhaite ajouter
	 */
	public void add_observer(Observer_grid obs);

	/**
	 * Méthode permettant de supprimer tout les observers de notre observable
	 */
	public void remove_observer();

	/**
	 * Méthode permettant d'envoyer un message de notification à tous les
	 * observeurs pour specifier de la victoire
	 * 
	 * @param winner
	 *            le joueur qui a remporté la victoire
	 */
	public void notify_win_observer(Cell_owner winner);

	/**
	 * Méthode permettant d'envoyer un message de notification à tous les
	 * observeurs pour spécifier d'un changement d'état dans le plateau
	 * 
	 * @param tab
	 *            le plateau modifié qu'il faut réinterpreter
	 */
	public void notify_observer(Cell[][] tab);

	/**
	 * Méthode permettant d'envoyer un message de notification à tous les
	 * observeurs pour spécifier d'un changement d'état dans le plateau
	 * 
	 * @param player
	 *            le joueur dont le score est modifié
	 */
	public void notify_score_increase(Player player);
}
