package model;

import java.util.Random;

public class Ball_bonus extends Abstract_bonus {

	private boolean already_apply;

	/**
	 * Construction de la clases ball bonus, ce bonus permet simplement de créer
	 * une nouvelle balle à l'endroit du bonus et qui part avec un angle
	 * complétement aléatoire
	 * 
	 * @param pos_x
	 *            la position x du bonus
	 * @param pos_y
	 *            la position y du bonus
	 * @param radius
	 *            le rayon du bonus
	 */
	public Ball_bonus(int pos_x, int pos_y, int radius) {
		super(pos_x, pos_y, radius, Bonus_mode.NEW_BALL);
		already_apply = false;
	}

	@Override
	public void apply_bonus(Board board) {
		// Applying bonus speed on ball
		Random rand = new Random();
		int random = rand.nextInt(360);
		board.add_ball(pos_x, pos_y, random);
		board.remove_bonus(this);
	}
}
