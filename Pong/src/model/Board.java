package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import observer_pattern.Observable;
import observer_pattern.Observer;

public class Board implements Observable {

	private static final int BASE_DIVIDE_SIZER_RACKET = 6;
	private static final int WIDTH_RACKET = 6;
	private static final int BASE_RADIUS_BALL = 7;
	private static final int BASE_SPEED_RACKET = 3;
	private static final int BASE_SPEED_BALL = 2;

	private Vector<Abstract_bonus> bonus_container;
	private Vector<Ball> balls_container;
	private int width;
	private int height;

	private Player player_1;
	private Player player_2;

	private Player_position loser_round;

	private Border_board border_up;
	private Border_board border_down;

	private ArrayList<Observer> observers;

	/**
	 * Constructeur de la classe plateau permettant d'initialiser tout les
	 * composants du plateau et d'envoyer une notification de la création du
	 * plateau
	 * 
	 * @param width
	 *            la largeur de notre plateau
	 * @param height
	 *            la hauteur de notre plateau
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.balls_container = new Vector<Ball>();
		this.bonus_container = new Vector<Abstract_bonus>();
		this.observers = new ArrayList<Observer>();

		this.player_1 = new Player(Player_position.PLAYER_ONE);
		this.player_2 = new Player(Player_position.PLAYER_TWO);

		this.loser_round = null;

		this.border_up = new Border_board(0, 0, width, height / 35);
		this.border_down = new Border_board(0, height, width, height / 35);
	}

	/**
	 * Méthode permettant d'ajouter un bonus dans notre plateau qui pourra
	 * rentrer en collision avec les balles pour etre déclenché
	 * 
	 * @param bonus
	 *            le bonus que l'on souhaite ajouter
	 */
	public void add_bonus(Abstract_bonus bonus) {
		this.bonus_container.add(bonus);
		notify_bonus_creation(bonus);
	}

	/**
	 * Méthode permettant de supprimer un bonus du plateau
	 * 
	 * @param bonus
	 *            le bonus que l'on souhaite détruire
	 */
	public void remove_bonus(Abstract_bonus bonus) {
		this.bonus_container.remove(bonus);
		notify_bonus_destruction();
	}

	/**
	 * Méthode permettant de récupérer la largeur du plateau
	 * 
	 * @return la largeur du plateau
	 */
	public int get_width() {
		return this.width;
	}

	/**
	 * Méthode permettant de récupérer la hauteur du plateau
	 * 
	 * @return la hauteur du plateau
	 */
	public int get_height() {
		return this.height;
	}

	/**
	 * Methode permettant de récupérer un vecteur contenant toutes les balles du
	 * plateau
	 * 
	 * @return un vecteur de toutes les différents balles
	 */
	public Vector<Ball> get_balls() {
		return this.balls_container;
	}

	/**
	 * Méthode permettant de placer la balle au bon endroit au début de la
	 * manche et de placer en meme temps un bonus qui est choisis au hasard
	 * parmis les différents bonus
	 * 
	 * @param position
	 *            le joueur a qui l'on souhaite accrocher la balle
	 */
	public void place_ball_to_player(Player_position position) {

		// Place ball
		if (position == Player_position.PLAYER_ONE) {
			add_ball(player_1.get_racket().get_pos_x()
					+ player_1.get_racket().get_width() + 5, player_1
					.get_racket().get_pos_y(), 0);
		} else if (position == Player_position.PLAYER_TWO) {
			add_ball(player_2.get_racket().get_pos_x()
					- player_2.get_racket().get_width() - 5, player_2
					.get_racket().get_pos_y(), 180);
		}

		if (bonus_container.size() != 0) {
			remove_bonus(bonus_container.elementAt(0));
		}

		// Place bonus
		Random rand = new Random();
		int random = rand.nextInt(Bonus_mode.values().length);
		Bonus_mode mode = Bonus_mode.values()[random];
		int low_x = 50;
		int high_x = width - 50;
		int res_x = rand.nextInt(high_x - low_x) + low_x;
		int low_y = 50;
		int high_y = height - 50;
		int res_y = rand.nextInt(high_y - low_y) + low_y;
		if (mode == Bonus_mode.NEW_BALL) {
			add_bonus(new Ball_bonus(res_x, res_y, BASE_RADIUS_BALL * 2));
		}
	}

	/**
	 * Méthode permettant de récupérer les différents bonus qui se trouve sur le
	 * plateau
	 * 
	 * @return un vector contenant les différents bonus du plateau
	 */
	public Vector<Abstract_bonus> get_bonus() {
		return this.bonus_container;
	}

	/**
	 * Méthode permettant de récupérer la bordure haute du plateau
	 * 
	 * @return la bordure haute du plateau
	 */
	public Border_board get_border_up() {
		return this.border_up;
	}

	/**
	 * Méthode permettant de récupérer la bordure basse du plateau
	 * 
	 * @return la bordure basse du plateau
	 */
	public Border_board get_border_down() {
		return this.border_down;
	}

	/**
	 * Méthode permettant de récupérer la raquette gauche du plateau
	 * 
	 * @return la raquette gauche du plateau
	 */
	public Racket get_racket_left() {
		return this.player_1.get_racket();
	}

	/**
	 * Méthode permettant de récupérer la raquette droite du plateau
	 * 
	 * @return la raquette droite du plateau
	 */
	public Racket get_racket_right() {
		return this.player_2.get_racket();
	}

	/**
	 * Méthode permettant de récupérer le dernier perdant de la manche du
	 * plateau
	 * 
	 * @return la dernier perdant
	 */
	public Player_position get_loser_round() {
		return this.loser_round;
	}

	/**
	 * Méthode permettant de récupérer le score du joueur de gauche
	 * 
	 * @return le score du joueur de gauche
	 */
	public int get_score_player_1() {
		return this.player_1.get_score();
	}

	/**
	 * Méthode permettant de récupérer le score du joueur de droite
	 * 
	 * @return le score du joueur de droite
	 */
	public int get_score_player_2() {
		return this.player_2.get_score();
	}

	/**
	 * Méthode permettant de demander de supprimer une balle du plateau
	 * 
	 * @param b
	 *            la balle que l'on souhaite supprimer
	 */
	public void kill_ball(Ball b) {
		this.balls_container.remove(b);
	}

	/**
	 * Méthode permettant d'incrémenter le score d'un joueur et de notifier la
	 * vue de cet ajout
	 * 
	 * @param player
	 *            le joueur dont l'on souhaite incrémenter le score
	 */
	public void increment_score(Player_position player) {
		if (player == Player_position.PLAYER_ONE) {
			player_1.increment_score();
			this.notify_score(player_1.get_score(), player);
			this.loser_round = Player_position.PLAYER_TWO;
		} else if (player == Player_position.PLAYER_TWO) {
			player_2.increment_score();
			this.notify_score(player_2.get_score(), player);
			this.loser_round = Player_position.PLAYER_ONE;
		}
	}

	/**
	 * Méthode permettant de savoir le nombre de balle active sur le plateau
	 * 
	 * @return le nombre de balle sur le plateau
	 */
	public int nb_ball_active() {
		return this.balls_container.size();
	}

	/**
	 * Méthode permettant de savoir si le joueur de gauche peut bouger sa
	 * raquette
	 * 
	 * @param direction
	 *            la direction dans laquelle il souhaite bouger
	 * @return vrai si il peut bouger, faux sinon
	 */
	public boolean player_1_can_move(Direction direction) {

		return this.player_1.can_move(direction);
	}

	/**
	 * Méthode permettant de savoir si le joueur de droite peut bouger sa
	 * raquette
	 * 
	 * @param direction
	 *            la direction dans laquelle il souhaite bouger
	 * @return vrai si il peut bouger, faux sinon
	 */
	public boolean player_2_can_move(Direction direction) {

		return this.player_2.can_move(direction);
	}

	/**
	 * Méthode permettant de faire bouger le joueur de gauche vers le haut
	 */
	public void move_up_player_1() {
		this.player_1.ask_for_up_moving();
	}

	/**
	 * Méthode permettant de faire bouger le joueur de gauche vers le bas
	 */
	public void move_down_player_1() {
		this.player_1.ask_for_down_moving();
	}

	/**
	 * Méthode permettant de faire bouger le joueur de droite vers le haut
	 */
	public void move_up_player_2() {
		this.player_2.ask_for_up_moving();
	}

	/**
	 * Méthode permettant de faire bouger le joueur de droite vers le bas
	 */
	public void move_down_player_2() {
		this.player_2.ask_for_down_moving();
	}

	/**
	 * Méthode permettant d'arreter le deplacement du bouger de gauche
	 */
	public void stop_player_1() {
		this.player_1.ask_for_stop_moving();
	}

	/**
	 * Méthode permettant d'arreter le deplacement du bouger de droite
	 */
	public void stop_player_2() {
		this.player_2.ask_for_stop_moving();
	}

	/**
	 * Méthode permettant d'initialiser le plateau en binder les raquettes au
	 * joueur
	 */
	public void init() {

		this.player_1.bind_racket(observers.get(0), 0 + (WIDTH_RACKET / 2),
				height / 2, WIDTH_RACKET, this.height
						/ BASE_DIVIDE_SIZER_RACKET, BASE_SPEED_RACKET, this);
		this.player_2.bind_racket(observers.get(0), width - (WIDTH_RACKET / 2),
				height / 2, WIDTH_RACKET, this.height
						/ BASE_DIVIDE_SIZER_RACKET, BASE_SPEED_RACKET, this);

		this.notify_creation(width, height, player_1.get_racket(),
				player_1.get_racket(), balls_container, bonus_container,
				border_up, border_down);

	}

	/**
	 * Méthode permettant d'ajouter une balle à notre plateau qui s'occupe aussi
	 * de lancer le thread qui la fera tourner
	 * 
	 * @param pos_x
	 *            la position x de la balle
	 * @param pos_y
	 *            la position y de la balle
	 * @param angle
	 *            l'angle de la balle
	 */
	public void add_ball(int pos_x, int pos_y, int angle) {
		Ball b = new Ball(pos_x, pos_y, BASE_RADIUS_BALL, BASE_SPEED_BALL,
				angle, this);
		balls_container.add(b);
		balls_container.elementAt(balls_container.size() - 1).add_observer(
				observers.get(0));
		new Thread(balls_container.elementAt(balls_container.size() - 1))
				.start();

	}

	@Override
	public void add_observer(Observer ob) {
		this.observers.add(ob);
	}

	@Override
	public void remove_all() {
		this.observers.clear();

	}

	@Override
	public void notify_creation(int width, int height, Racket racket_1,
			Racket racket_2, Vector<Ball> ball, Vector<Abstract_bonus> bonus,
			Border_board up, Border_board down) {
		for (Observer ob : observers) {
			ob.update_creation(width, height, player_1.get_racket(),
					player_2.get_racket(), ball, bonus, up, down);
		}

	}

	@Override
	public void notify_racket_move(Racket racket) {
		for (Observer ob : observers) {
			ob.update_racket_move(racket);
		}
	}

	@Override
	public void notify_ball_move(Ball ball) {
		for (Observer ob : observers) {
			ob.update_ball_move(ball);
		}
	}

	@Override
	public void notify_score(int score, Player_position player) {
		for (Observer ob : observers) {
			ob.update_score(score, player);
		}
	}

	@Override
	public void notify_ball_dead(Ball ball) {
	}

	@Override
	public void notify_bonus_creation(Abstract_bonus bonus) {
		for (Observer ob : observers) {
			ob.update_bonus_creation(bonus);
		}
	}

	@Override
	public void notify_bonus_destruction() {
		for (Observer ob : observers) {
			ob.update_bonus_destruction();
		}
	}

	@Override
	public void notify_victory(Player_position winner) {
		for (Observer ob : observers) {
			ob.update_victory(winner);
		}
	}

}
