package controler;

import model.Action_possible;
import model.Board;
import model.Direction;
import model.Player_position;

public class Board_controler {
	private Board model;
	private boolean is_running;

	/**
	 * Constructeur du controler qui va regarder toutes les actions réalises sur
	 * le plateau et lancer les bonnes actions en fonction. Dans ce controle
	 * tourne un thread continuellement qui s'occupe des conditions de victoire
	 * et de relancer une manche
	 * 
	 * @param model
	 *            la reference sur le modele
	 */
	public Board_controler(Board model) {
		this.model = model;
		this.is_running = true;

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (is_running) {
					/* check victory */
					if (model.get_score_player_1() >= 7
							&& model.get_score_player_1()
									- model.get_score_player_2() >= 2) {
						model.notify_victory(Player_position.PLAYER_ONE);
						is_running = false;
					} else if (model.get_score_player_2() >= 7
							&& model.get_score_player_2()
									- model.get_score_player_1() >= 2) {
						model.notify_victory(Player_position.PLAYER_TWO);
						is_running = false;
					}

					/* Check no ball */
					if (model.nb_ball_active() == 0) {
						model.place_ball_to_player(model.get_loser_round());

					}

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * Méthode appelé par la vue lorsqu'une touche est appuyé, qui en fonction
	 * du joueur l'ayant demandé et de l'action demandé, va demander au modele
	 * de réaliser l'action
	 * 
	 * @param action
	 *            l'action sous forme d'énumération qui a été demandé
	 * @param player_pos
	 *            le joueur qui a demandé l'action sous forme d'énumération
	 */
	public void ask_action_go(Action_possible action, Player_position player_pos) {
		switch (action) {
		case MOVE_UP:
			switch (player_pos) {
			case PLAYER_ONE:
				if (this.model.player_1_can_move(Direction.UP))
					this.model.move_up_player_1();
				break;
			case PLAYER_TWO:
				if (this.model.player_2_can_move(Direction.UP))
					this.model.move_up_player_2();
				break;
			}
			break;
		case MOVE_DOWN:
			switch (player_pos) {
			case PLAYER_ONE:
				if (this.model.player_2_can_move(Direction.DOWN))
					this.model.move_down_player_1();
				break;
			case PLAYER_TWO:
				if (this.model.player_2_can_move(Direction.DOWN))
					this.model.move_down_player_2();
				break;
			}
			break;
		}
	}

	/**
	 * Méthode appelé lors du relachement de certaines touches configuré dans la
	 * vue et qui donne une action possible
	 * 
	 * @param action
	 *            l'action que le joueur a demandé
	 * @param player_pos
	 *            le joueur qui a demandé à réaliser une action stop
	 */
	public void ask_action_stop(Action_possible action,
			Player_position player_pos) {
		switch (player_pos) {
		case PLAYER_ONE:
			this.model.stop_player_1();
			break;
		case PLAYER_TWO:
			this.model.stop_player_2();
			break;
		}
	}
}
