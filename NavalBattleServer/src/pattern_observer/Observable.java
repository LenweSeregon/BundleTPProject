package pattern_observer;

import Network.Server_state;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observeur à notre observable
	 * 
	 * @param ob
	 *            l'observeur que l'on souhaite ajouter
	 */
	public void add_observer(Observer ob);

	/**
	 * Méthode permettant de retirer tout les observeurs de notre observable
	 */
	public void remove_all_observers();

	/**
	 * Méthode permettant de notifier un observeur d'un nouveau message que l'on
	 * souhaite ajouter dans la console
	 * 
	 * @param message
	 *            le message que l'on souhaite ajouter
	 */
	public void notify_console_message(String message);

	/**
	 * Méthode permettant de notifier un observeur d'un changement dans le
	 * nombre de client connectés au serveur
	 * 
	 * @param nb
	 *            le nombre de client connectés
	 */
	public void notify_nb_client(int nb);

	/**
	 * Méthode permettant de notifier un observeur un changement de l'état de
	 * notre serveur
	 * 
	 * @param state
	 *            le nouvel état de notre serveur
	 */
	public void notify_server_state(Server_state state);

	/**
	 * Méthode permettant de notifier un observeur d'un changement dans le
	 * nombre de partie actuellement joué dans notre serveur
	 * 
	 * @param nb
	 *            le nombre partie en cours
	 */
	public void notify_nb_game(int nb);
}
