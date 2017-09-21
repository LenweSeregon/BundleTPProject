package model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import observer_pattern.Observable;
import observer_pattern.Observer;
import view.Audio;

public class Ball implements Observable, Runnable {

	private static int DEGREE_BOUNCE = 90;
	private double pos_x;
	private double pos_y;
	private int radius;
	private double angle_rad;

	private boolean is_moving;
	private int speed;
	private Board board;

	private Vector<Dimension> last_positions;
	private ArrayList<Observer> observers;

	private int nb_bounces;
	private boolean updated;

	private int id;
	private static int id_c = 0;

	/**
	 * Constructeur de la classe balle qui permet de réaliser toutes les actions
	 * en rapport avec la balle. C'est à dire le déplacement, les collisions de
	 * celles-ci. Le constructeur donne un identifiant unique à la balle pour la
	 * reconnaitre et initialise ces composants
	 * 
	 * @param pos_x
	 *            la position x de la balle
	 * @param pos_y
	 *            la position y de la balle
	 * @param radius
	 *            le rayon de la balle
	 * @param speed
	 *            la vitesse de la balle
	 * @param angle
	 *            l'angle de la balle
	 * @param board
	 *            une référence sur le plateau
	 */
	public Ball(double pos_x, double pos_y, int radius, int speed, int angle,
			Board board) {
		this.id = (id_c++);
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.radius = radius;
		this.speed = speed;

		this.board = board;
		this.angle_rad = -Math.toRadians(angle);

		this.last_positions = new Vector<Dimension>();
		this.observers = new ArrayList<Observer>();
		this.is_moving = true;
		this.nb_bounces = 0;
		this.updated = false;
	}

	/**
	 * Récupérer l'id unique de la balle
	 * 
	 * @return id de la balle
	 */
	public int get_id() {
		return this.id;
	}

	/**
	 * Méthode permettant de savoir si deux balles sont en collisions ou non
	 * 
	 * @param b1
	 *            la première balle que l'on veut tester pour la collision
	 * @param b2
	 *            la deuxième balle que l'on veut tester pour la collision
	 * @return vrai sur les deux balles sont en collision, faux sinon
	 */
	public boolean check_collide_balls(Ball b1, Ball b2) {

		double distance2 = Math.pow((b1.get_pos_x() - b2.get_pos_x()), 2)
				+ Math.pow((b1.get_pos_y() - b2.get_pos_y()), 2);

		return distance2 <= b1.get_radius() + b2.get_radius();
	}

	/**
	 * Méthode permettant de savoir si une balle est en collision avec un bonus
	 * 
	 * @param b1
	 *            La balle dont l'on souhaite savoir si elle est en collision
	 *            avec le bonus
	 * @param b2
	 *            le bonus dont l'on souhaite savoir si il est en collision avec
	 *            la balle
	 * @return vrai si la balle et le bonus sont en collisions, faux sinon
	 */
	public boolean check_collide_bonus(Ball b1, Abstract_bonus b2) {
		double distance2 = Math.sqrt(Math.pow(
				(b1.get_pos_x() - b2.get_pos_x()), 2)
				+ Math.pow((b1.get_pos_y() - b2.get_pos_y()), 2));

		return distance2 <= b1.get_radius() + b2.get_radius();
	}

	/**
	 * Méthode permettant de savoir si une balle est en collision avec une
	 * raquette
	 * 
	 * @param b1
	 *            La balle dont l'on souhaite savoir si elle est en collision
	 *            avec la raquette
	 * @param r
	 *            la raquette dont l'on souhaite savoir si elle est en collision
	 *            avec la balle
	 * @return vrai si la balle et la raquette sont en collisions, faux sinon
	 */
	public boolean check_collide_racket(Ball b1, Racket r) {

		double distance_x = Math.abs(b1.get_pos_x() - r.get_pos_x());
		double distance_y = Math.abs(b1.get_pos_y() - r.get_pos_y());

		if (distance_x > (r.get_width() / 2) + b1.get_radius()) {
			return false;
		}
		if (distance_y > (r.get_length() / 2) + b1.get_radius()) {
			return false;
		}

		if (distance_x <= (r.get_width() / 2)) {
			return true;
		}

		if (distance_y <= (r.get_length() / 2)) {
			return true;
		}

		double d_x = distance_x - r.get_width() / 2;
		double d_y = distance_y = r.get_width() / 2;

		return d_x * d_x + d_y * d_y <= (b1.get_radius() * b1.get_radius());
	}

	/**
	 * Méthode permettant de définir la position x de la balle
	 * 
	 * @param pos_x
	 *            la position x de la balle
	 */
	public void set_pos_x(double pos_x) {
		this.pos_x = pos_x;
	}

	/**
	 * Méthode permettant de définir la position y de la balle
	 * 
	 * @param pos_y
	 *            la position y de la balle
	 */
	public void set_pos_y(double pos_y) {
		this.pos_y = pos_y;
	}

	/**
	 * Méthode permettant de définir le rayon de la balle
	 * 
	 * @param radius
	 *            le rayon de la balle
	 */
	public void set_radius(int radius) {
		this.radius = radius;
	}

	/**
	 * Méthode permettant de donner un angle à ajouter à l'angle courant de
	 * notre balle. La méthode s'occupe de faire en sorte d'avoir un angle
	 * toujours entre 0 et 360 degrés
	 * 
	 * @param angle
	 *            l'angle que l'on veut ajouter à notre balle
	 */
	public void set_angle_value(int angle) {
		int new_angle = (this.get_angle_rad() + angle) % 360;
		this.angle_rad = -Math.toRadians(new_angle);

	}

	/**
	 * Méthode permettant de récupérer la position x de la balle
	 * 
	 * @return la position x de la balle
	 */
	public double get_pos_x() {
		return this.pos_x;
	}

	/**
	 * Méthode permettant de récupérer la position y de la balle
	 * 
	 * @return la position y de la balle
	 */
	public double get_pos_y() {
		return this.pos_y;
	}

	/**
	 * Méthode permettant de récupérer les dernières positions enregistrés de la
	 * balle, va servir pour réaliser le tracé de balle
	 * 
	 * @return un vecteur de dimension contenant les dernières positions
	 */
	public Vector<Dimension> get_last_positions() {
		return this.last_positions;
	}

	/**
	 * Méthode permettant de récupérer le rayon de la balle
	 * 
	 * @return le rayon de la balle
	 */
	public int get_radius() {
		return this.radius;
	}

	/**
	 * Méthode permettant de récupérer la vitesse de la balle
	 * 
	 * @return la vitese de la balle
	 */
	public int get_speed() {
		return this.speed;
	}

	/**
	 * Méthode permettant de récupérer en degré, l'angle de la balle, la méthode
	 * s'assure de toujours renvoyer une valeur comprise entre 0 et 360 degrés
	 * 
	 * @return la valeur de l'angle de la balle
	 */
	public int get_angle_rad() {
		int value = -(int) Math.toDegrees(this.angle_rad);
		if (value < 0) {
			value = value + 360;
		}
		return value;
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
	}

	@Override
	public void notify_racket_move(Racket racket) {
	}

	@Override
	public void notify_ball_move(Ball ball) {
		for (Observer ob : observers) {
			ob.update_ball_move(ball);
		}
	}

	/**
	 * @Override /** Méthode du thread permettant de faire avancer la balle.
	 *           Cette méthode réalise les différents tests de collisions et
	 *           faire avancer la balle. Si une balle est en collision, elle
	 *           change simplement d'angle selon certains critères et est remise
	 *           à sa position avant collision
	 */
	public void run() {
		while (is_moving) {
			boolean ask_kill = false;
			double save_x = this.get_pos_x();
			double save_y = this.get_pos_y();

			this.last_positions.add(new Dimension((int) save_x, (int) save_y));

			set_pos_x((get_pos_x() + speed * Math.cos(angle_rad)));
			set_pos_y((get_pos_y() + speed * Math.sin(angle_rad)));

			/* Collide ball to border */
			Ball b = this;
			int width = board.get_width();
			int height = board.get_height();

			if (b.get_pos_x() - b.get_radius() - 2 < 0 - (b.get_radius() * 2)) {
				board.increment_score(Player_position.PLAYER_TWO);
				this.is_moving = false;
				board.kill_ball(this);
				ask_kill = true;
				this.notify_ball_dead(this);

			} else if (b.get_pos_x() + b.get_radius() > width
					+ (b.get_radius() * 2)) {
				board.increment_score(Player_position.PLAYER_ONE);
				this.is_moving = false;
				ask_kill = true;
				board.kill_ball(this);
				this.notify_ball_dead(this);

			} else if (b.get_pos_y() - b.get_radius() - 2 < board
					.get_border_up().get_height()) {
				this.set_pos_y(save_y);
				this.last_positions.clear();
				nb_bounces++;
				updated = false;
				new Audio("/resources/rebond.wav").start();
				if (b.get_angle_rad() < 90) {
					b.set_angle_value(-DEGREE_BOUNCE);
				} else {
					b.set_angle_value(DEGREE_BOUNCE);
				}
			} else if (b.get_pos_y() + b.get_radius() + 2 > height
					- board.get_border_down().get_height()) {
				this.set_pos_y(save_y);
				this.last_positions.clear();
				nb_bounces++;
				updated = false;
				new Audio("/resources/rebond.wav").start();
				if (b.get_angle_rad() < 270) {
					b.set_angle_value(-DEGREE_BOUNCE);
				} else {
					b.set_angle_value(DEGREE_BOUNCE);
				}
			}

			/* Collide ball to ball */
			Vector<Ball> balls = board.get_balls();

			for (int i = 0; i < balls.size(); i++) {
				Ball b2 = balls.elementAt(i);
				if (this != b2) {
					if (check_collide_balls(b2, this)) {
						new Audio("/resources/rebond.wav").start();
						b2.set_angle_value(180);
						this.set_angle_value(180);
						this.last_positions.clear();
						b2.last_positions.clear();

						this.set_pos_x(save_x);
						this.set_pos_y(save_y);
					}
				}
			}

			/* Collide ball to racket */

			Racket racket_left = board.get_racket_left();
			Racket racket_right = board.get_racket_right();
			if (racket_left != null && racket_right != null) {
				/* Collision racket_left */

				if (check_collide_racket(this, racket_left)) {
					nb_bounces++;
					updated = false;
					new Audio("/resources/rebond.wav").start();
					Random rand = new Random();
					int low = 45;
					int high = 136;
					int angle_changer = rand.nextInt(high - low) + low;
					if (this.get_angle_rad() <= 180
							&& this.get_angle_rad() > 90) {
						this.set_angle_value(0 - angle_changer);
					} else {
						this.set_angle_value(0 + angle_changer);
					}

					this.last_positions.clear();
					this.set_pos_x(save_x + 1);
					this.set_pos_y(save_y);
				}

				if (check_collide_racket(this, racket_right)) {
					nb_bounces++;
					updated = false;
					new Audio("/resources/rebond.wav").start();
					Random rand = new Random();
					int low = 45;
					int high = 136;
					int angle_changer = rand.nextInt(high - low) + low;
					if (this.get_angle_rad() <= 360
							&& this.get_angle_rad() > 270) {
						this.set_angle_value(180 + angle_changer);
					} else {
						this.set_angle_value(180 - angle_changer);
					}

					this.last_positions.clear();
					this.set_pos_x(save_x - 1);
					this.set_pos_y(save_y);
				}
			}

			/* Collide ball to bonus */
			Vector<Abstract_bonus> bonus = board.get_bonus();
			for (int i = 0; i < bonus.size(); i++) {
				if (check_collide_bonus(this, bonus.get(i))) {
					bonus.get(i).apply_bonus(board);
				}
			}

			if (nb_bounces != 0 && nb_bounces % 5 == 0 && !updated) {
				this.speed++;
				updated = true;
			}

			if (!ask_kill) {
				notify_ball_move(this);
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void notify_score(int score, Player_position player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notify_ball_dead(Ball ball) {
		for (Observer ob : observers) {
			ob.update_ball_dead(ball);
		}
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
