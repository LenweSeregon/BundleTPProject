package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Menu extends JPanel {

	/**
	 * Méthode permettatn de construire la classe menu, les interactions
	 * possibles sur les boutons
	 * 
	 * @param width
	 *            la largeur de notre menu
	 * @param height
	 *            la hauteur de notre menu
	 * @param refWin
	 *            la référence sur notre fenetre pour envoyer les evenements sur
	 *            la fenetre lors de l'appuie des boutons
	 */
	public Menu(int width, int height, Window refWin) {
		GridLayout manager = new GridLayout(4, 1);
		manager.setHgap(30);
		manager.setVgap(70);
		this.setLayout(manager);
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(new EmptyBorder(50, 100, 50, 100));

		JLabel title = new JLabel("2048");
		title.setFont(title.getFont().deriveFont(64.0f));
		title.setMaximumSize(new Dimension(200, 150));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		JButton play = new JButton("Jouer");
		play.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		play.setMaximumSize(new Dimension(200, 150));
		play.setBackground(Color.RED);
		play.setForeground(Color.WHITE);
		play.setOpaque(true);
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refWin.launch_player_game(false);
			}
		});

		JButton IA = new JButton("IA");
		IA.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		IA.setMaximumSize(new Dimension(200, 150));
		IA.setBackground(Color.yellow);
		IA.setForeground(Color.BLACK);
		IA.setOpaque(true);
		IA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refWin.launcher_IA_game(false);
			}
		});

		JButton exit = new JButton("Quitter");
		exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		exit.setMaximumSize(new Dimension(200, 150));
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.setOpaque(true);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refWin.exit();
			}
		});

		this.add(title);
		this.add(play);
		this.add(IA);
		this.add(exit);

	}
}
