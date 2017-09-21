package model;

import java.util.Vector;

public class Column_indexes {

	private String identifiant;
	private int index_column;
	private Vector<Integer> indexes;

	/**
	 * Constructeur de la classe représentant un indicateur de colonne pour
	 * aider l'utilisateur à résoudre le puzzle. Cette classe a un identifiant
	 * qui est le même que celui du niveau pour pouvoir être retrouver dans le
	 * conteneur de tous les indicateurs de colonne
	 * 
	 * @param identifiant
	 *            l'identifiant de l'indicateur de colonne
	 * @param index_column
	 *            la colonne a laquelle figure cet indicateur
	 */
	public Column_indexes(String identifiant, int index_column) {
		this.identifiant = identifiant;
		this.index_column = index_column;
		this.indexes = new Vector<Integer>();
	}

	/**
	 * Méthode permettant de récupérer sous la forme d'un vecteur de nombre les
	 * différents indices qui se trouve sur une colonne donnée
	 * 
	 * @return un vecteurs des différents indices
	 */
	public Vector<Integer> get_indexes() {
		return indexes;
	}

	/**
	 * Méthode permettant de récupérer l'identifiant qui a été rattraché à
	 * l'indicateur de colonne représentant un niveau
	 * 
	 * @return l'identifiant de l'indicateur de colonne
	 */
	public String get_identifiant() {
		return identifiant;
	}

	/**
	 * Méthode permettant de récupérer l'index de colonne qui est représenté par
	 * cette instance de classe
	 * 
	 * @return l'indice de colonne
	 */
	public int get_index_column() {
		return index_column;
	}

	/**
	 * Méthode permettant d'ajouter à notre indicateur de colonne un indice de
	 * colonne qu'il doit représenter
	 * 
	 * @param index
	 *            l'indice de colonne que l'on souhaite ajouter
	 */
	public void add_index(int index) {
		indexes.add(index);
	}

}
