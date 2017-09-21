package model;

import java.util.Vector;

public class Boat {

	private Vector<Boat_part> boat_parts;
	private int size_boat;
	private Boat_orientation orientation;

	private int index_line;
	private int index_column;

	private int size_part_boat;
	private boolean is_player_boat;

	/**
	 * Constructeur du bateau qui permet d'initialiser un bateau
	 * 
	 * @param size_boat
	 *            le nombre de case que prend notre bateau
	 * @param orientation
	 *            l'orientation de notre bateau
	 * @param idx_line
	 *            l'index sur la ligne de la tete du bateau
	 * @param idx_column
	 *            l'index sur la colonne de la tete du bateau
	 * @param size_part_boat
	 *            la taille de cahque partie du bateau
	 * @param is_player_boat
	 *            permet de savoir si le bateau appartient au joueur
	 */
	public Boat(int size_boat, Boat_orientation orientation, int idx_line,
			int idx_column, int size_part_boat, boolean is_player_boat) {
		this.index_line = idx_line;
		this.index_column = idx_column;
		this.size_part_boat = size_part_boat;
		this.is_player_boat = is_player_boat;

		this.size_boat = size_boat;
		this.orientation = orientation;

		init_boat();
	}

	/**
	 * Méthode permettant de récupérer toutes les parties qui constituent le
	 * bateau
	 * 
	 * @return un vecteur de partie qui constitue le bateau
	 */
	public Vector<Boat_part> get_boat_parts() {
		return boat_parts;
	}

	/**
	 * Méthode permettant d'initaliser le bateau, c'est à dire de construire les
	 * différents parties du bateau à la bonne positon par rapport à la tete de
	 * ce dernier
	 */
	public void init_boat() {
		boat_parts = new Vector<Boat_part>();
		// Adding head
		boat_parts.add(new Boat_part(Boat_position.HEAD, index_line,
				index_column, size_part_boat));
		// Adding rest
		for (int i = 1; i < size_boat - 1; i++) {
			if (orientation == Boat_orientation.HORIZONTAL) {
				boat_parts.add(new Boat_part(Boat_position.MIDDLE, index_line,
						index_column + i, size_part_boat));
			} else {
				boat_parts.add(new Boat_part(Boat_position.MIDDLE, index_line
						+ i, index_column, size_part_boat));
			}
		}
		// Adding tail
		if (orientation == Boat_orientation.HORIZONTAL) {
			boat_parts.add(new Boat_part(Boat_position.TAIL, index_line,
					index_column + (size_boat - 1), size_part_boat));
		} else {
			boat_parts.add(new Boat_part(Boat_position.TAIL, index_line
					+ (size_boat - 1), index_column, size_part_boat));
		}

	}

	/**
	 * Méthode permettant de savoir si le bateau appartient au joueur ou non
	 * 
	 * @return vrai si le bateau appartient au joueur, faux sinon
	 */
	public boolean is_player_boat() {
		return is_player_boat;
	}

	/**
	 * Méthode permettant de savoir si une partie de bateau existe à une
	 * position donnée
	 * 
	 * @param i
	 *            la position sur l'index des lignes
	 * @param j
	 *            la position sur l'index des colonnes
	 * @return la partie du bateau si elle existe, null sinon
	 */
	public Boat_part is_boat_part_present(int i, int j) {
		for (Boat_part bp : boat_parts) {
			if (bp.get_index_line() == i && bp.get_index_column() == j) {
				return bp;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de savoir si une partie de bateau destructible (pas
	 * encore touché) existe à une position donnée
	 * 
	 * @param i
	 *            la position sur l'index des lignes
	 * @param j
	 *            la position sur l'index des colonnes
	 * @return vrai si une partie destructibles existe à la position, faux sinon
	 */
	public boolean is_boat_part_destructable(int i, int j) {
		Boat_part part = is_boat_part_present(i, j);
		if (part != null && !part.get_touched()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de toucher la partie du bateau
	 * 
	 * @param i
	 *            la position sur l'index des lignes de la partie du bateau
	 * @param j
	 *            la position sur l'index des colonnes de la partie du bateau
	 */
	public void touch_boat_part(int i, int j) {
		is_boat_part_present(i, j).set_touched(true);
	}

	/**
	 * Méthode permettant de récupérer l'index des lignes de la tete du bateau
	 * 
	 * @return l'index des lignes de la tete du bateau
	 */
	public int get_index_line_head() {
		return index_line;
	}

	/**
	 * Méthode permettant de récupérer l'index des colonnes de la tete du bateau
	 * 
	 * @return l'index des colonnes de la tete du bateau
	 */
	public int get_index_column_head() {
		return index_column;
	}

	/**
	 * Méthode permettant de récupérer les coordonnées en X en pixel de la tete
	 * du bateau
	 * 
	 * @return la position X de la taille du bateau
	 */
	public int get_pos_x() {
		return index_column * size_part_boat;
	}

	/**
	 * Méthode permettant de récupérer les coordonnées en Y en pixel de la tete
	 * du bateau
	 * 
	 * @return la position Y de la taille du bateau
	 */
	public int get_pos_y() {
		return index_line * size_part_boat;
	}

	/**
	 * Méthode permettant de récupérer l'orientation du bateau
	 * 
	 * @return l'orientation du bateau sous forme d'enumération
	 */
	public Boat_orientation get_orientation() {
		return orientation;
	}

	/**
	 * Méthode peremttant de récupérer le taille de chaque partie des bateaux
	 * 
	 * @return la taille de chaque partie du bateau
	 */
	public int get_size_part_boot() {
		return size_part_boat;
	}

	/**
	 * Méthode permettant de savoir si toutes les parties du bateau ont été
	 * touché
	 * 
	 * @return vrai si toutes les parties ont été détruites, faux sinon
	 */
	public boolean get_all_parts_touched() {
		for (Boat_part part : boat_parts) {
			if (!part.get_touched()) {
				return false;
			}
		}
		return true;
	}
}
