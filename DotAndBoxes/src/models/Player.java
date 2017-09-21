package models;

public class Player {

	private int cell_owned;
	private Cell_owner owner;

	/**
	 * Constructeur de la classe Player, elle permet simplement d'assigner à
	 * notre joueur sa valeur logique de possesseur
	 * 
	 * @param owner
	 *            le posseseur qui sera attribué à notre joueur
	 */
	public Player(Cell_owner owner) {
		this.cell_owned = 0;
		this.owner = owner;
	}

	/**
	 * Méthode permettant d'incrémenter le nombre de cellule possédé par le
	 * joueur
	 */
	public void increment_owned_cell() {
		this.cell_owned++;
	}

	/**
	 * Méthode permettant de savoir combien de cellule le joueur posséde
	 * 
	 * @return retourne le nombre de cellule possédée
	 */
	public int get_owned_cell() {
		return this.cell_owned;
	}

	/**
	 * Méthode permettant de savoir la valeur logique du possesseur
	 * 
	 * @return la valeur du possesseur de notre joueur
	 */
	public Cell_owner get_owner() {
		return this.owner;
	}
}
