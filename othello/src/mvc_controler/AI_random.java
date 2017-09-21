package mvc_controler;

import java.awt.Dimension;
import java.util.Random;
import java.util.Vector;

import mvc_model.Grid;

public class AI_random implements AI_strategy {

	private Grid grid;
	private Grid_controler controler;

	/**
	 * Constructeur de la classe représentant l'IA basique, c'est une dire une
	 * AI qui joue de manièr aléatoire sur une des cases disponibles
	 * 
	 * @param grid
	 *            référence vers la grille principale sur laquel jouer
	 * @param controler
	 *            référence vers le controleur principal du jeu
	 */
	public AI_random(Grid grid, Grid_controler controler) {
		this.grid = grid;
		this.controler = controler;
	}

	@Override
	public void play() {
		grid.calcul_possible_hit();
		Vector<Dimension> possible_play = grid.get_all_possible_hit_indexes();
		Random randint = new Random();

		Dimension chosen = possible_play.get(randint.nextInt(possible_play
				.size()));
		controler.click_tile(chosen.width, chosen.height);
	}
}
