package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.Column_indexes;
import model.Grid;
import model.Line_indexes;
import pattern_observer.Observer_game;
import utils.Resources;
import controler.Game_controler;

@SuppressWarnings("serial")
public class Board_view extends JPanel implements Observer_game {

	private int width;
	private int height;

	private int mouse_x_adder;
	private int mouse_y_adder;

	private JPanel center;
	private Game_controler controler;
	private Window ref_win;
	private Grid_view view;

	private boolean victory;
	private boolean surrended;
	private Chrono chrono;
	volatile private int font_size_end;
	volatile private String message_end;
	volatile private boolean exiting;

	private static Color writing_color = new Color(155, 91, 0);
	private static Color indexes_color_1 = new Color(125, 61, 0, 200);
	private static Color indexes_color_2 = new Color(85, 21, 0, 200);

	public Board_view(int width, int height, Window ref_win,
			Game_controler controler, boolean creation) {
		this.controler = controler;
		this.ref_win = ref_win;
		this.width = width;
		this.height = height;
		this.mouse_x_adder = 0;
		this.mouse_y_adder = 0;
		this.view = null;
		this.victory = false;
		this.exiting = false;
		this.surrended = false;
		this.font_size_end = 100;
		this.message_end = "";

		this.setPreferredSize(new Dimension(width, height));

		this.build_header(1.0f, 0.12f, "Unknow");
		this.build_center(1.0f, 0.76f, null);
		this.build_footer(1.0f, 0.12f, false);

		mouse_y_adder += (width * 0.12);

		this.bind_events(creation);
	}

	/**
	 * Une méthode englobante qui s'occupe de lier les différents écouteurs
	 * potentiels du plateau
	 * 
	 * @param creation
	 *            permet de savoir si les evenements doivent être lié dans un
	 *            esprit de résolution ou de création de grille
	 */
	private void bind_events(boolean creation) {
		this.bind_mouse_event();
		this.bind_keyboard_event(creation);
	}

	/**
	 * Méthode privée permettant de lier les différents évènements du menu pour
	 * que celui ci réponde à ces derniers et demande au controleur d'agir en
	 * conséquence
	 */
	private void bind_keyboard_event(boolean creation) {
		Action escape_pressed = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!victory && !exiting) {
					if (creation) {
						ref_win.launch_main_menu();
					} else {
						surrended = true;
						launch_ending_message("Vous avez abandonné !");
					}
				}
			}
		};

		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
		this.getActionMap().put("escape", escape_pressed);
	}

	/**
	 * Méthode privée permettant de lier les différents évènements de la souris
	 * du plateau pour que celui ci réponde à ces derniers et demande au
	 * controleur d'agir en conséquence
	 */
	private void bind_mouse_event() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (!victory) {
					Dimension dim = view.get_dimension_mouse_click(e.getX()
							- mouse_x_adder, e.getY() - mouse_y_adder + 3);
					if (dim != null) {
						controler.click_on_tile(dim.width, dim.height);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
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
	private void build_header(float width_modifier, float height_modifier,
			String title_text) {
		int width_header = (int) (this.width * width_modifier);
		int height_header = (int) (this.height * height_modifier);

		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		header.setPreferredSize(new Dimension(width_header, height_header));
		header.setOpaque(false);

		JLabel title = new JLabel(title_text);
		title.setPreferredSize(new Dimension(width_header, height_header));
		title.setFont(Resources.lotr.deriveFont(Font.BOLD, 40.f));
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
			Grid grid) {
		int width_center = (int) (this.width * width_modifier);
		int height_center = (int) (this.height * height_modifier);

		if (center != null) {
			this.remove(center);
		}

		center = new JPanel();
		center.setOpaque(false);
		center.setPreferredSize(new Dimension(width_center, height_center));
		center.setLayout(new BorderLayout());

		// Grid
		if (grid != null) {

			JPanel up_space = new JPanel();
			int w_up_space = (int) (width_center);
			int h_up_space = (int) (height_center * 0.02);
			up_space.setOpaque(false);
			up_space.setPreferredSize(new Dimension(w_up_space, h_up_space));

			JPanel right_space = new JPanel();
			int w_right_space = (int) (width_center * 0.2);
			int h_right_space = (int) (height_center * 0.96);
			mouse_x_adder += w_right_space;
			right_space.setOpaque(false);
			right_space.setPreferredSize(new Dimension(w_right_space,
					h_right_space));

			JPanel left_space = new JPanel();
			int w_left_space = (int) (width_center * 0.2);
			int h_left_space = (int) (height_center * 0.96);
			left_space.setOpaque(false);
			left_space.setPreferredSize(new Dimension(w_left_space,
					h_left_space));

			JPanel down_space = new JPanel();
			int w_down_space = (int) (width_center);
			int h_down_space = (int) (height_center * 0.02);
			down_space.setOpaque(false);
			down_space.setPreferredSize(new Dimension(w_down_space,
					h_down_space));

			JPanel board = new JPanel();
			int w_board = (int) (width_center * 0.6);
			int h_board = (int) (height_center * 0.96);
			board.setPreferredSize(new Dimension(w_board, h_board));
			board.setBackground(new Color(255, 255, 255, 180));
			board.setOpaque(false);
			board.setLayout(null);

			int nb_line = grid.get_nb_line();
			int nb_column = grid.get_nb_column();

			int w_h_grid = 0;
			if (grid.get_creation()) {
				w_h_grid = Math.min((int) (w_board * 1), (int) (h_board * 1)) - 7;
			} else {
				w_h_grid = Math.min((int) (w_board * 0.70) + 7,
						(int) (h_board * 0.70) + 7);
			}

			int size_cell = Math.min((w_h_grid / nb_column),
					(w_h_grid / nb_line));

			int w_grid = nb_column * size_cell;
			int h_grid = nb_line * size_cell;

			int pos_x = (w_board - w_grid) / 2;
			int pos_y = (h_board - h_grid) / 2;
			if (!grid.get_creation()) {
				pos_x += pos_x;
				pos_y += pos_y;
			}

			this.mouse_x_adder += pos_x;
			this.mouse_y_adder += pos_y;

			view = new Grid_view(w_grid, h_grid, pos_x - 4, pos_y - 3, nb_line,
					nb_column, grid.get_tiles());
			board.add(view);

			if (!grid.get_creation()) {

				int width_line = pos_x - 10;
				int height_line = size_cell;
				Vector<Line_indexes> iV = grid.get_line_indexes();
				Vector<Line_indexes_view> line_view = new Vector<Line_indexes_view>();
				for (int i = 0; i < nb_line; i++) {
					boolean found = false;
					for (int j = 0; j < iV.size(); j++) {
						if (iV.get(j).get_index_line() == i) {
							found = true;
							if (i % 2 == 0) {
								Line_indexes_view v = new Line_indexes_view(7,
										pos_y + (i * size_cell) - 3,
										width_line, height_line,
										indexes_color_2, iV.get(j)
												.get_indexes());
								line_view.add(v);
								board.add(v);
							} else {
								Line_indexes_view v = new Line_indexes_view(7,
										pos_y + (i * size_cell) - 3,
										width_line, height_line,
										indexes_color_1, iV.get(j)
												.get_indexes());
								line_view.add(v);
								board.add(v);
							}
						}
					}
					if (!found) {
						if (i % 2 == 0) {
							Line_indexes_view v = new Line_indexes_view(7,
									pos_y + (i * size_cell) - 3, width_line,
									height_line, indexes_color_2, null);
							board.add(v);
						} else {
							Line_indexes_view v = new Line_indexes_view(7,
									pos_y + (i * size_cell) - 3, width_line,
									height_line, indexes_color_1, null);
							board.add(v);
						}
					}
				}

				int min_size_cell_line = Integer.MAX_VALUE;
				int index_line = -1;
				int m = 0;
				for (Line_indexes_view v : line_view) {
					if (v.get_font_size() < min_size_cell_line) {
						index_line = m;
					}
					m++;
				}

				// Column indexes
				int width_column = size_cell;
				int height_column = pos_y - 9;
				Vector<Column_indexes> iC = grid.get_column_indexes();
				Vector<Column_indexes_view> column_view = new Vector<Column_indexes_view>();
				for (int i = 0; i < nb_column; i++) {
					boolean found = false;
					for (int j = 0; j < iC.size(); j++) {
						if (iC.get(j).get_index_column() == i) {
							found = true;
							if (i % 2 == 0) {
								Column_indexes_view v = new Column_indexes_view(
										pos_x - 4 + (i * size_cell), 7,
										width_column, height_column,
										indexes_color_2, iC.get(j)
												.get_indexes());
								column_view.add(v);
								board.add(v);
							} else {
								Column_indexes_view v = new Column_indexes_view(
										pos_x - 4 + (i * size_cell), 7,
										width_column, height_column,
										indexes_color_1, iC.get(j)
												.get_indexes());
								column_view.add(v);
								board.add(v);
							}
						}
					}
					if (!found) {
						if (i % 2 == 0) {
							Column_indexes_view v = new Column_indexes_view(
									pos_x - 4 + (i * size_cell), 7,
									width_column, height_column,
									indexes_color_2, null);
							board.add(v);
						} else {
							Column_indexes_view v = new Column_indexes_view(
									pos_x - 4 + (i * size_cell), 7,
									width_column, height_column,
									indexes_color_1, null);
							board.add(v);
						}
					}
				}

				int min_size_cell_column = Integer.MAX_VALUE;
				int index_column = -1;
				int p = 0;
				for (Column_indexes_view v : column_view) {
					if (v.get_font_size() < min_size_cell_column) {
						index_column = p;
					}
					p++;
				}

				if (min_size_cell_column < min_size_cell_line) {
					for (Line_indexes_view v : line_view) {
						v.set_font_size(column_view.get(index_column)
								.get_font_size());
					}

					for (Column_indexes_view v : column_view) {
						v.set_font_size(column_view.get(index_column)
								.get_font_size());
					}
				} else {
					for (Line_indexes_view v : line_view) {
						v.set_font_size(line_view.get(index_line)
								.get_font_size());
					}

					for (Column_indexes_view v : column_view) {
						v.set_font_size(line_view.get(index_line)
								.get_font_size());
					}
				}

				chrono = new Chrono(pos_x, pos_y);
				new Thread(chrono).start();
				board.add(chrono);
			}

			center.add(up_space, BorderLayout.NORTH);
			center.add(right_space, BorderLayout.EAST);
			center.add(left_space, BorderLayout.WEST);
			center.add(down_space, BorderLayout.SOUTH);
			center.add(board, BorderLayout.CENTER);
		}

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
	private void build_footer(float width_modifier, float height_modifier,
			boolean creation) {
		int width_south = (int) (this.width * width_modifier);
		int height_south = (int) (this.height * height_modifier);

		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1, 3));
		south.setPreferredSize(new Dimension(width_south, height_south));
		south.setOpaque(false);

		if (creation) {
			JButton save = new JButton("Sauvegarder");
			save.setHorizontalAlignment(JButton.CENTER);
			save.setVerticalAlignment(JButton.CENTER);
			save.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
			save.setForeground(writing_color);
			save.setFocusPainted(false);
			save.setBorderPainted(false);
			save.setContentAreaFilled(false);
			save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controler.save_datas();
					ref_win.launch_main_menu();
				}
			});
			south.add(save);
		} else {
			JButton surrend = new JButton("Abandonner");
			surrend.setHorizontalAlignment(JButton.CENTER);
			surrend.setVerticalAlignment(JButton.CENTER);
			surrend.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
			surrend.setForeground(writing_color);
			surrend.setFocusPainted(false);
			surrend.setBorderPainted(false);
			surrend.setContentAreaFilled(false);
			surrend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					surrended = true;
					launch_ending_message("Vous avez abandonné !");
				}
			});
			south.add(surrend);
		}

		JButton exit = new JButton("Quitter");
		exit.setHorizontalAlignment(JButton.CENTER);
		exit.setVerticalAlignment(JButton.CENTER);
		exit.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
		exit.setForeground(writing_color);
		exit.setFocusPainted(false);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		});

		south.add(new JLabel());
		south.add(exit);

		this.add(south, BorderLayout.SOUTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.map_1, 0, 0, getWidth(), getHeight(), null);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		if (victory || surrended) {
			g2.setColor(new Color(255 - (255 - ((int) (font_size_end * 2.5))),
					255 - (255 - ((int) (font_size_end * 2.5))),
					255 - (255 - ((int) (font_size_end * 2.5))),
					150 + (100 - font_size_end)));
			g2.fillRect(0, 0, getWidth(), getHeight());

			String text = message_end;
			Font font = Resources.lotr.deriveFont(Font.BOLD, font_size_end);
			int text_width = g2.getFontMetrics(font).stringWidth(text);
			int text_height = g2.getFontMetrics(font).getHeight();

			g2.setColor(writing_color);
			g2.setFont(font);
			g2.drawString(text, (width - text_width) / 2,
					(height - text_height) / 2);

			font_size_end--;
		}
	}

	@Override
	public void update_game_creation(Grid grid) {
		this.removeAll();
		if (grid.get_creation()) {
			this.build_header(1.0f, 0.12f, "Creation de " + grid.get_name());
		} else {
			this.build_header(1.0f, 0.12f, grid.get_name());
		}
		this.build_center(1.0f, 0.76f, grid);
		this.build_footer(1.0f, 0.12f, grid.get_creation());

	}

	@Override
	public void update_tile_change(int i, int j, boolean val) {
		view.set_tile_boolean_value(i, j, val);
	}

	@Override
	public void update_victory() {
		victory = true;
		launch_ending_message("Félicitation !");
	}

	/**
	 * Méthode privée de la classe qui est appelé lorsque la partie est gagné
	 * par l'utilisateur ou que celui ci abandonne. La fonction s'occupe de
	 * faire afficher un texte qui diminue de taille petit à petit tout en
	 * noircissant le fond. Lorsque la font a totalement disparut, on revient au
	 * message des niveaux
	 * 
	 * @param message
	 *            le message que l'on souhaite afficher
	 */
	private void launch_ending_message(String message) {
		this.exiting = true;
		this.message_end = message;
		this.font_size_end = 100;
		chrono.set_is_running(false);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (font_size_end >= 0) {
					repaint();

					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
		t.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (victory) {
					exiting = false;
					ref_win.launch_menu_choser();
				} else {
					exiting = false;
					ref_win.launch_menu_choser();
				}
			}

		});
		t2.start();
	}
}
