package model;

import java.util.ArrayList;
import java.util.Vector;

import pattern_observer.Observable_game;
import pattern_observer.Observer_game;
import utils.DB;

public class Grid implements Observable_game {

	private int nb_line;
	private int nb_column;
	private String name;
	private String id;

	private Vector<Line_indexes> line_idxs;
	private Vector<Column_indexes> column_idxs;
	private boolean creation;

	private ArrayList<Observer_game> observers_game;
	private boolean[][] tiles;
	private boolean[][] soluce;

	/**
	 * Constructeur de la classe qui représente le coeur du jeu avec la grille
	 * en elle même qui est représenté par un simple tableau de booléen qui si
	 * faux, représente une case non retrouvé, si vrai représente une case
	 * retourné. Les indicateurs de lignes et colonnes se trouvent aussi dans
	 * cette classe Ce constructeur est utilisé lorsque l'on crée une grille que
	 * l'on veut résoudre
	 * 
	 * @param level
	 *            le level qu l'on souhaite construire qui détient le nom, le
	 *            nombre de ligne et de colonne ainsi que l'identifiant
	 * @param lic
	 *            le conteneur des indices de lignes a qui ont demande les bons
	 *            indices grâce à l'identifiant
	 * @param cic
	 *            le conteneur des indices de colonnes a qui ont demande les
	 *            bons indices grâce à l'identifiant
	 */
	public Grid(Game_level level, Line_indexes_container lic,
			Column_indexes_container cic, DB db) {
		this.creation = false;
		this.observers_game = new ArrayList<Observer_game>();
		this.name = level.get_name();
		this.nb_column = level.get_nb_column();
		this.nb_line = level.get_nb_line();
		this.id = level.get_identifiant();
		this.line_idxs = lic.get_lines_indicator_from_id(level
				.get_identifiant());
		this.column_idxs = cic.get_columns_indicator_from_id(level
				.get_identifiant());
		this.soluce = new boolean[nb_line][nb_column];
		this.soluce = db.get_soluce_from_identifiant(level.get_identifiant(),
				nb_line, nb_column);

		this.init_grid();
	}

	/**
	 * Constructeur de la classe qui représente le coeur du jeu avec les
	 * cellules et leurs etats à tout moment. Ce constructeur ne prend pas les
	 * memes arugments que le premier puisque celui ci est dédié à la création
	 * de grille, on a ainsi pas besoin des indicateurs
	 * 
	 * @param name
	 *            le nom de la grille
	 * @param nb_line
	 *            le nombre de ligne de la grille
	 * @param nb_column
	 *            le nombre de colonne de la grille
	 */
	public Grid(String name, int nb_line, int nb_column) {
		this.observers_game = new ArrayList<Observer_game>();
		this.creation = true;
		this.name = name;
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.line_idxs = null;
		this.column_idxs = null;

		this.init_grid();
	}

	/**
	 * Méthode privée permettant d'initialiser la grille en mettant toutes les
	 * cellules à non découverte
	 */
	private void init_grid() {
		this.tiles = new boolean[nb_line][nb_column];
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j] = false;
			}
		}
	}

	/**
	 * Méthode permettant de réaliser de manière logique le clique sur une
	 * cellule. Si la cellule était en pas découverte, elle passe en découverte
	 * et inversement
	 * 
	 * @param i
	 *            l'index I sur l'axe des X
	 * @param j
	 *            l'index J sur l'axe des Y
	 * @return vrai si la case était enfoncé, faux sinon
	 */
	public boolean click_tiles(int i, int j) {
		if (tiles[i][j]) {
			tiles[i][j] = false;
			return false;
		} else {
			tiles[i][j] = true;
			return true;
		}
	}

	/**
	 * Méthode permettant de savoir si la grille est créée en tant que création
	 * ou en tant que résolution
	 * 
	 * @return vrai si elle est en création, faux sinon
	 */
	public boolean get_creation() {
		return creation;
	}

	/**
	 * Méthode permettant de récupérer l'identifiant unique qui apparait à un
	 * niveau chargé et qui a été affecté à la grille
	 * 
	 * @return l'identifiant de la grille
	 */
	public String get_identifiant() {
		return id;
	}

	/**
	 * Méthode permettant de savoir si une grille a été réalisé avec succès ou
	 * non
	 * 
	 * @return vrai si la grille est bonne, faux sinon
	 */
	public boolean grid_succeed() {
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j] != soluce[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Méthode permettant de récupérant le tableau de booléenne qui représente
	 * les cases du plateau à un instant T
	 * 
	 * @return un tableau de booléen représentant les cases
	 */
	public boolean[][] get_tiles() {
		return tiles;
	}

	/**
	 * Méthode permettant de récupérer les différents indicateurs de lignes qui
	 * forment la grille
	 * 
	 * @return les indicateurs de ligne de la grille
	 */
	public Vector<Line_indexes> get_line_indexes() {
		return line_idxs;
	}

	/**
	 * Méthode permettant de récupérer les différents indicateurs de colonnes
	 * qui forment la grille
	 * 
	 * @return les indicateurs de colonne de la grille
	 */
	public Vector<Column_indexes> get_column_indexes() {
		return column_idxs;
	}

	/**
	 * Méthode permettant de savoir le nombre de ligne qui compose la grille
	 * 
	 * @return le nombre de ligne
	 */
	public int get_nb_line() {
		return nb_line;
	}

	/**
	 * Méthode permettant de savoir le nombre de ligne qui compose la grille
	 * 
	 * @return le nombre de ligne
	 */
	public int get_nb_column() {
		return nb_column;
	}

	/**
	 * Méthode permettant de récupérer le nom de la grille
	 * 
	 * @return le nom du niveau
	 */
	public String get_name() {
		return name;
	}

	@Override
	public void add_observer_menu(Observer_game ob_g) {
		observers_game.add(ob_g);
	}

	@Override
	public void remove_all_observers_menu() {
		observers_game.clear();
	}

	@Override
	public void notify_game_creation(Grid grid) {
		for (Observer_game og : observers_game) {
			og.update_game_creation(grid);
		}
	}

	@Override
	public void notify_tile_change(int i, int j, boolean val) {
		for (Observer_game og : observers_game) {
			og.update_tile_change(i, j, val);
		}
	}

	@Override
	public void notify_victory() {
		for (Observer_game og : observers_game) {
			og.update_victory();
		}
	}
}
