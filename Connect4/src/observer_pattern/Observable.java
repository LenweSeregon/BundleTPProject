package observer_pattern;

import model.Cell;
import model.Owner;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observer à notre objet observable
	 * 
	 * @param obs
	 *            l'observer que l'on souhaite ajouter
	 */
	public void add_observer(Observer obs);

	/**
	 * Méthode permettant de supprimer tout les observers de notre observable
	 */
	public void remove_observer();

	/**
	 * Méthode permettant de notifier à la vue un changement d'état de la grille
	 * 
	 * @param board
	 *            la grille avec un nouvel état
	 */
	public void notify_observer_grid_change(Cell[][] board);

	/**
	 * Méthode permettant de notifier un changement de couleur ou de position
	 * sur la piece de référence
	 * 
	 * @param piece
	 *            la piéce de référence qui subit un changement
	 * @param pos
	 *            la position de cette piece
	 */
	public void notify_next_piece_move(Cell piece, int pos);

	/**
	 * Méthode permettant de notifier un message à envoyer au panneau
	 * d'affichage de la vue
	 * 
	 * @param message
	 *            le message que l'on souhaite afficher
	 */
	public void notify_message_box(String message);

	/**
	 * Méthode permettant de notifier la victoire de la partie
	 * 
	 * @param owner
	 *            le gagnant de la partie
	 */
	public void notify_victory(Owner owner);
}
