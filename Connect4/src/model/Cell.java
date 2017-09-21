package model;

public class Cell {

	private Owner owner;
	private int size_circle;

	/**
	 * Construction de la classe Cell, il initialise une cellule avec un
	 * propriétaire et une taille
	 * 
	 * @param owner
	 *            le propriétaire de la case
	 * @param size_circle
	 *            la taille du cercle représentant le pion
	 */
	public Cell(Owner owner, int size_circle) {
		this.owner = owner;
		this.size_circle = size_circle;
	}

	/**
	 * Méthode permettant de récupérer la taille du cercle, IE son rayon
	 * 
	 * @return retourne le rayon du cercle
	 */
	public int get_size_circle() {
		return this.size_circle;
	}

	/**
	 * Méthode permettant de récupérer le propriétaire de la cellule
	 * 
	 * @return le propriétaire de la cellule
	 */
	public Owner get_owner() {
		return this.owner;
	}

	/**
	 * Méthode permettant de choisir un propriétaire pour la cellule
	 * 
	 * @param owner
	 *            le propriétaire que l'on souhaite assigner à la cellule
	 */
	public void set_owner(Owner owner) {
		this.owner = owner;
	}

}
