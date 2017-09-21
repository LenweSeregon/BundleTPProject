package views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import models.Grid;
import controllers.Grid_controller;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private Grid_view view;
	private Grid grid;
	private Starting_menu menu;

	/**
	 * Construction de la classe Window, il initialise la fenetre et demande
	 * d'afficher le menu de démarrage
	 */
	public Window() {

		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setTitle("Dot & boxes");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		activate_ask_menu();
	}

	/**
	 * Méthode permettant de demander de construire le menu de démarrage et de
	 * l'afficher
	 */
	public void activate_ask_menu() {

		menu = new Starting_menu(700, 700, this);
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(15, 15, 15, 15));
		panel.add(menu);

		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Méthode permettant de demander de construire le plateau de jeu et de
	 * l'afficher
	 * 
	 * @param nb_player
	 *            le nombre de joueur que l'on souhaite dans notre plateau de
	 *            jeu
	 * @param size
	 *            le nombre de carré en largeur et en hauteur que l'on souhaite
	 *            pour notre plateau
	 */
	public void activate_player_menu(int nb_player, int size, int nb_ai) {

		this.setBackground(Color.white);
		Score_view score = new Score_view(nb_player);

		grid = new Grid(size, size, 70, nb_player, nb_ai);
		Grid_controller controller = new Grid_controller(grid);
		view = new Grid_view(size, size, 70, controller, this);
		grid.add_observer(view);
		grid.add_observer(score);

		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		BorderLayout layout_panel = new BorderLayout();
		layout_panel.setHgap(10);
		layout_panel.setVgap(10);
		panel.setLayout(layout_panel);
		panel.setBorder(new LineBorder(Color.white, 15));

		panel.add(view, BorderLayout.WEST);
		panel.add(score, BorderLayout.EAST);

		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
	}

	public void exit() {
		this.setVisible(false);
		this.dispose();
	}
}
