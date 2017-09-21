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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Network.Server;

public class Configuration_menu extends JPanel {

	private JTextField address_server;
	private JLabel error_text;
	private JLabel error_description;
	private Window ref_win;

	/**
	 * Constructeur de la classe du menu de configuration. Ce menu permet à
	 * l'utilisateur du serveur de créer un serveur sur un port précisé par lui
	 * même. Des vérifications sont effectués lors de la saisie du port pour
	 * s'assurer qu'il est valide. Tant que ce dernier n'est pas valide, le
	 * serveur ne peut pas être démarré
	 * 
	 * @param width
	 *            la largeur de la fenêtre de configuration
	 * @param height
	 *            la hauteur de la fenêtre de configuration
	 * @param win
	 *            une référence sur la fenetre pour pouvoir lancer une autre
	 *            fenetre à la validation
	 */
	public Configuration_menu(int width, int height, Window win) {
		this.ref_win = win;

		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(width, height));

		JLabel title = new JLabel("Configuration serveur bataille navale");
		int height_title = (int) (height * 0.15);
		title.setOpaque(true);
		title.setBackground(Color.BLACK);
		title.setForeground(Color.WHITE);
		title.setPreferredSize(new Dimension(width, height_title));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(45.f));
		title.setBorder(BorderFactory
				.createMatteBorder(0, 0, 6, 0, Color.WHITE));

		GridLayout layout_log = new GridLayout(3, 2);
		layout_log.setVgap(90);
		layout_log.setHgap(100);
		JPanel log_area = new JPanel();
		int height_log = (int) (height * 0.70);
		log_area.setBorder(new EmptyBorder(80, 250, 80, 250));
		log_area.setLayout(layout_log);
		log_area.setOpaque(false);
		log_area.setPreferredSize(new Dimension(width, height_log));

		JLabel address_message = new JLabel("Port serveur : ");
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
					try_launch_communication();
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
		log_area.add(new JLabel());
		log_area.add(new JLabel());
		log_area.add(error_text);
		log_area.add(error_description);

		JPanel bottom = new JPanel();
		int height_bottom = (int) (height * 0.15);
		bottom.setPreferredSize(new Dimension(width, height_bottom));
		bottom.setBackground(Color.BLACK);
		bottom.setOpaque(true);
		bottom.setBorder(BorderFactory.createMatteBorder(6, 0, 0, 0,
				Color.WHITE));

		JButton connexion = new JButton("Créer serveur");
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
				try_launch_communication();
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

		this.add(title, BorderLayout.NORTH);
		this.add(log_area, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);

		this.repaint();
		this.revalidate();
	}

	/**
	 * Méthode permettant de définir le message d'erreur qui s'affiche lorsque
	 * le port est incorrecte ou indisponible
	 * 
	 * @param message
	 *            le message d'erreur que l'on souhaite mettre à notre message
	 *            d'erreur
	 */
	public void set_error_message(String message) {
		this.error_description.setText(message);
	}

	/**
	 * Méthode permettant d'essayer de lancer le serveur. Cette méthode procéde
	 * à la vérification du port. Si le port n'est pas valide ou indisponible,
	 * un message d'errur est affecté et affiché. Sinon la fenetre contenant le
	 * tableau de bord de la bataille navale est lancé.
	 */
	public void try_launch_communication() {
		if (!address_server.getText().isEmpty()
				&& is_integer(address_server.getText())) {
			int value_port = Integer.valueOf(address_server.getText());

			if (Server.is_port_free(value_port)) {
				ref_win.launch_communication_menu("localhost", value_port);
			} else {
				set_error_message("Le port est indisponible . . .");
				error_description.setVisible(true);
				error_text.setVisible(true);
				repaint();
				revalidate();
			}
		} else {
			set_error_message("Port incorrecte . . .");
			error_description.setVisible(true);
			error_text.setVisible(true);
			repaint();
			revalidate();
		}
	}

	/**
	 * Méthode permettant de savoir si un string est strictement un nombre en
	 * appelant une méthode en donnant la base
	 * 
	 * @param s
	 *            le string que l'on veut tester en nombre
	 * @return vrai si le string est un nombre, faux sinon
	 */
	public boolean is_integer(String s) {
		return is_integer(s, 10);
	}

	/**
	 * Méthode permettant de savoir si un strng est strictement un nombre
	 * testant pour chaque caractère qu'il s'agit bien d'un chiffre
	 * 
	 * @param s
	 *            le string que l'on veut tester en nombre
	 * @param radix
	 *            la base ou l'on veut regarder le nombre
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
