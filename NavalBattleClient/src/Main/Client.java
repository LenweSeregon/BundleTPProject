package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import model.Action;
import view.Client_console_view;

//InetAddress.getLocalHost()

public class Client implements Runnable {

	private String identifier;
	private Socket socket_client;
	private BufferedReader in;
	private PrintWriter out;
	private InetAddress server_address;
	private int server_port;

	private boolean is_waiting_command;
	private boolean must_send_command;
	private boolean communication_active;
	private Action action_ask;

	private int order;

	private Client_console_view view;

	/**
	 * Constructeur de la classe client permettant de définir toutes les
	 * variables nécessaire à la communication
	 * 
	 * @param address
	 *            l'adresse du serveur sur lequel on va se connecter
	 * @param port
	 *            le port du serveur sur lequel on va se connecter
	 */
	public Client(InetAddress address, int port) {
		identifier = null;

		this.server_address = address;
		this.server_port = port;
		this.view = null;
		this.is_waiting_command = false;
		this.must_send_command = false;
		this.communication_active = false;
		this.action_ask = Action.NONE;
		this.order = -1;
	}

	/**
	 * Méthode permettant de se connecter au serveur que l'on a configurer dans
	 * le constructeur
	 * 
	 * @throws IOException
	 *             si la connexion n'est pas possible, on lance une exception
	 */
	public void connect_to_server() throws IOException {
		socket_client = new Socket(server_address, server_port);
		this.communication_active = true;
	}

	/**
	 * Méthode permettant de savoir l'ordre de jeu du joueur
	 * 
	 * @return son ordre de jeu
	 */
	public int get_order() {
		return this.order;
	}

	/**
	 * Méthode permettant de définir l'ordre de jeu du joueur
	 * 
	 * @param i
	 *            l'ordre de jeu
	 */
	public void set_order(int i) {
		this.order = i;
	}

	/**
	 * Méthode permettant de lancer un message de connexion et de récupérer
	 * l'identifiant d'un joueur
	 */
	public void communication_connection() {
		send_message("Connexion");
		String receive = receive_message();
		this.identifier = receive;
	}

	/**
	 * Méthode permettant de lier une console au client pour faire des
	 * affichages dans la console
	 * 
	 * @param console
	 *            la console du client
	 */
	public void bind_console(Client_console_view console) {
		this.view = console;
	}

	/**
	 * Méthode permettant de dire au client si il doit ou non envoyer un message
	 * 
	 * @param b
	 *            la valeur booelenne pour savoir si il doit envoyer un message
	 */
	public void set_must_send(boolean b) {
		this.must_send_command = b;
		if (b) {
			set_waiting_command(false);
		}
	}

	/**
	 * Méthode permettant de savoir si le client doit envoyer un message
	 * 
	 * @return vrai si il doit envoyer un message, faux sinon
	 */
	public boolean must_send() {
		return this.must_send_command;
	}

	/**
	 * Méthode permettant de dire au client si il doit ou non attendre un
	 * message
	 * 
	 * @param b
	 *            la valeur booelenne pour savoir si il doit attendre un message
	 */
	public void set_waiting_command(boolean b) {
		this.is_waiting_command = b;
		if (b) {
			set_must_send(false);
		}
	}

	/**
	 * Méthode permettant de savoir si le client doit attendre un message
	 * 
	 * @return vrai si il doit envoyer un message, faux sinon
	 */
	public boolean waiting_command() {
		return this.is_waiting_command;
	}

	/**
	 * Méthode permettant de recevoir un message, et renvoie ce messsage à la
	 * fin de la fonction
	 * 
	 * @return le message recu du serveur
	 */
	public String receive_message() {
		try {
			in = new BufferedReader(new InputStreamReader(
					socket_client.getInputStream()));
			view.add_message("Attente d'un message . . .");
			String message = in.readLine();
			view.add_message("Message venant du serveur : " + message);
			return message;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	/**
	 * Méthode permettant d'envoyer un message au serveur
	 * 
	 * @param message
	 *            le message allant au serveur
	 */
	public void send_message(String message) {
		try {
			System.out.println("Envoi au serveur " + message);

			new Thread(new Runnable() {
				@Override
				public void run() {
					view.add_message("Envoi au serveur : " + message);
				}
			});

			out = new PrintWriter(socket_client.getOutputStream());
			if (identifier != null) {
				out.println(message);
			} else {
				out.println(message);
			}

			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode permettant de dire au client de quitter le serveur
	 */
	public void exit_server() {

		try {
			send_message(identifier + " EXIT");
			socket_client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (communication_active) {
			// Reception de commande
			if (is_waiting_command) {
				String message = receive_message();
				System.out.println("Message du serveur = " + message);
			}

			// Envoi de commande
			else if (must_send_command) {
				if (action_ask != Action.NONE) {
					send_message(action_ask.toString());
				}
			}
		}
	}
}
