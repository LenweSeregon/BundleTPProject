package mvc_view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import enums.Owner;

public class Tile_view extends JLabel {

	private int pos_x;
	private int pos_y;
	private int size_tile;
	private Owner owner;
	private boolean possible_hit;

	/**
	 * Constructeur de la classe représentant une cellule à son état graphique.
	 * Cette classe va s'occuper de dessiner une case en restant les régles
	 * d'affichage et va s'adapter en fonction de son posseseur
	 * 
	 * @param p_x
	 *            la position X de la cellule
	 * @param p_y
	 *            la position Y de la cellule
	 * @param size_tile
	 *            la taille de la celulle
	 * @param owner
	 *            le possesur de la cellule
	 */
	public Tile_view(int p_x, int p_y, int size_tile, Owner owner) {
		this.pos_x = p_x;
		this.pos_y = p_y;
		this.size_tile = size_tile;
		this.owner = owner;
		this.possible_hit = false;
	}

	/**
	 * Méthode permettant de choisir un propriétaire pour la cellule
	 * 
	 * @param owner
	 *            le nouveau propriétaire
	 */
	public void set_owner(Owner owner) {
		this.owner = owner;
		this.repaint();
		this.revalidate();
	}

	/**
	 * Méthode permettant de définir si une case est possible à jouer
	 * graphiquement parlant
	 * 
	 * @param b
	 *            vrai si elle est touchable, faux sinon
	 */
	public void set_possible_hit(boolean b) {
		this.possible_hit = b;
	}

	/**
	 * Méthode permettant de récupérer le propriétaire de la cellule
	 * 
	 * @return
	 */
	public Owner get_owner() {
		return this.owner;
	}

	/**
	 * Méthode permettant de savoir si la souris se trouve à l'intérieur de la
	 * cellule. En faisant un simple de texte de detection de point dans un
	 * rectangle
	 * 
	 * @param mouse_x
	 *            la position en X de la souris
	 * @param mouse_y
	 *            la position en Y de la souris
	 * @return vrai si la souris est à l'intérieur, faux sinon
	 */
	public boolean mouse_in(int mouse_x, int mouse_y) {
		int pos_x = getX() + 83;
		int pos_y = getY() + 115;

		return mouse_x >= pos_x && mouse_x <= pos_x + getWidth()
				&& mouse_y >= pos_y && mouse_y <= pos_y + getHeight();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (possible_hit) {
			g.setColor(Color.WHITE);
			g.drawOval(5, 5, getWidth() - 10, getHeight() - 10);
		} else if (owner == Owner.WHITE) {
			g.setColor(Color.WHITE);
			g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
		} else if (owner == Owner.BLACK) {
			g.setColor(Color.BLACK);
			g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
		}
	}

}
