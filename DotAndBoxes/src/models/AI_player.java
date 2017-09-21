package models;

import strategy_pattern.AI_interface;

public class AI_player {

	private AI_interface strategy;
	private Cell_owner owner;

	public AI_player(AI_interface strat, Cell_owner owner) {
		this.strategy = strat;
		this.owner = owner;

	}

	public void set_strategy(AI_interface strat) {
		this.strategy = strat;
	}

	public void play(Grid grid) {

		this.strategy.execute(grid);

	}

	public Cell_owner get_owner() {
		return this.owner;
	}
}