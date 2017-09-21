package observer_pattern;

import model.Cell;
import model.Owner;

public interface Observer {

	/**
	 * Méthode permettant d'update graphiquement la grille en envoyant la
	 * nouvelle grille qui vient d'être modifié
	 * 
	 * @param board
	 *            la grille qui vient d'etre modifiée et qu'il faut envoyer
	 */
	public void update_grid(Cell[][] board);

	/**
	 * Méthode permettant d'update graphiquement la piece qui se trouve au
	 * dessus de la grille, pour signifier qu'on a changé de joueur donc de
	 * couleur ou bien la position de la piece
	 * 
	 * @param piece
	 *            la piece qui est toujours la meme qui représente la référence
	 *            pour ajouter une piece
	 * @param pos
	 *            la position de cette piece
	 */
	public void update_piece_move(Cell piece, int pos);

	/**
	 * Méthode permettant d'update le panneau d'affichage pour que les
	 * utilisateurs sachent qui doit jouer, qui a gagné, etc...
	 * 
	 * @param message
	 *            le message que l'on souhaite envoyer a la vue pour qu'elle
	 *            ajoute ce message
	 */
	public void update_message_box(String message);

	/**
	 * Méthode permettant d'update la vue en spécifiant un vainqueur, lors ce
	 * que la vue recoit cette notification, elle désactive les différents
	 * commandes et l'on ne peut plus que rejouer ou arreter le jeu
	 * 
	 * @param owner
	 *            le propriétaire qui a gagné la partie
	 */
	public void update_victory(Owner owner);
}
