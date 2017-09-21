package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Game_level;
import pattern_observer.Observer_menu;
import utils.Resources;
import controler.Controler_menu_choser;

@SuppressWarnings("serial")
public class Menu_level_choser extends JPanel implements Observer_menu {

	private int width;
	private int height;
	private int indexer;
	private Vector<Game_level_choser_view> levels_view;

	private JPanel center;
	private CardLayout layout_levels;
	private JButton btn_left;
	private JButton btn_right;

	private Controler_menu_choser controler;
	private Window ref_win;

	private static Color writing_color = new Color(155, 91, 0);

	/**
	 * Constructeur de classe contenant le choix des niveaux pour le picross.
	 * Cette classe s'assure de construire un ensemble cohérent avec des
	 * méthodes internes
	 * 
	 * @param width
	 *            la largeur du menu
	 * @param height
	 *            la hauteur du menu
	 */
	public Menu_level_choser(int width, int height,
			Controler_menu_choser controler, Window ref_win) {

		this.width = width;
		this.height = height;
		this.controler = controler;
		this.indexer = 0;
		this.levels_view = new Vector<Game_level_choser_view>();
		this.ref_win = ref_win;

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(255, 255, 255, 255));

		this.build_header(1.0f, 0.12f);
		this.build_center(0.6f, 0.76f, null);
		this.build_left(0.20f, 0.76f);
		this.build_right(0.20f, 0.76f);
		this.build_footer(1.0f, 0.12f);

		this.bind_keyboard_event();
	}

	/**
	 * Méthode privée permettant de lier les différents évènements du menu pour
	 * que celui ci réponde à ces derniers et demande au controleur d'agir en
	 * conséquence
	 */
	private void bind_keyboard_event() {

		Action left_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				indexer_to_left();
			}
		};

		Action right_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				indexer_to_right();
			}
		};

		Action enter_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_level_chosen(levels_view.get(indexer)
						.get_identifiant());
			}
		};

		Action escape_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_main_menu();
			}
		};

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getActionMap().put("left", left_pressed);

		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getActionMap().put("right", right_pressed);

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

		JLabel title = new JLabel("Choississez votre niveau");
		title.setPreferredSize(new Dimension(width_header, height_header));
		title.setFont(Resources.lotr.deriveFont(Font.BOLD, 70.f));
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
	private void build_center(float width_modifier, float height_modifier,
			Vector<Game_level> levels) {
		int width_center = (int) (this.width * width_modifier);
		int height_center = (int) (this.height * height_modifier);

		center = new JPanel();
		center.setPreferredSize(new Dimension(width_center, height_center));
		center.setBorder(new LineBorder(writing_color, 7));
		center.setBackground(new Color(255, 255, 255, 180));
		layout_levels = new CardLayout();
		center.setLayout(layout_levels);

		// Creation des differents panel de niveau
		if (levels != null) {
			for (Game_level lvl : levels) {
				levels_view.add(new Game_level_choser_view(width_center,
						height_center, lvl.get_identifiant(), lvl.get_name(),
						lvl.get_nb_line(), lvl.get_nb_column(), lvl
								.is_succeed(), ref_win));

				center.add(levels_view.lastElement(), lvl.get_identifiant());
			}
			layout_levels.show(center, levels_view.firstElement()
					.get_identifiant());
		} else {
			JLabel empty = new JLabel("Aucun niveau ...");
			empty.setHorizontalAlignment(JLabel.CENTER);
			empty.setVerticalAlignment(JLabel.CENTER);
			empty.setFont(Resources.lotr.deriveFont(35.F));

			center.add(empty);
		}

		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Méthode privée permettant de constuire la partie gauche de la fenêtre
	 * principale
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
	private void build_left(float width_modifier, float height_modifier) {
		int width_left = (int) (this.width * width_modifier);
		int height_left = (int) (this.height * height_modifier);

		JPanel left = new JPanel();
		left.setBorder(new EmptyBorder(3, 3, 3, 3));
		left.setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(width_left, height_left));
		left.setOpaque(false);

		JPanel space_up = new JPanel();
		space_up.setOpaque(false);
		space_up.setPreferredSize(new Dimension(width_left,
				(int) (height_left * 0.4)));

		JPanel space_down = new JPanel();
		space_down.setOpaque(false);
		space_down.setPreferredSize(new Dimension(width_left,
				(int) (height_left * 0.4)));

		Image img = new ImageIcon(Resources.left_arrow).getImage();
		Image newimg = img.getScaledInstance(width_left - 6,
				(int) (height_left * 0.2) - 45, java.awt.Image.SCALE_SMOOTH);

		btn_left = new JButton("");
		btn_left.setPreferredSize(new Dimension(width_left,
				(int) (height_left * 0.2)));
		btn_left.setIcon(new ImageIcon(newimg));
		btn_left.setFocusPainted(false);
		btn_left.setBorderPainted(false);
		btn_left.setContentAreaFilled(false);
		btn_left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				indexer_to_left();
			}
		});

		left.add(space_up, BorderLayout.NORTH);
		left.add(btn_left, BorderLayout.CENTER);
		left.add(space_down, BorderLayout.SOUTH);

		this.add(left, BorderLayout.WEST);
	}

	/**
	 * Méthode privée permettant de constuire la partie droite de la fenêtre
	 * principale
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
	private void build_right(float width_modifier, float height_modifier) {
		int width_right = (int) (this.width * width_modifier);
		int height_right = (int) (this.height * height_modifier);

		JPanel right = new JPanel();
		right.setBorder(new EmptyBorder(3, 3, 3, 3));
		right.setLayout(new BorderLayout());
		right.setPreferredSize(new Dimension(width_right, height_right));
		right.setOpaque(false);

		JPanel space_up = new JPanel();
		space_up.setOpaque(false);
		space_up.setPreferredSize(new Dimension(width_right,
				(int) (height_right * 0.4)));

		JPanel space_down = new JPanel();
		space_down.setOpaque(false);
		space_down.setPreferredSize(new Dimension(width_right,
				(int) (height_right * 0.4)));

		Image img = new ImageIcon(Resources.right_arrow).getImage();
		Image newimg = img.getScaledInstance(width_right - 6,
				(int) (height_right * 0.2) - 45, java.awt.Image.SCALE_SMOOTH);

		btn_right = new JButton("");
		btn_right.setPreferredSize(new Dimension(width_right,
				(int) (height_right * 0.2)));
		btn_right.setIcon(new ImageIcon(newimg));
		btn_right.setFocusPainted(false);
		btn_right.setBorderPainted(false);
		btn_right.setContentAreaFilled(false);
		btn_right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				indexer_to_right();
			}
		});

		right.add(space_up, BorderLayout.NORTH);
		right.add(btn_right);
		right.add(space_down, BorderLayout.SOUTH);

		this.add(right, BorderLayout.EAST);
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

		JButton back = new JButton("Retour");
		back.setHorizontalAlignment(JButton.CENTER);
		back.setVerticalAlignment(JButton.CENTER);
		back.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
		back.setForeground(writing_color);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_main_menu();
			}
		});

		JButton leave = new JButton("Quitter");
		leave.setHorizontalAlignment(JButton.CENTER);
		leave.setVerticalAlignment(JButton.CENTER);
		leave.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
		leave.setForeground(writing_color);
		leave.setFocusPainted(false);
		leave.setBorderPainted(false);
		leave.setContentAreaFilled(false);
		leave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		});

		south.add(back);
		south.add(new JLabel());
		south.add(leave);

		this.add(south, BorderLayout.SOUTH);
	}

	/**
	 * Méthode privée permettant de faire défiler les menus vers la gauche. La
	 * vue s'assure de ne jamais dépasser les limites imposer par le nombre de
	 * levels qui sont présents. La méthode s'assure aussi d'afficher les
	 * flèches quand il le faut
	 */
	private void indexer_to_left() {
		if (indexer > 1) {
			btn_left.setVisible(true);
		} else {
			btn_left.setVisible(false);
		}

		if (indexer > 0) {
			btn_right.setVisible(true);
			indexer--;
			controler.switch_level_choser_panel(indexer);
		}
	}

	/**
	 * Méthode privée permettant de faire défiler les menus vers la droite. La
	 * vue s'assure de ne jamais dépasser les limites imposer par le nombre de
	 * levels qui sont présents. La méthode s'assure aussi d'afficher les
	 * flèches quand il le faut
	 */
	private void indexer_to_right() {
		if (indexer < levels_view.size() - 2) {
			btn_right.setVisible(true);
		} else {
			btn_right.setVisible(false);
		}

		if (indexer < levels_view.size() - 1) {
			btn_left.setVisible(true);
			indexer++;
			controler.switch_level_choser_panel(indexer);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.map_1, 0, 0, getWidth(), getHeight(), null);
	}

	@Override
	public void update_loaded_levels(Vector<Game_level> levels) {
		if (levels.size() == 0) {
			this.build_center(0.6f, 0.7f, null);
		} else {
			this.build_center(0.6f, 0.7f, levels);
		}

		if (levels.size() == 0 || levels.size() == 1) {
			btn_left.setVisible(false);
			btn_right.setVisible(false);
		} else if (levels.size() == 2) {
			btn_left.setVisible(false);
			btn_right.setVisible(true);
		} else {
			btn_left.setVisible(false);
		}
		this.revalidate();
	}

	@Override
	public void update_change_level_choser(String identifier_wanted) {
		this.layout_levels.show(this.center, identifier_wanted);

		this.center.setBackground(new Color(255, 255, 255, 180));
		this.revalidate();
		this.repaint();
	}
}
