package models;

import java.awt.Dimension;
import java.util.Vector;

import observer_pattern.Observable_grid;
import observer_pattern.Observer_grid;
import strategy_pattern.CloserAI;

public class Grid implements Observable_grid {

	private int nb_line;
	private int nb_column;
	private int size_cell;
	private int turn;

	private Cell_owner winner;

	private Cell[][] board;
	private Vector<Observer_grid> observers;
	private Player[] players;
	private AI_player[] ai_players;

	/**
	 * Construction de la classe Grid qui représente le modèle principal du jeu.
	 * Il se charge d'instancier tous les attributs et les initialise
	 * 
	 * @param nb_line
	 *            le nobmre de ligne dans notre grille
	 * @param nb_column
	 *            le nombre de colonne dans notre grille
	 * @param size_cell
	 *            la taille des cellules dans notre grille
	 * @param nb_player
	 *            le nombre de joueurs qui vont jouer la partie
	 */
	public Grid(int nb_line, int nb_column, int size_cell, int nb_player,
			int nb_ai_player) {
		this.turn = 0;
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_cell = size_cell;

		this.board = new Cell[nb_line][nb_column];
		this.observers = new Vector<Observer_grid>();
		this.players = new Player[nb_player];
		this.ai_players = new AI_player[nb_ai_player];

		this.init();
		if (nb_player == nb_ai_player) {
			this.ai_players[0].play(this);
		}
	}

	/**
	 * Méthode qui intialise le tableau de joueurs et le plateau
	 */
	public void init() {

		for (int i = 0; i < this.players.length; i++) {
			this.players[i] = new Player(Cell_owner.values()[i]);
		}

		for (int i = 0; i < this.ai_players.length; i++) {
			this.ai_players[i] = new AI_player(new CloserAI(),
					Cell_owner.values()[this.players.length - 1 - i]);
		}

		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				this.board[i][j] = new Cell(i, j, this.size_cell);
				if (i == 0) {
					this.board[i][j].set_up_owner(Cell_owner.GRID);
				}
				if (i == this.nb_column - 1) {
					this.board[i][j].set_down_owner(Cell_owner.GRID);
				}
				if (j == 0) {
					this.board[i][j].set_left_owner(Cell_owner.GRID);
				}
				if (j == this.nb_line - 1) {
					this.board[i][j].set_right_owner(Cell_owner.GRID);
				}
			}
		}

		this.notify_observer(this.board);
	}

	public Grid get_copy() {

		Grid copy = new Grid(this.nb_line, this.nb_column, this.size_cell,
				this.players.length, this.ai_players.length);
		copy.turn = this.turn;

		for (int i = 0; i < this.players.length; i++) {
			copy.players[i] = new Player(Cell_owner.values()[i]);
		}

		for (int i = 0; i < this.ai_players.length; i++) {
			copy.ai_players[i] = new AI_player(new CloserAI(),
					Cell_owner.values()[copy.players.length - 1 - i]);
		}

		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				copy.board[i][j] = new Cell(i, j, copy.size_cell);
				copy.board[i][j].set_up_owner(this.board[i][j].get_up_owner());
				copy.board[i][j].set_down_owner(this.board[i][j]
						.get_down_owner());
				copy.board[i][j].set_left_owner(this.board[i][j]
						.get_left_owner());
				copy.board[i][j].set_right_owner(this.board[i][j]
						.get_right_owner());
				copy.board[i][j].set_owner(this.board[i][j].get_cell_owner());

			}
		}

		return copy;
	}

	public int get_player_score(Cell_owner player) {
		for (int i = 0; i < this.players.length; i++) {
			if (player == this.players[i].get_owner()) {
				return this.players[i].get_owned_cell();
			}
		}
		return 0;
	}

	public int get_next_player_score(Cell_owner asker_player) {

		int i = 0;
		for (; i < this.players.length; i++) {
			if (asker_player == this.players[i].get_owner()) {
				break;
			}
		}
		return this.players[(i + 1) % this.players.length].get_owned_cell();
	}

	/**
	 * Méthode qui permet de mettre le gagnant et de notifier la vue de la
	 * victoire d'un joueur
	 * 
	 * @param winner
	 *            le joueur gagnant
	 */
	public void set_winner(Cell_owner winner) {
		this.winner = winner;
		this.notify_win_observer(this.winner);
	}

	/**
	 * Méthode permettant de connaitre toutes les cellules qui sont libres dans
	 * le plateau
	 * 
	 * @return retourne un vecteur contenant toutes les cellules libres
	 */
	public Vector<Cell> get_all_free_cell() {
		Vector<Cell> free_cells = new Vector<Cell>();

		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				if (this.board[i][j].get_cell_owner() == Cell_owner.NONE) {
					free_cells.add(this.board[i][j]);
				}
			}
		}
		return free_cells;
	}

	/**
	 * Méthode permettant de récupérer toutes les bordures libres d'un position
	 * donnée dans le plateau
	 * 
	 * @param i
	 *            l'index X de la case qui nous intéresse pour les bordures
	 * @param j
	 *            l'index Y de la case qui nous intéresse pour les bordures
	 * @return retourne un vecteur contenant les bordures libres de la case
	 *         ciblée
	 */
	public Vector<Border_position> get_all_free_border_from_cell(int i, int j) {
		Vector<Border_position> free_borders = new Vector<Border_position>();
		if (this.board[i][j].get_up_owner() == Cell_owner.NONE) {
			free_borders.add(Border_position.UP);
		}
		if (this.board[i][j].get_down_owner() == Cell_owner.NONE) {
			free_borders.add(Border_position.DOWN);
		}
		if (this.board[i][j].get_left_owner() == Cell_owner.NONE) {
			free_borders.add(Border_position.LEFT);
		}
		if (this.board[i][j].get_right_owner() == Cell_owner.NONE) {
			free_borders.add(Border_position.RIGHT);
		}
		return free_borders;
	}

	/**
	 * Méthode permettant de savoir si un joueur à gagné de manière anticipé !
	 * C'est à dire qu'il possède des cases du tableau + 1
	 * 
	 * @return retourne le joueur qui a gagné si il existe, null sinon
	 */
	public Cell_owner anticipated_win_player() {
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i].get_owned_cell() >= (((this.nb_column * this.nb_line) / 2) + 1)) {
				return this.players[i].get_owner();
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de savoir si notre carte est pleine, c'est à dire
	 * qu'il ne reste plus de case sans propriétaire
	 * 
	 * @return retourne vrai si le plateau est plein, faux sinon
	 */
	public boolean is_map_full() {
		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				if (this.board[i][j].get_cell_owner() == Cell_owner.NONE) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Méthode permettant de récupérer le vainqueur sur le plateau. Le vainqueur
	 * est simplement le joueur qui possède le plus de case !
	 * 
	 * @return retourne le joueur qui a gagné
	 */
	public Cell_owner get_winner() {
		int highest = 0;
		Cell_owner highest_owner = null;
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i].get_owned_cell() > highest) {
				highest = this.players[i].get_owned_cell();
				highest_owner = this.players[i].get_owner();
			}
		}
		return highest_owner;
	}

	/**
	 * Méthode permettant d'incrémenter le nombre de cellule que possède un
	 * joueur
	 * 
	 * @param owner
	 *            le joueur dont l'on souhaite incrémenter le nombre de cellule
	 */
	public void increment_player_cell_owned(Cell_owner owner) {
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i].get_owner() == owner) {
				this.players[i].increment_owned_cell();
				notify_score_increase(this.players[i]);
			}
		}
	}

	/**
	 * Méthide permettant de récupérer toutes les positions de cellules que l'on
	 * peut potentiellement fermé car il ne reste plus qu'une seule bordure de
	 * libre
	 * 
	 * @return retourne un vecteur de Dimension qui servira de couple (x,y)
	 */
	public Vector<Dimension> get_all_closable_cell() {

		Vector<Dimension> closable = new Vector<Dimension>();

		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				if (this.board[i][j].get_free_border() == 1) {
					closable.add(new Dimension(i, j));
				}
			}
		}
		return closable;
	}

	/**
	 * Méthode permettant de récupérer l'unique bordure libre d'une case. Cette
	 * méthode doit etre appelé lorsqu'elle se trouve dans la méthode
	 * get_all_closable_cell
	 * 
	 * @param i
	 *            l'index X de la case dont l'on veut connaitre la bordure libre
	 * @param j
	 *            l'index Y de la case dont l'on veut connaitre la bordure libre
	 * @return retourne une énumération représentant la bordure libre de la case
	 */
	public Border_position get_unique_free_border(int i, int j) {
		if (this.board[i][j].get_up_owner() == Cell_owner.NONE) {
			return Border_position.UP;
		} else if (this.board[i][j].get_down_owner() == Cell_owner.NONE) {
			return Border_position.DOWN;
		} else if (this.board[i][j].get_left_owner() == Cell_owner.NONE) {
			return Border_position.LEFT;
		} else if (this.board[i][j].get_right_owner() == Cell_owner.NONE) {
			return Border_position.RIGHT;
		}
		return null;
	}

	/**
	 * Méthode permettant d'incrémenter le tour du joueur, c'est à dire d'aller
	 * au joueur suivant
	 */
	public void set_next_player(boolean IA_call) {
		this.turn = (this.turn + 1) % (players.length);
		if (!IA_call) {
			Cell_owner player_turn = this.get_player_turn();
			for (int i = 0; i < this.ai_players.length; i++) {
				if (this.ai_players[i].get_owner() == player_turn) {
					this.ai_players[i].play(this);

					if (this.is_map_full()
							|| this.anticipated_win_player() != null) {
						this.set_winner(this.get_winner());
					} else {
						this.set_next_player(IA_call);
					}
				}
			}
		}
	}

	/**
	 * Méthode permettant un affichage en mode console d'un element de la case
	 * 
	 * @param i
	 *            l'index X de l'element que l'on veut afficher
	 * @param j
	 *            l'index Y de l'element que l'on veut afficher
	 */
	public void display_i_j(int i, int j) {
		System.out.println("Proprietaire haut = "
				+ this.board[i][j].get_up_owner());
		System.out.println("Proprietaire bas = "
				+ this.board[i][j].get_down_owner());
		System.out.println("Proprietaire gauche = "
				+ this.board[i][j].get_left_owner());
		System.out.println("Proprietaire droite = "
				+ this.board[i][j].get_right_owner());
	}

	/**
	 * Méthode permettant de décrémenter le tour du joueur, c'est à dire revenir
	 * au joueur précédent, c'est utile lorsque l'on a réalisé un coup gagnant
	 */
	public void set_previous_player() {
		this.turn--;
	}

	/**
	 * Méthode permettant de récuperer le plateau de la classe
	 * 
	 * @return retourne le plateau
	 */
	public Cell[][] get_grid() {
		return this.board;
	}

	/**
	 * Méthode permettant de savoir à quelle joueur il faut donner la main
	 * 
	 * @return retourne le joueur dont c'est le tour
	 */
	public Cell_owner get_player_turn() {
		return Cell_owner.values()[this.turn];
	}

	/**
	 * Méthode permettant de récupérer le nombre de ligne dans la grille
	 * 
	 * @return retourne le nombre de ligne dans la grille
	 */
	public int get_nb_line() {
		return this.nb_line;
	}

	/**
	 * Méthode permettant de récupérer le nombre de colonne dans la grille
	 * 
	 * @return retourne le nombre de colonne dans la grille
	 */
	public int get_nb_column() {
		return this.nb_column;
	}

	/**
	 * Méthode permettant de récupérer la taille des cellules dans la grille
	 * 
	 * @return retourne la taille des cellules dans la grille
	 */
	public int get_size_cell() {
		return this.size_cell;
	}

	/**
	 * Méthode permettant de mettre une case avec un index x et y à un
	 * propriétaire donné
	 * 
	 * @param line_x
	 *            l'index x de la case que l'on assigner
	 * @param column_x
	 *            l'index y de la case que l'on assigner
	 * @param owner
	 *            le propriétaire que l'on souhaite assigner à la case
	 */
	public void set_cell_owner(int line_x, int column_x, Cell_owner owner) {
		this.board[line_x][column_x].set_owner(owner);
	}

	/**
	 * Méthode permettant de mettre le coté d'une case avec un index x et y à
	 * une propriétaire donné
	 * 
	 * @param line_x
	 *            l'index x de la case dont l'on souhaite assigner la bordure
	 * @param column_x
	 *            l'index y de la case dont l'on souhaite assigner la bordure
	 * @param border
	 *            la position de la bordure que l'on souhaite mettre
	 * @param owner
	 *            le propriétaire que l'on souhaite assigner à la bordure
	 */
	public void set_cell_border_to_owner(int line_x, int column_x,
			Border_position border, Cell_owner owner, boolean IA_call) {

		boolean p1 = false;
		boolean p2 = false;
		switch (border) {
		case UP:

			p1 = false;
			p2 = false;
			this.board[line_x][column_x].set_up_owner(owner);
			if (this.board[line_x][column_x].get_cell_owner() == Cell_owner.NONE
					&& this.board[line_x][column_x].get_free_border() == 0) {
				this.set_cell_owner(line_x, column_x, owner);
				this.increment_player_cell_owned(owner);
				this.set_cell_owner(line_x, column_x, owner);
				p1 = true;
			}
			if (line_x >= 1) {
				this.board[line_x - 1][column_x].set_down_owner(owner);
				if (this.board[line_x - 1][column_x].get_cell_owner() == Cell_owner.NONE
						&& this.board[line_x - 1][column_x].get_free_border() == 0) {
					this.set_cell_owner(line_x - 1, column_x, owner);
					this.increment_player_cell_owned(owner);
					this.set_cell_owner(line_x - 1, column_x, owner);
					p2 = true;
				}
			}

			if (p1 || p2) {
				this.set_previous_player();
			}
			break;
		case DOWN:

			p1 = false;
			p2 = false;
			this.board[line_x][column_x].set_down_owner(owner);
			if (this.board[line_x][column_x].get_cell_owner() == Cell_owner.NONE
					&& this.board[line_x][column_x].get_free_border() == 0) {
				this.set_cell_owner(line_x, column_x, owner);
				this.increment_player_cell_owned(owner);
				this.set_cell_owner(line_x, column_x, owner);
				p1 = true;
			}
			if (line_x < this.nb_line - 1) {
				this.board[line_x + 1][column_x].set_up_owner(owner);
				if (this.board[line_x + 1][column_x].get_cell_owner() == Cell_owner.NONE
						&& this.board[line_x + 1][column_x].get_free_border() == 0) {
					this.set_cell_owner(line_x + 1, column_x, owner);
					this.increment_player_cell_owned(owner);
					this.set_cell_owner(line_x + 1, column_x, owner);
					p2 = true;
				}
			}

			if (p1 || p2) {
				this.set_previous_player();
			}

			break;
		case LEFT:

			p1 = false;
			p2 = false;
			this.board[line_x][column_x].set_left_owner(owner);
			if (this.board[line_x][column_x].get_cell_owner() == Cell_owner.NONE
					&& this.board[line_x][column_x].get_free_border() == 0) {
				this.set_cell_owner(line_x, column_x, owner);
				this.increment_player_cell_owned(owner);
				this.set_cell_owner(line_x, column_x, owner);
				p1 = true;
			}

			if (column_x >= 1) {
				this.board[line_x][column_x - 1].set_right_owner(owner);
				if (this.board[line_x][column_x - 1].get_cell_owner() == Cell_owner.NONE
						&& this.board[line_x][column_x - 1].get_free_border() == 0) {
					this.set_cell_owner(line_x, column_x - 1, owner);
					this.increment_player_cell_owned(owner);
					this.set_cell_owner(line_x, column_x - 1, owner);
					p2 = true;
				}
			}

			if (p1 || p2) {
				this.set_previous_player();
			}

			break;
		case RIGHT:

			p1 = false;
			p2 = false;
			this.board[line_x][column_x].set_right_owner(owner);
			if (this.board[line_x][column_x].get_cell_owner() == Cell_owner.NONE
					&& this.board[line_x][column_x].get_free_border() == 0) {
				this.set_cell_owner(line_x, column_x, owner);
				this.increment_player_cell_owned(owner);
				p1 = true;
			}

			if (column_x < this.nb_column - 1) {
				this.board[line_x][column_x + 1].set_left_owner(owner);
				if (this.board[line_x][column_x + 1].get_cell_owner() == Cell_owner.NONE
						&& this.board[line_x][column_x + 1].get_free_border() == 0) {
					this.set_cell_owner(line_x, column_x + 1, owner);
					this.increment_player_cell_owned(owner);
					this.set_cell_owner(line_x, column_x + 1, owner);
					p2 = true;
				}
			}

			if (p1 || p2) {
				this.set_previous_player();
			}

			break;
		}

		notify_observer(this.board);
	}

	@Override
	public void add_observer(Observer_grid obs) {
		observers.add(obs);
		obs.receive_update(this.board);

	}

	@Override
	public void remove_observer() {
		observers.removeAllElements();

	}

	@Override
	public void notify_observer(Cell[][] tab) {
		for (Observer_grid obs : this.observers) {
			obs.receive_update(tab);
		}
	}

	@Override
	public void notify_win_observer(Cell_owner winner) {
		for (Observer_grid obs : this.observers) {
			obs.receive_winner_update(winner);
		}

	}

	@Override
	public void notify_score_increase(Player player) {
		for (Observer_grid obs : this.observers) {
			obs.receive_score_update(player);
		}

	}

}
