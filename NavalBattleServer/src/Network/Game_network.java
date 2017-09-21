package Network;

import java.util.ArrayList;
import java.util.Vector;

import pattern_observer.Observable;
import pattern_observer.Observer;

public class Game_network implements Runnable, Observable {

	private Vector<Client_entity> players;
	private boolean is_running;
	private boolean allow_add_during_game;
	private int current_broadcaster;
	private int max_player_game;

	private ArrayList<Observer> observers;

	private static int current_game_launched = 0;
	private int id_game;

	public Game_network(int max_player, boolean allow_add_during_game) {
		this.players = new Vector<Client_entity>();
		this.max_player_game = max_player;
		this.allow_add_during_game = allow_add_during_game;
		this.is_running = false;
		this.current_broadcaster = -1;
		this.id_game = current_game_launched++;

		this.observers = new ArrayList<Observer>();
	}

	public boolean add_player(Client_entity player) {
		if (players.size() < max_player_game) {
			players.addElement(player);
			return true;
		} else {
			return false;
		}
	}

	public void broadcast_to_all_player(Client_entity broadcaster,
			String message) {
		for (Client_entity c : players) {
			if (c != broadcaster) {
				c.send_message(message);
			}
		}
	}

	public Client_entity get_broadcaster_turn() {
		for (Client_entity c : players) {
			if (c.has_to_broadcast()) {
				return c;
			}
		}
		return null;
	}

	@Override
	public void run() {
		// Préparation ready joueur
		for (Client_entity c : players) {
			c.set_waiting_to_player(true);
		}
		boolean can_start = true;
		Thread[] threads = new Thread[players.size()];
		Ask_ready[] readys = new Ask_ready[players.size()];
		int i = 0;
		for (Client_entity c : players) {
			readys[i] = new Ask_ready(c);
			threads[i] = new Thread(readys[i]);
			threads[i].start();
			i++;
		}

		// Wait all thread
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (Ask_ready a : readys) {
			if (!a.is_accept()) {
				can_start = false;
			}
		}

		// Lancement partie
		if (can_start) {
			this.notify_console_message("Tous les joueurs sont prêtes !");

			// Attribution des tours de jeu

			for (Client_entity c : players) {
				c.set_must_broadcast(true);
			}

			int j = 0;
			for (Client_entity c : players) {
				c.send_message("GO " + String.valueOf(j++));
			}

			// Initialisation des tours
			this.is_running = true;
			this.current_broadcaster = 0;
			this.players.get(current_broadcaster).set_must_broadcast(true);

			// Execution
			while (is_running) {
				// Turn to :
				System.out.println("Is waiting broadcaster message from : "
						+ get_broadcaster_turn().get_identifier());
				Client_entity broadcaster = this.get_broadcaster_turn();
				String message_from_broadcaster = broadcaster.receive_message();

				System.out.println("Message from broadcaster = "
						+ message_from_broadcaster);

				// Broadcast to other
				this.broadcast_to_all_player(broadcaster,
						message_from_broadcaster);

				// Switch to next player
				this.current_broadcaster = ((this.current_broadcaster + 1) % this.players
						.size());
				broadcaster.set_must_broadcast(false);
				players.get(current_broadcaster).set_must_broadcast(true);
			}
		}
	}

	@Override
	public void add_observer(Observer ob) {
		observers.add(ob);
	}

	@Override
	public void remove_all_observers() {
		observers.clear();
	}

	@Override
	public void notify_console_message(String message) {
		for (Observer ob : observers) {
			ob.update_console_message(message);
		}
	}

	@Override
	public void notify_nb_client(int nb) {
	}

	@Override
	public void notify_server_state(Server_state state) {
	}

	@Override
	public void notify_nb_game(int nb) {
	}

	private class Ask_ready implements Runnable {

		private Client_entity ref_client;
		private boolean accept;

		public Ask_ready(Client_entity player) {
			ref_client = player;
			accept = false;
		}

		@Override
		public void run() {
			String receive = ref_client.receive_message();
			if (receive.equals("PLAY")) {
				accept = true;
			} else {
				accept = false;
			}
		}

		public boolean is_accept() {
			return accept;
		}
	}
}
