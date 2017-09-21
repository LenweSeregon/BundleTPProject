package Network;

import java.net.Socket;

public class Communication_controler {

	private Server server_model;
	private Connection_accepter connection_accepter;

	/**
	 * Constructeur de la classe controlleur du pattern MVC. Cette classe va
	 * réaliser toutes les vérifications venant de l'utilisateur du serveur
	 * avant de réaliser les actions demandés
	 * 
	 * @param server_model
	 *            le modele qui est représenté par le serveur
	 */
	public Communication_controler(Server server_model) {
		this.connection_accepter = null;
		this.server_model = server_model;
	}

	/**
	 * Méthode permettant de lier un accepteur de connexion à notre controlleur
	 * 
	 * @param conn_acc
	 *            l'accepteur de connexion que l'on doit lier
	 */
	public void bind_connection_accepter(Connection_accepter conn_acc) {
		this.connection_accepter = conn_acc;
	}

	/**
	 * Méthode permettant de demander au serveur de valider la connexion d'un
	 * nouveau utilisateur. Cette méthode va s'assurer de ne pas surcharger le
	 * serveur et de donner au client qui vient de se connecter un identifiant
	 * 
	 * @param socket_communication
	 *            la socket de communication que l'on va donner à notre entité
	 *            de client pour qu'il puisse communiqué
	 */
	public void ask_for_new_connexion(Socket socket_communication) {
		Client_entity new_client = server_model.add_client();
		if (new_client != null) {
			server_model.notify_console_message("Nouvelle connexion . . .");
			new_client.bind_socket(socket_communication);
			// On recoit la connexion du joueur
			String msg = new_client.receive_message();
			server_model.notify_console_message("Message connexion client = "
					+ msg);
			server_model.notify_nb_client(server_model.get_nb_clients());

			// On lui attribue son identifiant
			new_client.send_identifier_to_client();
			ask_to_start_new_game();
		} else {
			server_model
					.notify_console_message("Connexion impossible, serveur complet . . .");
		}
	}

	/**
	 * Méthode permettant de demander le lancement d'une nouvelle partie dans le
	 * serveur
	 */
	public void ask_to_start_new_game() {
		if (server_model.can_start_game()) {
			server_model.launch_game();
			server_model
					.notify_console_message("Lancement nouvelle partie . . .");
			server_model.notify_nb_game(server_model.get_game_launch());
		}
	}

	/**
	 * Méthode permettant de demander la suppression d'un utilisateur dans le
	 * serveur via son identifiant
	 * 
	 * @param identifier
	 *            l'identifiant du client à supprimer
	 */
	public void ask_for_remove_client(String identifier) {
		if (server_model.remove_client(identifier)) {
			server_model
					.notify_console_message("Déconnexion de l'utilisateur : "
							+ identifier);
			server_model.notify_nb_client(server_model.get_nb_clients());
		} else {
			server_model
					.notify_console_message("Déconnexion impossible, utilisateur inconnu");
		}
	}

	/**
	 * Méthode permettant de demander le démarrage du serveur
	 */
	public void ask_to_open_server() {
		if (server_model.run_server()) {
			server_model.notify_console_message("Lancement du serveur . . .");
			connection_accepter.bind_socket(server_model.get_server_socket());
			server_model.notify_server_state(server_model.get_server_state());

			new Thread(connection_accepter).start();
		} else {
			server_model
					.notify_console_message("Impossible de lancer le serveur . . .");
		}

	}

	/**
	 * Méthode permettant de demander la fermeture du serveur
	 */
	public void ask_to_close_server() {
		if (server_model.stop_server()) {
			this.connection_accepter.set_is_running(false);
			server_model.notify_console_message("Arrêt du serveur . . .");
			server_model.notify_server_state(server_model.get_server_state());
		} else {
			server_model
					.notify_console_message("Impossible d'arrêter du serveur . . .");
		}
	}

	/**
	 * Méthode permettant de demander la pause du serveur
	 */
	public void ask_to_pause_server() {
		if (server_model.pause_server()) {
			this.connection_accepter.set_is_paused(true);
			server_model.notify_console_message("Pause du serveur . . .");
			server_model.notify_server_state(server_model.get_server_state());
		} else {
			server_model
					.notify_console_message("Impossible de mettre en pause le serveur . . .");
		}

	}

	/**
	 * Méthode permettant de demander la reprise du serveur
	 */
	public void ask_to_resume_server() {
		if (server_model.resume_server()) {
			connection_accepter.set_is_paused(false);
			server_model.notify_console_message("Reprise du serveur . . . ");
			server_model.notify_server_state(server_model.get_server_state());
		} else {
			server_model
					.notify_console_message("Reprise impossible du serveur . . . ");
		}
	}
}
