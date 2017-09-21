package mvc_controler;

import mvc_model.Grid;
import enums.Owner;

public class AI_player {

	private AI_strategy strategy;
	private Owner turn;

	/**
	 * Constructeur de la classe représentant un joueur AI. Ceci-ci dispose
	 * d'une stratégie qu'il a appliquer lorsqu'il joue
	 * 
	 * @param owner
	 *            la position au niveau jeu de notre joueur IA
	 * @param grid
	 *            la référence vers la grille principale
	 * @param controler
	 *            la référence vers le controleur
	 */
	public AI_player(Owner owner, Grid grid, Grid_controler controler) {
		this.turn = owner;
		this.strategy = new AI_random(grid, controler);
	}

	/**
	 * Méthode permettant à tout moment de changer sa stratégie si on a besoin
	 * 
	 * @param strat
	 *            la stratégie que l'on souhaite appliquer à notre joueur IA
	 */
	public void set_strategy(AI_strategy strat) {
		this.strategy = strat;
	}

	/**
	 * Méthode qui va appeller la stratégie pour jouer
	 */
	public void play() {
		strategy.play();
	}

	/**
	 * Récupére le tour du joueur IA pour savoir lorsque celui-ci doit jouer
	 * 
	 * @return la position du joueur
	 */
	public Owner get_turn() {
		return turn;
	}
}
