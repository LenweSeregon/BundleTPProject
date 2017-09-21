package model;

public class Game_level {

	private String identifiant;
	private String name;
	private int nb_line;
	private int nb_column;
	private boolean succeed;

	/**
	 * Constructeur de la classe représentant un niveau de jeu, celui ci dispose
	 * juste des informations nécessaire pour construire un niveau. Ces
	 * informations doivent être couplés avec d'autres classes pour avoir un jeu
	 * fonctionnel bien entendu
	 * 
	 * @param id
	 *            l'identifiant du niveau
	 * @param n
	 *            le nom du niveau
	 * @param l
	 *            le nombre de ligne du niveau
	 * @param c
	 *            le nombre de colonne du niveau
	 * @param s
	 *            est ce que le niveau a été complété
	 */
	public Game_level(String id, String n, int l, int c, boolean s) {
		this.identifiant = id;
		this.name = n;
		this.nb_line = l;
		this.nb_column = c;
		this.succeed = s;
	}

	/**
	 * Méthode permettant de savoir le nombre de ligne qui compose le niveau
	 * 
	 * @return le nombre de ligne
	 */
	public int get_nb_line() {
		return nb_line;
	}

	/**
	 * Méthode permettant de savoir le nombre de colonne qui compose le niveau
	 * 
	 * @return le nombre de colonne
	 */
	public int get_nb_column() {
		return nb_column;
	}

	/**
	 * Méthode permettant de savoir si le niveau à déjà été réussi ou non
	 * 
	 * @return vrai si le niveau à été réussi, faux sinon
	 */
	public boolean is_succeed() {
		return succeed;
	}

	/**
	 * Méthode permettant de récupérer l'identifiant du niveau
	 * 
	 * @return l'identifiant du niveau
	 */
	public String get_identifiant() {
		return identifiant;
	}

	/**
	 * Méthode permettant de récupérer le nom du niveau
	 * 
	 * @return le nom du niveau
	 */
	public String get_name() {
		return name;
	}
}
