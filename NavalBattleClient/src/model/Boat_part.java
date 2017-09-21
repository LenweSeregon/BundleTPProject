package model;

public class Boat_part {

	private Boat_position position;
	private boolean touched;
	private int index_line;
	private int index_column;
	private int size_cell;

	/**
	 * Constructeur de la classe partie de bateau, qui représente une patie de
	 * bateau avec le fait d'etre touché ou non
	 * 
	 * @param position
	 *            la position de notre partie de bateau
	 * @param index_line
	 *            l'index de la ligne de la partie du beateau
	 * @param index_column
	 *            l'index de la colonne de la partie du bateau
	 * @param size_cell
	 *            la taille de notre partie de bateau
	 */
	public Boat_part(Boat_position position, int index_line, int index_column,
			int size_cell) {
		this.position = position;
		this.touched = false;
		this.index_line = index_line;
		this.index_column = index_column;
		this.size_cell = size_cell;
	}

	/**
	 * Méthode permettant de récupérer la position de notre bateau
	 * 
	 * @return la position du bateau
	 */
	public Boat_position get_position() {
		return position;
	}

	/**
	 * Méthode permettant de récupérer l'index de la line du bateau
	 * 
	 * @return l'index de la ligne du bateau
	 */
	public int get_index_line() {
		return index_line;
	}

	/**
	 * Méthode permettant de récupérer l'index de la colonne de notre bateau
	 * 
	 * @return l'index de la colonne du bateau
	 */
	public int get_index_column() {
		return index_column;
	}

	/**
	 * Méthode permettant de récupérer le taille de la partie du bateau
	 * 
	 * @return la taille de la partie du bateau
	 */
	public int get_size_cell() {
		return size_cell;
	}

	/**
	 * Méthode permettant de récupérer la position X en pixel de la partie du
	 * bateau
	 * 
	 * @return la position x de la partie
	 */
	public int get_pos_x() {
		return index_column * size_cell;
	}

	/**
	 * Méthode permettant de récupérer la position Y en pixel de la partie du
	 * bateau
	 * 
	 * @return la position Y de la partie
	 */
	public int get_pos_y() {
		return index_line * size_cell;
	}

	/**
	 * Méthode permettant de savoir si la partie du bateau a été touché ou non
	 * 
	 * @return vrai si la partie a été touché, faux sinon
	 */
	public boolean get_touched() {
		return touched;
	}

	/**
	 * Méthode permettant de mettre une valeur au fait d'avoir touché la partie
	 * du bateau
	 * 
	 * @param b
	 *            la valeur boolénne pour savoir touché le bateau
	 */
	public void set_touched(boolean b) {
		this.touched = b;
	}

}