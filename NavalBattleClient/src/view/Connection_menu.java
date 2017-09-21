package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Main.Client;

public class Connection_menu extends JPanel {

	private Window ref_win;
	private JTextField address_server;
	private JTextField port_server;
	private JLabel error_description;
	private JLabel error_text;

	/**
	 * Le menu de connexion du client ou il choisit l'adresse du serveur et le
	 * port sur lequel il veut se connecter
	 * 
	 * @param width
	 *            la largeur du menu de connexion
	 * @param height
	 *            la hauteur du menu de connexion
	 * @param window
	 *            une référence sur la fenetre principale pour lancer d'autre
	 *            fenetre
	 */
	public Connection_menu(int width, int height, Window window) {

		this.ref_win = window;

		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(width, height));

		JLabel title = new JLabel("Connexion bataille navale");
		title.setOpaque(true);
		title.setBackground(Color.BLACK);
		title.setForeground(Color.WHITE);
		title.setPreferredSize(new Dimension(width, height / 7));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(45.f));
		title.setBorder(BorderFactory
				.createMatteBorder(0, 0, 6, 0, Color.WHITE));

		GridLayout layout_log = new GridLayout(3, 2);
		layout_log.setVgap(90);
		layout_log.setHgap(100);
		JPanel log_area = new JPanel();
		log_area.setBorder(new EmptyBorder(80, 250, 80, 250));
		log_area.setLayout(layout_log);
		log_area.setBackground(new Color(4, 25, 57, 255));
		log_area.setPreferredSize(new Dimension(width, (int) (height)));
		log_area.setOpaque(false);

		JLabel address_message = new JLabel("Adresse serveur : ");
		address_message.setFont(address_message.getFont().deriveFont(20.f));
		address_message.setForeground(Color.WHITE);
		address_message.setHorizontalAlignment(JLabel.RIGHT);
		address_message.setVerticalAlignment(JLabel.CENTER);

		address_server = new JTextField();
		address_server.setHorizontalAlignment(JTextField.CENTER);
		address_server.setBorder(new LineBorder(Color.BLACK, 6));
		address_server.setBackground(Color.WHITE);
		address_server.setForeground(Color.BLACK);
		address_server.setPreferredSize(new Dimension(width / 3, height / 10));
		address_server.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_launching_connection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		JLabel port_message = new JLabel("Port serveur : ");
		port_message.setFont(address_message.getFont().deriveFont(20.f));
		port_message.setForeground(Color.WHITE);
		port_message.setHorizontalAlignment(JLabel.RIGHT);
		port_message.setVerticalAlignment(JLabel.CENTER);

		port_server = new JTextField();
		port_server.setBorder(new LineBorder(Color.BLACK, 6));
		port_server.setHorizontalAlignment(JTextField.CENTER);
		port_server.setBackground(Color.WHITE);
		port_server.setForeground(Color.BLACK);
		port_server.setPreferredSize(new Dimension(width / 3, height / 10));
		port_server.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_launching_connection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		error_text = new JLabel("Erreur : ");
		error_text.setForeground(Color.red);
		error_text.setFont(error_text.getFont().deriveFont(20.f));
		error_text.setHorizontalAlignment(JLabel.RIGHT);
		error_text.setVerticalAlignment(JLabel.CENTER);
		error_text.setVisible(false);

		error_description = new JLabel("Votre port est invalide . . .");
		error_description.setForeground(Color.red);
		error_description.setFont(error_text.getFont().deriveFont(20.f));
		error_description.setHorizontalAlignment(JLabel.LEFT);
		error_description.setVerticalAlignment(JLabel.CENTER);
		error_description.setVisible(false);

		log_area.add(address_message);
		log_area.add(address_server);
		log_area.add(port_message);
		log_area.add(port_server);
		log_area.add(error_text);
		log_area.add(error_description);

		JPanel bottom = new JPanel();
		bottom.setBackground(Color.BLACK);
		bottom.setOpaque(true);
		bottom.setBorder(BorderFactory.createMatteBorder(6, 0, 0, 0,
				Color.WHITE));

		JButton connexion = new JButton("Connexion");
		connexion.setForeground(Color.WHITE);
		connexion.setPreferredSize(new Dimension(width, height / 7));
		connexion.setHorizontalAlignment(JButton.CENTER);
		connexion.setVerticalAlignment(JButton.CENTER);
		connexion.setFont(connexion.getFont().deriveFont(30.f));
		connexion.setFocusPainted(false);
		connexion.setBorderPainted(false);
		connexion.setContentAreaFilled(false);
		connexion.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try_launching_connection();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				connexion.setForeground(Color.GRAY);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				connexion.setForeground(Color.WHITE);
			}

		});
		bottom.add(connexion);

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_launching_connection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		this.add(title, BorderLayout.NORTH);
		this.add(log_area, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);

		this.repaint();
		this.revalidate();
	}

	/**
	 * Méthode permettant de mettre la valeur du message d'erreur de l'affichage
	 * 
	 * @param message
	 *            le mesage d'erreur que l'on veut assigner
	 */
	public void set_error_message(String message) {
		this.error_description.setText(message);
	}

	/**
	 * Méthode permettant d'essayer de se connecter au serveur, si ce n'est pas
	 * possible, on affiche un message d'erreur
	 */
	public void try_launching_connection() {
		if (!port_server.getText().isEmpty()
				&& is_integer(port_server.getText())
				&& !address_server.getText().isEmpty()) {
			int value_port = Integer.valueOf(port_server.getText());
			String value_add = address_server.getText();

			if (value_add.equals("localhost") || value_add.equals("127.0.0.1")) {
				try {
					Client client = new Client(InetAddress.getByName(null),
							value_port);
					client.connect_to_server();
					ref_win.launch_connexion_bis(client);
					// ref_win.launch_connexion(client);
				} catch (UnknownHostException e) {
					System.out.println("ERROR 1");
					set_error_message("Conversion IP impossible . . .");
					error_description.setVisible(true);
					error_text.setVisible(true);
					repaint();
					revalidate();
				} catch (IOException e) {
					System.out.println("ERROR 2");
					set_error_message("Connexion impossible au serveur . . .");
					error_description.setVisible(true);
					error_text.setVisible(true);
					repaint();
					revalidate();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("ERROR 3");
				}
			} else {
				set_error_message("Le serveur est local . . .");
				error_description.setVisible(true);
				error_text.setVisible(true);
				repaint();
				revalidate();
			}

		} else {
			set_error_message("Les champs sont incorrectes");
			error_description.setVisible(true);
			error_text.setVisible(true);
			repaint();
			revalidate();
		}
	}

	/**
	 * Méthode permettant de savoir si un string est un nombre stricte en
	 * donnant une base de 10.
	 * 
	 * @param s
	 *            le string que l'on veut tester en nombre
	 * @return vrai si le string est un nombre, faux sinon
	 */
	public boolean is_integer(String s) {
		return is_integer(s, 10);
	}

	/**
	 * Méthode permettant de savoir si un string est un nombre en donnant la
	 * base dans laquelle on souhaite savoir
	 * 
	 * @param s
	 *            string est ce que le string est un nombre
	 * @param radix
	 *            la base dans laquelle on veut tester
	 * @return vrai si le string est un nombre, faux sinon
	 */
	public boolean is_integer(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw background
		g.setColor(new Color(4, 25, 57, 255));
		g.fillRect(0, 0, getWidth(), getHeight());

		// Draw line
		g.setColor(new Color(220, 220, 220, 150));
		for (int i = getHeight(); i >= 0; i -= 50) {
			g.drawLine(getWidth(), i, 0, i);
		}

		// Draw column
		for (int i = getWidth(); i >= 0; i -= 50) {
			g.drawLine(i, 0, i, getHeight());
		}
	}
}
