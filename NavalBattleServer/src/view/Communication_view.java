package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import pattern_observer.Observer;
import Network.Communication_controler;
import Network.Server_state;

public class Communication_view extends JPanel implements Observer {

	private Server_console_view view;
	private Window ref_win;
	private JLabel server_type;
	private JLabel server_name;
	private JLabel server_port;
	private JLabel nb_connection;
	private JLabel server_state;
	private JLabel in_game;

	private Communication_controler controler;

	/**
	 * Constructeur de la classe du tableau de bord du serveur. Cette classe
	 * permet à l'utilisateur du serveur de connaitre toutes les informations
	 * importantes sur ce dernier comme son port, le nombre de connectés, son
	 * statut, etc... On peut aussi agir sur le serveur directement en
	 * l'arrêtant, le mettant en pause, etc...
	 * 
	 * @param width
	 *            la largeur du tableau de bord
	 * @param height
	 *            la hauteur du tableau de bord
	 * @param ref_win
	 *            une référence sur la fenêtre pour appeler des méthodes comme
	 *            quitter la fenetre
	 * @param controler
	 *            une référence sur le controler pour respecter le pattern MVC
	 */
	public Communication_view(int width, int height, Window ref_win,
			Communication_controler controler) {

		this.ref_win = ref_win;
		this.controler = controler;
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());

		// Header
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		header.setBackground(Color.BLACK);
		int height_header = (int) (height * 0.15);
		header.setPreferredSize(new Dimension(width, height_header));
		header.setBorder(BorderFactory.createMatteBorder(0, 0, 6, 0,
				Color.WHITE));

		JLabel title = new JLabel("Tableau de bord serveur");
		title.setOpaque(true);
		title.setBackground(Color.BLACK);
		title.setForeground(Color.WHITE);
		title.setPreferredSize(new Dimension(width, height_header));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(45.f));

		header.add(title, BorderLayout.CENTER);

		// Footer

		JPanel bottom = new JPanel();
		GridLayout layout_bottom = new GridLayout(1, 4);
		layout_bottom.setHgap(180);
		bottom.setLayout(layout_bottom);
		int height_bottom = (int) (height * 0.15);
		bottom.setPreferredSize(new Dimension(width, height_bottom));
		bottom.setBackground(Color.BLACK);
		bottom.setBorder(BorderFactory.createMatteBorder(6, 0, 0, 0,
				Color.WHITE));

		JButton clear = new JButton("Vider console");
		clear.setForeground(Color.WHITE);
		clear.setBorder(new LineBorder(Color.WHITE, 4));
		clear.setOpaque(false);
		clear.setFont(clear.getFont().deriveFont(20.f));
		clear.setHorizontalAlignment(JButton.CENTER);
		clear.setPreferredSize(new Dimension((width / 4), height_bottom / 2));
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.erase_all_message();
				view.add_message(". . . ");
			}
		});
		JButton pause_resume = new JButton("Pause");
		JButton run_stop = new JButton("Stop");

		pause_resume.setForeground(Color.WHITE);
		pause_resume.setBorder(new LineBorder(Color.WHITE, 4));
		pause_resume.setOpaque(false);
		pause_resume.setFont(pause_resume.getFont().deriveFont(20.f));
		pause_resume.setHorizontalAlignment(JButton.CENTER);
		pause_resume.setPreferredSize(new Dimension((width / 4),
				height_bottom / 2));
		pause_resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pause_resume.getText().equals("Pause")) {
					if (run_stop.getText().equals("Stop")) {
						pause_resume.setText("Reprise");
						controler.ask_to_pause_server();
					}
				} else {
					if (run_stop.getText().equals("Stop")) {
						pause_resume.setText("Pause");
						controler.ask_to_resume_server();
					}
				}
			}
		});

		run_stop.setForeground(Color.WHITE);
		run_stop.setBorder(new LineBorder(Color.WHITE, 4));
		run_stop.setOpaque(false);
		run_stop.setFont(run_stop.getFont().deriveFont(20.f));
		run_stop.setHorizontalAlignment(JButton.CENTER);
		run_stop.setPreferredSize(new Dimension((width / 4), height_bottom / 2));
		run_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (run_stop.getText().equals("Stop")) {
					run_stop.setText("Démarrer");
					pause_resume.setText("Pause");
					controler.ask_to_close_server();
				} else {
					run_stop.setText("Stop");
					pause_resume.setText("Pause");
					controler.ask_to_open_server();
				}
			}
		});

		JButton exit = new JButton("Quitter");
		exit.setForeground(Color.WHITE);
		exit.setBorder(new LineBorder(Color.WHITE, 4));
		exit.setOpaque(false);
		exit.setFont(exit.getFont().deriveFont(20.f));
		exit.setHorizontalAlignment(JButton.CENTER);
		exit.setPreferredSize(new Dimension((width / 4), height_bottom / 2));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.exit();
			}
		});

		JPanel p1 = new JPanel();
		p1.setBorder(new EmptyBorder(20, 0, 0, 0));
		p1.setOpaque(false);
		p1.add(clear);

		JPanel p2 = new JPanel();
		p2.setBorder(new EmptyBorder(20, 0, 0, 0));
		p2.setOpaque(false);
		p2.add(run_stop);

		JPanel p3 = new JPanel();
		p3.setBorder(new EmptyBorder(20, 0, 0, 0));
		p3.setOpaque(false);
		p3.add(pause_resume);

		JPanel p4 = new JPanel();
		p4.setBorder(new EmptyBorder(20, 0, 0, 0));
		p4.setOpaque(false);
		p4.add(exit);

		bottom.add(p1);
		bottom.add(p2);
		bottom.add(p3);
		bottom.add(p4);

		// Center
		JPanel center = new JPanel();
		BorderLayout right_layout = new BorderLayout();
		right_layout.setHgap(6);
		right_layout.setVgap(6);
		center.setLayout(right_layout);
		center.setBackground(Color.WHITE);
		int height_center = (int) (height * 0.70);
		center.setPreferredSize(new Dimension(width, height_center));

		// Center console
		int width_view = (int) (width * 0.7) - 3;
		view = new Server_console_view(width_view, height_center);
		// view.setBackground(new Color(16, 52, 103, 255));
		view.setBackground(new Color(4, 25, 57, 255));

		// Center informations
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		int width_right = (int) (width * 0.3) - 3;
		right.setPreferredSize(new Dimension(width_right, height_center));
		right.setBackground(new Color(4, 25, 57, 255));

		JLabel title_right = new JLabel("<HTML><U> Informations :</U></HTML>");
		int height_title_right = (int) (height_center * 0.20);
		title_right.setPreferredSize(new Dimension(width_right,
				height_title_right));
		title_right.setHorizontalAlignment(JLabel.CENTER);
		title_right.setVerticalAlignment(JLabel.CENTER);
		title_right.setFont(title.getFont().deriveFont(35.f));
		title_right.setBackground(new Color(4, 25, 57, 255));
		title_right.setForeground(Color.WHITE);

		JPanel informations = new JPanel();
		informations.setBorder(new EmptyBorder(20, 20, 20, 20));
		informations.setBackground(new Color(4, 25, 57, 255));
		informations.setForeground(Color.WHITE);
		int height_informations = (int) (height_center * 0.80);
		informations.setLayout(new GridLayout(6, 1));
		informations.setPreferredSize(new Dimension(width_right,
				height_informations));

		int height_each_information = (int) (height_center * 0.10);

		server_type = new JLabel("Type serveur : Bataille Navale");
		server_type.setPreferredSize(new Dimension(width_right,
				height_each_information));
		server_type.setFont(server_type.getFont().deriveFont(20.f));
		server_type.setVerticalAlignment(JLabel.CENTER);
		server_type.setHorizontalAlignment(JLabel.LEFT);
		server_type.setForeground(Color.WHITE);

		server_name = new JLabel();
		server_name.setPreferredSize(new Dimension(width_right,
				height_each_information));
		server_name.setFont(server_name.getFont().deriveFont(20.f));
		server_name.setVerticalAlignment(JLabel.CENTER);
		server_name.setHorizontalAlignment(JLabel.LEFT);
		server_name.setForeground(Color.WHITE);
		this.set_server_name("Unknow");

		server_port = new JLabel();
		server_port.setPreferredSize(new Dimension(width_right,
				height_each_information));
		server_port.setFont(server_port.getFont().deriveFont(20.f));
		server_port.setVerticalAlignment(JLabel.CENTER);
		server_port.setForeground(Color.WHITE);
		server_port.setHorizontalAlignment(JLabel.LEFT);
		this.set_server_port(-1);

		nb_connection = new JLabel();
		nb_connection.setPreferredSize(new Dimension(width_right,
				height_each_information));
		nb_connection.setFont(nb_connection.getFont().deriveFont(20.f));
		nb_connection.setVerticalAlignment(JLabel.CENTER);
		nb_connection.setHorizontalAlignment(JLabel.LEFT);
		nb_connection.setForeground(Color.WHITE);
		this.set_nb_connection(0);

		server_state = new JLabel();
		server_state.setPreferredSize(new Dimension(width_right,
				height_each_information));
		server_state.setFont(server_state.getFont().deriveFont(20.f));
		server_state.setVerticalAlignment(JLabel.CENTER);
		server_state.setHorizontalAlignment(JLabel.LEFT);
		server_state.setForeground(Color.WHITE);

		in_game = new JLabel();
		in_game.setPreferredSize(new Dimension(width_right,
				height_each_information));
		in_game.setFont(server_state.getFont().deriveFont(20.f));
		in_game.setVerticalAlignment(JLabel.CENTER);
		in_game.setHorizontalAlignment(JLabel.LEFT);
		in_game.setForeground(Color.WHITE);
		this.set_game_launch(0);

		this.set_server_state(Server_state.RUNNING.get_text());

		informations.add(server_name);
		informations.add(server_port);
		informations.add(server_type);
		informations.add(nb_connection);
		informations.add(server_state);
		informations.add(in_game);

		right.add(title_right, BorderLayout.NORTH);
		right.add(informations, BorderLayout.CENTER);

		// Building center
		center.add(view, BorderLayout.WEST);
		center.add(right, BorderLayout.EAST);

		JButton erase_console = new JButton("Nettoyer console");
		erase_console.setHorizontalAlignment(JButton.CENTER);
		erase_console.setVerticalAlignment(JButton.CENTER);
		erase_console.setFont(erase_console.getFont().deriveFont(30.f));

		this.add(header, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}

	public void set_server_name(String name) {
		this.server_name.setText("Nom du serveur : " + name);
	}

	public void set_server_port(int port) {
		this.server_port.setText("Port du serveur : " + String.valueOf(port));
	}

	public void set_nb_connection(int connection) {
		this.nb_connection.setText("Nombre de connectés : "
				+ String.valueOf(connection));
	}

	public void set_server_state(String state) {
		this.server_state.setText("Etat du serveur : " + state);
	}

	public void set_game_launch(int nb) {
		this.in_game.setText("Partie lancée : " + nb);
	}

	public Server_console_view get_console_view() {
		return this.view;
	}

	@Override
	public void update_console_message(String message) {
		this.view.add_message(message);
	}

	@Override
	public void update_nb_client(int nb) {
		this.set_nb_connection(nb);

	}

	@Override
	public void update_server_state(Server_state state) {
		this.set_server_state(state.get_text());

	}

	@Override
	public void update_nb_game(int nb) {
		this.set_game_launch(nb);
	}
}
