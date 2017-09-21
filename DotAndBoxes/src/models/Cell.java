package models;

public class Cell {

	private Cell_owner border_up;
	private Cell_owner border_down;
	private Cell_owner border_right;
	private Cell_owner border_left;

	private Cell_owner cell_owner;

	private int index_line;
	private int index_column;
	private int size_cell;

	/**
	 * Constructeur de la classe Cell, permettant de construire une bordure avec
	 * toutes ces bordures sans possesseur et sans possesseur sur la cellule de
	 * base
	 */
	public Cell(int index_line, int index_column, int size_cell) {

		this.index_line = index_line;
		this.index_column = index_column;
		this.size_cell = size_cell;

		this.border_up = Cell_owner.NONE;
		this.border_down = Cell_owner.NONE;
		this.border_right = Cell_owner.NONE;
		this.border_left = Cell_owner.NONE;

		this.cell_owner = Cell_owner.NONE;
	}

	/**
	 * Méthode permettant de spécifier le posseseur de la case entière
	 * 
	 * @param owner
	 *            le possesseur que l'on souhaite attribuer pour la case entière
	 */
	public void set_owner(Cell_owner owner) {
		this.cell_owner = owner;
	}

	/**
	 * Méthode permettant de spécifier un posseseur à la bordure haute de la
	 * cellule
	 * 
	 * @param owner
	 *            le posseseur que l'on souhaite mettre pour la bordure
	 */
	public void set_up_owner(Cell_owner owner) {
		this.border_up = owner;
	}

	/**
	 * Méthode permettant de spécifier un posseseur à la bordure basse de la
	 * cellule
	 * 
	 * @param owner
	 *            le posseseur que l'on souhaite mettre pour la bordure
	 */
	public void set_down_owner(Cell_owner owner) {
		this.border_down = owner;
	}

	/**
	 * Méthode permettant de spécifier un posseseur à la bordure gauche de la
	 * cellule
	 * 
	 * @param owner
	 *            le posseseur que l'on souhaite mettre pour la bordure
	 */
	public void set_left_owner(Cell_owner owner) {
		this.border_left = owner;
	}

	/**
	 * Méthode permettant de spécifier un posseseur à la bordure droite de la
	 * cellule
	 * 
	 * @param owner
	 *            le posseseur que l'on souhaite mettre pour la bordure
	 */
	public void set_right_owner(Cell_owner owner) {
		this.border_right = owner;
	}

	/**
	 * Méthode permettant de récupérer le possesseur de la bordure haute
	 * 
	 * @return retourne le possesseur de la bordure
	 */
	public Cell_owner get_up_owner() {
		return this.border_up;
	}

	/**
	 * Méthode permettant de récupérer le possesseur de la bordure basse
	 * 
	 * @return retourne le possesseur de la bordure
	 */
	public Cell_owner get_down_owner() {
		return this.border_down;
	}

	/**
	 * Méthode permettant de récupérer le possesseur de la bordure droite
	 * 
	 * @return retourne le possesseur de la bordure
	 */
	public Cell_owner get_right_owner() {
		return this.border_right;
	}

	/**
	 * Méthode permettant de récupérer le possesseur de la bordure gauche
	 * 
	 * @return retourne le possesseur de la bordure
	 */
	public Cell_owner get_left_owner() {
		return this.border_left;
	}

	/**
	 * Méthode permettant de récupérer le possesseur de la cellule entière
	 * 
	 * @return retourne le possesseur de la cellule entière
	 */
	public Cell_owner get_cell_owner() {
		return this.cell_owner;
	}

	/**
	 * Méthode permettant de récuperer la position en X de la case
	 * 
	 * @return retourne la position X de la case dans la grille
	 */
	public int get_line_index() {
		return this.index_line;
	}

	/**
	 * Méthode permettant de récuperer la position en Y de la case
	 * 
	 * @return retourne la position Y de la case dans la grille
	 */
	public int get_column_index() {
		return this.index_column;
	}

	/**
	 * Méthode permettant de récuperer la longueur d'un coté du carré
	 * 
	 * @return retourne la longueur d'un coté du carré
	 */
	public int get_size_cell() {
		return this.size_cell;
	}

	/**
	 * Méthode permettant de savoir combien de bordure n'ont pas encore de
	 * propriétaire, c'est à dire les bordures qui ont comme posseseur "None"
	 * 
	 * @return retourne le nombre de bordure qui ne sont pas encore assigné à un
	 *         propriétaire
	 */
	public int get_free_border() {
		int free_border = 4;
		if (border_up != Cell_owner.NONE) {
			free_border--;
		}
		if (border_down != Cell_owner.NONE) {
			free_border--;
		}
		if (border_right != Cell_owner.NONE) {
			free_border--;
		}
		if (border_left != Cell_owner.NONE) {
			free_border--;
		}
		return free_border;
	}
}
