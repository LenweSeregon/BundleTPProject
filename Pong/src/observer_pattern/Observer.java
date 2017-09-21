package observer_pattern;

import java.util.Vector;

import model.Abstract_bonus;
import model.Ball;
import model.Border_board;
import model.Player_position;
import model.Racket;

public interface Observer {

	/**
	 * Méthode permettant de recevoit un update pour la création du modele
	 * 
	 * @param width
	 *            la largeur du modele
	 * @param height
	 *            la hauteur du modele
	 * @param racket_1
	 *            la raquette gauche du modele
	 * @param racket_2
	 *            la raquette droite du modele
	 * @param ball
	 *            le vecteur de ball du modele
	 * @param bonus
	 *            le vecteur de bonus du modele
	 * @param up
	 *            la bordure haute du modele
	 * @param down
	 *            la bordure base du modele
	 */
	public void update_creation(int width, int height, Racket racket_1,
			Racket racket_2, Vector<Ball> ball, Vector<Abstract_bonus> bonus,
			Border_board up, Border_board down);

	/**
	 * Méthode permettant etre notifier d'un mouvement de raquette
	 * 
	 * @param racket
	 *            la raquette qui vient d'etre déplacé
	 */
	public void update_racket_move(Racket racket);

	/**
	 * Méthode permettant d'etre notifier d'un mouvement de balle
	 * 
	 * @param ball
	 *            la balle qui vient de se déplacer
	 */
	public void update_ball_move(Ball ball);

	/**
	 * Méthode permettant d'etre notifier de la suppression d'une balle
	 * 
	 * @param ball
	 *            la balle qui vient d'etre supprimé
	 */
	public void update_ball_dead(Ball ball);

	/**
	 * Méthode permettant d'etre notifier d'un changement de score chez l'un des
	 * joueurs
	 * 
	 * @param score
	 *            le nouveau score du joueur en question
	 * @param player
	 *            le joueur dont le score vient d'etre modifié
	 */
	public void update_score(int score, Player_position player);

	/**
	 * Méthode permettant d'etre notifier de la création d'un bonus
	 * 
	 * @param bonus
	 *            le bonus qui vient d'etre crée
	 */
	public void update_bonus_creation(Abstract_bonus bonus);

	/**
	 * Méthode permettant d'etre notifier de la destruction du bonus qui se
	 * trouvait sur le plateau
	 */
	public void update_bonus_destruction();

	/**
	 * Méthode permettant d'etre notifier de la victoire d'un des joueurs
	 * 
	 * @param winner
	 *            le gagnant de la partie
	 */
	public void update_victory(Player_position winner);
}
