package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.DB;
import utils.Resources;

@SuppressWarnings("serial")
public class Validate_suppression_panel extends JPanel {

	private static Color writing_color = new Color(155, 91, 0);

	/**
	 * Constructeur de la classe représentant le menu de validation de
	 * suppression. Lorsque l'on demande de supprimer un niveau. On lance cette
	 * fenetre pour s'assurer que l'utilisateur est bien sur de vouloir
	 * supprimer le niveau
	 * 
	 * @param id
	 *            l'identifiant du niveau que l'on veut supprimer
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param ref_win
	 *            une référence sur la fenetre principale
	 * @param db
	 *            une référence sur la base de données
	 */
	public Validate_suppression_panel(String id, int width, int height,
			Window ref_win, DB db) {

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		JPanel space_up = new JPanel();
		int w_up = width;
		int h_up = (int) (height * 0.3);
		space_up.setOpaque(false);
		space_up.setPreferredSize(new Dimension(w_up, h_up));

		JPanel space_down = new JPanel();
		int w_down = width;
		int h_down = (int) (height * 0.4);
		space_down.setOpaque(false);
		space_down.setPreferredSize(new Dimension(w_down, h_down));

		JPanel center = new JPanel();
		int w_center = width;
		int h_center = (int) (height * 0.4);
		center.setLayout(new BorderLayout());
		center.setPreferredSize(new Dimension(w_center, h_center));
		center.setBackground(new Color(255, 255, 255, 180));
		center.setBorder(BorderFactory.createMatteBorder(6, 0, 6, 0,
				Color.BLACK));

		JPanel label_text = new JPanel();
		int w_lab = w_center;
		int h_lab = (int) (h_center * 0.5);
		label_text.setBorder(new EmptyBorder(50, 0, 0, 0));
		label_text.setPreferredSize(new Dimension(w_lab, h_lab));
		label_text.setOpaque(false);

		JPanel buttons = new JPanel();
		int w_btn = w_center;
		int h_btn = (int) (h_center * 0.5);
		buttons.setPreferredSize(new Dimension(w_btn, h_btn));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(50, 0, 0, 0));
		GridLayout l_buttons = new GridLayout();
		l_buttons.setHgap(100);

		JLabel validate = new JLabel(
				"Etes vous sur de vouloir supprimer le niveau ?");
		validate.setHorizontalAlignment(JLabel.CENTER);
		validate.setVerticalAlignment(JLabel.CENTER);
		validate.setFont(Resources.lotr.deriveFont(Font.BOLD, 25.F));

		JButton yes = new JButton("Oui");
		yes.setHorizontalAlignment(JButton.CENTER);
		yes.setVerticalAlignment(JButton.CENTER);
		yes.setFont(Resources.lotr.deriveFont(Font.BOLD, 30.F));
		yes.setForeground(writing_color);
		yes.setFocusPainted(false);
		yes.setBorderPainted(false);
		yes.setContentAreaFilled(false);
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				db.delete_from_id(id);
				ref_win.launch_menu_choser();
			}
		});

		JButton no = new JButton("Non");
		no.setHorizontalAlignment(JButton.CENTER);
		no.setVerticalAlignment(JButton.CENTER);
		no.setFont(Resources.lotr.deriveFont(Font.BOLD, 30.F));
		no.setForeground(writing_color);
		no.setFocusPainted(false);
		no.setBorderPainted(false);
		no.setContentAreaFilled(false);
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_win.launch_menu_choser();
			}
		});

		label_text.add(validate);
		buttons.add(yes);
		buttons.add(no);

		center.add(label_text, BorderLayout.NORTH);
		center.add(buttons, BorderLayout.SOUTH);

		this.add(space_up, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(space_down, BorderLayout.SOUTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.map_1, 0, 0, getWidth(), getHeight(), null);
	}
}
