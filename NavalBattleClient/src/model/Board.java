package model;

import java.util.ArrayList;

import pattern_observer.Observable;
import pattern_observer.Observer;

public class Board implements Observable {

	private Grid player_grid;
	private Grid enemy_grid;
	private int touched;

	private ArrayList<Observer> observers;

	/**
	 * Constructeur du plateau, il initialise simplement les deux grilles du
	 * plateau
	 * 
	 * @param nb_line
	 *            le nombre de ligne de chaque grille
	 * @param nb_column
	 *            le nombre de colonne de chaque grille
	 * @param size_cell
	 *            la taille des cellules de chaque grille
	 */
	public Board(int nb_line, int nb_column, int size_cell) {

		player_grid = new Grid(nb_line, nb_column, size_cell, true);
		enemy_grid = new Grid(nb_line, nb_column, size_cell, false);

		touched = 0;
		this.observers = new ArrayList<Observer>();
	}

	/**
	 * Méthode permettant de savoir si on peut toucher l'un de nos bateaux
	 * 
	 * @param i
	 *            la case i ou l'on se fait bombarder
	 * @param j
	 *            la case j ou l'on se fait bombarder
	 * @return le bateau qui s'est fait touché si il s'est fait touché, null
	 *         sinon
	 */
	public Boat can_hit_player_boat(int i, int j) {
		if (i >= 0 && i < player_grid.get_nb_line() && j >= 0
				&& j < player_grid.get_nb_column()) {
			Boat b = player_grid.can_touch_boat(i, j);
			return b;
		} else {
			return null;
		}
	}

	/**
	 * Méthode permettant de toucher l'un de nos bateaux
	 * 
	 * @param i
	 *            la case I ou l'on se fait bombarder
	 * @param j
	 *            la case J ou l'on se fait bombarder
	 * @return le bateau qui vient de se fait toucher
	 */
	public Boat hit_player_boat(int i, int j) {
		return player_grid.touch_boat(i, j);
	}

	/**
	 * Méthode permettant de savoir si l'on peut bombarder sur la case de
	 * l'ennemi
	 * 
	 * @param i
	 *            la case d'index I
	 * @param j
	 *            la case d'index J
	 * @return vrai si on a pas encore bombardé, faux sinon
	 */
	public boolean can_hit_enemy_cell(int i, int j) {
		if (i >= 0 && i < enemy_grid.get_nb_line() && j >= 0
				&& j < enemy_grid.get_nb_column()
				&& !enemy_grid.get_cell(i, j).get_has_been_visited()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de savoir si l'on peut bombarder se fait bombarder
	 * 
	 * @param i
	 *            la case d'index I
	 * @param j
	 *            la case d'index J
	 * @return vrai si on ne s'est pas encore fait bombardé, faux sinon
	 */
	public boolean can_hit_player_cell(int i, int j) {
		if (i >= 0 && i < player_grid.get_nb_line() && j >= 0
				&& j < player_grid.get_nb_column()
				&& !player_grid.get_cell(i, j).get_has_been_visited()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de toucher la cellule de l'ennemi
	 * 
	 * @param i
	 *            la case d'index I
	 * @param j
	 *            la case d'index J
	 */
	public void hit_enemy_cell(int i, int j) {
		enemy_grid.get_cell(i, j).set_has_been_visited(true);
	}

	/**
	 * Méthode permettant de toucher l'un de nos cellule
	 * 
	 * @param i
	 *            la case d'index I
	 * @param j
	 *            la case d'index J
	 */
	public void hit_player_cell(int i, int j) {
		player_grid.get_cell(i, j).set_has_been_visited(true);
	}

	/**
	 * Méthode permettant de récupérer la grille de notre joueur
	 * 
	 * @return la grille du joueur
	 */
	public Grid get_player_grid() {
		return player_grid;
	}

	/**
	 * Méthode permettant de récuperer la grille de l'adversaire
	 * 
	 * @return la grille de l'adversaire
	 */
	public Grid get_enemy_grid() {
		return enemy_grid;
	}

	/**
	 * Méthode permettant de savoir si l'on a gagné
	 * 
	 * @return vrai si on a gagné, faux sinon
	 */
	public boolean player_has_win() {
		return touched >= 17;
	}

	/**
	 * Méthode permettant de savoir si l'on a perdu
	 * 
	 * @return vrai si l'on a perdu, faux sinon
	 */
	public boolean enemy_has_win() {
		return !player_grid.one_boat_still_alive();
	}

	@Override
	public void add_observer(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void remove_all() {
		observers.clear();
	}

	@Override
	public void notify_creation(Grid player_grid, Grid enemy_grid) {
		for (Observer ob : observers) {
			ob.update_creation(player_grid, enemy_grid);
		}
	}

	@Override
	public void notify_cell_change(Grid grid, Cell cell) {
		for (Observer ob : observers) {
			ob.update_cell_change(grid, cell);
		}
	}

	@Override
	public void notify_boat_hitted(Grid grid, Boat boat) {
		for (Observer ob : observers) {
			ob.update_boat_hitted(grid, boat);
		}
	}

	@Override
	public void notify_winner(Winner winner) {
		for (Observer ob : observers) {
			ob.update_winner(winner);
		}
	}

	@Override
	public void notify_enemy_hitted(int i, int j) {
		touched++;
		for (Observer ob : observers) {
			ob.update_enemy_hitted(i, j);
		}
	}

	@Override
	public void notify_message(String message) {
		for (Observer ob : observers) {
			ob.update_message(message);
		}
	}

	@Override
	public void notify_message_splash(String message) {
		for (Observer ob : observers) {
			ob.update_message_splash(message);
		}
	}

}
