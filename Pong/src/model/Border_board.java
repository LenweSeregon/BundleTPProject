package model;

public class Border_board {

	private int pos_x;
	private int pos_y;
	private int width;
	private int height;

	/**
	 * Constructeur de la classe bordure plateau, ces bordures dans le modele
	 * logique permettant de tester les collisions et d'avoir une données modele
	 * pour la vue
	 * 
	 * @param pos_x
	 *            la position x de la bordure
	 * @param pos_y
	 *            la position y de la bordure
	 * @param width
	 *            la largeur de la bordure
	 * @param height
	 *            la hauteur de la bordure
	 */
	public Border_board(int pos_x, int pos_y, int width, int height) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Méthode permettant de récupérer la position x de la bordure
	 * 
	 * @return la position x de la bordure
	 */
	public int get_pos_x() {
		return this.pos_x;
	}

	/**
	 * Méthode permettant de récupérer la position y de la bordure
	 * 
	 * @return la position y de la bordure
	 */
	public int get_pos_y() {
		return this.pos_y;
	}

	/**
	 * Méthode permettant de récupérer la largeur de la bordure
	 * 
	 * @return la largeur de la bordure
	 */
	public int get_width() {
		return this.width;
	}

	/**
	 * Méthode permettant de récupérer la hauteur de la bordure
	 * 
	 * @return la hauteur de la bordure
	 */
	public int get_height() {
		return this.height;
	}
}
