package Graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import main.Direction;
import main.Grid;
import main.IA_player;
import strategy.OptimumIA;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private boolean human_game_playing;
	private boolean ia_game_playing;

	private Thread main_thread;
	private boolean help_activated;
	private Grid grid;
	private IA_player ia;

	private JButton replay;
	private JLabel score;
	private JLabel title;
	private JLabel sentence;

	private JPanel header_;
	private JPanel north_up;
	private JPanel north_down;

	private Menu menu;
	private Help help;

	/**
	 * Méthode permettant de construire une fenetre, de créer l'interface et les
	 * interactions et initialise tout les composants necessaire au
	 * fonctionnement de l'applciation
	 */
	public Window() {

		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.human_game_playing = false;
		this.ia_game_playing = false;

		this.grid = new Grid(4, 4, 150);
		this.grid.create_UI();
		this.grid.reinit();

		// this.ia = new IA_player(new OptimumIA(this.grid));
		this.ia = new IA_player(new OptimumIA());

		this.init();
		this.create_UI();
		this.help_activated = false;

		this.setContentPane(this.menu);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Méthode permettant de créer l'interface et d'initialiser les composants
	 * graphiques de la fenetre
	 */
	public void create_UI() {
		JPanel header_ = new JPanel();
		JPanel titleAndScore = new JPanel();
		JPanel sentenceAndBut = new JPanel();

		BorderLayout five_space_layout = new BorderLayout();
		five_space_layout.setHgap(5);
		five_space_layout.setVgap(5);

		this.setLayout(five_space_layout);
		header_.setLayout(five_space_layout);
		titleAndScore.setLayout(five_space_layout);
		sentenceAndBut.setLayout(five_space_layout);

		/* MENU */
		this.menu = new Menu(4 * 150, 4 * 150, this);

		/* Help */
		this.help = new Help(4 * 150, 4 * 150);

		/* REPLAY BUTTON */
		Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		this.replay = new JButton("New game");
		this.replay.setFocusable(false);
		this.replay.setPreferredSize(new Dimension(100, 25));
		this.replay.setBorder(border);
		this.replay.setBackground(java.awt.Color.DARK_GRAY);
		this.replay.setForeground(java.awt.Color.white);
		this.replay.setOpaque(true);

		this.replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replay();
			}
		});

		/* SCORE LABEL */
		this.score = new JLabel();
		this.score.setFocusable(false);
		this.score.setPreferredSize(new Dimension(100, 25));
		this.score.setText("0");
		this.score.setHorizontalAlignment(JLabel.CENTER);
		this.score.setVerticalAlignment(JLabel.CENTER);
		this.score.setBackground(java.awt.Color.gray);
		this.score.setForeground(java.awt.Color.white);
		this.score.setOpaque(true);

		/* TITLE LABEL */
		this.title = new JLabel();
		this.title.setFocusable(false);
		this.title.setForeground(java.awt.Color.DARK_GRAY);
		this.title.setFont(title.getFont().deriveFont(25f));
		this.title.setHorizontalAlignment(JLabel.LEFT);
		this.title.setVerticalAlignment(JLabel.CENTER);
		this.title.setText("2048");

		/* SENTENCE LABEL */
		this.sentence = new JLabel();
		this.sentence.setFocusable(false);
		this.sentence.setForeground(java.awt.Color.DARK_GRAY);
		this.sentence.setFont(title.getFont().deriveFont(10f));
		this.sentence.setHorizontalAlignment(JLabel.LEFT);
		this.sentence.setVerticalAlignment(JLabel.CENTER);
		this.sentence.setOpaque(true);
		this.sentence.setText("Join the numbers and get to the 2048 tile !");

		/* HEADER BUILDING */
		this.north_up = new JPanel();
		this.north_down = new JPanel();
		this.header_ = new JPanel();

		this.header_.setPreferredSize(new Dimension(4 * 150, 90));
		this.north_up.setPreferredSize(new Dimension(4 * 150, 45));
		this.north_down.setPreferredSize(new Dimension(4 * 150, 45));

		this.north_up.setLayout(new BorderLayout());
		this.north_up.setBorder(new EmptyBorder(7, 7, 7, 7));
		this.north_up.add(title, BorderLayout.WEST);
		this.north_up.add(score, BorderLayout.EAST);

		this.north_down.setLayout(new BorderLayout());
		this.north_down.setBorder(new EmptyBorder(7, 7, 7, 7));
		this.north_down.add(sentence, BorderLayout.WEST);
		this.north_down.add(replay, BorderLayout.EAST);

		this.header_.setLayout(new BorderLayout());
		this.header_.add(this.north_up, BorderLayout.NORTH);
		this.header_.add(this.north_down, BorderLayout.SOUTH);

		/* BUILDING WINDOW */
		this.setFocusable(true);
		this.setTitle("2048");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				keyboard_event_manager(e);
			}
		});
	}

	/**
	 * Méthode permettant d'initiliaser les composants logiques de l'application
	 */
	public void init() {
		this.grid.reinit();
	}

	/**
	 * Méthode permettant de recommencer une partie
	 */
	public void replay() {
		this.init();
		if (this.human_game_playing) {
			launch_player_game(false);
		} else if (this.ia_game_playing) {
			launcher_IA_game(false);
		}
	}

	/**
	 * Méthode permettant de lancer une partie avec une joueur humain
	 */
	public void launch_player_game(boolean resume) {
		this.ia_game_playing = false;
		this.human_game_playing = true;

		if (!resume) {
			this.init();
		}

		this.getContentPane().removeAll();
		BorderLayout five_space_layout = new BorderLayout();
		five_space_layout.setHgap(5);
		five_space_layout.setVgap(5);

		this.setLayout(five_space_layout);

		this.add(this.header_, BorderLayout.NORTH);
		this.add(this.grid, BorderLayout.CENTER);
		this.pack();
		this.repaint();
		this.grid.repaint();
		this.header_.repaint();
		this.revalidate();
		this.grid.revalidate();
		this.header_.revalidate();

		this.main_thread = new Thread(new Runnable() {
			public void run() {
				while (grid.action_possible_bis() && human_game_playing) {
					if (!grid.action_possible_bis()) {
						break;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					score.setText(Long.toString(grid.getScore()));

					grid.draw_cell();
					grid.revalidate();
					repaint();
					grid.repaint();
				}
				revalidate();
			}
		});

		this.main_thread.start();

	}

	/**
	 * Méthode permettant de lancer une partie avec un joueur IA
	 */
	public void launcher_IA_game(boolean resume) {
		this.ia_game_playing = true;
		this.human_game_playing = false;
		if (!resume) {
			this.init();
		}
		this.getContentPane().removeAll();
		BorderLayout five_space_layout = new BorderLayout();
		five_space_layout.setHgap(5);
		five_space_layout.setVgap(5);

		this.setLayout(five_space_layout);
		this.add(this.header_, BorderLayout.NORTH);
		this.add(this.grid, BorderLayout.CENTER);
		this.repaint();
		this.grid.repaint();
		this.header_.repaint();
		this.revalidate();
		this.grid.revalidate();
		this.header_.revalidate();

		this.main_thread = new Thread(new Runnable() {
			public void run() {
				while (grid.action_possible_bis() && ia_game_playing) {
					if (!grid.action_possible_bis()) {
						break;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					ia.play(grid);
					score.setText(Long.toString(grid.getScore()));

					grid.draw_cell();
					grid.revalidate();
					repaint();
					grid.repaint();
				}
				revalidate();
			}
		});

		this.main_thread.start();
		this.pack();
	}

	/**
	 * Méthode permettant de quitter l'application
	 */
	public void exit() {

		if (this.main_thread != null) {
			this.main_thread.stop();
		}

		System.exit(0);
		this.dispose(); // Destroy the JFrame object
		this.setVisible(false);
	}

	/**
	 * Méthode permettant de demander à la fenetre d'afficher le menu de
	 * démarrage de l'application
	 */
	public void launch_menu() {
		this.human_game_playing = false;
		this.ia_game_playing = false;

		this.menu = new Menu(4 * 150, 4 * 150, this);
		this.getContentPane().removeAll();
		BorderLayout five_space_layout = new BorderLayout();
		five_space_layout.setHgap(5);
		five_space_layout.setVgap(5);

		this.setLayout(five_space_layout);
		this.setContentPane(this.menu);
		this.repaint();
		this.revalidate();
		this.menu.repaint();
		this.menu.revalidate();
		this.pack();
	}

	/**
	 * Méthode permettant de demander à la fenetre d'afficher la page d'aide de
	 * démarrage de l'application
	 */
	public void launch_help() {
		this.help = new Help(4 * 150, 4 * 150);
		this.getContentPane().removeAll();
		BorderLayout five_space_layout = new BorderLayout();
		five_space_layout.setHgap(5);
		five_space_layout.setVgap(5);

		this.setLayout(five_space_layout);
		this.setContentPane(this.help);
		this.repaint();
		this.revalidate();
		this.help.repaint();
		this.help.revalidate();

		this.pack();
	}

	/**
	 * Méthode permettant de gérer les entrées claviers de l'utilisateur et de
	 * déclarer les actions en conséquence
	 * 
	 * @param e
	 *            l'evenement clavier qui a été récupéré
	 */
	public void keyboard_event_manager(KeyEvent e) {

		if (!this.ia_game_playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				this.grid.move_to_direction(Direction.UP, false);
				this.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.grid.move_to_direction(Direction.DOWN, false);
				this.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.grid.move_to_direction(Direction.RIGHT, false);
				this.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.grid.move_to_direction(Direction.LEFT, false);
				this.repaint();
			}
		}

		if (e.getKeyChar() == 'j') { // Replay
			this.replay();
		} else if (e.getKeyChar() == 'h') { // Help
			if (!this.help_activated) {
				this.help_activated = true;
				this.launch_help();
			} else {
				if (this.human_game_playing) {
					this.help_activated = false;
					launch_player_game(true);
				} else if (this.ia_game_playing) {
					this.help_activated = false;
					launcher_IA_game(true);
				} else {
					this.help_activated = false;
					this.launch_menu();
				}
			}

		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Quit
			this.exit();
		} else if (e.getKeyChar() == 'm') { // menu
			this.launch_menu();
		}

	}
}
