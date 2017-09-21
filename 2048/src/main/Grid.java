package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Grid extends JPanel {

	private int line;
	private int column;
	private int size_pixel;
	private long score;
	Cell[][] tab;

	private boolean is_playing;

	public Grid(int line, int column, int size_pixel) {

		this.line = line;
		this.column = column;
		this.size_pixel = size_pixel;

		this.tab = new Cell[this.line][this.column];
	}

	/******************************************************************/
	/**************************** INITIALIZATION **********************/
	/******************************************************************/

	/**
	 * Methode permettant de créer l'interface graphique de la grille,
	 * construction des bordures et initialisation des bordures
	 */
	public void create_UI() {

		this.setPreferredSize(new Dimension(column * size_pixel + 14, line * size_pixel));
		Border border = this.getBorder();
		Border margin = new EmptyBorder(7, 7, 7, 7);
		this.setBorder(new CompoundBorder(border, margin));

		GridLayout gl = new GridLayout(line, column);
		gl.setHgap(7);
		gl.setVgap(7);
		this.setLayout(gl);

	}

	/**
	 * Methode permettant de réinitialiser la grille de manière, c'est à dire
	 * que l'on vide toutes les cases du plateau et on remet tous les label à un
	 * état neutre
	 */
	public void reinit() {
		this.is_playing = true;
		this.score = 0;

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				this.tab[i][j] = null;
			}
		}

		this.removeAll();
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				JLabel label = new JLabel();
				label.setBackground(java.awt.Color.white);
				label.setText("");
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setVerticalAlignment(JLabel.CENTER);
				this.add(label);
			}
		}
		this.generate_cell(false);
		this.draw_cell();

	}

	/******************************************************************/
	/******************************** GETTER **************************/
	/******************************************************************/

	/**
	 * Méthode permettant de récupérer une valuation de la grille en fonction de
	 * plusieurs critères avec par exemple la cellule avec la plus haute valeur,
	 * le nombre de cellules libres, etc...
	 * 
	 * @param chosen
	 *            La direction choisis pour le déplacement
	 * @param first
	 *            La direction a privilégier en premier pour une valuation plus
	 *            forte
	 * @param second
	 *            La direction à privilégier en deuxième pour une valuation plus
	 *            forte
	 * @return retourne la valuation sous la forme d'un long
	 */
	public long get_valuation(Direction chosen, Direction first, Direction second) {

		int highest_tile = this.get_highest_tile();
		int free_cell = this.get_nb_free_position() * 20;
		long merge = (this.get_merge_indication());
		boolean highest_corner = this.is_highest_at_corner();
		boolean neighbor_bool = this.first_and_snd_neighbor();
		long corner = 0;
		long neighbor = 0;
		long good_direction = 0;
		if (highest_corner) {
			corner = 10000 + (highest_tile / 2);
		}
		if (neighbor_bool) {
			neighbor = 250 + (highest_tile / 2);
		}

		return highest_tile + free_cell + merge + corner + neighbor + good_direction;
	}

	/**
	 * 
	 * Méthode pas termine pour tester la monoticité à partir de la plus grand
	 * valeur
	 *
	 * @return retourne la valeur de la monoticité
	 */
	public int monotonic() {

		int step = 0;
		Dimension dim = this.get_highest_tile_position();
		if (dim.width == 0 && dim.height == 0) {
			step = monotonic_top_left();
		} else if (dim.width == (this.line - 1) && dim.height == 0) {
			step = monotonic_down_left();
		} else if (dim.width == 0 && dim.height == (this.line - 1)) {
			step = monotonic_top_right();
		} else if (dim.width == (this.line - 1) && dim.height == (this.line - 1)) {
			step = monotonic_down_right();
		}

		return step;
	}

	/**
	 * Méthode pas termine pour tester la monoticité à partir de la plus grand
	 * valeur
	 * 
	 * @return l'étape de monoticité
	 */
	public int monotonic_top_right() {
		int xStart = 0;
		int yStart = 0;

		int step_monotonic = 0;
		if (this.tab[xStart - 1][yStart] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart - 1][yStart].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart - 2][yStart] != null && this.tab[xStart - 1][yStart] != null
					&& this.tab[xStart - 2][yStart].get_value() <= this.tab[xStart - 1][yStart].get_value()) {
				step_monotonic++;
				if (this.tab[xStart - 3][yStart] != null && this.tab[xStart - 2][yStart] != null
						&& this.tab[xStart - 3][yStart].get_value() <= this.tab[xStart - 2][yStart].get_value()) {
					step_monotonic++;
				}
			}
		}

		if (this.tab[xStart][yStart - 1] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart][yStart - 1].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart][yStart - 2] != null && this.tab[xStart][yStart - 1] != null
					&& this.tab[xStart][yStart - 2].get_value() <= this.tab[xStart][yStart - 1].get_value()) {
				step_monotonic++;
				if (this.tab[xStart][yStart - 3] != null && this.tab[xStart][yStart - 2] != null
						&& this.tab[xStart][yStart - 3].get_value() <= this.tab[xStart][yStart - 2].get_value()) {
					step_monotonic++;
				}
			}
		}

		return step_monotonic;
	}

	/**
	 * Méthode pas termine pour tester la monoticité à partir de la plus grand
	 * valeur
	 * 
	 * @return l'étape de monoticité
	 */
	public int monotonic_top_left() {
		int xStart = 0;
		int yStart = 0;

		int step_monotonic = 0;
		if (this.tab[xStart - 1][yStart] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart - 1][yStart].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart - 2][yStart] != null && this.tab[xStart - 1][yStart] != null
					&& this.tab[xStart - 2][yStart].get_value() <= this.tab[xStart - 1][yStart].get_value()) {
				step_monotonic++;
				if (this.tab[xStart - 3][yStart] != null && this.tab[xStart - 2][yStart] != null
						&& this.tab[xStart - 3][yStart].get_value() <= this.tab[xStart - 2][yStart].get_value()) {
					step_monotonic++;
				}
			}
		}

		if (this.tab[xStart][yStart + 1] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart][yStart + 1].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart][yStart + 2] != null && this.tab[xStart][yStart + 1] != null
					&& this.tab[xStart][yStart + 2].get_value() <= this.tab[xStart][yStart + 1].get_value()) {
				step_monotonic++;
				if (this.tab[xStart][yStart + 3] != null && this.tab[xStart][yStart + 2] != null
						&& this.tab[xStart][yStart + 3].get_value() <= this.tab[xStart][yStart + 2].get_value()) {
					step_monotonic++;
				}
			}
		}

		return step_monotonic;
	}

	/**
	 * Méthode pas termine pour tester la monoticité à partir de la plus grand
	 * valeur
	 * 
	 * @return l'étape de monoticité
	 */
	public int monotonic_down_right() {
		int xStart = 0;
		int yStart = 0;

		int step_monotonic = 0;
		if (this.tab[xStart + 1][yStart] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart + 1][yStart].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart + 2][yStart] != null && this.tab[xStart + 1][yStart] != null
					&& this.tab[xStart + 2][yStart].get_value() <= this.tab[xStart + 1][yStart].get_value()) {
				step_monotonic++;
				if (this.tab[xStart + 3][yStart] != null && this.tab[xStart + 2][yStart] != null
						&& this.tab[xStart + 3][yStart].get_value() <= this.tab[xStart + 2][yStart].get_value()) {
					step_monotonic++;
				}
			}
		}

		if (this.tab[xStart][yStart - 1] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart][yStart - 1].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart][yStart - 2] != null && this.tab[xStart][yStart - 1] != null
					&& this.tab[xStart][yStart - 2].get_value() <= this.tab[xStart][yStart - 1].get_value()) {
				step_monotonic++;
				if (this.tab[xStart][yStart - 3] != null && this.tab[xStart][yStart - 2] != null
						&& this.tab[xStart][yStart - 3].get_value() <= this.tab[xStart][yStart - 2].get_value()) {
					step_monotonic++;
				}
			}
		}

		return step_monotonic;
	}

	/**
	 * Méthode pas termine pour tester la monoticité à partir de la plus grand
	 * valeur
	 * 
	 * @return l'étape de monoticité
	 */
	public int monotonic_down_left() {
		int xStart = 0;
		int yStart = 0;

		int step_monotonic = 0;
		if (this.tab[xStart + 1][yStart] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart + 1][yStart].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart + 2][yStart] != null && this.tab[xStart + 1][yStart] != null
					&& this.tab[xStart + 2][yStart].get_value() <= this.tab[xStart + 1][yStart].get_value()) {
				step_monotonic++;
				if (this.tab[xStart + 3][yStart] != null && this.tab[xStart + 2][yStart] != null
						&& this.tab[xStart + 3][yStart].get_value() <= this.tab[xStart + 2][yStart].get_value()) {
					step_monotonic++;
				}
			}
		}

		if (this.tab[xStart][yStart + 1] != null && this.tab[xStart][yStart] != null
				&& this.tab[xStart][yStart + 1].get_value() <= this.tab[xStart][yStart].get_value()) {
			step_monotonic++;
			if (this.tab[xStart][yStart + 2] != null && this.tab[xStart][yStart + 1] != null
					&& this.tab[xStart][yStart + 2].get_value() <= this.tab[xStart][yStart + 1].get_value()) {
				step_monotonic++;
				if (this.tab[xStart][yStart + 3] != null && this.tab[xStart][yStart + 2] != null
						&& this.tab[xStart][yStart - 3].get_value() <= this.tab[xStart][yStart + 2].get_value()) {
					step_monotonic++;
				}
			}
		}

		return step_monotonic;
	}

	/**
	 * Méthode permettant de savoir si la cellule avec le score le plus elevé se
	 * trouve dans un des langues de la grille
	 * 
	 * @return vrai si la cellule est dans un angle, faux sinon
	 */
	public boolean is_highest_at_corner() {
		Dimension dim = this.get_highest_tile_position();
		if (dim.width == 0 && dim.height == 0) {
			return true;
		} else if (dim.width == 0 && (dim.height == (this.column - 1))) {
			return true;
		} else if ((dim.width == (this.line - 1)) && dim.height == 0) {
			return true;
		} else if (dim.width == this.line && (dim.height == (this.column - 1))) {
			return true;
		}
		return false;
	}

	/**
	 * Methode retournant toutes les positions libres dans la grille sous forme
	 * d'un vector de Dimension
	 * 
	 * @return Retourne toutes les positions libres
	 */
	public Vector<Dimension> get_all_free_position() {

		Vector<Dimension> free_position = new Vector<Dimension>();

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] == null) {
					free_position.add(new Dimension(i, j));
				}
			}
		}
		return free_position;
	}

	/**
	 * Méthode permettant de récuperer une valuation de merge, c'est à dire que
	 * l'on regarde pour chaque cellule si on voit un merge possible pour le
	 * coup suivant, si oui, on ajoute cette valeur de merge à une variable à
	 * retourner
	 * 
	 * @return retourne le valeur de merge maximum
	 */
	public long get_merge_indication() {
		long indication = 0;
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null) {
					if (this.merge_possible_on_cell(i, j)) {
						indication += this.tab[i][j].get_value() * 2;
					}
				}
			}
		}
		return indication;
	}

	/**
	 * Méthode permettant de retourner le nombre de position libre
	 * 
	 * @return le nomre de position libre
	 */
	public int get_nb_free_position() {
		return get_all_free_position().size();
	}

	/**
	 * Méthode permettant de récuperer le nombre de ligne dans la grille
	 * 
	 * @return le nombre de ligne dans la grille
	 */
	public int get_line() {
		return this.line;
	}

	/**
	 * Méthode permettant de récupérer le nombre de colonne dans la grille
	 * 
	 * @return le nombre de colonne dans la grille
	 */
	public int get_column() {
		return this.column;
	}

	/**
	 * Méthode permettant de récuperer le score actuelle qui se trouve dans la
	 * grille
	 * 
	 * @return le score dans la grille
	 */
	public long getScore() {
		return this.score;
	}

	/**
	 * Méthode permettant de savoir si l'action de jouer est possible c'est à
	 * dire que l'ia ou que le joueur sont en train de jouer
	 * 
	 * @return vrai si le jeu est en train de se jouer, faux sinon
	 */
	public boolean isPlaying() {
		return this.is_playing;
	}

	/**
	 * Méthode permettant de mettre la valeur de is_playing dans la grille
	 * 
	 * @param b
	 *            la valeur de is_playing que l'on souhaite assigner
	 */
	public void setIsPlaying(boolean b) {
		this.is_playing = b;
	}

	/**
	 * Méthode permettant de savoir si il est possible de jouer dans la grille,
	 * c'est à dire qu'il reste des actions possibles en tant que mouvement ou
	 * de fusion
	 * 
	 * @return retourne vrai si on peut encore jouer, faux sinon
	 */
	public boolean can_play() {
		return this.action_possible_bis();
	}

	/**
	 * Méthode permettant de récuperer une copie conforme de la grille actuelle
	 * 
	 * @return retourne une grille qui est une copie distincte de la grille
	 *         actuelle
	 */
	public Grid get_copy() {
		Grid copy = new Grid(this.line, this.column, this.size_pixel);

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] == null) {
					copy.tab[i][j] = null;
				} else {
					copy.tab[i][j] = new Cell(this.tab[i][j].get_value());
				}
			}
		}
		return copy;
	}

	/**
	 * Méthode permettant de savoir si la case la plus haute en valeur est à
	 * coté de la deuxième la plus haute en valeur
	 * 
	 * @return retourne vrai si les deux cases sont cote à cote, faux sinon
	 */
	public boolean first_and_snd_neighbor() {

		Dimension first = this.get_highest_tile_position();
		Dimension snd = this.get_second_highest_tile_position();

		if (first != null && snd != null) {
			int distance = (int) Math
					.sqrt(Math.pow((snd.width - first.width), 2) + Math.pow((snd.height - first.height), 2));

			return (distance == 1) && (first.width == snd.width || first.height == snd.height);
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de récuperer la cellule avec la plus grand valeur dans
	 * la grille
	 * 
	 * @return retourne la plus grande valeur dans la grille
	 */
	public int get_highest_tile() {
		int higher = 0;
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null && this.tab[i][j].get_value() > higher) {
					higher = this.tab[i][j].get_value();
				}
			}
		}
		return higher;
	}

	/**
	 * Méthode permettant de récuperer la positon de la cellule avec la plus
	 * grand valeur dans la grille
	 * 
	 * @return retourne la position de la cellule avec la plus grande valeur
	 *         sous forme d'une variable Dimension
	 * 
	 * @see Dimension
	 */
	public Dimension get_highest_tile_position() {
		Dimension higher = null;
		int higher_val = 0;

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null && this.tab[i][j].get_value() > higher_val) {
					higher_val = this.tab[i][j].get_value();
					higher = new Dimension(i, j);
				}
			}
		}
		return higher;
	}

	/**
	 * Méthode permettant de récuperer la deuxieme cellule avec la plus grand
	 * valeur dans la grille
	 * 
	 * @return retourne la deuximeple plus grande valeur dans la grille
	 */
	public int get_second_highest_tile() {
		Dimension highest_dim = get_highest_tile_position();

		int second_highest = 0;

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null && (i != highest_dim.width || j != highest_dim.height)) {
					if (second_highest < this.tab[i][j].get_value()) {
						second_highest = this.tab[i][j].get_value();
					}
				}
			}
		}
		return second_highest;
	}

	/**
	 * Méthode permettant de récuperer la deuxieme positon de la cellule avec la
	 * plus grand valeur dans la grille
	 * 
	 * @return retourne la position de la deuxieme cellule avec la plus grande
	 *         valeur sous forme d'une variable Dimension
	 * 
	 * @see Dimension
	 */
	public Dimension get_second_highest_tile_position() {
		Dimension highest_dim = get_highest_tile_position();

		int second_highest = 0;
		Dimension second_highest_pos = null;

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null && (i != highest_dim.width || j != highest_dim.height)) {
					if (second_highest < this.tab[i][j].get_value()) {
						second_highest = this.tab[i][j].get_value();
						second_highest_pos = new Dimension(i, j);
					}
				}
			}
		}
		return second_highest_pos;
	}

	/******************************************************************/
	/************************** GENERAL CHECKING **********************/
	/******************************************************************/

	/**
	 * Méthode permettant de savoir si une action est possible quelque soit la
	 * direction, c'est à dire que l'on peut réaliser un mouvement OU une fusion
	 * 
	 * @return vrai si action possible, faux sinon
	 */
	public boolean action_possible_bis() {
		return (move_possible_bis() || merge_possible_bis());
	}

	/**
	 * Méthode permettant de savoir si une action est possible avec une
	 * direction donné, c'est à dire que l'on peut réaliser un mouvement OU une
	 * fusion dans cette direction
	 * 
	 * @param direction
	 *            la direction avec laquelle on veut savoir si on peut réaliser
	 *            une action
	 * @return vrai si l'action est possible, faux sinon
	 */
	public boolean action_possible_bis(Direction direction) {

		return (move_possible_bis(direction) || merge_possible_bis(direction));
	}

	/******************************************************************/
	/************************ MOVE DIRECTION FUCNTION *****************/
	/******************************************************************/

	/**
	 * Méthode permettant de réaliser un mouvement de grille avec une direction
	 * donné. Si c'est un mouvement IA, alors on ne redessine pas les cellules
	 * car c'est un mouvement interne pour simuler
	 * 
	 * @param direction
	 *            la direction que l'on souhaite pour le mouvement
	 * @param IA_move
	 *            savoir si le mouvement demandé est réalisé par l'utilisateur
	 *            ou par l'IA
	 */
	public void move_to_direction(Direction direction, boolean IA_move) {

		if (move_possible_bis(direction) || merge_possible_bis(direction)) {
			apply_physic_to_all_cell(direction);
			generate_cell(IA_move);
			if (!IA_move) {
				draw_cell();
			}
		}
	}

	/**
	 * Méthode permettant de savoir si un mouvement est possible quelque soit la
	 * direction
	 * 
	 * @return retourne vrai si un mouvement est possible, faux sinon
	 */
	public boolean move_possible_bis() {
		boolean can = false;
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null && move_possible_on_cell(i, j)) {
					can = true;
				}
			}
		}
		return can;
	}

	/**
	 * Méthode permettant de savoir si un mouvement est possible avec une
	 * direction donnée
	 * 
	 * @param direction
	 *            la direction que l'on souhaite tester
	 * @return retourne vrai si un mouvement est possible, faux sinon
	 */
	public boolean move_possible_bis(Direction direction) {
		boolean can = false;
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null) {
					if (direction == Direction.UP) {
						can |= move_possible_on_cell_top(i, j);
					} else if (direction == Direction.DOWN) {
						can |= move_possible_on_cell_down(i, j);
					} else if (direction == Direction.LEFT) {
						can |= move_possible_on_cell_left(i, j);
					} else {
						can |= move_possible_on_cell_right(i, j);
					}
				}
			}
		}
		return can;
	}

	/**
	 * Méthode permetttant de savoir si un mouvement est possible avec une
	 * cellule donnée
	 * 
	 * @param i
	 *            l'index X de la cellule que l'on souhaite tester
	 * @param j
	 *            l'index Y de la cellule que l'on souhaite tester
	 * @return retourne vrai si le mouvement sur la cellule est possible, faux
	 *         sinon
	 */
	public boolean move_possible_on_cell(int i, int j) {
		return (move_possible_on_cell_top(i, j) || move_possible_on_cell_down(i, j) || move_possible_on_cell_right(i, j)
				|| move_possible_on_cell_left(i, j));
	}

	/**
	 * Méthode permettant de savoir si un mouvement est possible avec une
	 * cellule données vers le haut
	 * 
	 * @param i
	 *            l'index X de la cellule que l'on souhaite tester
	 * @param j
	 *            l'index Y de la cellule que l'on souhaite tester
	 * @return retourne vrai si on peut bouger la cellule vers le haut, faux
	 *         sinon
	 */
	public boolean move_possible_on_cell_top(int i, int j) {

		if (i >= 1) {
			return this.tab[i - 1][j] == null;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de savoir si un mouvement est possible avec une
	 * cellule données vers le bas
	 * 
	 * @param i
	 *            l'index X de la cellule que l'on souhaite tester
	 * @param j
	 *            l'index Y de la cellule que l'on souhaite tester
	 * @return retourne vrai si on peut bouger la cellule vers le haut, faux
	 *         sinon
	 */
	public boolean move_possible_on_cell_down(int i, int j) {

		if (i < this.line - 1) {
			return this.tab[i + 1][j] == null;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de savoir si un mouvement est possible avec une
	 * cellule données vers la gauche
	 * 
	 * @param i
	 *            l'index X de la cellule que l'on souhaite tester
	 * @param j
	 *            l'index Y de la cellule que l'on souhaite tester
	 * @return retourne vrai si on peut bouger la cellule vers le haut, faux
	 *         sinon
	 */
	public boolean move_possible_on_cell_left(int i, int j) {

		if (j >= 1) {
			return this.tab[i][j - 1] == null;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de savoir si un mouvement est possible avec une
	 * cellule données vers la droite
	 * 
	 * @param i
	 *            l'index X de la cellule que l'on souhaite tester
	 * @param j
	 *            l'index Y de la cellule que l'on souhaite tester
	 * @return retourne vrai si on peut bouger la cellule vers le haut, faux
	 *         sinon
	 */
	public boolean move_possible_on_cell_right(int i, int j) {

		if (j < this.column - 1) {
			return this.tab[i][j + 1] == null;
		} else {
			return false;
		}
	}

	/******************************************************************/
	/********************** MERGE DIRECTION FUCNTION ******************/
	/******************************************************************/

	/**
	 * Méthode permettant de savoir si une fusion est possible dans la grille
	 * quelque soit la direction
	 * 
	 * @return retourne vrai si la direction est possible, faux sinon
	 */
	public boolean merge_possible_bis() {
		boolean can = false;
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] != null && merge_possible_on_cell(i, j)) {
					can = true;
				}
			}
		}
		return can;
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible dans la grille
	 * avec une direction donnée
	 * 
	 * @param direction
	 *            la direction que l'on souhaite tester pour la fusion
	 * @return vrai si la fusion est possible, faux sinon
	 */
	public boolean merge_possible_bis(Direction direction) {
		boolean can = false;
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (direction == Direction.UP) {
					if (this.tab[i][j] != null && merge_possible_on_cell_up(i, j)) {
						can = true;
					}
				} else if (direction == Direction.DOWN) {
					if (this.tab[i][j] != null && merge_possible_on_cell_down(i, j)) {
						can = true;
					}
				} else if (direction == Direction.LEFT) {
					if (this.tab[i][j] != null && merge_possible_on_cell_left(i, j)) {
						can = true;
					}
				} else {
					if (this.tab[i][j] != null && merge_possible_on_cell_right(i, j)) {
						can = true;
					}
				}
			}
		}
		return can;
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible à une cellule
	 * donnée quelque soit la direction
	 * 
	 * @param i
	 *            index X de la cellule que l'on souhaite tester
	 * @param j
	 *            index Y de la cellule que l'on souhaite tester
	 * @return retourne vrai si l'on peut fusionner sur cette cellule, faux
	 *         sinon
	 */
	public boolean merge_possible_on_cell(int i, int j) {
		return (merge_possible_on_cell_up(i, j) || merge_possible_on_cell_down(i, j)
				|| merge_possible_on_cell_right(i, j) || merge_possible_on_cell_left(i, j));
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible à une cellule
	 * donnée avec une direction donnée
	 * 
	 * @param i
	 *            index X de la cellule que l'on souhaite tester
	 * @param j
	 *            index Y de la cellule que l'on souhaite tester
	 * @param direction
	 *            la direction que l'on souhaite tester la fusion
	 * @return retourne vrai si l'on peut fusionner avec cette cellule, faux
	 *         sinon
	 */
	public boolean merge_possible_on_cell_direction(int i, int j, Direction direction) {
		if (direction == Direction.UP) {
			return merge_possible_on_cell_up(i, j);
		} else if (direction == Direction.DOWN) {
			return merge_possible_on_cell_down(i, j);
		} else if (direction == Direction.LEFT) {
			return merge_possible_on_cell_left(i, j);
		} else {
			return merge_possible_on_cell_right(i, j);
		}
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible à une cellule
	 * donnée vers le haut
	 * 
	 * @param i
	 *            index X de la cellule que l'on souhaite tester
	 * @param j
	 *            index Y de la cellule que l'on souhaite tester
	 * @return return vrai si la fusion vers le haut est possible, faux sinon
	 */
	public boolean merge_possible_on_cell_up(int i, int j) {

		int upper = i - 1;
		boolean upper_stop = false;
		boolean possible = false;
		while (!upper_stop) {

			if (upper < 0) {
				upper_stop = true;
			}
			if (!upper_stop && this.tab[upper][j] != null) {
				if (!can_merge(i, j, upper, j)) {
					upper_stop = true;
				} else {
					possible = true;
					break;
				}

			}
			upper--;
		}
		return possible;
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible à une cellule
	 * donnée vers le bas
	 * 
	 * @param i
	 *            index X de la cellule que l'on souhaite tester
	 * @param j
	 *            index Y de la cellule que l'on souhaite tester
	 * @return return vrai si la fusion vers le bas est possible, faux sinon
	 */
	public boolean merge_possible_on_cell_down(int i, int j) {

		int downer = i + 1;
		boolean downer_stop = false;
		boolean possible = false;

		while (!downer_stop) {

			if (downer >= this.column) {
				downer_stop = true;
			}
			if (!downer_stop && this.tab[downer][j] != null) {
				if (!can_merge(i, j, downer, j)) {
					downer_stop = true;
				} else {
					possible = true;
					break;

				}
				downer_stop = true;
			}
			downer++;
		}
		return possible;
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible à une cellule
	 * donnée vers la gauche
	 * 
	 * @param i
	 *            index X de la cellule que l'on souhaite tester
	 * @param j
	 *            index Y de la cellule que l'on souhaite tester
	 * @return return vrai si la fusion vers la gauche est possible, faux sinon
	 */
	public boolean merge_possible_on_cell_left(int i, int j) {

		int lefter = j - 1;
		boolean lefter_stop = false;
		boolean possible = false;

		while (!lefter_stop) {

			if (lefter < 0) {
				lefter_stop = true;
			}

			if (!lefter_stop && this.tab[i][lefter] != null) {
				if (!can_merge(i, j, i, lefter)) {
					lefter_stop = true;
				} else {
					possible = true;
					break;
				}
			}

			lefter--;
		}
		return possible;
	}

	/**
	 * Méthode permettant de savoir si une fusion est possible à une cellule
	 * donnée vers la droite
	 * 
	 * @param i
	 *            index X de la cellule que l'on souhaite tester
	 * @param j
	 *            index Y de la cellule que l'on souhaite tester
	 * @return return vrai si la fusion vers la droite est possible, faux sinon
	 */
	public boolean merge_possible_on_cell_right(int i, int j) {

		int righter = j + 1;
		boolean righter_stop = false;
		boolean possible = false;

		while (!righter_stop) {

			if (righter >= this.column) {
				righter_stop = true;
			}
			if (!righter_stop && this.tab[i][righter] != null) {
				if (!can_merge(i, j, i, righter)) {
					righter_stop = true;
				} else {
					possible = true;
					break;
				}
			}
			righter++;
		}
		return possible;
	}

	/**
	 * Méthode permettant de générer une cellule dans la grille de manière
	 * aléatoire selon les positions restantes
	 * 
	 * @param IA_generation
	 *            boolean permettant de savoir si il faut redesinner les
	 *            cellules ou non
	 */
	public void generate_cell(boolean IA_generation) {
		Vector<Dimension> available_index = new Vector<Dimension>();

		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (this.tab[i][j] == null) {
					available_index.add(new Dimension(i, j));
				}
			}
		}

		Random rand = new Random();
		int proba = rand.nextInt(101);

		if (available_index.size() >= 1) {
			int value = rand.nextInt(available_index.size());
			Dimension dim = available_index.elementAt(value);

			if (proba >= 25) {
				this.tab[dim.width][dim.height] = new Cell(2);
			} else {

				this.tab[dim.width][dim.height] = new Cell(4);
			}

			if (!IA_generation)
				this.draw_cell();
		}
	}

	/**
	 * Méthode permettant de générer une cellule dans la grille a une position
	 * donnée
	 * 
	 * @param i
	 *            index X de la cellule ou l'on veut générer la case
	 * @param j
	 *            index Y de la celulle ou l'on veut générer la case
	 * @param IA_generation
	 *            boolean permettant de savoir si il faut redesinner les
	 *            cellules ou non
	 */
	public void generate_cell_at(int i, int j, boolean IA_generation) {
		Random rand = new Random();
		int proba = rand.nextInt(101);

		if (proba >= 25) {
			this.tab[i][j] = new Cell(2);
		} else {
			this.tab[i][j] = new Cell(4);
		}

		if (!IA_generation) {
			this.draw_cell();
		}

	}

	/**
	 * Méthode permettant de savoir si une fusion est possible entre deux
	 * cellules, ie : si les deux cellules ont la meme valeur
	 * 
	 * @param i1
	 *            index X de la premiére cellule
	 * @param j1
	 *            index Y de la premiére cellule
	 * @param i2
	 *            index X de la deuxième cellule
	 * @param j2
	 *            index Y de la deuxième cellule
	 * @return retourne vrai si la fusion est possible, faux sinon
	 */
	public boolean can_merge(int i1, int j1, int i2, int j2) {
		if (this.tab[i1][j1] != null && this.tab[i2][j2] != null) {
			return this.tab[i1][j1].get_value() == this.tab[i2][j2].get_value();
		}
		return false;
	}

	/**
	 * Méthode permettant d'appliquer les méthode de physique la grille selon
	 * une direction donnée
	 * 
	 * @param dir
	 *            la direction vers laquelle on veut appliquer la physique
	 */
	public void apply_physic_to_all_cell(Direction dir) {
		switch (dir) {
		case UP:
			for (int i = 0; i < this.line; i++) {
				for (int j = 0; j < this.column; j++) {
					if (this.tab[i][j] != null) {
						apply_physic_to_cell_top(i, j);
					}
				}
			}
			break;
		case DOWN:
			for (int i = this.line - 1; i >= 0; i--) {
				for (int j = 0; j < this.column; j++) {
					apply_physic_to_cell_down(i, j);
				}
			}

			break;
		case LEFT:
			for (int i = 0; i < this.line; i++) {
				for (int j = 0; j < this.column; j++) {
					apply_physic_to_cell_left(i, j);
				}
			}

			break;
		case RIGHT:
			for (int i = 0; i < this.line; i++) {
				for (int j = this.column - 1; j >= 0; j--) {
					apply_physic_to_cell_right(i, j);
				}
			}
			break;
		}
	}

	/**
	 * Méthode permettant d'appliquer la physique sur une cellule donnée vers le
	 * haut
	 * 
	 * @param i
	 *            index X de la cellule ou l'on veut appliquer la physique
	 * @param j
	 *            index Y de la cellule ou l'on veut appliquer la physique
	 * @return retourne vrai si une fusion a été appliqué , faux sinon
	 */
	public boolean apply_physic_to_cell_top(int i, int j) {
		if (this.tab[i][j] != null) {
			boolean wall = false;
			int it = i - 1;
			while (it >= 0 && !wall) {
				if (this.tab[it][j] != null) {
					wall = true;
				} else {
					this.tab[it][j] = this.tab[it + 1][j];
					this.tab[it + 1][j] = null;
				}
				it--;
			}
			it++;

			if (wall && this.can_merge(it + 1, j, it, j)) {
				this.tab[it][j].set_value(this.tab[it][j].get_value() * 2);
				this.tab[it + 1][j] = null;
				this.score += this.tab[it][j].get_value();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Méthode permettant d'appliquer la physique sur une cellule donnée vers le
	 * bas
	 * 
	 * @param i
	 *            index X de la cellule ou l'on veut appliquer la physique
	 * @param j
	 *            index Y de la cellule ou l'on veut appliquer la physique
	 * @return retourne vrai si une fusion a été appliqué , faux sinon
	 */
	public boolean apply_physic_to_cell_down(int i, int j) {
		if (this.tab[i][j] != null) {
			boolean wall = false;
			int it = i + 1;
			while (it <= this.line - 1 && !wall) {
				if (this.tab[it][j] != null) {
					wall = true;
				} else {
					this.tab[it][j] = this.tab[it - 1][j];
					this.tab[it - 1][j] = null;
				}
				it++;
			}
			it--;

			if (wall && this.can_merge(it - 1, j, it, j)) {
				this.tab[it][j].set_value(this.tab[it][j].get_value() * 2);
				this.tab[it - 1][j] = null;
				this.score += this.tab[it][j].get_value();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Méthode permettant d'appliquer la physique sur une cellule donnée vers la
	 * gauche
	 * 
	 * @param i
	 *            index X de la cellule ou l'on veut appliquer la physique
	 * @param j
	 *            index Y de la cellule ou l'on veut appliquer la physique
	 * @return retourne vrai si une fusion a été appliqué , faux sinon
	 */
	public boolean apply_physic_to_cell_left(int i, int j) {

		if (this.tab[i][j] != null) {
			boolean wall = false;
			int it = j - 1;
			while (it >= 0 && !wall) {
				if (this.tab[i][it] != null) {
					wall = true;
				} else {
					this.tab[i][it] = this.tab[i][it + 1];
					this.tab[i][it + 1] = null;
				}
				it--;
			}
			it++;

			if (wall && this.can_merge(i, it + 1, i, it)) {
				this.tab[i][it].set_value(this.tab[i][it].get_value() * 2);
				this.tab[i][it + 1] = null;
				this.score += this.tab[i][it].get_value();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Méthode permettant d'appliquer la physique sur une cellule donnée vers la
	 * droite
	 * 
	 * @param i
	 *            index X de la cellule ou l'on veut appliquer la physique
	 * @param j
	 *            index Y de la cellule ou l'on veut appliquer la physique
	 * @return retourne vrai si une fusion a été appliqué , faux sinon
	 */
	public boolean apply_physic_to_cell_right(int i, int j) {

		if (this.tab[i][j] != null) {
			boolean wall = false;
			int it = j + 1;
			while (it <= this.column - 1 && !wall) {
				if (this.tab[i][it] != null) {
					wall = true;
				} else {
					this.tab[i][it] = this.tab[i][it - 1];
					this.tab[i][it - 1] = null;
				}
				it++;
			}
			it--;

			if (wall && this.can_merge(i, it - 1, i, it)) {
				this.tab[i][it].set_value(this.tab[i][it].get_value() * 2);
				this.tab[i][it - 1] = null;
				this.score += this.tab[i][it].get_value();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Méthode permettant d'appliquer la physique à toute les cellules avec une
	 * direction donnée
	 * 
	 * @param dir
	 *            la direction que l'on souhaite utiliser pour la physique
	 */
	public void physic_to_direction(Direction dir) {
		apply_physic_to_all_cell(dir);
	}

	public void paintComponent(Graphics g2) {

		Toolkit.getDefaultToolkit().sync();
		Graphics2D g = (Graphics2D) g2;
		/* Black back */

		g.setColor(java.awt.Color.gray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

	}

	/**
	 * Méthode permettant de récuperer une couleur prédéfinie en fonction de la
	 * valeur d'une cellule
	 * 
	 * @param value
	 *            la valeur de la cellule dont l'on veut connaitre la couleur
	 * @return retourne une couleur sous forme de couleur SWING avec une valeur
	 *         RGB
	 */
	private java.awt.Color get_color(int value) {

		java.awt.Color color;
		switch (value) {
		case 2:
			color = new java.awt.Color(241, 233, 219);
			break;
		case 4:
			color = new java.awt.Color(236, 223, 201);
			break;
		case 8:
			color = new java.awt.Color(246, 174, 130);
			break;
		case 16:
			color = new java.awt.Color(234, 155, 108);
			break;
		case 32:
			color = new java.awt.Color(233, 135, 93);
			break;
		case 64:
			color = new java.awt.Color(231, 110, 74);
			break;
		case 128:
			color = new java.awt.Color(235, 205, 121);
			break;
		case 256:
			color = new java.awt.Color(234, 201, 106);
			break;
		case 512:
			color = new java.awt.Color(234, 197, 91);
			break;
		case 1024:
			color = new java.awt.Color(233, 195, 78);
			break;
		case 2048:
			color = new java.awt.Color(233, 192, 66);
			break;
		default:
			color = new java.awt.Color(60, 58, 50);
			break;
		}

		return color;
	}

	/**
	 * Méthode d'affichage console pour réaliser les vérifications pendant le
	 * développement
	 */
	public void display() {
		for (int i = 0; i < this.line; i++) {
			for (int j = 0; j < this.column; j++) {
				if (tab[i][j] == null) {
					System.out.print("X ");
				} else {
					System.out.print(tab[i][j].get_value() + " ");
				}
			}
			System.out.println("");
		}
	}

	/**
	 * Méthode permettant de dessiner les cellules, elle va en réaliser
	 * actualiser les JLabel de la fenetre avec les bonnes valeurs, les bonnes
	 * couleurs et les bonnes positions
	 */
	public void draw_cell() {
		Toolkit.getDefaultToolkit().sync();
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < column; j++) {
				if (tab[i][j] != null) {
					if (tab[i][j].get_value() >= 8) {
						((JLabel) this.getComponent(i * column + j)).setForeground(java.awt.Color.white);
					} else {
						((JLabel) this.getComponent(i * column + j)).setForeground(java.awt.Color.black);
					}
					((JLabel) this.getComponent(i * column + j)).setBackground(this.get_color(tab[i][j].get_value()));
					((JLabel) this.getComponent(i * column + j)).setOpaque(true);
					((JLabel) this.getComponent(i * column + j)).setText(Integer.toString(this.tab[i][j].get_value()));
				} else {
					((JLabel) this.getComponent(i * column + j)).setText("");
					((JLabel) this.getComponent(i * column + j)).setBackground(java.awt.Color.LIGHT_GRAY);
					((JLabel) this.getComponent(i * column + j)).setOpaque(true);
				}
			}
		}
	}
}
