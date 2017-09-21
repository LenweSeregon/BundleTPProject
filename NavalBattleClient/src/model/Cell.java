package model;

public class Cell {

	private int index_line;
	private int index_column;
	private int size_cell;
	private boolean has_been_visited;

	/**
	 * Constructeur de la classe cellule, celle ci permet juste de savoir à tout
	 * moment si une case a déjà été visité ou non
	 * 
	 * @param index_line
	 *            l'index de la ligne de la cellule
	 * @param index_column
	 *            l'index de la colonne de la cellule
	 * @param size_cell
	 *            la taille de la cellule
	 */
	public Cell(int index_line, int index_column, int size_cell) {
		this.index_line = index_line;
		this.index_column = index_column;
		this.size_cell = size_cell;
		this.has_been_visited = false;
	}

	/**
	 * Méthode permettant de récupérer l'index de la ligne de la cellule
	 * 
	 * @return l'index de la ligne
	 */
	public int get_index_line() {
		return index_line;
	}

	/**
	 * Méthode permettant de récupérer l'index de la colonne de la cellule
	 * 
	 * @return l'index de la colonne
	 */
	public int get_index_column() {
		return index_column;
	}

	/**
	 * Méthode permettant de récupérer la taille de la cellule
	 * 
	 * @return la taille de la cellule
	 */
	public int get_size_cell() {
		return size_cell;
	}

	/**
	 * Méthode permettant de récupérer la position X en pixel de la case
	 * 
	 * @return la position X de la cellule
	 */
	public int get_pos_x() {
		return index_column * size_cell;
	}

	/**
	 * Méthode permettant de récupérer la position Y en pixel de la case
	 * 
	 * @return la position Y de la cellule
	 */
	public int get_pos_y() {
		return index_line * size_cell;
	}

	/**
	 * Méthode permettant de savoir si la case a déjà été visité
	 * 
	 * @return vrai si la case a été visité, faux sinon
	 */
	public boolean get_has_been_visited() {
		return has_been_visited;
	}

	/**
	 * Méthode permettant de dire si une case à été visité ou non
	 * 
	 * @param b
	 *            la valeur booléenne a assigner
	 */
	public void set_has_been_visited(boolean b) {
		has_been_visited = b;
	}
}
