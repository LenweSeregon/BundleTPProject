package mvc_model;

import enums.Owner;

public class Tile {

	private int idx_x;
	private int idx_y;
	private int size_tile;
	private int pos_x;
	private int pos_y;
	private Owner owner;
	private boolean possible_hit;

	/**
	 * Constructeur de la classe représentant une cellule dans le modèle du jeu.
	 * Cette cellule contient les différentes informations nécessaires à son
	 * traitement.
	 * 
	 * @param idx_x
	 *            son index en X dans la grille
	 * @param idx_y
	 *            son index en Y dans la grille
	 * @param size_tile
	 *            la taille d'une cellule
	 * @param owner
	 *            le posseseur de la cellule
	 */
	public Tile(int idx_x, int idx_y, int size_tile, Owner owner) {
		this.idx_x = idx_x;
		this.idx_y = idx_y;
		this.size_tile = size_tile;
		this.pos_x = idx_y * size_tile;
		this.pos_y = idx_x * size_tile;
		this.owner = owner;
		this.possible_hit = false;
	}

	/**
	 * Méthode permettant de choisir le propriétaire de la cellule
	 * 
	 * @param owner
	 *            le nouveau propriétaire
	 */
	public void set_owner(Owner owner) {
		this.owner = owner;
	}

	/**
	 * Méthode permettant de choisir si la case est possible à cliqué ou non
	 * 
	 * @param b
	 *            la valeur de la possibilité
	 */
	public void set_possible_hit(boolean b) {
		this.possible_hit = b;
	}

	/**
	 * Méthode permettant de savoir qui est le propriétaire de la cellule
	 * 
	 * @return le propriétaire de la cellule
	 */
	public Owner get_owner() {
		return this.owner;
	}

	/**
	 * Méthode permeettant de récupérer l'index X de la cellule
	 * 
	 * @return l'index X de la cellule
	 */
	public int get_index_x() {
		return this.idx_x;
	}

	/**
	 * Méthode permettant de récupérer l'index Y de la cellule
	 * 
	 * @return l'index Y de la cellule
	 */
	public int get_index_y() {
		return this.idx_y;
	}

	/**
	 * Méthode permettant de récupérer la position X en pixel de la cellule
	 * 
	 * @return la position X en pixel
	 */
	public int get_pos_x() {
		return this.pos_x;
	}

	/**
	 * Méthode permettant de récupérer la position Y en pixel de la cellule
	 * 
	 * @return la position Y en pixel
	 */
	public int get_pos_y() {
		return this.pos_y;
	}

	/**
	 * Méthode permettant de récuérer la taille de la cellule
	 * 
	 * @return
	 */
	public int get_size_tile() {
		return this.size_tile;
	}

	/**
	 * Méthode permettant de savoir si sur la cellule il y a un coup possible à
	 * jouer
	 * 
	 * @return vrai si il y a un coup possible, faux sinon
	 */
	public boolean get_possible_hit() {
		return this.possible_hit;
	}

	/**
	 * Méthode permettant de récupérer une copie de l'instant de la cellule qui
	 * est identique à celle ci
	 * 
	 * @return la copie de la cellule
	 */
	public Tile get_copy() {
		Tile tile = new Tile(this.idx_x, this.idx_y, this.size_tile, this.owner);
		tile.set_possible_hit(this.get_possible_hit());
		return tile;
	}
}
