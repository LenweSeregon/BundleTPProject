package view;

import javax.swing.JFrame;

import model.Game_level_container;
import model.Grid;
import utils.DB;
import utils.Datas_container;
import controler.Controler_menu_choser;
import controler.Game_controler;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private int width;
	private int height;

	private DB db;
	private Datas_container datas;

	/**
	 * Constructeur de la classe représentant la fenetre principale du jeu d(ou
	 * vont être lancé les différents panels
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Window(int width, int height) {
		db = new DB("//localhost:3306/Picross", "root", "root");
		boolean connection_ok = db.connect();
		if (!connection_ok) {
			System.out.println("Erreur connexion");
		}

		datas = new Datas_container(db);
		datas.load_datas();

		this.width = width;
		this.height = height;

		this.setTitle("PiCross");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.launch_main_menu();
	}

	/**
	 * Méthode permettant de lancer le menu principal
	 */
	public void launch_main_menu() {
		Main_menu menu = new Main_menu(width, height, this);
		this.setContentPane(menu);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		menu.setFocusable(true);
		menu.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer la fenetre de creation de grille
	 */
	public void launch_config_creation() {
		Menu_configure_creation menu = new Menu_configure_creation(width,
				height, this, db);

		this.setContentPane(menu);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		menu.setFocusable(true);
		menu.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le menu de validation de suppression de
	 * niveau
	 * 
	 * @param id
	 *            l'identifiant du niveau que l'on veut supprimer
	 */
	public void launch_validate_delete(String id) {
		Validate_suppression_panel validate = new Validate_suppression_panel(
				id, width, height, this, db);

		this.setContentPane(validate);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		validate.setFocusable(true);
		validate.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le menu de selection de niveau pour jouer
	 */
	public void launch_menu_choser() {

		datas.load_datas();

		Game_level_container ctn = datas.get_levels_container();
		Controler_menu_choser ctr = new Controler_menu_choser(ctn);
		Menu_level_choser view = new Menu_level_choser(width, height, ctr, this);
		ctn.add_observer_menu(view);
		ctr.load_level();

		this.setContentPane(view);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le menu de création d'une grille
	 * 
	 * @param name
	 *            le nom de la grille que l'on va créer
	 * @param nb_line
	 *            le nombre de ligne de la grille que l'on va créer
	 * @param nb_column
	 *            le nombre de colonne de la grille que l'on va créer
	 */
	public void launch_level_creation(String name, int nb_line, int nb_column) {

		Grid grid = new Grid(name, nb_line, nb_column);
		Game_controler ctrl = new Game_controler(grid, db);
		Board_view view = new Board_view(width, height, this, ctrl, true);
		grid.add_observer_menu(view);
		ctrl.start();

		this.setContentPane(view);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer la fenetre qui va afficher le jeu en lui
	 * meme avec un niveau que l'on a choisis
	 * 
	 * @param identifiant
	 *            l'identifiant du niveau que l'on a choisit
	 */
	public void launch_level_chosen(String identifiant) {

		Grid grid = new Grid(datas.get_levels_container()
				.get_game_level_by_identifier(identifiant),
				datas.get_line_indexes_container(),
				datas.get_column_indexes_container(), db);

		Game_controler ctrl = new Game_controler(grid, db);
		Board_view view = new Board_view(width, height, this, ctrl, false);
		grid.add_observer_menu(view);
		ctrl.start();

		this.setContentPane(view);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de fermer la fenetre et de couper tous les eventuels
	 * threads
	 */
	public void exit() {
		this.setVisible(false);
		this.dispose();
		System.exit(0);
		db.close();
	}
}
