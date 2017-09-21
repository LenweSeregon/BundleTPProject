package Graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Help extends JPanel {

	/**
	 * Construction de la classe help, elle initialise tout les composantes
	 * graphiques qui la compose et les indentes
	 * 
	 * @param width
	 *            la largeur de notre menu
	 * @param height
	 *            la hauteur de notre menu
	 */
	public Help(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(new EmptyBorder(50, 100, 50, 100));

		JLabel title = new JLabel("Aide");
		title.setFont(title.getFont().deriveFont(40.0f));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		JLabel explaination = new JLabel();
		explaination.setFont(explaination.getFont().deriveFont(18.0f));
		explaination.setText(
				"<html>Voici l'aide pour le 2048.<br><br> Le but du jeu est d'obtenir une cellule contenant la valeur 2048. "
						+ "Pour cela, déplacer les cases grâce aux fléches directionnelles.<br>2048 incorpore un système de gravité "
						+ ", ainsi les cases se déplaceront toujours vers la direction choisis et irons jusqu'a "
						+ "rencontrer un mur ou une autre cellule.<br><br>" + "M = Retourner menu<br>"
						+ "J = Rejouer une partie<br>" + "H = Ouvrir / fermer l'aide<br>"
						+ "Echap = Quitter l'application</html>");

		this.setFocusable(false);
		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
		this.add(explaination, BorderLayout.CENTER);
	}
}
