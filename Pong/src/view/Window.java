package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board;
import model.Player_position;
import controler.Board_controler;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private static int SIZE_X = 700;
	private static int SIZE_Y = 400;

	private Menu menu;

	/**
	 * Constructeur de la classe fenetre
	 */
	public Window() {

		this.setTitle("Pong");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// this.launch_game();

		launch_menu();

		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	/**
	 * Méthode permettant de lancer le menu principal
	 */
	public void launch_menu() {
		menu = new Menu(SIZE_X, SIZE_Y, this);
		this.setContentPane(menu);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	/**
	 * Méthode permettant de lancer le menu de fin en affichant le vainqueur
	 * 
	 * @param position
	 *            le vainqueur
	 */
	public void launch_end_game(Player_position position) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		panel.setBackground(new Color(0, 0, 0, 150));
		panel.setOpaque(true);

		JLabel label = new JLabel("Le vainqueur est : " + position.toString());
		label.setFont(label.getFont().deriveFont(25.f));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setForeground(Color.white);

		panel.add(label);

		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	/**
	 * Méthode permettant de lancer le jeu
	 */
	public void launch_game() {
		Board board = new Board(700, 400);
		Board_controler controler = new Board_controler(board);
		Board_view view = new Board_view(controler, this);

		menu.setFocusable(false);

		board.add_observer(view);
		board.init();
		board.place_ball_to_player(Player_position.PLAYER_TWO);

		this.setContentPane(view);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		view.setVisible(true);
		view.setFocusable(true);
		view.requestFocusInWindow();
	}
}
