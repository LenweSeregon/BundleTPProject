package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Play_mode;

public class Main_menu extends JPanel {

	private Image background;
	private Window ref_win;

	/**
	 * Construction de la classe Main_menu qui permet d'initialiser les
	 * différents elements graphiques et les réponses aux évènements de clique
	 * sur les boutons
	 * 
	 * @param ref_win
	 *            une référence de la fenetre
	 */
	public Main_menu(Window ref_win) {

		this.setLayout(null);
		this.setPreferredSize(new Dimension(1200, 800));

		this.background = Toolkit.getDefaultToolkit().createImage(
				"./resources/menu_bg.jpg");
		this.ref_win = ref_win;

		JButton jouer = new JButton("Jouer");
		jouer.setOpaque(true);
		jouer.setBackground(Color.black);
		jouer.setForeground(Color.white);
		jouer.setPreferredSize(new Dimension(300, 100));
		jouer.setHorizontalAlignment(JButton.CENTER);
		jouer.setVerticalAlignment(JButton.CENTER);
		jouer.setBounds(100, 150, 300, 100);

		jouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_config_menu(Play_mode.HUMAN);
			}
		});

		JButton ai = new JButton("AI");
		ai.setPreferredSize(new Dimension(300, 100));
		ai.setOpaque(true);
		ai.setBackground(Color.black);
		ai.setForeground(Color.white);
		ai.setHorizontalAlignment(JButton.CENTER);
		ai.setVerticalAlignment(JButton.CENTER);
		ai.setBounds(100, 350, 300, 100);

		ai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_config_menu(Play_mode.AI);
			}
		});

		JButton quit = new JButton("Quitter");
		quit.setOpaque(true);
		quit.setBackground(Color.black);
		quit.setForeground(Color.white);
		quit.setPreferredSize(new Dimension(300, 100));
		quit.setHorizontalAlignment(JButton.CENTER);
		quit.setVerticalAlignment(JButton.CENTER);
		quit.setBounds(100, 550, 300, 100);

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		});

		this.add(jouer);
		this.add(ai);
		this.add(quit);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.background, 0, 0, 1200, 800, this);
	}
}
