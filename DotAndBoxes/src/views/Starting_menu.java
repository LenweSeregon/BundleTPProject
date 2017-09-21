package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Starting_menu extends JPanel {

	private int nb_players;
	private int nb_ai;
	private int size_board;
	private Window ref_win;

	/**
	 * Constructeur de la classe Starting_menu, elle initialise simplement tout
	 * les composants en leur donnant des actions spécifiques telles que
	 * recevoir des valeurs utilisateurs ou de demander à la fenetre principale
	 * de lancer le jeu avec les valeurs de l'utilisateur
	 * 
	 * @param width
	 *            La largeur que l'on souhaite pour notre menu
	 * @param height
	 *            la hauteur que l'on souhaite pour notre menu
	 * @param win
	 *            une référence vers la fenetre principale
	 */
	public Starting_menu(int width, int height, Window win) {

		this.ref_win = win;
		this.nb_players = 2;
		this.size_board = 5;
		this.nb_ai = 0;
		this.setLayout(new GridLayout(7, 1));
		this.setPreferredSize(new Dimension(width, height));

		JLabel label_size = new JLabel("Selectionnez la taille de la carte");
		label_size.setFont(label_size.getFont().deriveFont(20.0f));
		label_size.setHorizontalAlignment(JLabel.CENTER);
		label_size.setVerticalAlignment(JLabel.CENTER);

		JSlider slider_size = new JSlider();
		slider_size.setMaximum(20);
		slider_size.setMinimum(3);
		slider_size.setValue(5);
		slider_size.setPaintTicks(true);
		slider_size.setPaintLabels(true);
		slider_size.setMinorTickSpacing(1);
		slider_size.setMajorTickSpacing(1);
		slider_size.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				size_board = Integer.valueOf(((JSlider) event.getSource())
						.getValue());
			}
		});

		JLabel label_player = new JLabel("Selectionnez le nombre de joueurs");
		label_player.setFont(label_player.getFont().deriveFont(20.0f));
		label_player.setHorizontalAlignment(JLabel.CENTER);
		label_player.setVerticalAlignment(JLabel.CENTER);

		JSlider slider_ia = new JSlider();
		slider_ia.setMaximum(1);
		slider_ia.setMinimum(0);
		slider_ia.setValue(0);
		slider_ia.setPaintTicks(true);
		slider_ia.setPaintLabels(true);
		slider_ia.setMinorTickSpacing(10);
		slider_ia.setMajorTickSpacing(1);
		slider_ia.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				nb_ai = Integer.valueOf(((JSlider) event.getSource())
						.getValue());
			}
		});

		JSlider slider_player = new JSlider();
		slider_player.setMaximum(4);
		slider_player.setMinimum(2);
		slider_player.setValue(2);
		slider_player.setPaintTicks(true);
		slider_player.setPaintLabels(true);
		slider_player.setMinorTickSpacing(10);
		slider_player.setMajorTickSpacing(1);
		slider_player.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				nb_players = Integer.valueOf(((JSlider) event.getSource())
						.getValue());
				slider_ia.setMaximum(Integer.valueOf(((JSlider) event
						.getSource()).getValue()) - 1);
				if (slider_ia.getValue() > slider_ia.getMaximum()) {
					slider_ia.setValue(Integer.valueOf(((JSlider) event
							.getSource()).getValue()));
				}
			}
		});

		JLabel label_ai = new JLabel("Selectionnez le nombre de joueurs IA");
		label_ai.setFont(label_player.getFont().deriveFont(20.0f));
		label_ai.setHorizontalAlignment(JLabel.CENTER);
		label_ai.setVerticalAlignment(JLabel.CENTER);

		JButton valid = new JButton("Jouer");
		valid.setBackground(Color.black);
		valid.setForeground(Color.white);
		valid.setMaximumSize(new Dimension(150, 50));
		valid.setMinimumSize(new Dimension(150, 50));
		valid.setSize(new Dimension(150, 50));
		valid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ref_win.activate_player_menu(nb_players, size_board, nb_ai);
			}
		});

		this.add(label_size);
		this.add(slider_size);
		this.add(label_player);
		this.add(slider_player);
		this.add(label_ai);
		this.add(slider_ia);
		this.add(valid);
	}
}
