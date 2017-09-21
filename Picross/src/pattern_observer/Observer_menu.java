package pattern_observer;

import java.util.Vector;

import model.Game_level;

public interface Observer_menu {

	/**
	 * Méthode permettant d'être notifié du chargement des différents niveaux de
	 * jeu dans le modèle
	 * 
	 * @param levels
	 *            les différents niveaux qui ont été chargé
	 */
	public void update_loaded_levels(Vector<Game_level> levels);

	/**
	 * Méthode permettant d'être noitifé d'un changement voulu par l'utilisateur
	 * de l'affichage du niveau qu'il souhaite faire
	 * 
	 * @param identifier_wanted
	 *            l'identifiant du niveau qu'il souhaite afficher
	 */
	public void update_change_level_choser(String identifier_wanted);
}
