package model;

import java.util.Vector;

public class Line_indexes {

	private String identifiant;
	private int index_line;
	private Vector<Integer> indexes;

	/**
	 * Constructeur de la classe représentant un indicateur de ligne pour aider
	 * l'utilisateur à résoudre le puzzle. Cette classe a un identifiant qui est
	 * le même que celui du niveau pour pouvoir être retrouver dans le conteneur
	 * de tous les indicateurs de lignes
	 * 
	 * @param identifiant
	 *            l'identifiant de l'indicateur de ligne
	 * @param index_line
	 *            la ligne a laquelle figure cet indicateur
	 */
	public Line_indexes(String identifiant, int index_line) {
		this.identifiant = identifiant;
		this.index_line = index_line;
		this.indexes = new Vector<Integer>();
	}

	/**
	 * Méthode permettant de récupérer sous la forme d'un vecteur de nombre les
	 * différents indices qui se trouve sur une ligne donnée
	 * 
	 * @return un vecteurs des différents indices
	 */
	public Vector<Integer> get_indexes() {
		return indexes;
	}

	/**
	 * Méthode permettant de récupérer l'identifiant qui a été rattraché à
	 * l'indicateur de ligne représentant un niveau
	 * 
	 * @return l'identifiant de l'indicateur de ligne
	 */
	public String get_identifiant() {
		return identifiant;
	}

	/**
	 * Méthode permettant de récupérer l'index de ligne qui est représenté par
	 * cette instance de classe
	 * 
	 * @return l'indice de ligne
	 */
	public int get_index_line() {
		return index_line;
	}

	/**
	 * Méthode permettant d'ajouter à notre indicateur de ligne un indice de
	 * ligne qu'il doit représenter
	 * 
	 * @param index
	 *            l'indice de ligne que l'on souhaite ajouter
	 */
	public void add_index(int index) {
		indexes.add(index);
	}
}
