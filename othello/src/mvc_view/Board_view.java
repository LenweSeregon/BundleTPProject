package mvc_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import mvc_controler.Grid_controler;
import mvc_model.Grid;
import mvc_model.Tile;
import observer_pattern.Observer;
import enums.Owner;

public class Board_view extends JPanel implements Observer {

	private int width;
	private int height;
	private Grid_controler controler;
	private Grid_view grid_view;
	private Window window;

	private JLabel turn;

	private String winner;
	private boolean display_no_hit;
	private boolean game_end;

	/**
	 * Constructeur de la classe représentant la vue générale du plateau de jeu.
	 * Celle ci détient la vue de la grille qui est l'element central de ce
	 * panel. Finalement, c'est cette fenetre qui va répondre aux différents
	 * evenements déclenché par l'utilisateur
	 * 
	 * @param controler
	 *            une référence sur le controleur du pattern MVC
	 * @param width
	 *            la largeur de la vue du plateau
	 * @param height
	 *            la hauteur de la vue du plateau
	 * @param win
	 *            une référence sur la fenetre principal pour diverses actions
	 */
	public Board_view(Grid_controler controler, int width, int height,
			Window win) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());

		this.width = width;
		this.height = height;
		this.controler = controler;
		this.grid_view = null;
		this.display_no_hit = false;
		this.game_end = false;
		this.window = win;
		this.winner = null;

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_E) {
					System.out.println("E");
					controler.save_game();
				}
			}

		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!display_no_hit && !game_end) {
					Dimension d = grid_view.click_on(e.getX(), e.getY());
					if (d != null) {
						controler.click_tile(d.width, d.height);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		build_header(1.0f, 0.05f);
		build_center(1.0f, 0.9f, null);
		build_footer(1.0f, 0.05f);

		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("Menu");
		JMenuItem save_simple = new JMenuItem("Sauvegarde");
		save_simple.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.save_game();
			}
		});
		JMenuItem save_quit = new JMenuItem("Sauvegarder et quitter");
		save_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.save_game();
				window.launch_start_menu();
			}
		});
		JMenuItem exit = new JMenuItem("Quitter");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.launch_start_menu();
			}
		});

		file.add(save_simple);
		file.add(save_quit);
		file.add(exit);
		bar.add(file);
		window.setJMenuBar(bar);

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
	private void build_header(float modifier_w, float modifier_h) {
		int w_header = (int) (width * modifier_w);
		int h_header = (int) (height * modifier_h);

		JPanel header = new JPanel();
		header.setBackground(Color.BLACK);
		header.setPreferredSize(new Dimension(w_header, h_header));
		header.setLayout(new BorderLayout());
		header.setBorder(new MatteBorder(0, 0, 5, 0, Color.WHITE));

		JLabel title = new JLabel("Othello");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 30.f));

		header.add(title, BorderLayout.CENTER);

		this.add(header, BorderLayout.NORTH);
	}

	/**
	 * Méthode privée de la classe permettant de construire le centre de page de
	 * la fenetre en fonction de certains critères de dimension donnés en
	 * paramètre
	 * 
	 * @param w_mod
	 *            le modifieur en largeur
	 * @param h_mod
	 *            le modifieur en hauteur
	 */
	private void build_center(float modifier_w, float modifier_h, Grid grid) {
		int w_center = (int) (width * modifier_w);
		int h_center = (int) (height * modifier_h);

		JPanel center = new JPanel();
		center.setBackground(new Color(0, 98, 33));
		center.setPreferredSize(new Dimension(w_center, h_center));
		center.setLayout(new BorderLayout());

		if (grid != null) {
			int w_numbers = (int) (w_center * 0.10);
			int h_numbers = (int) (h_center * 0.80);

			int w_letters = (int) (w_center * 0.80);
			int h_letters = (int) (h_center * 0.10);

			int w_space = (int) (w_center * 0.10);
			int h_space = (int) (h_center * 0.10);

			int w_grid = (int) (w_center * 0.80);
			int h_grid = (int) (h_center * 0.80);

			JPanel numbers_left = new JPanel();
			numbers_left.setPreferredSize(new Dimension(w_numbers, h_numbers));
			numbers_left.setBackground(new Color(80, 43, 0));
			// numbers_left.setLayout(new GridLayout(grid.get_nb_line(), 1));
			numbers_left.setLayout(new GridLayout(grid.get_nb_line(), 1));

			for (int i = 1; i <= grid.get_nb_line(); i++) {
				JLabel add = new JLabel("" + i);
				add.setHorizontalAlignment(JLabel.CENTER);
				add.setVerticalAlignment(JLabel.CENTER);
				add.setForeground(Color.WHITE);
				add.setFont(add.getFont().deriveFont(Font.BOLD, 20.f));
				numbers_left.add(add);
			}

			JPanel numbers_right = new JPanel();
			numbers_right.setPreferredSize(new Dimension(w_numbers, h_numbers));
			numbers_right.setBackground(new Color(80, 43, 0));
			// numbers_right.setLayout(new GridLayout(grid.get_nb_line(), 1));
			numbers_right.setLayout(new GridLayout(grid.get_nb_line(), 1));

			for (int i = 1; i <= grid.get_nb_line(); i++) {
				JLabel add = new JLabel("" + i);
				add.setHorizontalAlignment(JLabel.CENTER);
				add.setVerticalAlignment(JLabel.CENTER);
				add.setForeground(Color.WHITE);
				add.setFont(add.getFont().deriveFont(Font.BOLD, 20.f));
				numbers_right.add(add);
			}

			JPanel up = new JPanel();
			up.setBackground(new Color(80, 43, 0));
			up.setLayout(new BorderLayout());

			JPanel space_left_up = new JPanel();
			JPanel space_right_up = new JPanel();
			space_left_up.setPreferredSize(new Dimension(w_space, h_space));
			space_right_up.setPreferredSize(new Dimension(w_space, h_space));
			space_left_up.setOpaque(false);
			space_right_up.setOpaque(false);

			JPanel letters_up = new JPanel();
			letters_up.setPreferredSize(new Dimension(w_letters, h_letters));
			letters_up.setOpaque(false);
			letters_up.setLayout(new GridLayout(1, grid.get_nb_column()));

			for (int i = 0; i < grid.get_nb_column(); i++) {
				JLabel add = new JLabel("" + (char) ('A' + i));
				add.setHorizontalAlignment(JLabel.CENTER);
				add.setVerticalAlignment(JLabel.CENTER);
				add.setForeground(Color.WHITE);
				add.setFont(add.getFont().deriveFont(Font.BOLD, 20.f));
				letters_up.add(add);
			}

			up.add(space_left_up, BorderLayout.WEST);
			up.add(letters_up, BorderLayout.CENTER);
			up.add(space_right_up, BorderLayout.EAST);

			JPanel down = new JPanel();
			down.setBackground(new Color(80, 43, 0));
			down.setLayout(new BorderLayout());

			JPanel space_left_down = new JPanel();
			JPanel space_right_down = new JPanel();
			space_left_down.setPreferredSize(new Dimension(w_space, h_space));
			space_right_down.setPreferredSize(new Dimension(w_space, h_space));
			space_left_down.setOpaque(false);
			space_right_down.setOpaque(false);

			JPanel letters_down = new JPanel();
			letters_down.setPreferredSize(new Dimension(w_letters, h_letters));
			letters_down.setBackground(Color.MAGENTA);
			letters_down.setOpaque(false);
			letters_down.setLayout(new GridLayout(1, grid.get_nb_column()));

			down.add(space_left_down, BorderLayout.WEST);
			down.add(space_right_down, BorderLayout.EAST);
			down.add(letters_down, BorderLayout.CENTER);

			for (int i = 0; i < grid.get_nb_column(); i++) {
				JLabel add = new JLabel("" + (char) ('A' + i));
				add.setHorizontalAlignment(JLabel.CENTER);
				add.setVerticalAlignment(JLabel.CENTER);
				add.setForeground(Color.WHITE);
				add.setFont(add.getFont().deriveFont(Font.BOLD, 20.f));
				letters_down.add(add);
			}

			grid_view = new Grid_view(grid, w_grid, h_grid);
			grid_view.setOpaque(false);
			grid_view.setPreferredSize(new Dimension(w_grid, h_grid));

			center.add(numbers_left, BorderLayout.WEST);
			center.add(numbers_right, BorderLayout.EAST);
			center.add(up, BorderLayout.NORTH);
			center.add(down, BorderLayout.SOUTH);
			center.add(grid_view, BorderLayout.CENTER);

		}
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
	private void build_footer(float modifier_w, float modifier_h) {
		int w_footer = (int) (width * modifier_w);
		int h_footer = (int) (height * modifier_h);

		JPanel footer = new JPanel();
		footer.setBackground(Color.BLACK);
		footer.setPreferredSize(new Dimension(w_footer, h_footer));
		footer.setLayout(new BorderLayout());
		footer.setBorder(new MatteBorder(5, 0, 0, 0, Color.WHITE));

		turn = new JLabel("C'est au tour du joueur noir !");
		turn.setHorizontalAlignment(JLabel.CENTER);
		turn.setVerticalAlignment(JLabel.CENTER);
		turn.setFont(turn.getFont().deriveFont(Font.BOLD, 25.F));
		turn.setForeground(Color.WHITE);

		footer.add(turn, BorderLayout.CENTER);

		this.add(footer, BorderLayout.SOUTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (grid_view != null) {
			grid_view.paintComponent(g);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		if (display_no_hit) {
			System.out.println("END 2");
			g2.setColor(new Color(0, 0, 0, 180));
			g2.fillRect(0, 0, width, height);
			g2.getFontMetrics(g2.getFont().deriveFont(Font.BOLD, 40));
			int width_t = g2.getFontMetrics(
					g2.getFont().deriveFont(Font.BOLD, 40)).stringWidth(
					"Aucune possibilité, joueur suivant");
			int height_t = g2.getFontMetrics(
					g2.getFont().deriveFont(Font.BOLD, 40)).getHeight();

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
			g2.setColor(Color.WHITE);
			g2.drawString("Aucune possibilité, joueur suivant",
					(width - width_t) / 2, (height - height_t) / 2);

		} else if (game_end) {
			g2.setColor(new Color(0, 0, 0, 180));
			g2.fillRect(0, 0, width, height);
			g2.getFontMetrics(g2.getFont().deriveFont(Font.BOLD, 40));
			int width_t = g2.getFontMetrics(
					g2.getFont().deriveFont(Font.BOLD, 40)).stringWidth(winner);
			int height_t = g2.getFontMetrics(
					g2.getFont().deriveFont(Font.BOLD, 40)).getHeight();

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
			g2.setColor(Color.WHITE);
			g2.drawString(winner, (width - width_t) / 2,
					(height - height_t) / 2);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					window.launch_start_menu();
				}
			}).start();
		}
	}

	@Override
	public void update_creation(Grid grid) {
		build_center(1.0f, 0.9f, grid);
		this.repaint();
	}

	@Override
	public void update_grid_change(Tile[][] grid) {
		grid_view.set_grid_change(grid);
		this.repaint();
	}

	@Override
	public void update_change_player(Owner owner) {
		turn.setText("C'est au tour du joueur " + owner.toString() + " !");
		this.repaint();
	}

	@Override
	public void update_no_hit(boolean display) {
		display_no_hit = display;
		this.repaint();
	}

	@Override
	public void update_winner(Owner owner) {
		this.display_no_hit = false;
		this.game_end = true;
		if (owner == Owner.NONE) {
			winner = new String("Equalité ! Aucun gagnant");
		} else {
			winner = new String("Le gagnant est le joueur " + owner.toString()
					+ " !");
		}
		this.repaint();
	}
}
