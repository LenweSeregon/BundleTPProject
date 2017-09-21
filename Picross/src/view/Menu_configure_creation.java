package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import utils.DB;
import utils.Resources;

@SuppressWarnings("serial")
public class Menu_configure_creation extends JPanel {

	private int width;
	private int height;
	private Window ref_win;

	private JTextField name_text;
	private JFormattedTextField line_text;
	private JFormattedTextField column_text;
	private JLabel error;
	private DB db;

	private static Color writing_color = new Color(155, 91, 0);

	/**
	 * Constructeur de la classe représentant l'interface de création d'un
	 * niveau. Cette classe permet de rentrer un nom, un nombre de ligne et un
	 * nombre de colonne. La classe va s'assurer en interne que la validation
	 * est possible, c'est à dire qu'un niveau avec un nom égale n'existe pas
	 * 
	 * @param width
	 *            la largeur du menu
	 * @param height
	 *            la hauteur du menu
	 * @param ref_win
	 *            une référence sur la fenetre principale
	 * @param db
	 *            une référence sur la base de données
	 */
	public Menu_configure_creation(int width, int height, Window ref_win, DB db) {
		this.width = width;
		this.height = height;
		this.ref_win = ref_win;
		this.db = db;

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

		Action enter_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				can_configure_new_creation();
			}
		};

		Action escape_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_main_menu();
			}
		};

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

		JLabel title = new JLabel("Configuration");
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

		JPanel center_content = new JPanel();
		int w_center = (int) (width_center * 0.6);
		int h_center = (int) (height_center);
		center_content.setLayout(new BorderLayout());
		center_content.setBorder(new LineBorder(writing_color, 10));
		center_content.setPreferredSize(new Dimension(w_center, h_center));
		center_content.setBackground(new Color(0, 0, 0, 150));

		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(w_center, h_center - 100));
		GridLayout layout_buttons = new GridLayout(3, 2);
		layout_buttons.setHgap(40);
		layout_buttons.setVgap(120);
		buttons.setLayout(layout_buttons);
		buttons.setBorder(new EmptyBorder(50, 50, 50, 50));
		buttons.setOpaque(false);

		JPanel validate = new JPanel();
		validate.setLayout(new GridLayout(2, 1));
		validate.setPreferredSize(new Dimension(w_center, 100));
		validate.setBorder(new EmptyBorder(0, 100, 0, 100));
		validate.setBackground(new Color(0, 0, 0, 150));
		validate.setOpaque(false);

		JButton create = new JButton("Créer");
		create.setForeground(writing_color);
		create.setHorizontalAlignment(JButton.CENTER);
		create.setVerticalAlignment(JButton.CENTER);
		create.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
		create.setFocusPainted(false);
		create.setBorderPainted(false);
		create.setContentAreaFilled(false);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				can_configure_new_creation();
			}
		});

		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setVisible(false);
		error.setHorizontalAlignment(JLabel.CENTER);
		error.setVerticalAlignment(JLabel.CENTER);
		error.setFont(Resources.lotr.deriveFont(Font.BOLD, 20.F));

		JLabel name = new JLabel("Nom du niveau :");
		name.setForeground(writing_color);
		name.setHorizontalAlignment(JButton.RIGHT);
		name.setVerticalAlignment(JButton.CENTER);
		name.setFont(Resources.lotr.deriveFont(Font.BOLD, 18.F));

		JLabel nb_line = new JLabel("Nombre de lignes :");
		nb_line.setForeground(writing_color);
		nb_line.setHorizontalAlignment(JButton.RIGHT);
		nb_line.setVerticalAlignment(JButton.CENTER);
		nb_line.setFont(Resources.lotr.deriveFont(Font.BOLD, 18.F));

		JLabel nb_column = new JLabel("Nombre de colonnes :");
		nb_column.setForeground(writing_color);
		nb_column.setHorizontalAlignment(JButton.RIGHT);
		nb_column.setVerticalAlignment(JButton.CENTER);
		nb_column.setFont(Resources.lotr.deriveFont(Font.BOLD, 18.F));

		name_text = new JTextField();
		name_text.setHorizontalAlignment(JTextField.CENTER);
		name_text.setFont(Resources.lotr.deriveFont(22.f));
		name_text.setBorder(new LineBorder(writing_color, 5));
		name_text.setBackground(Color.WHITE);
		name_text.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					can_configure_new_creation();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		NumberFormat f1 = NumberFormat.getIntegerInstance();
		f1.setGroupingUsed(false);
		column_text = new JFormattedTextField(f1);
		column_text.setHorizontalAlignment(JTextField.CENTER);
		column_text.setFont(Resources.lotr.deriveFont(Font.BOLD, 22.f));
		column_text.setBorder(new LineBorder(writing_color, 5));
		column_text.setBackground(Color.WHITE);
		column_text.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					can_configure_new_creation();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		NumberFormat f2 = NumberFormat.getIntegerInstance();
		f2.setGroupingUsed(false);
		line_text = new JFormattedTextField(f2);
		line_text.setFont(Resources.lotr.deriveFont(Font.BOLD, 22.f));
		line_text.setHorizontalAlignment(JTextField.CENTER);
		line_text.setBorder(new LineBorder(writing_color, 5));
		line_text.setBackground(Color.WHITE);
		line_text.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					can_configure_new_creation();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		buttons.add(name);
		buttons.add(name_text);
		buttons.add(nb_line);
		buttons.add(line_text);
		buttons.add(nb_column);
		buttons.add(column_text);

		validate.add(create);
		validate.add(error);

		center_content.add(buttons, BorderLayout.NORTH);
		center_content.add(validate, BorderLayout.SOUTH);

		center.add(space_left, BorderLayout.WEST);
		center.add(center_content, BorderLayout.CENTER);
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

		JButton cancel = new JButton("Annuler");
		cancel.setHorizontalAlignment(JButton.RIGHT);
		cancel.setVerticalAlignment(JButton.CENTER);
		cancel.setFont(Resources.lotr.deriveFont(Font.BOLD, 45.F));
		cancel.setForeground(writing_color);
		cancel.setFocusPainted(false);
		cancel.setBorderPainted(false);
		cancel.setContentAreaFilled(false);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_main_menu();
			}
		});

		south.add(cancel);
		south.add(new JLabel());
		south.add(new JLabel());

		this.add(south, BorderLayout.SOUTH);
	}

	/**
	 * Méthode permettant de vérifier si l'on peut créer la grille en fonction
	 * des valeurs des champs. Ceux ci doivent tous etre non null. Le nombre de
	 * lignes et de colonnes doivent être compris entre 0 et 30. Le nom doit
	 * etre non vide et ne pas déjà exister dans la base de données. Si ce n'est
	 * pas accepté, un message d'erreur est envoyé
	 * 
	 * @return vrai si la création est possible, faux sinon
	 */
	private boolean can_configure_new_creation() {
		if (name_text.getText().equals("") || line_text.getText().equals("")
				|| column_text.getText().equals("")) {
			error.setText("Erreur, une/des case(s) sont vides");
			error.setVisible(true);
			this.repaint();
			return false;
		} else {
			int value_line = Integer.valueOf(line_text.getText());
			int value_column = Integer.valueOf(column_text.getText());

			if (!(value_line <= 0 || value_line > 30 || value_column <= 0 || value_column > 30)) {
				if (!db.name_available(name_text.getText())) {
					error.setText("Erreur, nom déja utilisé / invalide");
					error.setVisible(true);
					this.repaint();
					return false;
				} else if (name_text.getText().length() > 20) {
					error.setText("Erreur, nom trop long");
					error.setVisible(true);
					this.repaint();
					return false;
				} else {

					ref_win.launch_level_creation(name_text.getText(),
							value_line, value_column);
					return true;
				}
			} else {
				error.setText("Erreur, mauvaise(s) valeure(s)");
				error.setVisible(true);
				this.repaint();
				return false;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.map_1, 0, 0, getWidth(), getHeight(), null);
	}
}
