package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Server_console_view extends JPanel {

	private JTextArea messages;
	private JLabel title;

	/**
	 * Constructeur de la classe console vue, cette classe est une vue
	 * représentant la console du serveur. Elle permet d'afficher et d'ajouter
	 * des messages qui sont envoyés par un controller et de les effacer si l'on
	 * veut retrouver un terminal propre
	 * 
	 * @param width
	 *            la largeur de la console
	 * @param height
	 *            la hauteur de la console
	 */
	public Server_console_view(int width, int height) {

		BorderLayout layout = new BorderLayout();
		layout.setHgap(50);
		layout.setVgap(50);

		// Panel
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
		this.setBorder(new EmptyBorder(50, 50, 50, 50));

		// Box text
		messages = new JTextArea();
		messages.setBorder(new EmptyBorder(20, 20, 20, 20));
		messages.setOpaque(true);
		messages.setBackground(Color.BLACK);
		messages.setForeground(Color.WHITE);
		messages.setEditable(false);

		JScrollPane scroll = new JScrollPane(messages,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBorder(new LineBorder(Color.WHITE, 4));
		scroll.setOpaque(true);
		scroll.setBackground(Color.BLACK);
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scroll.getViewport().setBorder(null);
		scroll.setViewportBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(8);

		// Title
		title = new JLabel("Terminale serveur");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(35.f));
		title.setOpaque(false);
		title.setForeground(Color.WHITE);

		this.add(title, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant d'effacer tout les messages se trouvant dans le
	 * terminal
	 */
	public void erase_all_message() {
		messages.setText("");
		revalidate();
	}

	/**
	 * Méthod permettant d'ajouter un message dans le terminal sont va s'ajouter
	 * automatique et grace au JScrollPane, si le message vient à dépasser de la
	 * boite, on pourra scroller
	 * 
	 * @param message
	 *            le message que l'on souhaite ajouter à notre terminale
	 */
	public void add_message(String message) {
		messages.append("> " + message + "\n");
		revalidate();
	}
}
