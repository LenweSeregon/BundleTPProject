package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {

	/**
	 * Constructeur du menu, il peut de construire le menu principal et
	 * d'associer le bouton jouer avec un evenement qui crée le plateau de jeu
	 * 
	 * @param width
	 *            la largeur du menu
	 * @param height
	 *            la hauteur du menu
	 * @param window
	 *            une référence sur la fenetre principale
	 */
	public Menu(int width, int height, Window window) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		this.setLayout(new BorderLayout());

		JLabel title = new JLabel("Pong");
		title.setPreferredSize(new Dimension(width, height / 4));
		title.setOpaque(false);
		title.setForeground(Color.WHITE);
		title.setFont(title.getFont().deriveFont(55.f));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		JButton button = new JButton("Jouer");
		button.setPreferredSize(new Dimension(width, height / 5));
		button.setOpaque(false);
		button.setForeground(Color.WHITE);
		button.setFont(title.getFont().deriveFont(35.f));
		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.launch_game();
			}
		});

		this.add(title, BorderLayout.NORTH);
		this.add(button, BorderLayout.SOUTH);

	}
}
