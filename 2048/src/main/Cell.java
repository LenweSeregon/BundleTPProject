package main;

public class Cell {

	private int value;

	/**
	 * Constructeur de la classe Cell, elle initialise une case avec une valeur
	 * sous forme logique qui sera utilisé dans un tableau
	 * 
	 * @param value
	 *            La valeur de la case
	 */
	public Cell(int value) {
		this.value = value;
	}

	/**
	 * Methode qui permet de mettre une valeur dans la case
	 * 
	 * @param value
	 *            La valeur que l'on souhaite mettre dans la case
	 */
	public void set_value(int value) {
		this.value = value;
	}

	/**
	 * Methode qui permet de récupérer la valeur qui se trouve dans la case
	 * 
	 * @return valeur dans la case
	 */
	public int get_value() {
		return this.value;
	}
}
