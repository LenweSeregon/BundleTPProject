package view;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.JComponent;

import utils.Resources;

public class Column_indexes_view extends JComponent {

	private int pos_x;
	private int pos_y;
	private int width;
	private int height;
	private Color color;
	private Vector<Integer> indexes;
	private int width_text;
	private int height_text;
	private int font_size;
	private Vector<String> string_indexes_array;

	/**
	 * Constructeur de la classe représentant de manière graphique les aides
	 * visuelles d'indices qui se trouvent sur ce grille. Ces aides sont
	 * indispensables c'est pour cela qu'on les place de manière propre et
	 * ordonné pour que les utilisateurs puissent facilement avoir accès
	 * 
	 * @param pos_x
	 *            la position X de l'indicateur de colonne
	 * @param pos_y
	 *            la position Y de l'indicateur de colonne
	 * @param width
	 *            la largeur de l'indicateur de colonne
	 * @param height
	 *            la hauteur de l'indicateur de colonne
	 * @param color
	 *            la couleur de l'indicateur de colonne
	 * @param indexes
	 *            le vecteur contenant les différents indicateurs
	 */
	public Column_indexes_view(int pos_x, int pos_y, int width, int height,
			Color color, Vector<Integer> indexes) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBounds(pos_x, pos_y, width, height);
		this.setLayout(new GridLayout(4, 1));
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.indexes = new Vector<Integer>();
		this.string_indexes_array = new Vector<String>();

		if (indexes != null) {
			for (Integer i : indexes) {
				this.indexes.add(i.intValue());
				string_indexes_array.add(new String(i.toString()));
			}
		}

		int font_size = 100;
		int text_width = Integer.MIN_VALUE;
		int text_height = 0;
		while (true) {
			text_height = 0;
			text_width = Integer.MIN_VALUE;
			Canvas c = new Canvas();
			Font font = Resources.lotr.deriveFont(Font.BOLD, font_size);
			FontMetrics fm = c.getFontMetrics(font);

			for (String s : string_indexes_array) {
				text_width = Math.max(text_width, fm.stringWidth(s));
				text_height += fm.getHeight() + font_size + 2;
			}

			if (text_width <= width && text_height <= height - 10) {
				break;
			}

			font_size--;
			/*
			 * try { Thread.sleep(10); } catch (InterruptedException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			// break;
		}
		this.width_text = text_width;
		this.height_text = text_height;
		this.font_size = font_size;
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
	public void set_font_size(int font_size) {
		this.font_size = font_size;
		Canvas c = new Canvas();
		Font font = Resources.lotr.deriveFont(Font.BOLD, font_size);
		FontMetrics fm = c.getFontMetrics(font);

		int width = Integer.MIN_VALUE;
		for (String s : string_indexes_array) {
			width = Math.max(width, fm.stringWidth(s));
		}
		this.width_text = width;

		int height = 0;
		for (String s : string_indexes_array) {
			height += fm.getHeight();
		}
		this.height_text = height;
		this.repaint();
	}

	/**
	 * Méthode permettant de récupérer la taille de la police qui est utilisé
	 * 
	 * @return la taille de la plice
	 */
	public int get_font_size() {
		return font_size;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
		int x = (width / 3);
		int y = height - 10;
		for (int j = indexes.size() - 1; j >= 0; j--) {
			int i = indexes.get(j);
			String text = String.valueOf(i);
			Font font = Resources.lotr.deriveFont(Font.BOLD, font_size);
			int text_width = g2.getFontMetrics(font).stringWidth(text);
			int text_height = g2.getFontMetrics(font).getHeight();

			g2.drawString(text, (width - text_width) / 2, y);
			y -= font_size;
		}
	}
}
