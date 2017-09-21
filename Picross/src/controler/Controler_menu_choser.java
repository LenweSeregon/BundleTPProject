package controler;

import model.Game_level;
import model.Game_level_container;

public class Controler_menu_choser {

	private Game_level_container model;

	/**
	 * Constructeur de la classe représentant le controlleur pour la gestion des
	 * choix de niveau dans le menu. La classe s'assure simplement de vérifier
	 * que le changement de niveau est possible est qu'on utilise bien le modele
	 * logique qui a chargé les niveaux dans la base de données
	 * 
	 * @param model
	 *            le modèle logique ou sont stockés les niveaux qui ont été
	 *            chargé via la base de données
	 */
	public Controler_menu_choser(Game_level_container model) {
		this.model = model;
	}

	/**
	 * Méthode appellé pour charger tout les niveaux qui se trouvent dans la
	 * base de données. Une fois le chargement terminé, le controleur demande au
	 * modéle de notifier la vue du chargement
	 */
	public void load_level() {
		this.model.notify_loaded_levels(this.model.get_all_levels());
	}

	/**
	 * Méthode permettant de demander de changer de vue de niveau dans le menu
	 * des choix. Cette fonction s'assure que le niveau que l'on demande est
	 * bien trouvable. Si celui ci est trouvable, il dit au modele de notifier
	 * la vue du modèle à afficher
	 * 
	 * @param index
	 *            l'index que l'on veut afficher
	 */
	public void switch_level_choser_panel(int index) {
		Game_level gl = model.get_game_level_by_index(index);
		if (gl != null) {
			model.notify_change_level_choser(gl.get_identifiant());
		}
	}
}
