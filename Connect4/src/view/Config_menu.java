package view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Play_mode;

@SuppressWarnings("serial")
public class Config_menu extends JPanel {

	private int column;
	private int line;
	private int win_hit;
	private boolean suicide;
	private Color player_color;

	/**
	 * Construction de la classe Config_menu pour créer le menu de
	 * configuration, initialiser les élements graphiques et pouvoir lancer une
	 * partie en fonction des valeurs demandés
	 * 
	 * @param ref_win
	 *            une référence vers la fenetre pour envoyer des informations
	 *            tels que lancer une partie
	 * @param mode
	 *            le mode de jeu qui a été choisi sur le menu principal
	 */
	public Config_menu(Window ref_win, Play_mode mode) {

		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		this.line = 6;
		this.column = 7;
		this.win_hit = 4;
		this.suicide = false;
		this.player_color = Color.blue;

		JLabel title_line = new JLabel("Nombre de ligne :");
		title_line.setFont(title_line.getFont().deriveFont(20.0f));
		title_line.setHorizontalAlignment(JLabel.LEFT);
		title_line.setVerticalAlignment(JLabel.CENTER);
		title_line.setForeground(Color.white);

		JSlider nb_line = new JSlider();
		nb_line.setMaximum(10);
		nb_line.setMinimum(3);
		nb_line.setValue(6);
		nb_line.setPaintTicks(true);
		nb_line.setPaintLabels(true);
		nb_line.setMinorTickSpacing(1);
		nb_line.setMajorTickSpacing(1);
		nb_line.setOpaque(false);
		nb_line.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				line = Integer.valueOf(((JSlider) event.getSource()).getValue());
			}
		});

		JLabel title_column = new JLabel("Nombre de colonne :");
		title_column.setFont(title_column.getFont().deriveFont(20.0f));
		title_column.setHorizontalAlignment(JLabel.LEFT);
		title_column.setVerticalAlignment(JLabel.CENTER);
		title_column.setForeground(Color.white);

		JSlider nb_column = new JSlider();
		nb_column.setMaximum(10);
		nb_column.setMinimum(3);
		nb_column.setValue(7);
		nb_column.setPaintTicks(true);
		nb_column.setPaintLabels(true);
		nb_column.setMinorTickSpacing(1);
		nb_column.setMajorTickSpacing(1);
		nb_column.setOpaque(false);
		nb_column.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				column = Integer.valueOf(((JSlider) event.getSource())
						.getValue());
			}
		});

		JLabel title_win_hit = new JLabel("Nombre de coup gagnant :");
		title_win_hit.setFont(title_win_hit.getFont().deriveFont(20.0f));
		title_win_hit.setHorizontalAlignment(JLabel.LEFT);
		title_win_hit.setVerticalAlignment(JLabel.CENTER);
		title_win_hit.setForeground(Color.white);

		// Nb coup gagnant 4 5 6 7
		JSlider win_hit_slider = new JSlider();
		win_hit_slider.setMaximum(7);
		win_hit_slider.setMinimum(3);
		win_hit_slider.setValue(4);
		win_hit_slider.setPaintTicks(true);
		win_hit_slider.setPaintLabels(true);
		win_hit_slider.setMinorTickSpacing(1);
		win_hit_slider.setMajorTickSpacing(1);
		win_hit_slider.setOpaque(false);
		win_hit_slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				win_hit = Integer.valueOf(((JSlider) event.getSource())
						.getValue());
			}
		});

		JLabel title_suicide = new JLabel("Mode suicide :");
		title_suicide.setFont(title_win_hit.getFont().deriveFont(20.0f));
		title_suicide.setHorizontalAlignment(JLabel.LEFT);
		title_suicide.setVerticalAlignment(JLabel.CENTER);
		title_suicide.setForeground(Color.white);

		String[] suicide_options = { "Activé", "Désactivé" };

		// Create the combo box, select item at index 4.
		// Indices start at 0, so 4 specifies the pig.
		JComboBox suicid_list = new JComboBox(suicide_options);
		suicid_list.setSelectedIndex(1);
		suicid_list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String selected = (String) cb.getSelectedItem();
				if (selected == "Activé") {
					suicide = true;
				} else {
					suicide = false;
				}
			}
		});

		JLabel color_first = new JLabel("Couleur premier joueur :");
		color_first.setFont(title_win_hit.getFont().deriveFont(20.0f));
		color_first.setHorizontalAlignment(JLabel.LEFT);
		color_first.setVerticalAlignment(JLabel.CENTER);
		color_first.setForeground(Color.white);

		String[] color_options = { "Jaune", "Rouge" };

		// Create the combo box, select item at index 4.
		// Indices start at 0, so 4 specifies the pig.
		JComboBox color_list = new JComboBox(color_options);
		color_list.setSelectedIndex(0);
		color_list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String selected = (String) cb.getSelectedItem();
				if (selected == "Jaune") {
					player_color = Color.yellow;
				} else {
					player_color = Color.red;
				}
			}
		});

		JButton valid = new JButton("Valider");
		valid.setHorizontalAlignment(JButton.CENTER);
		valid.setVerticalAlignment(JButton.CENTER);
		valid.setBackground(Color.white);
		valid.setForeground(Color.black);
		valid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mode == Play_mode.AI) {
					ref_win.launch_versus_ai(line, column, win_hit, suicide,
							player_color);
				} else {
					ref_win.launch_versus_human(line, column, win_hit, suicide,
							player_color);
				}
			}
		});

		GridLayout layout = new GridLayout(6, 3);
		layout.setHgap(30);
		layout.setVgap(30);
		this.setLayout(layout);

		this.add(title_line);
		this.add(nb_line);
		this.add(new JLabel());

		this.add(title_column);
		this.add(nb_column);
		this.add(new JLabel());

		this.add(title_win_hit);
		this.add(win_hit_slider);
		this.add(new JLabel());

		this.add(title_suicide);
		this.add(suicid_list);
		this.add(new JLabel());

		this.add(color_first);
		this.add(color_list);
		this.add(new JLabel());

		this.add(new JLabel());
		this.add(new JLabel());
		this.add(valid);

		this.repaint();

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		GradientPaint grad = new GradientPaint(0, 0, Color.BLACK, getWidth(),
				getHeight(), Color.WHITE);
		g2.setPaint(grad);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
}
