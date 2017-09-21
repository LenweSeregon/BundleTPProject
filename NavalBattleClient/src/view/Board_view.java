package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Boat;
import model.Cell;
import model.Grid;
import model.Winner;
import pattern_observer.Observer;
import controler.Board_controler;

public class Board_view extends JPanel implements Observer {

	private Board_controler controler;
	private Grid_view player_grid;
	private Grid_view enemy_grid;
	private int width;
	private int height;

	private boolean winner;
	private String msg_winner;

	private boolean animation_running;
	private JButton ready;
	private JLabel message;
	private JLabel versus;
	volatile private int font_size;
	volatile private boolean display_message;
	volatile private String message_to_display;

	/**
	 * Constructeur de la classe plateau vue qui abrites les composants
	 * graphiques de plus haut niveau
	 * 
	 * @param controler
	 *            une référence sur le controlleur du pattern MVC
	 * @param width
	 *            la largeur du plateau
	 * @param height
	 *            la hauteur du plateau
	 */
	public Board_view(Board_controler controler, int width, int height,
			Client_console_view cv) {
		this.controler = controler;
		this.width = width;
		this.height = height;
		display_message = false;
		animation_running = false;

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);

		this.player_grid = null;
		this.enemy_grid = null;
		this.winner = false;

		ready = new JButton("Prêt");
		ready.setHorizontalAlignment(JLabel.CENTER);
		ready.setVerticalAlignment(JLabel.CENTER);
		ready.setBackground(Color.WHITE);
		ready.setPreferredSize(new Dimension(150, 70));
		ready.setBounds((width / 3), height - 80, width / 3, height / 10);

		versus = new JLabel("Versus");
		versus.setHorizontalAlignment(JLabel.CENTER);
		versus.setVerticalAlignment(JLabel.CENTER);
		versus.setForeground(Color.WHITE);
		versus.setBounds(575, 350, 150, 100);
		versus.setFont(versus.getFont().deriveFont(Font.BOLD, 30.f));

		message = new JLabel("En attente d'un autre joueur");
		message.setFont(message.getFont().deriveFont(Font.BOLD, 30.f));
		message.setForeground(Color.WHITE);
		message.setHorizontalAlignment(JLabel.CENTER);
		message.setVerticalAlignment(JLabel.CENTER);
		message.setBounds(width / 4, height - 150, width / 2, height / 10);

		cv.setBounds(350, 0, 600, 300);

		this.add(ready);
		this.add(versus);
		this.add(message);
		this.add(cv);
		ready.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ready.setEnabled(false);
				ready.setVisible(false);
				controler.launch_player_thread();
				// message.setText("En attente d'un autre joueuuuuuur");
			}
		});

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!winner && !animation_running) {
					Dimension d = enemy_grid.is_cell_click(e.getX(), e.getY());
					if (d != null) {
						controler.try_hit_enemy_cell(d.width, d.height);
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
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (player_grid != null) {
			player_grid.paintComponent(g);
		}

		if (enemy_grid != null) {
			enemy_grid.paintComponent(g);
		}

		if (winner) {
			g.setColor(new Color(255, 255, 255, 160));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		if (display_message) {
			g2.setColor(new Color(255, 255, 255, 150));
			g2.fillRect(0, 0, getWidth(), getHeight());

			String text = message_to_display;
			Font font = g2.getFont().deriveFont(Float.valueOf(font_size));
			int text_width = g2.getFontMetrics(font).stringWidth(text);
			int text_height = g2.getFontMetrics(font).getHeight();

			g2.setColor(Color.BLACK);
			g2.setFont(g2.getFont().deriveFont(Float.valueOf(font_size)));
			g2.drawString(text, (width - text_width) / 2,
					(height - text_height) / 2);

			font_size--;
		} else if (winner) {
			g2.setColor(new Color(255, 255, 255, 150));
			g2.fillRect(0, 0, getWidth(), getHeight());

			String text = msg_winner;
			Font font = g2.getFont().deriveFont(Float.valueOf(60.f));
			int text_width = g2.getFontMetrics(font).stringWidth(text);
			int text_height = g2.getFontMetrics(font).getHeight();

			g2.setColor(Color.BLACK);
			g2.setFont(g2.getFont().deriveFont(Float.valueOf(60.f)));
			g2.drawString(text, (width - text_width) / 2,
					((height - text_height) / 2) + 50);
		} else {

			g2.setColor(new Color(255, 255, 255, 0));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public void update_creation(Grid player_grid, Grid enemy_grid) {
		this.player_grid = new Grid_view(player_grid, 250, 300);
		this.enemy_grid = new Grid_view(enemy_grid, 750, 300);
		this.repaint();
	}

	@Override
	public void update_cell_change(Grid grid, Cell cell) {
		if (grid.get_is_player()) {
			this.player_grid.set_cell_has_been_visited(cell.get_index_line(),
					cell.get_index_column());
		} else {
			this.enemy_grid.set_cell_has_been_visited(cell.get_index_line(),
					cell.get_index_column());
		}
		this.repaint();
	}

	@Override
	public void update_boat_hitted(Grid grid, Boat boat) {
		if (grid.get_is_player()) {
			if (player_grid.boat_present(boat.get_pos_x(), boat.get_pos_y())) {
				player_grid.set_boat(boat.get_pos_x(), boat.get_pos_y(), boat);
			}
		} else {
			if (enemy_grid.boat_present(boat.get_pos_x(), boat.get_pos_y())) {
				enemy_grid.set_boat(boat.get_pos_x(), boat.get_pos_y(), boat);
			}
		}
		this.repaint();
	}

	@Override
	public void update_winner(Winner winner) {
		this.winner = true;
		if (winner == Winner.PLAYER) {
			msg_winner = "Vous avez gagné !";
		} else {
			msg_winner = "Vous avez perdu !";
		}
		this.repaint();
	}

	@Override
	public void update_enemy_hitted(int i, int j) {
		enemy_grid.set_cell_boat_dead(i, j);
	}

	@Override
	public void update_message(String message) {
		this.message.setText(message);
		this.repaint();
		this.revalidate();
	}

	@Override
	public void update_message_splash(String message) {

		font_size = 80;
		display_message = true;
		animation_running = true;
		message_to_display = message;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (font_size >= 0) {
					repaint();

					try {
						Thread.sleep(10);
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
			}

		});
		t2.start();
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display_message = false;
		animation_running = false;
		this.repaint();
	}
}
