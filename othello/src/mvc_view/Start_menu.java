package mvc_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class Start_menu extends JPanel {

	private int width;
	private int height;
	private Window wind;

	/**
	 * Constructeur de la classe représentant le menu principale de
	 * l'application
	 * 
	 * @param width
	 *            la largeur du menu principal
	 * @param height
	 *            la hauteur du menu principal
	 * @param win
	 *            une référence vers la fenetre principal pour réaliser
	 *            différents appels
	 */
	public Start_menu(int width, int height, Window win) {

		this.width = width;
		this.height = height;
		this.wind = win;

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());

		build_header(1.0f, 0.15f);
		build_center(1.0f, 0.7f);
		build_footer(1.0f, 0.15f);

		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("Menu");
		JMenuItem load = new JMenuItem("Charger dernière partie");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.load_game();
			}
		});

		JMenuItem exit = new JMenuItem("Quitter");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.exit();
			}
		});

		file.add(load);
		file.add(exit);
		bar.add(file);
		wind.setJMenuBar(bar);
	}

	/**
	 * Méthode privée de la classe permettant de construire le haut de page de
	 * la fenetre en fonction de certains critères de dimension donnés en
	 * paramètre
	 * 
	 * @param w_mod
	 *            le modifieur en largeur
	 * @param h_mod
	 *            le modifieur en hauteur
	 */
	private void build_header(float w_mod, float h_mod) {
		int w_header = (int) (width * w_mod);
		int h_header = (int) (height * h_mod);

		JPanel header = new JPanel();
		header.setBackground(Color.BLACK);
		header.setPreferredSize(new Dimension(w_header, h_header));
		header.setLayout(new BorderLayout());
		header.setBorder(new MatteBorder(0, 0, 5, 0, Color.WHITE));

		JLabel title = new JLabel("Othello");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 40.f));

		header.add(title, BorderLayout.CENTER);

		this.add(header, BorderLayout.NORTH);
	}

	/**
	 * Méthode privée de la classe permettant de construire le center de la page
	 * de la fenetre en fonction de certains critères de dimension donnés en
	 * paramètre
	 * 
	 * @param w_mod
	 *            le modifieur en largeur
	 * @param h_mod
	 *            le modifieur en hauteur
	 */
	private void build_center(float w_mod, float h_mod) {
		JPanel center = new JPanel();
		center.setBorder(new EmptyBorder(70, 150, 70, 150));
		GridLayout l = new GridLayout(4, 1);
		l.setHgap(40);
		l.setVgap(40);
		center.setLayout(l);

		JButton pl_vs_pl = new JButton("Joueur contre joueur");
		pl_vs_pl.setHorizontalAlignment(JLabel.CENTER);
		pl_vs_pl.setVerticalAlignment(JLabel.CENTER);
		pl_vs_pl.setContentAreaFilled(false);
		pl_vs_pl.setFocusPainted(false);
		pl_vs_pl.setFont(pl_vs_pl.getFont().deriveFont(Font.BOLD, 20.f));
		pl_vs_pl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.launch_game();
			}
		});

		JButton pl_vs_ai = new JButton("Joueur contre IA");
		pl_vs_ai.setHorizontalAlignment(JLabel.CENTER);
		pl_vs_ai.setVerticalAlignment(JLabel.CENTER);
		pl_vs_ai.setContentAreaFilled(false);
		pl_vs_ai.setFocusPainted(false);
		pl_vs_ai.setFont(pl_vs_ai.getFont().deriveFont(Font.BOLD, 20.f));
		pl_vs_ai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.launch_game_vs_ai();
			}
		});

		JButton load = new JButton("Charger dernière partie");
		load.setHorizontalAlignment(JLabel.CENTER);
		load.setVerticalAlignment(JLabel.CENTER);
		load.setContentAreaFilled(false);
		load.setFocusPainted(false);
		load.setFont(load.getFont().deriveFont(Font.BOLD, 20.f));
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.load_game();
			}
		});

		JButton exit = new JButton("Quitter");
		exit.setHorizontalAlignment(JLabel.CENTER);
		exit.setVerticalAlignment(JLabel.CENTER);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		exit.setFont(exit.getFont().deriveFont(Font.BOLD, 20.f));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.exit();
			}
		});

		center.add(pl_vs_pl);
		center.add(pl_vs_ai);
		center.add(load);
		center.add(exit);

		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Méthode privée de la classe permettant de construire le bas de page de la
	 * fenetre en fonction de certains critères de dimension donnés en paramètre
	 * 
	 * @param w_mod
	 *            le modifieur en largeur
	 * @param h_mod
	 *            le modifieur en hauteur
	 */
	private void build_footer(float w_mod, float h_mod) {
		int w_footer = (int) (width * w_mod);
		int h_footer = (int) (height * h_mod);

		JPanel footer = new JPanel();
		footer.setBackground(Color.BLACK);
		footer.setPreferredSize(new Dimension(w_footer, h_footer));
		footer.setLayout(new BorderLayout());
		footer.setBorder(new MatteBorder(5, 0, 0, 0, Color.WHITE));

		JLabel copy = new JLabel("Nicolas Serf - 2017");
		copy.setHorizontalAlignment(JLabel.CENTER);
		copy.setVerticalAlignment(JLabel.CENTER);
		copy.setFont(copy.getFont().deriveFont(Font.BOLD, 25.F));
		copy.setForeground(Color.WHITE);

		footer.add(copy, BorderLayout.CENTER);

		this.add(footer, BorderLayout.SOUTH);
	}

}
