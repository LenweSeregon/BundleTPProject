package pattern_observer;

import java.util.Vector;

import model.Game_level;

public interface Observable_menu {

	/**
	 * Méthode permettant d'ajouter un observeur de menu sur notre classe
	 * observable de menu
	 * 
	 * @param ob_m
	 *            l'observeur de menu que l'on souhaite ajouter
	 */
	public void add_observer_menu(Observer_menu ob_m);

	/**
	 * Méthode permettant de supprimer tout les observeurs de menu sur notre
	 * classe observable de menu
	 */
	public void remove_all_observers_menu();

	/**
	 * Méthode permettant d'avertir les observeurs du chargements des différents
	 * niveaux
	 * 
	 * @param levels
	 *            les différents niveaux possibles contenu dans un vector
	 */
	public void notify_loaded_levels(Vector<Game_level> levels);

	/**
	 * Méthode permettant d'avertir les observeurs qu'on veut changer de panneau
	 * de level affiché
	 * 
	 * @param level
	 *            le level qu'on a demandé d'afficher
	 */
	public void notify_change_level_choser(String identifier_wanted);
}
