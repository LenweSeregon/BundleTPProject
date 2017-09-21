package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import utils.Resources;

public class Chrono extends JLabel implements Runnable {

	private long elapsed_time;
	private StringBuilder time_representation;
	volatile private boolean is_running;

	/**
	 * Constructeur de la classe représentant le chrono du temps. Ce chrono va
	 * s'actualiser toutes les secondes pour que le joueur sache combien de
	 * temps il a mit pour réaliser un niveau. La classe extendu un JLabel pour
	 * avoir le score en texte et mettre une image de fond et implemente
	 * Runnable pour ne pas bloquer le Thread principal
	 * 
	 * @param pos_x
	 *            la position X du chrono
	 * @param pos_y
	 *            la position Y du chrono
	 */
	public Chrono(int pos_x, int pos_y) {
		this.elapsed_time = 0;
		this.time_representation = new StringBuilder("00:00:00");
		this.is_running = false;

		// Oeil de sauron
		Image img = new ImageIcon(Resources.sauron_eye).getImage();
		Image newimg = img.getScaledInstance(pos_x - 20, pos_y - 20,
				java.awt.Image.SCALE_SMOOTH);

		this.setText(time_representation.toString());
		this.setOpaque(false);
		this.setFont(Resources.lotr.deriveFont(Font.BOLD, 40.f));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setVerticalTextPosition(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setForeground(Color.WHITE);
		this.setBorder(new LineBorder(Color.black, 7));
		this.setBounds(7, 7, pos_x - 7, pos_y - 7);
		this.setIcon(new ImageIcon(newimg));
	}

	/**
	 * Méthode permettant de choisir la valeur de is running dans la classe
	 * chrono. Si celle ci passe à false. Le chrono s'arretera
	 * 
	 * @param b
	 *            la valeure booléenne que l'on souhaite mettre au
	 *            fonctionnement du chrono
	 */
	public void set_is_running(boolean b) {
		this.is_running = b;
	}

	@Override
	public void run() {
		this.is_running = true;

		while (is_running) {
			elapsed_time++;
			if (elapsed_time < 60) {
				time_representation.setLength(0);

				if (elapsed_time < 10) {
					time_representation.append("00:00:0"
							+ String.valueOf(elapsed_time));
				} else {
					time_representation.append("00:00:"
							+ String.valueOf(elapsed_time));
				}
			} else {
				int hours = (int) elapsed_time / 3600;
				int minutes = (int) (elapsed_time % 3600) / 60;
				int seconds = (int) elapsed_time % 60;

				time_representation.setLength(0);
				time_representation.append(String.format("%02d:%02d:%02d",
						hours, minutes, seconds));

			}
			this.setText(time_representation.toString());
			this.revalidate();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
