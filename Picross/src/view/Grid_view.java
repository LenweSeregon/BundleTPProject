package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grid_view extends JPanel {

	private int width;
	private int height;
	private int pos_x;
	private int pos_y;
	private int nb_line;
	private int nb_column;
	private int size_cell;
	private Tile_view[][] tiles;

	private static Color grid_color = Color.BLACK;

	/**
	 * Constructeur de la classe qui représente une grille de manière graphique.
	 * Cette classe va permettre de savoir quelle taille donné à ses cases et
	 * aussi de stocker les différentes cases qui sont la représentations
	 * graphiques des cases du plateau de jeu.
	 * 
	 * @param width
	 *            la largeur de la grille
	 * @param height
	 *            la hauteur de la grille
	 * @param nb_line
	 *            le nombre de ligne dans la grille
	 * @param nb_column
	 *            le nombre de colonne dans la grille
	 */
	public Grid_view(int width, int height, int pos_x, int pos_y, int nb_line,
			int nb_column, boolean[][] tiles_representation) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBounds(pos_x, pos_y, width, height);
		this.setBackground(Color.gray);
		this.width = width;
		this.height = height;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_cell = Math.min((width / nb_column), (height / nb_line));
		this.tiles = new Tile_view[nb_line][nb_column];

		this.init_tiles(tiles_representation);
	}

	/**
	 * Méthode permettant de savoir si une case qui se trouve dans la grille a
	 * été cliqué et si c'est le cas, on va mettre dans une classe Dimension les
	 * valeurs d'index X et Y de la case que l'on va renvoyer
	 * 
	 * @param mouse_x
	 *            la position en X de la souris
	 * @param mouse_y
	 *            la position en Y de la souris
	 * @return la dimension avec (X,Y) de la case si il y a eu un clique, null
	 *         sinon
	 */
	public Dimension get_dimension_mouse_click(int mouse_x, int mouse_y) {
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j].mouse_in(mouse_x, mouse_y)) {
					return new Dimension(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de choisir la valeur de est ce que la case a été
	 * appuyé d'une cellule graphique
	 * 
	 * @param i
	 *            l'index I sur l'axe X de la case
	 * @param j
	 *            l'index J sur l'axe Y de la case
	 * @param value
	 *            la valeur que l'on souhaite affeter à la cellule
	 */
	public void set_tile_boolean_value(int i, int j, boolean value) {
		tiles[i][j].set_has_been_pressed(value);
		repaint();
	}

	/**
	 * Méthode interne permettant d'initialiser les représentations graphiques
	 * des cases à partir des valeurs du modèle
	 * 
	 * @param tiles_repres
	 *            les valeurs du modèle
	 */
	private void init_tiles(boolean[][] tiles_repres) {

		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j] = new Tile_view(j * size_cell, i * size_cell,
						size_cell, size_cell, tiles_repres[i][j]);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);
		this.setOpaque(false);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j].paintComponent(g);
			}
		}

		g2.setColor(grid_color);
		g2.setStroke(new BasicStroke(3));
		// Dessin des lignes
		for (int i = 0; i <= nb_line; i++) {
			g2.drawLine(0, i * size_cell, size_cell * nb_column, i * size_cell);
		}

		// Dessin des colonnes
		for (int i = 0; i <= nb_column; i++) {
			g2.drawLine(i * size_cell, 0, i * size_cell, size_cell * nb_line);
		}

		g2.setStroke(new BasicStroke(1));
	}

}
