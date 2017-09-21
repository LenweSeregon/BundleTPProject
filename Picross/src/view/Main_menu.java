package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import utils.Resources;

public class Main_menu extends JPanel {

	private int width;
	private int height;
	private Window ref_win;

	private JButton[] all_buttons;
	private JLabel[] all_images;

	private int current = 0;

	private static Color writing_color = new Color(155, 91, 0);

	/**
	 * Constructeur de la classe qui représente le menu principal du jeu. Ce
	 * menu met en place et permet de choisir des actions pour créer une
	 * nouvelle grille, jouer sur une certaine grille ou alors de quitter
	 * 
	 * @param width
	 *            la largeur du menu principal
	 * @param height
	 *            la hauteur du menu principal
	 * @param ref_win
	 *            une référence sur la fenetre principale
	 */
	public Main_menu(int width, int height, Window ref_win) {
		this.width = width;
		this.height = height;
		this.ref_win = ref_win;
		all_buttons = new JButton[3];
		all_images = new JLabel[3];

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());

		this.build_header(1.0f, 0.12f);
		this.build_center(1.0f, 0.76f);
		this.build_footer(1.0f, 0.12f);
		this.bind_keyboard_event();
	}

	/**
	 * Méthode privée permettant de lier les différents évènements du menu pour
	 * que celui ci réponde à ces derniers et demande au controleur d'agir en
	 * conséquence
	 */
	private void bind_keyboard_event() {
		@SuppressWarnings("serial")
		Action up_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				to_up();
			}
		};

		@SuppressWarnings("serial")
		Action down_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				to_down();
			}
		};

		@SuppressWarnings("serial")
		Action enter_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (current == 0) {
					ref_win.launch_menu_choser();
				} else if (current == 1) {
					ref_win.launch_config_creation();
				} else if (current == 2) {
					ref_win.exit();
				}
			}
		};

		@SuppressWarnings("serial")
		Action escape_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		};

		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
		this.getActionMap().put("up", up_pressed);

		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		this.getActionMap().put("down", down_pressed);

		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
		this.getActionMap().put("enter", enter_pressed);

		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
		this.getActionMap().put("escape", escape_pressed);

	}

	/**
	 * Méthode privée permettant de constuire le haut de la fenêtre principale
	 * 
	 * @param width_modifier
	 *            le modificateur de largeur que l'on souhaite appliquer par
	 *            rapport à la largeur de base pour obtenir la largeur de ce
	 *            panel
	 * @param height_modifier
	 *            le modificateur de hauteur que l'on souhaite appliquer par
	 *            rapport à la hauteur de base pour obtenir la hauteur de ce
	 *            panel
	 */
	private void build_header(float width_modifier, float height_modifier) {

		int width_header = (int) (this.width * width_modifier);
		int height_header = (int) (this.height * height_modifier);

		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		header.setPreferredSize(new Dimension(width_header, height_header));
		header.setOpaque(false);

		JLabel title = new JLabel("Picross");
		title.setPreferredSize(new Dimension(width_header, height_header));
		title.setFont(Resources.lotr.deriveFont(Font.BOLD, 80.f));
		title.setForeground(writing_color);
		title.setOpaque(false);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		header.add(title, BorderLayout.CENTER);

		this.add(header, BorderLayout.NORTH);
	}

	/**
	 * Méthode privée permettant de constuire le centre de la fenêtre principale
	 * 
	 * @param width_modifier
	 *            le modificateur de largeur que l'on souhaite appliquer par
	 *            rapport à la largeur de base pour obtenir la largeur de ce
	 *            panel
	 * @param height_modifier
	 *            le modificateur de hauteur que l'on souhaite appliquer par
	 *            rapport à la hauteur de base pour obtenir la hauteur de ce
	 *            panel
	 */
	private void build_center(float width_modifier, float height_modifier) {
		int width_center = (int) (this.width * width_modifier);
		int height_center = (int) (this.height * height_modifier);

		JPanel center = new JPanel();
		center.setOpaque(false);
		center.setPreferredSize(new Dimension(width_center, height_center));
		center.setLayout(new BorderLayout());

		JPanel space_left = new JPanel();
		int w_left = (int) (width_center * 0.2);
		int h_left = (int) height_center;
		space_left.setOpaque(false);
		space_left.setPreferredSize(new Dimension(w_left, h_left));

		JPanel space_right = new JPanel();
		int w_right = (int) (width_center * 0.2);
		int h_right = (int) height_center;
		space_right.setOpaque(false);
		space_right.setPreferredSize(new Dimension(w_right, h_right));

		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(0, 0, 0, 150));
		int w_center = (int) (width_center * 0.6);
		int h_center = (int) (height_center);
		GridLayout layout_buttons = new GridLayout(3, 2);
		layout_buttons.setHgap(40);
		layout_buttons.setVgap(40);
		buttons.setBorder(new LineBorder(writing_color, 10));
		buttons.setLayout(layout_buttons);
		buttons.setPreferredSize(new Dimension(w_center, h_center));

		JButton play = new JButton("Jouer");
		play.setForeground(Color.GRAY);
		play.setHorizontalAlignment(JButton.RIGHT);
		play.setVerticalAlignment(JButton.CENTER);
		play.setFont(Resources.lotr.deriveFont(Font.BOLD, 45.F));
		play.setFocusPainted(false);
		play.setBorderPainted(false);
		play.setContentAreaFilled(false);
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_menu_choser();
			}
		});
		all_buttons[0] = play;

		JButton create = new JButton("Creer");
		create.setHorizontalAlignment(JButton.RIGHT);
		create.setVerticalAlignment(JButton.CENTER);
		create.setFont(Resources.lotr.deriveFont(Font.BOLD, 45.F));
		create.setForeground(writing_color);
		create.setFocusPainted(false);
		create.setBorderPainted(false);
		create.setContentAreaFilled(false);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_config_creation();
			}
		});
		all_buttons[1] = create;

		JButton quit = new JButton("Quitter");
		quit.setHorizontalAlignment(JButton.RIGHT);
		quit.setVerticalAlignment(JButton.CENTER);
		quit.setFont(Resources.lotr.deriveFont(Font.BOLD, 45.F));
		quit.setForeground(writing_color);
		quit.setFocusPainted(false);
		quit.setBorderPainted(false);
		quit.setContentAreaFilled(false);
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		});
		all_buttons[2] = quit;

		Image img = new ImageIcon(Resources.sword).getImage();
		Image newimg = img.getScaledInstance(210, (int) (50),
				java.awt.Image.SCALE_SMOOTH);

		JLabel lb1 = new JLabel();
		lb1.setIcon(new ImageIcon(newimg));
		lb1.setVisible(true);
		all_images[0] = lb1;

		JLabel lb2 = new JLabel();
		lb2.setIcon(new ImageIcon(newimg));
		lb2.setVisible(false);
		all_images[1] = lb2;

		JLabel lb3 = new JLabel();
		lb3.setIcon(new ImageIcon(newimg));
		lb3.setVisible(false);
		all_images[2] = lb3;

		buttons.add(play);
		buttons.add(lb1);
		buttons.add(create);
		buttons.add(lb2);
		buttons.add(quit);
		buttons.add(lb3);

		center.add(space_left, BorderLayout.WEST);
		center.add(buttons, BorderLayout.CENTER);
		center.add(space_right, BorderLayout.EAST);

		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Méthode privée permettant de constuire le bas de la fenêtre principale
	 * 
	 * @param width_modifier
	 *            le modificateur de largeur que l'on souhaite appliquer par
	 *            rapport à la largeur de base pour obtenir la largeur de ce
	 *            panel
	 * @param height_modifier
	 *            le modificateur de hauteur que l'on souhaite appliquer par
	 *            rapport à la hauteur de base pour obtenir la hauteur de ce
	 *            panel
	 */
	private void build_footer(float width_modifier, float height_modifier) {
		int width_south = (int) (this.width * width_modifier);
		int height_south = (int) (this.height * height_modifier);

		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1, 3));
		south.setPreferredSize(new Dimension(width_south, height_south));
		south.setOpaque(false);

		JLabel copyright = new JLabel("Copyright : Nicolas Serf");
		copyright.setHorizontalAlignment(JLabel.CENTER);
		copyright.setVerticalAlignment(JLabel.CENTER);
		copyright.setFont(Resources.lotr.deriveFont(Font.BOLD, 20.F));
		copyright.setForeground(writing_color);

		JLabel contact = new JLabel("serf.nicolas@gmail.com");
		contact.setHorizontalAlignment(JLabel.CENTER);
		contact.setVerticalAlignment(JLabel.CENTER);
		contact.setFont(Resources.lotr.deriveFont(Font.BOLD, 20.F));
		contact.setForeground(writing_color);

		south.add(copyright);
		south.add(new JLabel());
		south.add(contact);

		this.add(south, BorderLayout.SOUTH);
	}

	/**
	 * Méthode privée appelé uniquement en interne lors du clique de la fleche
	 * haute pour mettre à jour les différents composants graphiques et de
	 * demander un affichage propre et cohérent
	 */
	private void to_up() {
		if (current > 0) {
			all_images[current].setVisible(false);
			all_buttons[current].setForeground(writing_color);
			current--;
			all_buttons[current].setForeground(Color.gray);
			all_images[current].setVisible(true);
			repaint();
			revalidate();
		}
	}

	/**
	 * Méthode privée appelé uniquement en interne lors du clique de la fleche
	 * basse pour mettre à jour les différents composants graphiques et de
	 * demander un affichage propre et cohérent
	 */
	private void to_down() {
		if (current < 2) {
			all_images[current].setVisible(false);
			all_buttons[current].setForeground(writing_color);
			current++;
			all_buttons[current].setForeground(Color.gray);
			all_images[current].setVisible(true);
			repaint();
			revalidate();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.map_1, 0, 0, getWidth(), getHeight(), null);
	}
}
