package controler;

import model.Grid;
import utils.DB;

public class Game_controler {

	private Grid model;
	private DB db;

	/**
	 * Constructeur de la classe représentant le controlleur du pattern MVC de
	 * la partie jeu de Picross. Au travers de ce controlleur, on va pouvoir
	 * demander des actions tels que selectionner une case ou demander la
	 * sauvegarde de grille crée
	 * 
	 * @param model
	 *            une référence du modèle du pattern MVC qui contient toutes les
	 *            données
	 * @param db
	 *            une référence de la base de données pour faire les actions de
	 *            sauvegarde
	 */
	public Game_controler(Grid model, DB db) {
		this.model = model;
		this.db = db;
	}

	/**
	 * Méthode permettant de demander de réaliser un clique sur une cellule avec
	 * les coordonnées données
	 * 
	 * @param i
	 *            l'index I sur l'axe des X
	 * @param j
	 *            l'index J sur l'axe des Y
	 */
	public void click_on_tile(int i, int j) {
		boolean new_val = this.model.click_tiles(i, j);
		this.model.notify_tile_change(i, j, new_val);
		if (!this.model.get_creation() && this.model.grid_succeed()) {
			this.db.update_level_succeed(model.get_identifiant());
			this.model.notify_victory();
		}
	}

	/**
	 * Méthode permettant simplement de notifier de la création de la grille
	 * pour notifier la vue des données à afficher
	 */
	public void start() {
		this.model.notify_game_creation(model);
	}

	/**
	 * Méthode permettant de sauvegarder les différentes données qui se trouvent
	 * dans la grille et de les inserer dans la base de données
	 */
	public void save_datas() {
		String id = "level" + db.get_free_identifier();
		String name = model.get_name();
		int nb_line = model.get_nb_line();
		int nb_column = model.get_nb_column();
		boolean succeed = false;

		// Insertion du niveau
		db.insert_game_level(id, name, nb_line, nb_column, succeed);

		boolean[][] tiles = model.get_tiles();
		// Insertion de la solution
		for (int i = 0; i < model.get_nb_line(); i++) {
			for (int j = 0; j < model.get_nb_column(); j++) {
				db.insert_soluce(id, i, j, tiles[i][j]);
			}
		}

		// Insertion des lignes d'indication
		for (int i = 0; i < model.get_nb_line(); i++) {
			int ranged = 0;
			for (int j = 0; j < model.get_nb_column(); j++) {
				if (tiles[i][j]) {
					ranged++;
				} else {
					if (ranged > 0) {
						db.insert_line_indication(id, i, ranged);
						ranged = 0;
					}
				}
			}
			if (ranged > 0) {
				db.insert_line_indication(id, i, ranged);
			}
		}

		// Insertion des colonnes d'indication
		for (int i = 0; i < model.get_nb_column(); i++) {
			int ranged = 0;
			for (int j = 0; j < model.get_nb_line(); j++) {
				if (tiles[j][i]) {
					ranged++;
				} else {
					if (ranged > 0) {
						db.insert_column_indication(id, i, ranged);
						ranged = 0;
					}
				}
			}
			if (ranged > 0) {
				db.insert_column_indication(id, i, ranged);
			}
		}

	}
}
