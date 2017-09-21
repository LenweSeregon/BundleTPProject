package mvc_controler;

import mvc_model.Grid;
import enums.Owner;

public class Grid_controler {

	private Grid model;
	private boolean ai_mode;
	private AI_player ai;

	/**
	 * Constructeur de la classe représentant le controlleur du modèle MVC. La
	 * classe va simplement dicter les différents ordre au modele que celui ci
	 * ecoutera.
	 * 
	 * @param model
	 *            le modele que l'on souhaite attacher à notre grille
	 */
	public Grid_controler(Grid model) {
		this.model = model;
		this.ai_mode = this.model.is_ai_mode();
		this.ai = new AI_player(Owner.WHITE, model, this);
	}

	/**
	 * Méthode permettant de sauvegarder la partie actuel à un instant T
	 */
	public void save_game() {
		this.model.save_options("src/resources/files/game.xml");
	}

	/**
	 * Méthode permettant de charger une partie actuel à un instant T
	 */
	public boolean load_game() {
		if (!this.model.load_game("/resources/files/game.xml")) {
			return false;
		}
		this.model.calcul_possible_hit();
		this.model.notify_grid_change(model.get_tiles());
		this.model.notify_change_player(model.get_player());
		return true;
	}

	/**
	 * Méthode étant appelé lorsqu'un click à été detecté sur une des cases du
	 * plateau. Cette méthode va s'assurer de toutes les vérifications engendrés
	 * par ce click
	 * 
	 * @param i
	 *            l'index sur l'axe X de la case cliquée
	 * @param j
	 *            l'index sur l'axe Y de la case cliquée
	 */
	public void click_tile(int i, int j) {
		if (this.model.get_tile(i, j).get_possible_hit()) {
			this.model.get_tile(i, j).set_owner(model.get_player());
			this.model.click_on_tile(i, j);
			this.model.next_player();
			this.model.calcul_possible_hit();
			this.model.notify_grid_change(this.model.get_tiles());
			this.model.notify_change_player(this.model.get_player());

			if (ai_mode && model.get_player() == ai.get_turn()) {
				this.ai.play();
			}

			if (this.model.get_nb_possiblity() == 0) {
				model.next_player();
				model.calcul_possible_hit();
				if (model.get_nb_possiblity() == 0) {

					if (model.get_nb_piece_black() > model.get_nb_piece_white()) {
						model.notify_winner(Owner.BLACK);
					} else if (model.get_nb_piece_white() > model
							.get_nb_piece_black()) {
						model.notify_winner(Owner.WHITE);
					} else {
						model.notify_winner(Owner.NONE);
					}
				} else {

					this.model.notify_no_hit(true);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							model.notify_no_hit(false);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							model.notify_change_player(model.get_player());
							model.notify_grid_change(model.get_tiles());
						}
					}).start();
				}
			}
		}
	}
}
