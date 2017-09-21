package enums;

/**
 * Une énumération représentatnt les valeurs possbiles de possession d'une case.
 * Cette énumérations est aussi utilité à de multiples endroits pour savoir quel
 * est le tour du joueur, quels sont ces coups possibles, etc...
 * 
 * @author nicolasserf
 *
 */
public enum Owner {
	WHITE("blanc"), BLACK("noir"), NONE("none");

	private final String name;

	/**
	 * Constructeur de l'énumération qui va permettre d'associer un string
	 * 
	 * @param s
	 *            le string à associer
	 */
	private Owner(String s) {
		name = s;
	}

	/**
	 * Méthode permettant de comparer la valeur d'énumération avec un string
	 * 
	 * @param otherName
	 *            le string à comparer
	 * @return vrai si les deux strings sont identiques, faux sinon
	 */
	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	/**
	 * Méthode permettant de récupérer notre énumération sous la forme d'un
	 * string
	 */
	public String toString() {
		return this.name;
	}
}
