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

public class Client_console_view extends JPanel {

	private JTextArea messages;
	private JLabel title;

	/**
	 * Constructeur de la classe représentant une console pour le client. On va
	 * dedans y afficher tous les messages d'entrée et de sorti
	 * 
	 * @param width
	 *            la largeur de la grille
	 * @param height
	 *            la hauteur de la grille
	 */
	public Client_console_view(int width, int height) {

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
		title = new JLabel("Client Bataille navale");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(35.f));
		title.setOpaque(false);
		title.setForeground(Color.WHITE);

		this.add(title, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant d'ajouter un message dans la console
	 * 
	 * @param message
	 *            le message a ajouter
	 */
	public void add_message(String message) {
		messages.append(message + "\n");
		repaint();
		revalidate();
	}
}
