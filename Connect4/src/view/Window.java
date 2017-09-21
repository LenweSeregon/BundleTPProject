package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import model.Grid;
import model.Play_mode;
import controller.Grid_controller;

public class Window extends JFrame {

	private Main_menu main_menu;
	private Config_menu config_menu;

	/**
	 * Construction de la classe Window; elle permet de l'initialiser et de la
	 * configurer, et lance par défaut l'affichage du menu principal
	 */
	public Window() {

		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.main_menu = new Main_menu(this);

		this.setTitle("Puissance 4");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setContentPane(this.main_menu);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Méthode permettant de lancer le menu principale du jeu
	 */
	public void launch_main_menu() {
		this.main_menu = new Main_menu(this);
		this.setContentPane(main_menu);
		this.pack();
	}

	/**
	 * Méthode permettant de lancer le panneau de configuration pour lancer une
	 * partie
	 * 
	 * @param mode
	 *            le mode de jeu que l'on souhaite lancer entre contre humain et
	 *            contre ia
	 */
	public void launch_config_menu(Play_mode mode) {
		this.config_menu = new Config_menu(this, mode);

		this.setContentPane(this.config_menu);
		this.pack();
	}

	/**
	 * Méthode permettant de lancer une partie contre une intelligence
	 * artificielle
	 * 
	 * @param nb_line
	 *            le nombre de ligne de la grille de jeu
	 * @param nb_column
	 *            le nombre de colonne de la grille de jeu
	 * @param win_hit
	 *            le nombre de coup gagnant
	 * @param suicide
	 *            le mode suicide est-il activé
	 * @param player_color
	 *            la couleur de notre joueur
	 */
	public void launch_versus_ai(int nb_line, int nb_column, int win_hit,
			boolean suicide, Color player_color) {

		Grid grid = new Grid(nb_line, nb_column, 70, win_hit, suicide, true);
		Grid_controller controller = new Grid_controller(grid);
		Grid_view view = new Grid_view(nb_line, nb_column, 70, controller,
				this, player_color);

		grid.add_observer(view);
		this.setContentPane(view);
		this.pack();
		view.setVisible(true);
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer une partie contre un autre humain
	 * 
	 * @param nb_line
	 *            le nombre de ligne de la grille de jeu
	 * @param nb_column
	 *            le nombre de colonne de la grille de jeu
	 * @param win_hit
	 *            le nombre de coup gagnant
	 * @param suicide
	 *            le mode suicide est-il activé
	 * @param player_color
	 *            la couleur de notre joueur
	 */
	public void launch_versus_human(int nb_line, int nb_column, int win_hit,
			boolean suicide, Color player_color) {

		Grid grid = new Grid(nb_line, nb_column, 70, win_hit, suicide, false);
		Grid_controller controller = new Grid_controller(grid);
		Grid_view view = new Grid_view(nb_line, nb_column, 70, controller,
				this, player_color);

		grid.add_observer(view);
		this.setContentPane(view);
		this.pack();
		view.setVisible(true);
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de quitter la fenetre
	 */
	public void exit() {
		this.setVisible(false);
		this.dispose();
	}
}
