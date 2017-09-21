package pattern_observer;

import Network.Server_state;

public interface Observer {

	/**
	 * Méthode permettant de mettre à jour la console en affichant un message
	 * que l'on recoit via un pattern observer
	 * 
	 * @param message
	 *            le message que l'on veut ajouter à la console
	 */
	public void update_console_message(String message);

	/**
	 * Méthode permettant de mettre à jour le nombre de client qui se trouve
	 * dans le serveur de manière graphique
	 * 
	 * @param nb
	 *            le nombre de client que l'on veut afficher
	 */
	public void update_nb_client(int nb);

	/**
	 * Méthode permettant de mettre à jour le statut du serveru de manière
	 * graphique
	 * 
	 * @param state
	 *            l'état du serveur
	 */
	public void update_server_state(Server_state state);

	/**
	 * Méthode permettant de mettre à jour le nombre de partie qui sont
	 * actuellement en cours dans le serveur
	 * 
	 * @param nb
	 *            le nombre de partie en cours
	 */
	public void update_nb_game(int nb);
}
