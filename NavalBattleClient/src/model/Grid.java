package model;

import java.util.Collections;
import java.util.Vector;

public class Grid {

	private int nb_line;
	private int nb_column;
	private int size_cell;

	private boolean is_player;

	private Cell[][] cells;
	private Vector<Boat> boats;

	/**
	 * Constructeur de la classe grille. Le constructeur s'occupe d'initiliaser
	 * les composants comme il faut et aussi d'initialiser les cellules et de
	 * générer des bateaux placés aléatoirement
	 * 
	 * @param nb_line
	 *            le nombre de ligne dans la grille
	 * @param nb_column
	 *            le nombre de colonne dans la grille
	 * @param size_cell
	 *            la taille des cellules
	 * @param is_player
	 *            permet de savoir si une grille appartient au joueur
	 */
	public Grid(int nb_line, int nb_column, int size_cell, boolean is_player) {
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_cell = size_cell;
		this.is_player = is_player;

		boats = new Vector<Boat>();
		cells = new Cell[nb_line][nb_column];

		init_cells();
		init_boats();
	}

	/**
	 * Méthode permettant de savoir si c'est la grille du joueur
	 * 
	 * @return vrai si c'est la grille du joueur, faux sinon
	 */
	public boolean get_is_player() {
		return is_player;
	}

	/**
	 * Méthode permettant d'initialiser les cellules de la grile
	 */
	public void init_cells() {
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				cells[i][j] = new Cell(i, j, size_cell);
			}
		}
	}

	/**
	 * Méthode permettant de savoir à un instant T, avec une position, une
	 * taille de bateau et une orientation si il est possible de placer un
	 * bateau
	 * 
	 * @param size_boat
	 *            la taille du bateau
	 * @param ori
	 *            l'orientation du bateau
	 * @param i
	 *            la position sur la ligne
	 * @param j
	 *            la position sur la colonne
	 * @return vrai si on peut placer le bateau, faux sinon
	 */
	private boolean can_place(int size_boat, Boat_orientation ori, int i, int j) {

		if (ori == Boat_orientation.HORIZONTAL) {
			int j_cpt = j;
			for (int k = 0; k < size_boat; k++) {
				Boat b = boat_present(i, j_cpt);
				if (b != null || j_cpt < 0 || j_cpt >= nb_column) {
					return false;
				}
				j_cpt++;
			}
			return true;
		} else {
			int i_cpt = i;
			for (int k = 0; k < size_boat; k++) {
				Boat b = boat_present(i_cpt, j);
				if (b != null || i_cpt < 0 || i_cpt >= nb_line) {
					return false;
				}
				i_cpt++;
			}
			return true;
		}

	}

	/**
	 * Méthode permettant d'initialiser les différents bateaux en les placant
	 * aléatoirement et s'assure de la cohésion du positionnement
	 */
	public void init_boats() {

		int[] sizes = new int[] { 2, 3, 3, 4, 5 };

		for (int i = 0; i < sizes.length; i++) {
			boolean ok = false;

			// Init
			Vector<Integer> possible_x = new Vector<Integer>();
			Vector<Integer> possible_y = new Vector<Integer>();
			Vector<Boat_orientation> possible_orientation = new Vector<Boat_orientation>();
			for (Boat_orientation o : Boat_orientation.values()) {
				possible_orientation.add(o);
			}

			for (int k = 0; k < nb_column; k++) {
				possible_x.add(k);
			}
			for (int k = 0; k < nb_line; k++) {
				possible_y.add(k);
			}

			Collections.shuffle(possible_x);
			Collections.shuffle(possible_y);
			Collections.shuffle(possible_orientation);

			while (!ok) {
				boolean done = false;
				for (Integer a : possible_x) {
					for (Integer b : possible_y) {
						for (Boat_orientation o : possible_orientation) {
							if (can_place(sizes[i], o, a, b)) {
								boats.add(new Boat(sizes[i], o, a, b,
										size_cell, is_player));
								done = true;
								break;
							}
						}
						if (done)
							break;
					}
					if (done) {
						ok = true;
						break;
					}
				}
			}
		}
	}

	/**
	 * Méthode permettant de récupérer tout les bateaux d'une grille
	 * 
	 * @return les bateaux d'une grille
	 */
	public Vector<Boat> get_boats() {
		return this.boats;
	}

	/**
	 * Méthode permettant de récupérer une cellule de la grille via ses
	 * positions
	 * 
	 * @param i
	 *            l'index de ligne de la cellule
	 * @param j
	 *            l'index de colonne de la cellule
	 * @return la cellule que l'on souhaite obtenir
	 */
	public Cell get_cell(int i, int j) {
		return cells[i][j];
	}

	/**
	 * Méthode permettant de savoir si à une position donné il existe un bateau
	 * 
	 * @param i
	 *            l'index de la ligne que l'on souhaite vérifier
	 * @param j
	 *            l'index de la colonne que l'on souhaite vérifier
	 * @return
	 */
	public Boat boat_present(int i, int j) {
		for (Boat b : boats) {
			if (b.is_boat_part_present(i, j) != null) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de savoir si il reste sur la grille au moins un bateau
	 * en vie
	 * 
	 * @return vrai si un bateau existe encore, faux sinon
	 */
	public boolean one_boat_still_alive() {

		for (Boat b : boats) {
			if (!b.get_all_parts_touched()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de savoir si il est possible de toucher une partie de
	 * bateau dans la grille
	 * 
	 * @param i
	 *            l'index de ligne de la zone de bombardement voulu
	 * @param j
	 *            l'index de la colonne de la zone de bombardement voulu
	 * @return le bateau touché si c'est possible, null sinon
	 */
	public Boat can_touch_boat(int i, int j) {
		for (Boat b : boats) {
			if (b.is_boat_part_destructable(i, j)) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de toucher un bateau dans la grille via ses positions
	 * 
	 * @param i
	 *            l'index de la ligne de la zone de bombardement voulu
	 * @param j
	 *            l'index de la colonne de la zone de bombardement voulu
	 * @return le bateau qui a été touché par le bombardement si il existe, null
	 *         sinon
	 */
	public Boat touch_boat(int i, int j) {
		for (Boat b : boats) {
			if (b.is_boat_part_destructable(i, j)) {
				b.touch_boat_part(i, j);
				return b;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de récupérer le nombre de ligne dans la grille
	 * 
	 * @return le nombre de ligne
	 */
	public int get_nb_line() {
		return this.nb_line;
	}

	/**
	 * Méthode permettant de récupérer le nombre de colonne dans la grille
	 * 
	 * @return le nombre de colonne
	 */
	public int get_nb_column() {
		return this.nb_column;
	}

	/**
	 * Méthode permettant de récupérer la taille des cellules dans la grille
	 * 
	 * @return la taille des cellules
	 */
	public int get_size_cell() {
		return this.size_cell;
	}
}
