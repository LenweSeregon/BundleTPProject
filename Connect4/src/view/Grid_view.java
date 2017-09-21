package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import model.Cell;
import model.Owner;
import observer_pattern.Observer;
import controller.Grid_controller;

public class Grid_view extends JPanel implements Observer {

	private Cell[][] ref_board;
	private Cell ref_next_piece;
	private int pos_next_piece;
	private Window ref_win;

	private int nb_line;
	private int nb_column;
	private int size_circle;
	private Grid_controller controller;

	private JLabel message_box;
	private JLabel title;
	private JButton replay;
	private JButton exit;
	private boolean win;

	private Color color_first_player;

	/**
	 * Constructeur de la classe Grid_view qui représente dans la vue dans notre
	 * modèle vue controller
	 * 
	 * @param nb_line
	 *            nombre de ligne qui compose notre vue de la grille
	 * @param nb_column
	 *            nobmre de colonne qui compose notre vue de la grille
	 * @param size_circle
	 *            la taille d'un cellule en pixel
	 * @param controller
	 *            une référence vers le controller pour le MVC
	 * @param ref_win
	 *            une référence vers la fenetre pour envoyer des instructions
	 *            comme recommencer une partie ou quitter
	 * @param color_first_player
	 *            la couleur du premier joueur
	 */
	public Grid_view(int nb_line, int nb_column, int size_circle,
			Grid_controller controller, Window ref_win, Color color_first_player) {
		this.color_first_player = color_first_player;
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_circle = size_circle;
		this.ref_board = null;
		this.ref_next_piece = null;
		this.controller = controller;
		this.ref_win = ref_win;

		this.win = false;

		this.title = new JLabel("Puissance 4");
		title.setFont(title.getFont().deriveFont(35.f));
		title.setOpaque(true);
		title.setBackground(Color.white);
		title.setForeground(Color.black);
		title.setBounds(nb_column * size_circle + ((nb_column + 1) * 20) + 10,
				0, 300,
				((nb_line * size_circle + ((nb_line + 1) * 20) + 100)) / 8);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setBorder(new LineBorder(Color.black, 5));

		this.message_box = new JLabel();
		message_box.setFont(title.getFont().deriveFont(20.f));
		message_box.setOpaque(true);
		message_box.setBackground(Color.white);
		message_box.setForeground(Color.black);
		message_box.setBounds(nb_column * size_circle + ((nb_column + 1) * 20)
				+ 10, 110, 300,
				((nb_line * size_circle + ((nb_line + 1) * 20) + 100)) / 2);
		message_box.setHorizontalAlignment(JLabel.CENTER);
		message_box.setVerticalAlignment(JLabel.CENTER);
		message_box.setBorder(new LineBorder(Color.black, 5));

		this.replay = new JButton("Rejouer");
		this.replay.setHorizontalAlignment(JButton.CENTER);
		this.replay.setVerticalAlignment(JButton.CENTER);
		this.replay.setBackground(Color.white);
		this.replay.setForeground(Color.black);
		this.replay.setBorder(new LineBorder(Color.black, 5));
		this.replay
				.setBounds(nb_column * size_circle + ((nb_column + 1) * 20)
						+ 10, 130 + ((nb_line * size_circle
						+ ((nb_line + 1) * 20) + 100)) / 2, 130, ((nb_line
						* size_circle + ((nb_line + 1) * 20) + 100)) / 8);
		this.replay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_main_menu();
			}
		});

		this.exit = new JButton("Quitter");
		this.exit.setHorizontalAlignment(JButton.CENTER);
		this.exit.setVerticalAlignment(JButton.CENTER);
		this.exit.setBackground(Color.white);
		this.exit.setForeground(Color.black);
		this.exit.setBorder(new LineBorder(Color.black, 5));
		this.exit
				.setBounds(nb_column * size_circle + ((nb_column + 1) * 20)
						+ 180, 130 + ((nb_line * size_circle
						+ ((nb_line + 1) * 20) + 100)) / 2, 130, ((nb_line
						* size_circle + ((nb_line + 1) * 20) + 100)) / 8);
		this.exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		});

		this.setLayout(null);
		this.setPreferredSize(new Dimension(nb_column * size_circle
				+ ((nb_column + 1) * 20 + 310), nb_line * size_circle
				+ ((nb_line + 1) * 20) + 100));

		this.add(title);
		this.add(message_box);
		this.add(replay);
		this.add(exit);

		Action left_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!win) {
					controller.move_next_piece_to(-1);
				}
			}
		};

		Action right_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!win) {
					controller.move_next_piece_to(1);
				}
			}
		};

		Action enter_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!win) {
					controller.ask_piece_placement();
				}
			}
		};

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getActionMap().put("left", left_pressed);

		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getActionMap().put("right", right_pressed);

		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
		this.getActionMap().put("enter", enter_pressed);

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		if (this.ref_board != null) {

			g.setColor(Color.blue);
			g.fillRect(0, 0, getWidth(), getHeight());

			if (this.ref_next_piece != null) {
				int posX = this.pos_next_piece
						* ref_next_piece.get_size_circle();
				int posY = 35;
				if (ref_next_piece.get_owner() == Owner.YELLOW) {
					if (this.color_first_player == Color.red) {
						g.setColor(Color.red);
					} else {
						g.setColor(Color.yellow);
					}
				} else {
					if (this.color_first_player == Color.red) {
						g.setColor(Color.yellow);
					} else {
						g.setColor(Color.red);
					}
				}
				g.fillOval(posX + 20 + (this.pos_next_piece * 20), posY,
						ref_next_piece.get_size_circle(),
						ref_next_piece.get_size_circle());
			}

			for (int j = 0; j < ref_board.length; j++) {
				for (int i = 0; i < ref_board[j].length; i++) {
					if (this.ref_board[j][i].get_owner() == Owner.NONE) {
						g.setColor(Color.white);
						g.fillOval(i * this.size_circle + ((i + 1) * 20), j
								* this.size_circle + ((j + 1) * 20) + 90,
								this.size_circle, this.size_circle);
					} else if (this.ref_board[j][i].get_owner() == Owner.YELLOW) {
						if (this.color_first_player == Color.red) {
							g.setColor(Color.red);
						} else {
							g.setColor(Color.yellow);
						}
						g.fillOval(i * this.size_circle + ((i + 1) * 20), j
								* this.size_circle + ((j + 1) * 20 + 90),
								this.size_circle, this.size_circle);
					} else {
						if (this.color_first_player == Color.red) {
							g.setColor(Color.yellow);
						} else {
							g.setColor(Color.red);
						}
						g.fillOval(i * this.size_circle + ((i + 1) * 20), j
								* this.size_circle + ((j + 1) * 20 + 90),
								this.size_circle, this.size_circle);
					}
				}
			}

			if (this.win) {
				g.setColor(new Color(255, 255, 255, 200));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}
	}

	@Override
	public void update_grid(Cell[][] board) {
		this.ref_board = board;
		this.repaint();
	}

	@Override
	public void update_piece_move(Cell piece, int pos) {
		this.ref_next_piece = piece;
		this.pos_next_piece = pos;
		this.repaint();
	}

	@Override
	public void update_message_box(String message) {
		this.message_box.setText(message);
	}

	@Override
	public void update_victory(Owner owner) {
		if (owner == Owner.NONE) {
			this.message_box.setText("Egalité, aucun vainqueur");
		} else {
			if (owner == Owner.RED) {
				if (this.color_first_player == Color.red) {
					this.message_box.setText("Victoire du jaune");
				} else {
					this.message_box.setText("Victoire du rouge");
				}
			} else {
				if (this.color_first_player == Color.red) {
					this.message_box.setText("Victoire du rouge");
				} else {
					this.message_box.setText("Victoire du jaune");
				}
			}

		}
		this.win = true;
		this.repaint();
	}
}
