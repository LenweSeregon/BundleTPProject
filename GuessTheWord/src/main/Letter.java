package main;
public class Letter {

	private char letter;
	private boolean found;

	/**
	 * Constructeur de la classe caractere qui crée une lettre avec un caractere
	 * et le met en non trouvé
	 * 
	 * @param c
	 *            caractere que l'on veut assigner à notre lettre
	 */
	public Letter(char c) {
		this.letter = c;
		this.found = false;
	}

	/**
	 * Methode pour savoir la lettre a été découverte
	 * 
	 * @return retourne vrai si la lettre est découverte, faux sinon
	 */
	public boolean is_found() {
		return this.found;
	}

	/**
	 * Methode pour récuperer le caractère de la classe
	 * 
	 * @return retourne le caractere encapsuler dans la classe Letter
	 */
	public char get_letter() {
		return this.letter;
	}

	/**
	 * Methode permettant de modifier la valeur found de la classe via un
	 * paramètre
	 * 
	 * @param b
	 *            boolean pour mettre la valeur de found
	 */
	public void set_found(boolean b) {
		this.found = b;
	}
}
