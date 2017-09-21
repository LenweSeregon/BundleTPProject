package controler;

import model.Board;
import model.Boat;
import model.Winner;
import Main.Client;

public class Board_controler {

	private Client client_ref;
	private Board board;
	private int last_try_i;
	private int last_try_j;
	private boolean first;
	private boolean has_display_message_splash;
	private boolean listening;
	volatile private int bomb_i_asked;
	volatile private int bomb_j_asked;
	private boolean has_been_touched;
	private String last_message_receive;
	private boolean win_detected;
	private boolean lose_detected;

	/**
	 * Constructeur qui permet de construire le controlleur avec une référence
	 * vers le client et le modele
	 * 
	 * @param board
	 *            le modele
	 * @param client_ref
	 *            la communication du client avec le serveur
	 */
	public Board_controler(Board board, Client client_ref) {
		this.board = board;
		this.client_ref = client_ref;

		this.has_display_message_splash = true;
		this.win_detected = false;
		this.lose_detected = false;
		this.first = false;
		this.listening = false;
		this.bomb_i_asked = -1;
		this.bomb_j_asked = -1;
		this.last_try_i = -1;
		this.last_try_j = -1;
		this.has_been_touched = false;
	}

	/**
	 * La méthode qui permet de lancer le fonctionnement d'un joueur dans sa
	 * boucle infini
	 */
	public void launch_player_thread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Envoi du play obligatoire
				client_ref.send_message("PLAY");
				listening = true;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				while (true) {
					if (listening) {
						last_message_receive = client_ref.receive_message();

						String[] message_split = last_message_receive
								.split(" ");
						if (message_split[0].equals("GO")) {
							int position = Integer.valueOf(message_split[1]);
							if (position == 0) {
								first = true;
								listening = false;
								board.notify_message("C'est à votre tour de bombarder !");
							} else {
								first = false;
								listening = true;
								board.notify_message("C'est à votre tour de subir l'assaut !");
							}

						} else if (message_split[0].equals("WIN")) {
							board.notify_winner(Winner.ENEMY);
						} else if (message_split[0].equals("LOSE")) {
							board.notify_winner(Winner.PLAYER);
						} else {

							int bomb_on_me_x = 0;
							int bomb_on_me_y = 0;
							if (message_split[0].equals("BOMB")) {
								bomb_on_me_x = Integer
										.valueOf(message_split[1]);
								bomb_on_me_y = Integer
										.valueOf(message_split[2]);

							} else if (message_split[0].equals("TOUCHED")) {
								bomb_on_me_x = Integer
										.valueOf(message_split[2]);
								bomb_on_me_y = Integer
										.valueOf(message_split[3]);
								board.notify_enemy_hitted(last_try_i,
										last_try_j);
								if (board.player_has_win()) {
									win_detected = true;
								}

							} else if (message_split[0].equals("MISSED")) {
								bomb_on_me_x = Integer
										.valueOf(message_split[2]);
								bomb_on_me_y = Integer
										.valueOf(message_split[3]);
							}

							board.hit_player_cell(bomb_on_me_x, bomb_on_me_y);
							board.notify_cell_change(
									board.get_player_grid(),
									board.get_enemy_grid().get_cell(
											bomb_on_me_x, bomb_on_me_y));

							Boat b = board.hit_player_boat(bomb_on_me_x,
									bomb_on_me_y);
							if (b != null) {
								board.notify_boat_hitted(
										board.get_player_grid(), b);
								has_been_touched = true;
								if (board.enemy_has_win()) {
									lose_detected = true;
								}
							} else {
								has_been_touched = false;
							}
							has_display_message_splash = false;
							board.notify_message("C'est à votre tour de bombarder !");
							listening = false;
						}

					} else {
						if (win_detected) {
							client_ref.send_message("WIN");
							board.notify_winner(Winner.PLAYER);
						} else if (lose_detected) {
							client_ref.send_message("LOSE");
							board.notify_winner(Winner.ENEMY);
						} else if (!has_display_message_splash) {
							if (has_been_touched) {
								board.notify_message_splash("Vous avez été touché !!!");
							} else {
								board.notify_message_splash("PLOUF");
							}
							has_display_message_splash = true;
						}

						else if (bomb_i_asked != -1 && bomb_j_asked != -1) {
							last_try_i = bomb_i_asked;
							last_try_j = bomb_j_asked;

							if (first) {
								client_ref.send_message("BOMB " + bomb_i_asked
										+ " " + bomb_j_asked);
								first = false;
							} else {
								if (has_been_touched) {
									client_ref
											.send_message("TOUCHED BOMB "
													+ bomb_i_asked + " "
													+ bomb_j_asked);
								} else {
									client_ref
											.send_message("MISSED BOMB "
													+ bomb_i_asked + " "
													+ bomb_j_asked);
								}
							}

							board.hit_enemy_cell(bomb_i_asked, bomb_j_asked);
							board.notify_cell_change(
									board.get_enemy_grid(),
									board.get_enemy_grid().get_cell(
											bomb_i_asked, bomb_j_asked));
							has_been_touched = false;
							bomb_i_asked = bomb_j_asked = -1;
							listening = true;
							board.notify_message("C'est à votre tour de subir l'assaut !");
						}
					}
				}
			}
		}).start();
	}

	/**
	 * Méthode peremttant d'essayer d'attaquer une cellule ennemi
	 * 
	 * @param i
	 *            la cellule i sur l'axe X
	 * @param j
	 *            la cellule j sur l'axe Y
	 */
	public void try_hit_enemy_cell(int i, int j) {
		if (board.can_hit_enemy_cell(i, j) && !listening) {
			bomb_i_asked = i;
			bomb_j_asked = j;
		}
	}

}
