package mvc_view;

import javax.swing.JFrame;

import mvc_controler.Grid_controler;
import mvc_model.Grid;

public class Window extends JFrame {

	private int width;
	private int height;

	/**
	 * Constructeur de la classe représentant la fenetre du jeu. Cette classe
	 * donne une interface pour lancer les différents panels qui sont présent
	 * dans le jeu
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Window(int width, int height) {
		this.width = width;
		this.height = height;

		this.setTitle("Othello");
		this.setUndecorated(true);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		this.setVisible(true);

		launch_start_menu();
	}

	/**
	 * Méthode permettant de lancer la partie
	 */
	public void launch_game() {
		Grid model = new Grid(8, 8, 100, false);
		Grid_controler ctrl = new Grid_controler(model);
		Board_view view = new Board_view(ctrl, width, height, this);

		model.add_observer(view);
		model.notify_creation(model);

		this.setContentPane(view);
		this.pack();
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	public void launch_game_vs_ai() {
		Grid model = new Grid(8, 8, 100, true);
		Grid_controler ctrl = new Grid_controler(model);
		Board_view view = new Board_view(ctrl, width, height, this);

		model.add_observer(view);
		model.notify_creation(model);

		this.setContentPane(view);
		this.pack();
		view.setFocusable(true);
		view.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de charger une partie existante
	 */
	public void load_game() {
		Grid model = new Grid(8, 8, 100, "/resources/files/game.xml");
		Grid_controler ctrl = new Grid_controler(model);
		Board_view view = new Board_view(ctrl, width, height, this);

		model.add_observer(view);
		model.notify_creation(model);

		if (!ctrl.load_game()) {
			this.launch_start_menu();
			return;
		}

		this.setContentPane(view);
		this.pack();
		view.setFocusable(true);
		view.requestFocusInWindow();

	}

	/**
	 * Méthode permettant de lancer le menu de démarrage
	 */
	public void launch_start_menu() {
		Start_menu menu = new Start_menu(width, height, this);

		this.setContentPane(menu);
		this.pack();
		menu.setFocusable(true);
		menu.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de quitter la feneter et par conséquent le programme
	 */
	public void exit() {
		this.setVisible(false);
		this.dispose();
		System.exit(0);
	}
}
