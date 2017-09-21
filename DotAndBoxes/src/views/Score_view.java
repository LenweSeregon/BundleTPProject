package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import models.Cell;
import models.Cell_owner;
import models.Player;
import observer_pattern.Observer_grid;

@SuppressWarnings("serial")
public class Score_view extends JPanel implements Observer_grid {

	JLabel[] all_scores;

	public Score_view(int nb_player) {

		this.setLayout(new GridLayout(nb_player * 2, 1));
		this.setPreferredSize(new Dimension(200, nb_player * 2 * 100));
		this.setBackground(Color.white);

		all_scores = new JLabel[nb_player];
		for (int i = 0; i < nb_player; i++) {
			all_scores[i] = new JLabel(Cell_owner.values()[i].toString()
					+ "= 0");
			all_scores[i].setHorizontalAlignment(JLabel.CENTER);
			all_scores[i].setVerticalAlignment(JLabel.CENTER);
			all_scores[i].setBorder(new LineBorder(this
					.get_associated_color(Cell_owner.values()[i]), 5));
			this.add(all_scores[i]);
			this.add(new JLabel());
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
	public void receive_winner_update(Cell_owner winner) {
		// Nothing to do

	}

	@Override
	public void receive_update(Cell[][] tab) {
		// Nothing to do

	}

	@Override
	public void receive_score_update(Player player) {
		for (int i = 0; i < Cell_owner.values().length; i++) {
			if (Cell_owner.values()[i].toString() == player.get_owner()
					.toString()) {
				this.all_scores[i].setText(player.get_owner().toString()
						+ " = " + player.get_owned_cell());
			}
		}
	}
}
