package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Vector;

import pattern_observer.Observable;
import pattern_observer.Observer;

public class Server implements Observable {

	private ServerSocket server_socket;

	private int port;
	private boolean is_stop;
	private boolean is_pause;
	private Server_state state;
	private Client_manager client_manager;
	private Game_manager game_manager;

	private ArrayList<Observer> observers;

	/**
	 * Constructeur de la classe Serveur, cette classe permet de mettre en place
	 * un environnement reseau ou un serveur est accessible en localhost par
	 * différents clients. Le serveur a pour vocation finale d'accueilir un
	 * nombre de joueur déterminé, et de pouvoir gérer différentes parties en
	 * même temps
	 * 
	 * @param port
	 *            le port sur lequel le serveur doit écouter
	 * @param nb_client_maximum
	 *            le nombre maximum de joueur qui peuvent être connecté en même
	 *            temps.
	 */
	public Server(int port, int nb_client_maximum) {
		this.port = port;
		this.client_manager = new Client_manager(nb_client_maximum);
		this.game_manager = new Game_manager(10);
		this.is_pause = false;
		this.is_stop = true;
		this.state = Server_state.STOPPED;
		this.observers = new ArrayList<Observer>();
	}

	/**
	 * Méthode permettant d'ajouter un client à notre serveur en demander au
	 * Client_manager de créer un nouveau joueur.
	 * 
	 * @return le client ajouté si il reste de la classe sur le serveur, null
	 *         sinon
	 */
	public Client_entity add_client() {
		return client_manager.add_client();
	}

	/**
	 * Méthode permettant de supprimer un client du serveur via son identifier
	 * 
	 * @param identifier
	 *            l'identifiant du clietn
	 * @return vrai si le client a été supprimé du serveur (il existait), faux
	 *         sinon
	 */
	public boolean remove_client(String identifier) {
		return this.client_manager.remove_client_from_identifier(identifier);
	}

	/**
	 * Méthode permettant de lancer le serveur en lui affectant les bonnes
	 * valeurs
	 * 
	 * @return vrai si le serveur a été lancé, faux sinon
	 */
	public boolean run_server() {
		try {
			server_socket = new ServerSocket(port);
			this.is_stop = false;
			this.is_pause = false;
			this.state = Server_state.RUNNING;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Méthode permettant de stopper le serveur en lui affectants les bonnes
	 * valeurs
	 * 
	 * @return vrai si le serveur a été arrété, faux sinon
	 */
	public boolean stop_server() {
		try {
			if (!server_socket.isClosed()) {
				server_socket.close();
			}
			this.is_stop = true;
			this.is_pause = false;
			this.state = Server_state.STOPPED;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Méthode permettant de mettre le serveur en pause en lui affectant les
	 * bonnes valeurs
	 * 
	 * @return vrai si le serveur a été mis en pause, faux sinon
	 */
	public boolean pause_server() {
		if (!this.is_stop) {
			this.is_pause = true;
			this.state = Server_state.PAUSED;
			return true;
		}
		return false;
	}

	/**
	 * Méthode permettant de reprendre le serveur la ou il s'était arrété en lui
	 * affectant les bonnes valeurs
	 * 
	 * @return vrai si le serveur a repris, faux sinon
	 */
	public boolean resume_server() {
		if (!this.is_stop) {
			this.is_pause = false;
			this.state = Server_state.RUNNING;
			return true;
		}
		return false;
	}

	/**
	 * Méthode permettant de savoir si le serveur est capable de faire démarrer
	 * une nouvelle partie à des clients. C'est à dire qu'il y a au moins 2
	 * joueurs qui sont en recherche de partie
	 * 
	 * @return vrai si le serveur peut lancer une nouvelle partie, faux sinon
	 */
	public boolean can_start_game() {
		return this.client_manager.get_nb_client_waiting_to_play() >= 2;
	}

	/**
	 * Méthode permettant de lancer une partie, cette méthode suppose d'être
	 * appelé aprés avoir vérifier qu'il y avait assez de joueurs pour démarrer
	 * une partie
	 */
	public void launch_game() {
		Vector<Client_entity> clients = this.client_manager
				.get_two_clients_to_play();

		Game_network new_game = this.game_manager.add_game(clients.get(0),
				clients.get(1));
		if (new_game != null) {
			new Thread(new_game).start();
		}

		for (Observer ob : observers) {
			new_game.add_observer(ob);
		}
	}

	public int get_nb_clients() {
		return this.client_manager.get_nb_client_connected();
	}

	public Server_state get_server_state() {
		return this.state;
	}

	public int get_game_launch() {
		return this.game_manager.get_nb_game_launched();
	}

	/**
	 * Méthode permettant de récupérer le socket de communication vers le
	 * serveur
	 * 
	 * @return le socket du serveur
	 */
	public ServerSocket get_server_socket() {
		return this.server_socket;
	}

	/**
	 * Méthode static de la classe serveur permettant de savoir si il est
	 * possible de créer un socket de communicatin avec un port
	 * 
	 * @param port
	 *            le port que l'on veut tester pour l'ouverture d'un serveur
	 * @return vrai si le serveur peut etre ouvert avec le port en paramétre,
	 *         faux sinon
	 */
	public static boolean is_port_free(int port) {
		try {
			ServerSocket socket = new ServerSocket(port);
			socket.close();
		} catch (IllegalArgumentException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void add_observer(Observer ob) {
		this.observers.add(ob);
	}

	@Override
	public void remove_all_observers() {
		this.observers.clear();
	}

	@Override
	public void notify_console_message(String message) {
		for (Observer ob : observers) {
			ob.update_console_message(message);
		}
	}

	@Override
	public void notify_nb_client(int nb) {
		for (Observer ob : observers) {
			ob.update_nb_client(nb);
		}
	}

	@Override
	public void notify_server_state(Server_state state) {
		for (Observer ob : observers) {
			ob.update_server_state(state);
		}
	}

	@Override
	public void notify_nb_game(int nb) {
		for (Observer ob : observers) {
			ob.update_nb_game(nb);
		}
	}
}
