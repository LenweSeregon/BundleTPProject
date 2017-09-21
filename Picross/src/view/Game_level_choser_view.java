package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Resources;

@SuppressWarnings("serial")
public class Game_level_choser_view extends JPanel {

	private int width;
	private int height;

	private String id;
	private String name;
	private int nb_line;
	private int nb_column;
	private boolean succeed;
	private Window ref_win;

	private static Color writing_color = Color.DARK_GRAY;

	/**
	 * Constructeur de la classe présentant le choix des menus de manière
	 * graphique. La classe stock toutes les données qui se trouve dans le
	 * modèle pour les replacer dans un panel et les afficher
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param id
	 *            l'identifiant du niveau
	 * @param name
	 *            le nom du niveau
	 * @param line
	 *            le nombre de ligne
	 * @param column
	 *            le nombre de colonne
	 * @param succeed
	 *            le level a t il été réussi
	 */
	public Game_level_choser_view(int width, int height, String id,
			String name, int line, int column, boolean succeed, Window ref_win) {
		this.width = width;
		this.height = height;

		this.ref_win = ref_win;
		this.id = id;
		this.name = name;
		this.nb_line = line;
		this.nb_column = column;
		this.succeed = succeed;

		this.build_panel();
	}

	/**
	 * Méthode permettant de récupérer l'identifiant de la représentation d'un
	 * niveau (utilise pour l'affichage du CardLayout)
	 * 
	 * @return l'identifiant unique du niveau
	 */
	public String get_identifiant() {
		return this.id;
	}

	public int get_nb_line() {
		return nb_line;
	}

	public int get_nb_column() {
		return nb_column;
	}

	/**
	 * Méthode privée qui va avoir pour but de construire le panel et d'avoir un
	 * affichage propre. La construction du panel respecte la taille donnée au
	 * panel en ajustant via un GridLayout
	 */
	private void build_panel() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.setOpaque(false);

		int width_informations = width;
		int height_informations = (int) (height * 0.7);

		int width_images = width;
		int height_images = (int) (height * 0.3);

		JPanel informations = new JPanel();
		informations.setPreferredSize(new Dimension(width_informations,
				height_informations));
		informations.setLayout(new GridLayout(3, 1));
		informations.setOpaque(false);

		JPanel images = new JPanel();
		images.setPreferredSize(new Dimension(width_images, height_images));
		GridLayout layout_images = new GridLayout(1, 2);
		layout_images.setHgap(80);
		images.setLayout(layout_images);
		images.setOpaque(false);
		images.setBorder(new EmptyBorder(35, 150, 35, 150));

		JLabel name = new JLabel(this.name);
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setVerticalAlignment(JLabel.CENTER);
		name.setFont(Resources.lotr.deriveFont(Font.BOLD, 36.f));
		name.setForeground(writing_color);

		JLabel line = new JLabel("Nombre de ligne = "
				+ String.valueOf(this.nb_line));
		line.setHorizontalAlignment(JLabel.CENTER);
		line.setVerticalAlignment(JLabel.CENTER);
		line.setFont(Resources.lotr.deriveFont(27.f));
		line.setForeground(writing_color);

		JLabel column = new JLabel("Nombre de colonne = "
				+ String.valueOf(this.nb_column));
		column.setHorizontalAlignment(JLabel.CENTER);
		column.setVerticalAlignment(JLabel.CENTER);
		column.setFont(Resources.lotr.deriveFont(27.f));
		column.setForeground(writing_color);

		Image img = null;
		if (this.succeed) {
			img = new ImageIcon(Resources.validate).getImage();
		} else {
			img = new ImageIcon(Resources.no_validate).getImage();
		}
		Image new_img = img.getScaledInstance((int) (height * 0.15),
				(int) (width * 0.15), java.awt.Image.SCALE_SMOOTH);

		JLabel succeed = new JLabel("");
		succeed.setHorizontalAlignment(JLabel.CENTER);
		succeed.setVerticalAlignment(JLabel.CENTER);
		succeed.setIcon(new ImageIcon(new_img));
		// succeed.setBorder(new LineBorder(new Color(155, 91, 0), 3));

		Image img2 = new ImageIcon(Resources.bin).getImage();
		Image new_img2 = img2.getScaledInstance((int) (height * 0.15),
				(int) (width * 0.15), java.awt.Image.SCALE_SMOOTH);

		JButton delete = new JButton("");
		delete.setHorizontalAlignment(JButton.CENTER);
		delete.setVerticalAlignment(JButton.CENTER);
		delete.setFont(Resources.lotr.deriveFont(Font.BOLD, 35.F));
		delete.setForeground(writing_color);
		delete.setFocusPainted(false);
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);

		delete.setIcon(new ImageIcon(new_img2));
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_validate_delete(id);
			}
		});

		informations.add(name);
		informations.add(line);
		informations.add(column);

		images.add(delete);
		images.add(succeed);

		this.add(informations, BorderLayout.NORTH);
		this.add(images, BorderLayout.SOUTH);
	}

}