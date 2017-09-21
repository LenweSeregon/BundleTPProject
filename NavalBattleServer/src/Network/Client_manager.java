package Network;

import java.util.Vector;

public class Client_manager {

	private Vector<Client_entity> clients;
	private int nb_client_maximum;
	private String identifier_str_giver;
	private int identifier_int_giver;

	/**
	 * Constructeur de classe gestionnaire des clients. Cette classe va
	 * s'assurer d'ajouter des nouveaux clients si il reste de la place et de
	 * les stocker permettant de récupérer les utilisateurs à n'importe quel
	 * moment
	 * 
	 * @param nb_client_maximum
	 *            le nombre de client maximum connecté simultanément qui doit
	 *            etre accepté
	 */
	public Client_manager(int nb_client_maximum) {
		this.nb_client_maximum = nb_client_maximum;
		this.clients = new Vector<Client_entity>();
		this.identifier_str_giver = "client";
		this.identifier_int_giver = 0;
	}

	/**
	 * Méthode permettant de récupérer le nombre de personne connecté simultané
	 * sur le serveur
	 * 
	 * @return le nombre de personne connecté
	 */
	public int get_nb_client_connected() {
		return this.clients.size();
	}

	/**
	 * Méthode permettant de récupérer le nombre de personne connecté simultané
	 * sur le serveur et qui sont dans un statut d'attente pour une partie
	 * 
	 * @return le nombre de personne connecté attendant une partie
	 */
	public int get_nb_client_waiting_to_play() {
		int nb = 0;
		for (Client_entity client : clients) {
			if (client.get_is_waiting()) {
				nb++;
			}
		}
		return nb;
	}

	/**
	 * Méthode permettant de récupérer les deux premiers joueurs que le
	 * gestionnaire de client trouve qui sont en recherche de partie
	 * 
	 * @return un vecteur de 2 personnes si il y a 2 personnes prêtes à jouer,
	 *         null sinon
	 */
	public Vector<Client_entity> get_two_clients_to_play() {

		Vector<Client_entity> players = new Vector<Client_entity>();
		for (Client_entity c : clients) {
			if (c.get_is_waiting()) {
				System.out.println("Is waiting");
				players.add(c);
			}
			if (players.size() == 2) {
				break;
			}
		}

		if (clients.size() == 2) {
			clients.get(0).set_waiting_to_player(false);
			clients.get(1).set_waiting_to_player(false);
			return players;
		} else {
			return null;
		}
	}

	/**
	 * Méthode permettant d'ajouter un client à notre gestionnaire de client
	 * 
	 * @return un client si on a réussi à en ajouter un, null sinon
	 */
	public Client_entity add_client() {
		if (clients.size() <= nb_client_maximum) {
			clients.add(new Client_entity(identifier_str_giver
					+ String.valueOf(identifier_int_giver++)));
			return clients.get(clients.size() - 1);
		} else {
			return null;
		}
	}

	/**
	 * Méthode permettant de supprimer un client du serveur en fournissant une
	 * référence directe sur le client
	 * 
	 * @param client
	 *            le client que l'on souhaite supprimer
	 * @return vrai si le client a été supprimé (il existe), faux sinon
	 */
	public boolean remove_client(Client_entity client) {
		if (clients.remove(clients)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de supprimer un client du serveur en fournissant
	 * l'identifiant du client
	 * 
	 * @param client
	 *            l'identifiant du client que l'on souhaite supprimer
	 * @return vrai si le client a été supprimé (il existe), faux sinon
	 */
	public boolean remove_client_from_identifier(String identifier) {
		Client_entity to_remove = null;
		for (Client_entity c : clients) {
			if (c.get_identifier().equals(identifier)) {
				to_remove = c;
			}
		}
		if (to_remove != null) {
			clients.remove(to_remove);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de récupérer un client connecté au serveur via son
	 * identifiant
	 * 
	 * @param identifier
	 *            l'identifiant du client
	 * @return le client si celui ci existe, null sinon
	 */
	public Client_entity get_client_from_identifier(String identifier) {
		for (Client_entity c : clients) {
			if (c.get_identifier().equals(identifier)) {
				return c;
			}
		}
		return null;
	}
}
