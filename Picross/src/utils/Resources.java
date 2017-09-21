package utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	// Fonts
	public static Font libertin_font;
	public static Font enchant_land;
	public static Font lotr;

	// Images

	public static BufferedImage left_arrow;
	public static BufferedImage right_arrow;
	public static BufferedImage no_validate;
	public static BufferedImage validate;
	public static BufferedImage minas_tirith_1;
	public static BufferedImage minas_tirith_2;
	public static BufferedImage minas_tirith_3;
	public static BufferedImage minas_tirith_4;
	public static BufferedImage map_1;
	public static BufferedImage map_2;
	public static BufferedImage map_3;
	public static BufferedImage sauron_eye;
	public static BufferedImage ring_1;
	public static BufferedImage ring_2;
	public static BufferedImage sword;
	public static BufferedImage flammes;
	public static BufferedImage bin;

	/**
	 * Constructeur d'une classe qui ne contient que des attributs publique et
	 * static qui représentent les différentes ressources du jeu
	 */
	public Resources() {

		try {
			// Font loading
			libertin_font = Font.createFont(Font.TRUETYPE_FONT, getClass()
					.getResourceAsStream("/resources/fonts/libertine.ttf"));
			enchant_land = Font.createFont(Font.TRUETYPE_FONT, getClass()
					.getResourceAsStream("/resources/fonts/enchant_land.otf"));
			lotr = Font.createFont(Font.TRUETYPE_FONT, getClass()
					.getResourceAsStream("/resources/fonts/lotr.ttf"));

			// Image loading
			left_arrow = ImageIO.read(getClass().getResource(
					"/resources/images/left_arrow.png"));

			right_arrow = ImageIO.read(getClass().getResource(
					"/resources/images/right_arrow.png"));

			no_validate = ImageIO.read(getClass().getResource(
					"/resources/images/no_validate.png"));

			validate = ImageIO.read(getClass().getResource(
					"/resources/images/validate.png"));

			minas_tirith_1 = ImageIO.read(getClass().getResource(
					"/resources/images/minas_tirith_1.jpeg"));

			minas_tirith_2 = ImageIO.read(getClass().getResource(
					"/resources/images/minas_tirith_2.jpeg"));

			minas_tirith_3 = ImageIO.read(getClass().getResource(
					"/resources/images/minas_tirith_3.jpeg"));

			minas_tirith_4 = ImageIO.read(getClass().getResource(
					"/resources/images/minas_tirith_4.jpg"));

			map_1 = ImageIO.read(getClass().getResource(
					"/resources/images/map_1.png"));

			map_2 = ImageIO.read(getClass().getResource(
					"/resources/images/map_2.jpg"));

			map_3 = ImageIO.read(getClass().getResource(
					"/resources/images/map_3.jpg"));

			sauron_eye = ImageIO.read(getClass().getResource(
					"/resources/images/sauron_eye.jpg"));

			ring_1 = ImageIO.read(getClass().getResource(
					"/resources/images/ring_1.png"));

			ring_2 = ImageIO.read(getClass().getResource(
					"/resources/images/ring_2.png"));

			sword = ImageIO.read(getClass().getResource(
					"/resources/images/sword.png"));

			flammes = ImageIO.read(getClass().getResource(
					"/resources/images/flammes.png"));

			bin = ImageIO.read(getClass().getResource(
					"/resources/images/bin.png"));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

	}
}
