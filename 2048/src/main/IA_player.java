package main;

import strategy.IA_interface;

public class IA_player {

	private IA_interface ia;

	/**
	 * Méthode permettant de construire un joueur IA avec une stratégie de base
	 * 
	 * @param strat
	 *            la stratégie de base utilisé par notre joueur
	 */
	public IA_player(IA_interface strat) {
		this.ia = strat;
	}

	/**
	 * Méthode permettant de dire à notre joueur IA de jouer un tour en
	 * utilisateur sa stratégie
	 * 
	 * @param grid
	 *            une référence vers la grille original que l'on souhaite
	 *            modifier via la réflexion de l'ia
	 */
	public void play(Grid grid) {
		this.ia.execute(grid);
	}

	/**
	 * Méthode permettant d'appliquer une stratégie à notre joueur IA
	 * 
	 * @param strat
	 *            la stratégique que l'on souhaite faire adopter à notre joueur
	 *            IA
	 */
	public void set_strategy(IA_interface strat) {
		this.ia = strat;
	}

}
