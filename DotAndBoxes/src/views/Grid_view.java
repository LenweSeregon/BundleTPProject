package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Cell;
import models.Cell_owner;
import models.Player;
import observer_pattern.Observer_grid;
import controllers.Grid_controller;

@SuppressWarnings("serial")
public class Grid_view extends JPanel implements Observer_grid {

	@SuppressWarnings("unused")
	private Grid_controller controller;
	private Cell[][] reference_grid;
	private Window ref_win;

	private int size_cell;
	private int nb_line;
	private int nb_column;

	/**
	 * Construction de la classe Grid_view, il permet simplement d'initialiser
	 * les différents attributs et de créer un mouseListener sur notre fenetre
	 * qui appelera le controlleur à chaque clique éxecuté
	 * 
	 * @param nb_line
	 *            le nombre de ligne dans notre grille graphique
	 * @param nb_column
	 *            le nombre de colonne dans notre grille graphique
	 * @param size_cell
	 *            la taille des cellule de notre grille graphique
	 * @param controller
	 *            une référence vers le controller en intéraction avec le modèle
	 *            de la grille
	 * @param ref_win
	 *            une référence vers la fenetre principale à laquelle on peut
	 *            envoyer des ordres tellement que changer de contentPane etc...
	 */
	public Grid_view(int nb_line, int nb_column, int size_cell,
			Grid_controller controller, Window ref_win) {

		this.ref_win = ref_win;
		this.controller = controller;
		this.reference_grid = null;
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_cell = size_cell;

		this.setPreferredSize(new Dimension(nb_line * size_cell, nb_column
				* size_cell));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.mouse_click_manager(e);
			}
		});

	}

	/**
	 * Méthode surchargée de JPanel, elle s'occupe de dessiner notre plateau de
	 * jeu à chaque fois que la classe est notifié d'un changement dans ce
	 * dernier
	 */
	public void paintComponent(Graphics g2) {

		Graphics2D g = (Graphics2D) g2;

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (this.reference_grid != null) {

			for (int i = 0; i < this.nb_line; i++) {
				for (int j = 0; j < this.nb_column; j++) {
					g.setStroke(new BasicStroke(8));
					g.setColor(Color.black);

					// Draw top border
					Cell_owner top_owner = this.reference_grid[i][j]
							.get_up_owner();
					g.setColor(get_associated_color(top_owner));
					g.drawLine(j * size_cell + 8, i * size_cell,
							(j * size_cell) + size_cell - 8, i * size_cell);

					// Draw down border
					Cell_owner down_owner = this.reference_grid[i][j]
							.get_down_owner();
					g.setColor(get_associated_color(down_owner));
					g.drawLine(j * size_cell + 8, (i * size_cell) + size_cell,
							(j * size_cell) + size_cell - 8, (i * size_cell)
									+ size_cell);

					// Draw right border

					Cell_owner right_owner = this.reference_grid[i][j]
							.get_right_owner();
					g.setColor(get_associated_color(right_owner));
					g.drawLine((j * size_cell) + size_cell, i * size_cell + 8,
							(j * size_cell) + size_cell, (i * size_cell)
									+ size_cell - 8);

					// Draw left border
					Cell_owner left_owner = this.reference_grid[i][j]
							.get_left_owner();
					g.setColor(get_associated_color(left_owner));
					g.drawLine(j * size_cell, (i * size_cell) + size_cell - 8,
							j * size_cell, i * size_cell + 8);

					// Owner
					Cell_owner cell_owner = this.reference_grid[i][j]
							.get_cell_owner();
					if (cell_owner != Cell_owner.NONE) {
						g.setColor(get_associated_color(cell_owner));
						g.drawLine(j * size_cell + (size_cell / 5), i
								* size_cell + (size_cell / 5), j * size_cell
								+ size_cell - (size_cell / 5), i * size_cell
								+ size_cell - (size_cell / 5));

						g.drawLine(j * size_cell + size_cell - (size_cell / 5),
								i * size_cell + (size_cell / 5), j * size_cell
										+ (size_cell / 5), i * size_cell
										+ size_cell - (size_cell / 5));
					}
				}
			}
		}
	}

	/**
	 * Méthode permettant de récupérer la couleur par rapport à un joueur donné
	 * 
	 * @param owner
	 *            le joueur dont l'on souhaite connaitre la couleur
	 * @return retourne la couleur qui est associé au joueur en paramètre
	 */
	public Color get_associated_color(Cell_owner owner) {

		switch (owner) {
		case PLAYER_1:
			return Color.blue;
		case PLAYER_2:
			return Color.red;
		case PLAYER_3:
			return Color.orange;
		case PLAYER_4:
			return Color.green;
		case GRID:
			return Color.black;
		default:
			return Color.black;
		}
	}

	@Override
	public void receive_update(Cell[][] tab) {
		this.reference_grid = tab;
		this.repaint();
	}

	@Override
	public void receive_winner_update(Cell_owner winner) {
		JOptionPane.showMessageDialog(null, "Le joueur " + winner
				+ " a gagné !", "Partie terminée",
				JOptionPane.INFORMATION_MESSAGE);

		int option = JOptionPane.showConfirmDialog(null, "Rejouer",
				"Voulez vous refaire une partie ?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (option == JOptionPane.OK_OPTION) {
			this.ref_win.activate_ask_menu();
		} else {
			this.ref_win.exit();
		}

	}

	@Override
	public void receive_score_update(Player player) {
		// Nothing to do
	}

}
