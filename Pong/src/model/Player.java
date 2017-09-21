package model;

import observer_pattern.Observer;

public class Player {

	private boolean is_asking_moving_up;
	private boolean is_asking_moving_down;

	private Racket racket;
	private Player_position player_owner;
	private Board board;

	private int score;

	/**
	 * Constructeur de la classe Joueur, elle initialise un joueur avec un score
	 * de 0 et sans raquette
	 * 
	 * @param owner
	 *            la position du joueur sous forme d'énumération
	 */
	public Player(Player_position owner) {
		this.is_asking_moving_down = false;
		this.is_asking_moving_up = false;
		this.player_owner = owner;
		this.racket = null;
		this.score = 0;
		this.board = board;
	}

	/**
	 * Méthode permettant d'incrémenter le score du joueur de 1
	 */
	public void increment_score() {
		this.score++;
	}

	/**
	 * Méthode permettant de récupérer le score du joueur
	 * 
	 * @return le score du joueur
	 */
	public int get_score() {
		return this.score;
	}

	/**
	 * Méthode permettant de binder la raquette à un joueur en créant cette
	 * dernière
	 * 
	 * @param ob
	 *            l'observer de la raquette
	 * @param pos_x
	 *            la position x de la raquette
	 * @param pos_y
	 *            la position y de la raquette
	 * @param width
	 *            la largeur de la raquette
	 * @param length
	 *            la hauteur de la raquette
	 * @param speed
	 *            la vitesse de déplacement de la raquette
	 * @param board
	 *            une référence sur le plateau
	 */
	public void bind_racket(Observer ob, int pos_x, int pos_y, int width,
			int length, int speed, Board board) {

		this.racket = new Racket(pos_x, pos_y, width, length, speed,
				this.player_owner, board);
		this.racket.add_observer(ob);
		Thread thread = new Thread(this.racket);
		thread.start();
	}

	/**
	 * Méthode permettant de savoir si le joueur peut déplacer sa raquette
	 * 
	 * @param direction
	 *            la direction dans laquelle on souhaite déplacer le raquette
	 * @return vrai si le joueur peut déplacer sa raquette, faux sinon
	 */
	public boolean can_move(Direction direction) {
		switch (direction) {
		case UP:
			if ((this.racket.get_pos_y() - this.racket.get_length() / 2) > 0) {
				return true;
			} else {
				return false;
			}
		case DOWN:
			if ((this.racket.get_pos_y() + this.racket.get_length() / 2) < 700) {
				return true;
			} else {
				return false;
			}
		case NONE:
			return false;
		}
		return false;
	}

	/**
	 * Méthode permettant de récupérer la raquette du joueur
	 * 
	 * @return la raquette du joueur
	 */
	public Racket get_racket() {
		return this.racket;
	}

	/**
	 * Méthode permettant de demander a la raquette de se déplacer vers le haut
	 */
	public void ask_for_up_moving() {
		this.is_asking_moving_down = false;
		this.is_asking_moving_up = true;
		this.racket.set_is_moving(true);
		this.racket.set_direction(Direction.UP);
	}

	/**
	 * Méthode permettant de demander à la raquette de se déplacer vers le bas
	 */
	public void ask_for_down_moving() {
		this.is_asking_moving_up = false;
		this.is_asking_moving_down = true;
		this.racket.set_is_moving(true);
		this.racket.set_direction(Direction.DOWN);
	}

	/**
	 * Méthode permettant de demander à la raquette d'arreter son mouvement
	 */
	public void ask_for_stop_moving() {
		this.is_asking_moving_down = false;
		this.is_asking_moving_up = false;
		this.racket.set_is_moving(false);
	}

	/**
	 * Méthode permettant de savoir si la raquette bouge vers le haut
	 * 
	 * @return vrai si la raquette se déplace vers le haut, faux sinon
	 */
	public boolean is_moving_up() {
		return this.is_asking_moving_up;
	}

	/**
	 * Méthode permettant de savoir si la raquette bouge vers le bas
	 * 
	 * @return vrai si la raquette se déplace vers le bas, faux sinon
	 */
	public boolean is_moving_down() {
		return this.is_asking_moving_down;
	}
}
