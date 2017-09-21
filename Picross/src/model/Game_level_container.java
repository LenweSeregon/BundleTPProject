package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import pattern_observer.Observable_menu;
import pattern_observer.Observer_menu;
import utils.DB;

public class Game_level_container implements Observable_menu {

	private Vector<Game_level> levels;

	private ArrayList<Observer_menu> observers_menu;

	/**
	 * Constructeur de la classe représentant un conteneur des différents
	 * niveaux possibles étant présents dans le jeu. Cette classe permet un
	 * accès facile en fournissant une interface pour obtenir des niveau
	 * facilement, les charger et les sauvegarder
	 */
	public Game_level_container() {

		levels = new Vector<Game_level>();
		observers_menu = new ArrayList<Observer_menu>();
	}

	/**
	 * Méthode permettant de récupérer tout les niveaux qui ont été chargé dans
	 * la classe et qui sont stockés dans un vecteur
	 * 
	 * @return un vecteur contenant tout les niveaux dans l'ordre ou il les a
	 *         chargé
	 */
	public Vector<Game_level> get_all_levels() {
		return this.levels;
	}

	/**
	 * Méthode permettant de récupérer via un identifiant un niveau de jeu si
	 * celui ci existe. Une référence vers le niveau est directement envoyé en
	 * retour de la fonction
	 * 
	 * @param identifier
	 *            l'identifiant du niveau que l'on cherche
	 * @return une référence sur le niveau si celui ci existe, null sinon
	 */
	public Game_level get_game_level_by_identifier(String identifier) {

		for (Game_level lvl : levels) {
			if (lvl.get_identifiant().equals(identifier)) {
				return lvl;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de récupérer via un index un niveau de jeu si celui ci
	 * existe. Une référence vsr le niveau est directement envoyé en retour de
	 * la fonction
	 * 
	 * @param index
	 *            l'index auquel on veut avoir accès
	 * @return une référence sur le niveau si celui ci existe, null sinon
	 */
	public Game_level get_game_level_by_index(int index) {
		if (index < 0 || index >= levels.size()) {
			return null;
		} else {
			return levels.get(index);
		}
	}

	/**
	 * Méthode qui va charger les différents levels disponibles dans le jeu
	 */
	public void load_all_levels(DB data_base) {
		try {
			ResultSet res = data_base.exec("select * from Game_level");
			if (res != null) {
				while (res.next()) {
					String id = res.getString(1);
					String name = res.getString(2);
					int nb_line = res.getInt(3);
					int nb_column = res.getInt(4);
					boolean done = res.getBoolean(5);

					levels.addElement(new Game_level(id, name, nb_line,
							nb_column, done));
				}
			}
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void add_observer_menu(Observer_menu ob_m) {
		observers_menu.add(ob_m);
	}

	@Override
	public void remove_all_observers_menu() {
		observers_menu.clear();
	}

	@Override
	public void notify_loaded_levels(Vector<Game_level> levels) {
		for (Observer_menu ob : observers_menu) {
			ob.update_loaded_levels(this.levels);
		}
	}

	@Override
	public void notify_change_level_choser(String identifier_wanted) {
		for (Observer_menu ob : observers_menu) {
			ob.update_change_level_choser(identifier_wanted);
		}
	}

}
