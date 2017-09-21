package Network;

import java.util.Vector;

public class Game_manager {

	private Vector<Game_network> games;
	private int nb_max_game;

	public Game_manager(int nb_max_game) {
		this.nb_max_game = nb_max_game;
		this.games = new Vector<Game_network>();
	}

	public Game_network add_game(Client_entity e_1, Client_entity e_2) {
		if (this.games.size() < nb_max_game) {
			Game_network game = new Game_network(2, true);
			game.add_player(e_1);
			game.add_player(e_2);
			this.games.add(game);
			return game;
		} else {
			return null;
		}
	}

	public int get_nb_game_launched() {
		return this.games.size();
	}

	public boolean remove_game(Game_network game) {
		if (games.remove(game)) {
			return true;
		} else {
			return false;
		}
	}
}
