package observer_pattern;

import java.util.Vector;

import model.Abstract_bonus;
import model.Ball;
import model.Border_board;
import model.Player_position;
import model.Racket;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observeur à observable
	 * 
	 * @param ob
	 *            l'observeur que l'on souhaite ajouter
	 */
	public void add_observer(Observer ob);

	/**
	 * Méthode permettant de retirer tout les observeurs
	 */
	public void remove_all();

	/**
	 * Méthode permettant de notifier de la création de notre modele
	 * 
	 * @param width
	 *            la largeur de notre modele
	 * @param height
	 *            la hauteur de notre modele
	 * @param racket_1
	 *            la raquette gauche du modele
	 * @param racket_2
	 *            la raquette droite du modele
	 * @param ball
	 *            le vecteur de balle du modele
	 * @param bonus
	 *            le vecteur de bonus du modele
	 * @param up
	 *            la bordure haute du modele
	 * @param down
	 *            la bordure basse du modele
	 */
	public void notify_creation(int width, int height, Racket racket_1,
			Racket racket_2, Vector<Ball> ball, Vector<Abstract_bonus> bonus,
			Border_board up, Border_board down);

	/**
	 * Méthode permettant de notifier le déplacement d'une raquette
	 * 
	 * @param racket
	 */
	public void notify_racket_move(Racket racket);

	/**
	 * Méthode permettant de notifier la suppression du balle
	 * 
	 * @param ball
	 *            la balle supprimée
	 */
	public void notify_ball_dead(Ball ball);

	/**
	 * Méthode permettant de notifier le mouvement d'une balle
	 * 
	 * @param ball
	 *            la balle en déplacement
	 */
	public void notify_ball_move(Ball ball);

	/**
	 * Méthode permettant de notifier la création d'un bonus
	 * 
	 * @param bonus
	 *            le bonus crée
	 */
	public void notify_bonus_creation(Abstract_bonus bonus);

	/**
	 * Méthode permettant de notifier le changement de score d'un des joueurs du
	 * plateau
	 * 
	 * @param score
	 *            le score du joueur
	 * @param player
	 *            le joueur dont le score a changé
	 */
	public void notify_score(int score, Player_position player);

	/**
	 * Méthode permettant de notifier de la destruction du bonus
	 */
	public void notify_bonus_destruction();

	/**
	 * Méthode permettant de notifier de la victoire d'un joueur
	 * 
	 * @param winner
	 *            le gagnant de la partie
	 */
	public void notify_victory(Player_position winner);

}
