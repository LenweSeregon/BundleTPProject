package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JPanel;

import model.Abstract_bonus;
import model.Action_possible;
import model.Ball;
import model.Border_board;
import model.Player_position;
import model.Racket;
import observer_pattern.Observer;
import controler.Board_controler;

public class Board_view extends JPanel implements Observer {

	private Board_controler controler;
	private Window window;

	private int width;
	private int height;
	private KeyboardManager k_manager_player_1;
	private KeyboardManager k_manager_player_2;

	private Racket_view racket_left;
	private Racket_view racket_right;
	private Vector<Ball_view> balls_view;
	private Bonus_view bonus_view;
	private Border_view up_border;
	private Border_view down_border;
	private boolean initialize;

	private int score_player_1;
	private int score_player_2;

	/**
	 * Constructeur de la classe plateau vue qui regroupe toutes les entités
	 * graphiques du plateau
	 * 
	 * @param controler
	 *            une référence sur le controler du pattern mvc
	 * @param window
	 *            une référence sur la fenetre
	 */
	public Board_view(Board_controler controler, Window window) {
		this.controler = controler;
		this.initialize = false;
		this.window = window;

		this.racket_left = null;
		this.racket_right = null;
		this.up_border = null;
		this.down_border = null;
		this.bonus_view = null;
		this.balls_view = new Vector<Ball_view>();
		this.score_player_1 = 0;
		this.score_player_2 = 0;

		this.init_keyboard_managers();
		this.addKeyListener(this.k_manager_player_1);
		this.addKeyListener(this.k_manager_player_2);
		this.setPreferredSize(new Dimension(700, 400));

		this.score_player_1 = 0;
		this.score_player_2 = 0;

	}

	/**
	 * Méthode permettant d'initialiser les écouteurs associés aux joueurs
	 */
	public void init_keyboard_managers() {
		// Player 1
		this.k_manager_player_1 = new KeyboardManager(this.controler,
				Player_position.PLAYER_ONE);
		this.k_manager_player_1.add_event(KeyEvent.VK_Z,
				Action_possible.MOVE_UP);
		this.k_manager_player_1.add_event(KeyEvent.VK_S,
				Action_possible.MOVE_DOWN);

		// Player 2
		this.k_manager_player_2 = new KeyboardManager(this.controler,
				Player_position.PLAYER_TWO);
		this.k_manager_player_2.add_event(KeyEvent.VK_UP,
				Action_possible.MOVE_UP);
		this.k_manager_player_2.add_event(KeyEvent.VK_DOWN,
				Action_possible.MOVE_DOWN);

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		Toolkit.getDefaultToolkit().sync();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw background
		g2.setColor(Color.black);
		g2.fillRect(0, 0, width, height);

		if (initialize) {

			// Draw up border
			up_border.paintComponent(g);

			// Draw mid line
			g2.setColor(Color.white);
			for (int i = 0; i < this.height; i += 10) {
				g2.fillOval(width / 2, i, 2, 2);
			}

			// Draw down border
			down_border.paintComponent(g);

			// Draw racket left
			racket_left.paintComponent(g);

			// Draw racket right
			racket_right.paintComponent(g);

			// Draw balls
			for (int i = 0; i < balls_view.size(); i++) {
				balls_view.elementAt(i).paintComponent(g);
			}

			// Draw score player_1
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(20.f));
			g2.drawString(String.valueOf(this.score_player_1),
					(this.width / 2) - 65, 35);

			// Draw score player_2
			g2.setColor(Color.WHITE);
			g2.drawString(String.valueOf(this.score_player_2),
					(this.width / 2) + 50, 35);

			// Draw bonus
			if (bonus_view != null) {
				bonus_view.paintComponent(g2);
			}
		}
	}

	@Override
	public void update_creation(int width, int height, Racket racket_1,
			Racket racket_2, Vector<Ball> ball, Vector<Abstract_bonus> bonus,
			Border_board up, Border_board down) {
		// TODO Auto-generated method stub
		this.initialize = true;
		this.width = width;
		this.height = height;

		this.racket_left = new Racket_view(racket_1.get_pos_x(),
				racket_1.get_pos_y(), racket_1.get_length(),
				racket_1.get_width());

		this.racket_right = new Racket_view(racket_2.get_pos_x(),
				racket_2.get_pos_y(), racket_2.get_length(),
				racket_2.get_width());

		this.up_border = new Border_view(up.get_pos_x(), up.get_pos_y(),
				up.get_width(), up.get_height());

		this.down_border = new Border_view(down.get_pos_x(), down.get_pos_y()
				- down.get_height(), down.get_width(), down.get_height());

		this.repaint();
	}

	@Override
	public void update_racket_move(Racket racket) {
		if (racket.get_owner() == Player_position.PLAYER_ONE) {
			this.racket_left.set_pos_y(racket.get_pos_y());
		} else {
			this.racket_right.set_pos_y(racket.get_pos_y());
		}

		this.repaint();
	}

	@Override
	public void update_ball_move(Ball ball) {
		if (balls_view.size() == 0) {
			balls_view.add(new Ball_view(ball.get_id(), (int) ball.get_pos_x(),
					(int) ball.get_pos_y(), ball.get_radius()));
		}

		boolean find = false;
		for (Ball_view b : this.balls_view) {
			if (ball.get_id() == b.get_id()) {
				b.set_pos_x((int) ball.get_pos_x());
				b.set_pos_y((int) ball.get_pos_y());
				b.set_last_pos(ball.get_last_positions());
				find = true;
			}

		}

		if (!find) {
			balls_view.add(new Ball_view(ball.get_id(), (int) ball.get_pos_x(),
					(int) ball.get_pos_y(), ball.get_radius()));
		}
		this.repaint();
	}

	@Override
	public void update_score(int score, Player_position player) {
		if (player == Player_position.PLAYER_ONE) {
			this.score_player_1 = score;
			this.repaint();
		} else if (player == Player_position.PLAYER_TWO) {
			this.score_player_2 = score;
			this.repaint();
		}

	}

	@Override
	public void update_ball_dead(Ball ball) {
		for (int i = 0; i < balls_view.size(); i++) {
			if (balls_view.elementAt(i).get_id() == ball.get_id()) {
				balls_view.removeElementAt(i);
			}
		}
		this.repaint();
	}

	@Override
	public void update_bonus_creation(Abstract_bonus bonus) {
		this.bonus_view = new Bonus_view(bonus.get_pos_x(), bonus.get_pos_y(),
				bonus.get_radius(), bonus.get_mode());
		this.repaint();
	}

	@Override
	public void update_bonus_destruction() {
		this.bonus_view = null;
		this.repaint();
	}

	@Override
	public void update_victory(Player_position winner) {
		this.removeKeyListener(k_manager_player_1);
		this.removeKeyListener(k_manager_player_2);
		this.window.launch_end_game(winner);
	}
}
