package model;

import java.util.ArrayList;
import java.util.Vector;

import observer_pattern.Observable;
import observer_pattern.Observer;

public class Racket implements Runnable, Observable {

	private int pos_x;
	private int pos_y;
	private int length;
	private int speed;
	private int width;

	private boolean is_moving;
	private Direction direction;
	private Player_position owner;

	private Board board;

	private ArrayList<Observer> observers;

	/**
	 * Constructeur de la classe raquette, il initialise tout les composants de
	 * raquette
	 * 
	 * @param pos_x
	 *            la position x de la raquette
	 * @param pos_y
	 *            la position y de la raquette
	 * @param width
	 *            la largeur de la raquette
	 * @param length
	 *            la hauteur de la raquette
	 * @param speed
	 *            la vitesse de la raquette
	 * @param owner
	 *            le posseseur de la raquette
	 * @param board
	 *            une référence sur le plateau
	 */
	public Racket(int pos_x, int pos_y, int width, int length, int speed,
			Player_position owner, Board board) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.length = length;
		this.width = width;
		this.speed = speed;
		this.owner = owner;

		this.board = board;

		this.is_moving = false;
		this.direction = Direction.NONE;
		this.observers = new ArrayList<Observer>();
	}

	/**
	 * Méthode permettant de savoir si la rquette est en déplacement
	 * 
	 * @return
	 */
	public boolean get_is_moving() {
		return this.is_moving;
	}

	/**
	 * Méthode permettant de connaitre le posseseur de la raquette
	 * 
	 * @return le possesseur de la raquette
	 */
	public Player_position get_owner() {
		return this.owner;
	}

	/**
	 * Méthode permettant de savoir la direction emprunté par la raquette
	 * 
	 * @param direction
	 *            la direction de la raquette à un instant t
	 */
	public void set_direction(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Méthode permettant de donner une valeur à is_moving de la raquette
	 * 
	 * @param bool
	 *            la valeur booléenne pour le déplacement de la raquette
	 */
	public void set_is_moving(boolean bool) {
		this.is_moving = bool;
	}

	/**
	 * Méthode permettant de choisir la position x de la raquette
	 * 
	 * @param pos_x
	 *            la position x de la raquette
	 */
	public void set_pos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	/**
	 * Méthode permettant de choisir la position y de la raquette
	 * 
	 * @param pos_y
	 *            la position y de la raquette
	 */
	public void set_pos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	/**
	 * Méthode permettant de choisir la taille de la raquette
	 * 
	 * @param length
	 *            la nouvelle taille
	 */
	public void set_length(int length) {
		this.length = length;
	}

	/**
	 * Méthode permettant de choisir la vitesse de la raquette
	 * 
	 * @param speed
	 *            la vitesse de la rquette
	 */
	public void set_speed(int speed) {
		this.speed = speed;
	}

	/**
	 * Méthode permettant de savoir la position x de la raquette
	 * 
	 * @return la position x de la raquette
	 */
	public int get_pos_x() {
		return this.pos_x;
	}

	/**
	 * Méthode permettant de savoir la position y de la raquette
	 * 
	 * @return la position y de la raquette
	 */
	public int get_pos_y() {
		return this.pos_y;
	}

	/**
	 * Méthode permettant de savoir la largeur de la raquette
	 * 
	 * @return la largeur de la raquette
	 */
	public int get_width() {
		return this.width;
	}

	/**
	 * Méthode permettant de savoir la hauteur de la raquette
	 * 
	 * @return la hauteur de la raquette
	 */
	public int get_length() {
		return this.length;
	}

	/**
	 * Méthode permettant de savoir la vitesse de la raquette
	 * 
	 * @return la vitesse de la raquette
	 */
	public int get_speed() {
		return this.speed;
	}

	/**
	 * Méthode permettant de savoir si la raquette du joueur peut se déplacer
	 * dans une direction donnée
	 * 
	 * @param direction
	 *            la direction ou veut se déplacer le raquette
	 * @return vrai si la raquette peut se déplacer, faux sinon
	 */
	public boolean can_move(Direction direction) {
		switch (direction) {
		case UP:
			if ((this.get_pos_y() - (this.get_length() / 2) - 4) > board
					.get_border_up().get_height()) {
				return true;
			} else {
				return false;
			}
		case DOWN:
			if ((this.get_pos_y() + (this.get_length() / 2) + 4) < board
					.get_height() - board.get_border_down().get_height()) {
				return true;
			} else {
				return false;
			}
		case NONE:
			return false;
		}
		return false;
	}

	@Override
	public void run() {
		while (true) {
			if (this.is_moving) {
				/* Test if racket still can move */

				/* Execution */
				switch (this.direction) {
				case UP:
					if (can_move(Direction.UP)) {
						this.set_pos_y(this.get_pos_y() - this.speed);
					} else {
						this.is_moving = false;
					}

					break;
				case DOWN:
					if (can_move(Direction.DOWN)) {
						this.set_pos_y(this.get_pos_y() + this.speed);
					} else {
						this.is_moving = false;
					}

					break;
				default:
					break;
				}
				this.notify_racket_move(this);
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void add_observer(Observer ob) {
		observers.add(ob);

	}

	@Override
	public void remove_all() {
		observers.clear();

	}

	@Override
	public void notify_creation(int width, int height, Racket racket_1,
			Racket racket_2, Vector<Ball> ball, Vector<Abstract_bonus> bonus,
			Border_board up, Border_board down) {
	}

	@Override
	public void notify_racket_move(Racket racket) {
		for (Observer ob : this.observers) {
			ob.update_racket_move(this);
		}
	}

	@Override
	public void notify_ball_move(Ball ball) {
	}

	@Override
	public void notify_score(int score, Player_position player) {
	}

	@Override
	public void notify_ball_dead(Ball ball) {
	}

	@Override
	public void notify_bonus_creation(Abstract_bonus bonus) {
	}

	@Override
	public void notify_bonus_destruction() {
	}

	@Override
	public void notify_victory(Player_position winner) {
	}
}
