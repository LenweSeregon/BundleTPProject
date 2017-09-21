package model;

public abstract class Abstract_bonus {

	protected int pos_x;
	protected int pos_y;
	protected int radius;
	protected Bonus_mode mode;
	protected int id;
	private static int id_c = 0;

	/**
	 * Constructeur de la classe abstraite bonus, elle permet d'obtenir un
	 * pattern que vont suivre les différentes classes concrétes qui hériteront
	 * de celle ci. Chaque bonus a un id unique
	 * 
	 * @param pos_x
	 *            la position x du bonus
	 * @param pos_y
	 *            la position y du bonus
	 * @param radius
	 *            le rayon du bonus (pour la collision)
	 * @param mode
	 *            le mode du bonus
	 */
	public Abstract_bonus(int pos_x, int pos_y, int radius, Bonus_mode mode) {
		this.id = (id_c++);
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.radius = radius;
		this.mode = mode;
	}

	/**
	 * Méthode permettant de récupérer l'idée du bonus
	 * 
	 * @return l'id du bonus
	 */
	public int get_id() {
		return this.id;
	}

	/**
	 * Méthode permettant de récupérer la position x du bonus
	 * 
	 * @return la position x du bonus
	 */
	public int get_pos_x() {
		return this.pos_x;
	}

	/**
	 * Méthode permettant de récupérer la postiion y du bonus
	 * 
	 * @return la position y du bonus
	 */
	public int get_pos_y() {
		return this.pos_y;
	}

	/**
	 * Méthode permettant de récupérer le rayon du bonus
	 * 
	 * @return le rayon du bonus
	 */
	public int get_radius() {
		return this.radius;
	}

	/**
	 * Méthode permettant de récupérer le mode du bonus sous forme d'énumération
	 * 
	 * @return le mode du bonus
	 */
	public Bonus_mode get_mode() {
		return this.mode;
	}

	/**
	 * Méthode abstraite qui sera redéfini dans les autres classes et qui
	 * executeront une action sur le plateau
	 * 
	 * @param board
	 *            une référence sur le plateau pour réaliser des actions
	 */
	abstract public void apply_bonus(Board board);
}
