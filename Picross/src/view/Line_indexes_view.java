package view;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.JComponent;

import utils.Resources;

public class Line_indexes_view extends JComponent {

	private int pos_x;
	private int pos_y;
	private int width;
	private int height;
	private Color color;
	private Vector<Integer> indexes;
	private String indexes_string;
	private int font_size;
	private int width_text;
	private int height_text;

	/**
	 * Consutrcteur de la classe représentant de manière graphique les aides
	 * visuelles d'indices qui se trouvent sur ce grille. Ces aides sont
	 * indispensables c'est pour cela qu'on les place de manière propre et
	 * ordonné pour que les utilisateurs puissent facilement avoir accès
	 * 
	 * @param pos_x
	 *            la position X de l'indicateur de ligne
	 * @param pos_y
	 *            la position Y de l'indicateur de ligne
	 * @param width
	 *            la largeur de l'indicateur de ligne
	 * @param height
	 *            la hauteur de l'indicateur de ligne
	 * @param color
	 *            la couleur de l'indicateur de ligne
	 * @param indexes
	 *            le vecteur contenant les différents indicateurs
	 */
	public Line_indexes_view(int pos_x, int pos_y, int width, int height,
			Color color, Vector<Integer> indexes) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBounds(pos_x, pos_y, width, height);
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.indexes = new Vector<Integer>();

		StringBuffer str = new StringBuffer("");
		if (indexes != null) {
			for (Integer i : indexes) {
				this.indexes.add(i.intValue());
				str.append(String.valueOf(i) + " ");
			}
		}
		this.indexes_string = str.toString();
		int text_width;
		int text_height;
		int font_size = 100;
		while (true) {
			Font font = Resources.lotr.deriveFont(Font.BOLD, font_size);
			Canvas c = new Canvas();
			FontMetrics fm = c.getFontMetrics(font);
			text_width = fm.stringWidth(indexes_string);
			text_height = fm.getHeight();
			if (text_width + 20 < width && text_height < height) {
				break;
			}
			font_size--;
		}
		this.font_size = font_size;
		this.width_text = text_width;
		this.height_text = text_height;
	}

	/**
	 * Méthode permettant de récupérer la taille de la police qui est utilisé
	 * 
	 * @return la taille de la plice
	 */
	public int get_font_size() {
		return font_size;
	}

	/**
	 * Méthode permettant de définir la taille de la police des nombres qui sont
	 * ajoutés au barres d'indication. La méthode va s'occuper de recalculer
	 * avec la taille de la police la largeur et la hauteur de la barre
	 * d'indication
	 * 
	 * @param font_size
	 *            la taille de la police.
	 */
	public void set_font_size(int size) {
		this.font_size = size;

		Canvas c = new Canvas();
		Font font = Resources.lotr.deriveFont(Font.BOLD, font_size);
		FontMetrics fm = c.getFontMetrics(font);
		this.width_text = fm.stringWidth(indexes_string);
		this.height_text = fm.getHeight();

		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(color);
		g2.fillRect(0, 0, width, height);
		g2.setColor(new Color(65, 11, 0));
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(0, 0, width, height);
		g2.setStroke(new BasicStroke(1));

		g2.setColor(Color.WHITE);
		g2.setFont(Resources.lotr.deriveFont(Font.BOLD, font_size));
		g2.drawString(indexes_string.toString(), width - width_text, font_size);
	}
}
