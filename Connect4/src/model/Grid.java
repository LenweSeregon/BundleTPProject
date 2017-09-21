package model;

import java.util.ArrayList;

import observer_pattern.Observable;
import observer_pattern.Observer;

public class Grid implements Observable {

	private ArrayList<Observer> observers;
	private int nb_line;
	private int nb_column;
	private int size_circle;
	private Cell[][] board;
	private Cell next_piece;
	private int placement_next_piece;

	private int winnable_hit;
	private int last_placement_X;
	private int last_placement_Y;

	private Owner player_turn;
	private boolean mode_suicide;

	private boolean AI_mode;
	private Player_AI ai;

	/**
	 * Constructeur de la classe Grid, il s'occupe d'initialiser tous les
	 * composants du modele et d'initialiser la grille avec toutes les valeurs
	 * par défaut et les paramètres donnés en entrée
	 * 
	 * @param nb_line
	 *            le nombre de ligne dans notre grille
	 * @param nb_column
	 *            le nombre de colonne dans notre grille
	 * @param size_circle
	 *            la taille des cellules dans notre grille
	 * @param winnable_hit
	 *            le nombre de coup gagnant necessaire
	 * @param mode_suicide
	 *            le mode de jeu qui est appliqué à la grille entre normal ou
	 *            suicide
	 * @param AI
	 *            permet de savoir si l'on est en mode humain ou contre l'ia
	 */
	public Grid(int nb_line, int nb_column, int size_circle, int winnable_hit,
			boolean mode_suicide, boolean AI) {
		this.observers = new ArrayList<Observer>();
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_circle = size_circle;
		this.board = new Cell[nb_line][nb_column];
		this.mode_suicide = mode_suicide;

		this.player_turn = Owner.YELLOW;
		this.next_piece = new Cell(Owner.YELLOW, size_circle);

		this.winnable_hit = winnable_hit;
		this.AI_mode = AI;
		if (this.AI_mode) {
			this.ai = new Player_AI(Owner.RED);
		} else {
			this.ai = null;
		}

		this.init();
	}

	/**
	 * Méthode permettant d'obtenir une copie identique de la grille
	 * 
	 * @return retourne une copie de la grille
	 */
	public Grid get_copy() {

		Grid copy = new Grid(this.nb_line, this.nb_column, this.size_circle,
				this.winnable_hit, this.mode_suicide, this.AI_mode);
		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				copy.board[i][j].set_owner(this.board[i][j].get_owner());
			}
		}
		copy.player_turn = this.player_turn;
		copy.next_piece = new Cell(this.player_turn, this.size_circle);
		copy.last_placement_X = this.last_placement_X;
		copy.last_placement_Y = this.last_placement_Y;
		copy.placement_next_piece = this.placement_next_piece;

		return copy;
	}

	/**
	 * Méthode permettant d'obtenir une évaluation de la grille en fonction de
	 * son état
	 * 
	 * @param owner
	 *            le joueur qui demande l'évaluation de la grill
	 * @return retourne une valuation pour la grille
	 */
	public int valuate_grid(Owner owner) {
		// System.out.println("Valuation owner = " + owner);
		if (this.game_win()) {
			if (this.player_turn == owner) {
				return Integer.MAX_VALUE;
			} else {
				return Integer.MIN_VALUE;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Méthode permettant de savoir si l'IA a été activé pour la partie
	 * 
	 * @return vrai si l'IA est activé, faux sinon
	 */
	public boolean AI_activated() {
		return this.AI_mode;
	}

	/**
	 * Méthode permettant de passer au prochain joueur
	 */
	public void next_player() {
		if (this.player_turn == Owner.YELLOW) {
			this.player_turn = Owner.RED;
			next_piece.set_owner(this.player_turn);
		} else {
			this.player_turn = Owner.YELLOW;
			next_piece.set_owner(this.player_turn);
		}
	}

	/**
	 * Méthode permettant de faire jouer l'intelligence artificielle
	 */
	public void make_ai_play() {
		this.ai.play(this, this.player_turn);
	}

	/**
	 * Méthode permet de savoir si le mode suicide a été activé pour la partie
	 * 
	 * @return vrai si le mode suicide est activé, faux sinon
	 */
	public boolean is_suicid_mode() {
		return this.mode_suicide;
	}

	/**
	 * Méthode permettant de savoir qui doit jouer
	 * 
	 * @return retourne sous forme d'enumeration le joueur qui doit jouer son
	 *         tour
	 */
	public Owner get_player_turn() {
		return this.player_turn;
	}

	/**
	 * Méthode permettant de récupérer le plateau de jeu
	 * 
	 * @return le plateau de jeu
	 */
	public Cell[][] get_grid() {
		return this.board;
	}

	/**
	 * Méthode permettant de récupérer la piece de référence
	 * 
	 * @return la piece de référence placer en haut du plateau
	 */
	public Cell get_next_piece() {
		return this.next_piece;
	}

	/**
	 * Méthode permettant de récupérer la position de la piece de référence du
	 * plateau situé en haut
	 * 
	 * @return la position de la piece de référence
	 */
	public int get_placemeent_next_piece() {
		return this.placement_next_piece;
	}

	/**
	 * Méthode permettant de bouger la piece de référence du plateau
	 * 
	 * @param i
	 *            la position ou l'on souhaite placer la piece de référence
	 */
	public void move_placement_next_piece(int i) {
		this.placement_next_piece = i;
		this.notify_next_piece_move(this.next_piece, this.placement_next_piece);
	}

	/**
	 * Méthode permettant d'initialiser le plateau en mettant toutes les
	 * cellulles avec un propriétaire nul et en spécifiant la vue que l'on vient
	 * d'initialiser
	 */
	public void init() {
		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				this.board[i][j] = new Cell(Owner.NONE, this.size_circle);
			}
		}
		this.notify_observer_grid_change(this.board);
	}

	/**
	 * Méthode permettant de connaitre le nombre de ligne dans la grille
	 * 
	 * @return le nombre de ligne
	 */
	public int get_nb_line() {
		return this.nb_line;
	}

	/**
	 * Méthode permettant de connaitre le nombre de colonne dans la grille
	 * 
	 * @return le nombre de colonne
	 */
	public int get_nb_column() {
		return this.nb_column;
	}

	/**
	 * Méthode permettant de récupérer la taille d'une cellule dans la grille
	 * 
	 * @return la taille d'un cellule
	 */
	public int get_cell_size() {
		return this.size_circle;
	}

	/**
	 * Méthode permettant de savoir combien d'espace libre il reste dans une
	 * colonne
	 * 
	 * @param column
	 *            la colonne dont on veut savoir le nombre de cellule libre
	 *            restante
	 * @return le nombre de place restante
	 */
	public int get_column_empty_space(int column) {
		int iterator = 0;
		for (int i = 0; i < this.nb_line; i++) {
			if (this.board[i][column].get_owner() == Owner.NONE) {
				iterator++;
			} else {
				break;
			}
		}
		return iterator;
	}

	/**
	 * Méthode permettant de vérifier si il y a une combinaison sur la colonne
	 * d'un cellule donnée
	 * 
	 * @param iStart
	 *            la position I de la celulle cible
	 * @param jStart
	 *            la position J de la celulle cible
	 * @return retourne vrai si il y a une combinaison sur la colonne
	 */
	public boolean check_column(int iStart, int jStart) {
		boolean wall_top = false;
		boolean wall_bot = false;
		int it_top = iStart - 1;
		int it_bot = iStart + 1;
		int iterator_align = 1;
		while (!wall_top || !wall_bot) {

			if (it_top < 0) {
				wall_top = true;
			}
			if (it_bot >= this.nb_line) {
				wall_bot = true;
			}

			if (!wall_top) {
				if (this.board[it_top][jStart].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_top = true;
				}
			}
			if (!wall_bot) {
				if (this.board[it_bot][jStart].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_bot = true;
				}
			}

			if (iterator_align >= this.winnable_hit) {
				break;
			}
			it_top--;
			it_bot++;
		}
		return iterator_align >= this.winnable_hit;
	}

	/**
	 * Méthode permettant de vérifier si il y a une combinaison sur la line d'un
	 * cellule donnée
	 * 
	 * @param iStart
	 *            la position I de la celulle cible
	 * @param jStart
	 *            la position J de la celulle cible
	 * @return retourne vrai si il y a une combinaison sur la line
	 */
	public boolean check_line(int iStart, int jStart) {

		boolean wall_left = false;
		boolean wall_right = false;
		int it_left = jStart - 1;
		int it_right = jStart + 1;
		int iterator_align = 1;
		while (!wall_left || !wall_right) {

			if (it_left < 0) {
				wall_left = true;
			}
			if (it_right >= this.nb_column) {
				wall_right = true;
			}

			if (!wall_left) {
				if (this.board[iStart][it_left].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_left = true;
				}
			}
			if (!wall_right) {
				if (this.board[iStart][it_right].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_right = true;
				}
			}

			if (iterator_align >= this.winnable_hit) {
				break;
			}
			it_left--;
			it_right++;
		}
		return iterator_align >= this.winnable_hit;
	}

	/**
	 * Méthode permettant de vérifier si il y a une combinaison sur la diagonale
	 * partant du haut gauche d'un cellule donnée
	 * 
	 * @param iStart
	 *            la position I de la celulle cible
	 * @param jStart
	 *            la position J de la celulle cible
	 * @return retourne vrai si il y a une combinaison sur la diagonale
	 */
	public boolean check_up_left_diag(int iStart, int jStart) {
		boolean wall_up_left = false;
		boolean wall_down_right = false;

		int it_up_left_i = iStart - 1;
		int it_up_left_j = jStart - 1;

		int it_down_right_i = iStart + 1;
		int it_down_right_j = jStart + 1;
		int iterator_align = 1;
		while (!wall_up_left || !wall_down_right) {
			if (it_up_left_i < 0 || it_up_left_j < 0) {
				wall_up_left = true;
			}
			if (it_down_right_i >= this.nb_line
					|| it_down_right_j >= this.nb_column) {
				wall_down_right = true;
			}

			if (!wall_up_left) {
				if (this.board[it_up_left_i][it_up_left_j].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_up_left = true;
				}
			}

			if (!wall_down_right) {
				if (this.board[it_down_right_i][it_down_right_j].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_up_left = true;
				}
			}
			if (iterator_align >= this.winnable_hit) {
				break;
			}
			it_up_left_i--;
			it_up_left_j--;

			it_down_right_i++;
			it_down_right_j++;

		}
		return iterator_align >= this.winnable_hit;
	}

	/**
	 * Méthode permettant de vérifier si il y a une combinaison sur la diagonale
	 * partant du haut droit d'un cellule donnée
	 * 
	 * @param iStart
	 *            la position I de la celulle cible
	 * @param jStart
	 *            la position J de la celulle cible
	 * @return retourne vrai si il y a une combinaison sur la diagonale
	 */
	public boolean check_up_right_diag(int iStart, int jStart) {
		boolean wall_up_left = false;
		boolean wall_down_right = false;

		int it_up_right_i = iStart + 1;
		int it_up_right_j = jStart - 1;

		int it_down_left_i = iStart - 1;
		int it_down_left_j = jStart + 1;
		int iterator_align = 1;
		while (!wall_up_left || !wall_down_right) {
			if (it_up_right_i >= this.nb_line || it_up_right_j < 0) {
				wall_up_left = true;
			}
			if (it_down_left_i < 0 || it_down_left_j >= this.nb_column) {
				wall_down_right = true;
			}

			if (!wall_up_left) {
				if (this.board[it_up_right_i][it_up_right_j].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_up_left = true;
				}
			}

			if (!wall_down_right) {
				if (this.board[it_down_left_i][it_down_left_j].get_owner() == this.board[iStart][jStart]
						.get_owner()) {
					iterator_align++;
				} else {
					wall_up_left = true;
				}
			}
			if (iterator_align >= this.winnable_hit) {
				break;
			}
			it_up_right_i++;
			it_up_right_j--;

			it_down_left_i--;
			it_down_left_j++;

		}
		return iterator_align >= this.winnable_hit;
	}

	/**
	 * Méthode permettant de savoir si la partie à été remporté par le dernier
	 * coup joué
	 * 
	 * @return vrai si la partie a été gagné, faux sinon
	 */
	public boolean game_win() {

		return check_line(this.last_placement_X, this.last_placement_Y)
				|| check_column(this.last_placement_X, this.last_placement_Y)
				|| check_up_left_diag(this.last_placement_X,
						this.last_placement_Y)
				|| check_up_right_diag(this.last_placement_X,
						this.last_placement_Y);
	}

	/**
	 * Méthode permettant de savoir si la grille est complétement remplie, c'est
	 * à dire que chaque cellule dispose d'un propriétaire
	 * 
	 * @return vrai si la grille est pleine, faux sinon
	 */
	public boolean is_grid_fill() {
		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				if (this.board[i][j].get_owner() == Owner.NONE) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Méthode permettant d'inserer une piece dans le plateau à une colonne
	 * donné, cette méthode va aussi s'assurer d'appliquer la physique de chute
	 * sur la piece
	 * 
	 * @param column
	 *            la colonne ou l'on souhaite ajouter le pion
	 */
	public void set_piece_to_column(int column) {
		this.board[0][column] = new Cell(this.player_turn, this.size_circle);
		this.apply_physic(column);
	}

	/**
	 * Méthode permettant d'appliquer la physique de chute sur une colonne
	 * donnée en supposant que la piece est placé sur la ligne 0 au début
	 * 
	 * @param column
	 *            la colonne ou l'on veut appliquer la physique
	 */
	public void apply_physic(int column) {
		int i = 0;
		for (; i < this.nb_line - 1; i++) {
			if (this.board[i + 1][column].get_owner() == Owner.NONE) {
				this.board[i + 1][column].set_owner(this.player_turn);
				this.board[i][column].set_owner(Owner.NONE);
			} else {
				break;
			}
		}
		this.last_placement_X = i;
		this.last_placement_Y = column;
	}

	@Override
	public void add_observer(Observer obs) {
		this.observers.add(obs);
		notify_observer_grid_change(this.board);
		notify_next_piece_move(this.next_piece, this.placement_next_piece);
		notify_message_box("C'est au tour de : " + this.player_turn.toString());

	}

	@Override
	public void remove_observer() {
		this.observers.clear();
	}

	@Override
	public void notify_observer_grid_change(Cell[][] board) {
		for (Observer ob : observers) {
			ob.update_grid(board);
		}
	}

	@Override
	public void notify_next_piece_move(Cell piece, int pos) {
		for (Observer ob : observers) {
			ob.update_piece_move(piece, pos);
		}
	}

	@Override
	public void notify_message_box(String message) {
		for (Observer ob : observers) {
			ob.update_message_box(message);
		}
	}

	@Override
	public void notify_victory(Owner owner) {
		for (Observer ob : observers) {
			ob.update_victory(owner);
		}
	}
}
