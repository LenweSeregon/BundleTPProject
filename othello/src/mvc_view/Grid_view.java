package mvc_view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import mvc_model.Grid;
import mvc_model.Tile;
import enums.Owner;

public class Grid_view extends JComponent {

	private int nb_line;
	private int nb_column;
	private int size_cell;
	private Tile_view[][] tiles;

	/**
	 * Constructeur de la classe représentant la grille de jeu en elle meme.
	 * Cette grille contient les différentes vue de cellule et permet à la
	 * première création de les initialiser
	 * 
	 * @param grid
	 *            la grille logique avec laquel on va construire les cellules
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Grid_view(Grid grid, int width, int height) {

		this.nb_line = grid.get_nb_line();
		this.nb_column = grid.get_nb_column();
		this.size_cell = grid.get_size_tile();

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new GridLayout(nb_line, nb_column));

		this.tiles = new Tile_view[nb_line][nb_column];

		Tile[][] logic = grid.get_tiles();
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j] = new Tile_view(logic[i][j].get_pos_x(),
						logic[i][j].get_pos_y(), logic[i][j].get_size_tile(),
						logic[i][j].get_owner());
				tiles[i][j].set_possible_hit(logic[i][j].get_possible_hit());
				tiles[i][j].setHorizontalAlignment(JLabel.CENTER);
				tiles[i][j].setVerticalAlignment(JLabel.CENTER);
				this.add(tiles[i][j]);
			}
		}
	}

	/**
	 * Méthode permettant de savoir un clique a été réalisé sur une cellule
	 * 
	 * @param mouse_x
	 *            la position en X de la cellule
	 * @param mouse_y
	 *            la position en Y de la cellule
	 * @return la dimension avec les index (x,j) si le clique a trouvé une case,
	 *         null sinon
	 */
	public Dimension click_on(int mouse_x, int mouse_y) {
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j].mouse_in(mouse_x, mouse_y))
					return new Dimension(i, j);
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de choisir le propriétaire d'une cellule graphique
	 * 
	 * @param i
	 *            l'index sur l'axe Y
	 * @param j
	 *            l'index sur l'axe X
	 * @param owner
	 *            le nouveau propriétaire de la case
	 */
	public void set_owner(int i, int j, Owner owner) {
		tiles[i][j].set_owner(owner);
	}

	/**
	 * Méthode permettant de dire à la classe qui la grille a changé de manière
	 * générale et qu'il faut mettre à jour les propriétaire et les coups
	 * possibles
	 * 
	 * @param grid
	 *            la grille logique qui va servir de référence pour la
	 *            reconstruction
	 */
	public void set_grid_change(Tile[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {

				tiles[i][j].set_owner(grid[i][j].get_owner());
				tiles[i][j].set_possible_hit(grid[i][j].get_possible_hit());
				this.repaint();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(3));
		// Dessin des lignes
		for (int i = 0; i <= nb_line; i++) {
			g2.drawLine(0, i * (getHeight() / nb_line), getWidth(), i
					* getHeight() / nb_line);

		}

		// Dessin des colonnes
		for (int i = 0; i <= nb_column; i++) {
			g2.drawLine(i * (getWidth() / nb_column), 0, i
					* (getWidth() / nb_column), getHeight());

		}
	}
}
