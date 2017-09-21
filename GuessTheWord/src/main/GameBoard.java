package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	private DrawArea draw_area;
	private JPanel letter_word_area;
	private JPanel letters_area;

	private char detected;

	private Word word;
	private int nb_life;
	private String difficulty_level;
	private Vector<String> all_words;

	private Window refWin;

	/**
	 * Constructeur de la classe, permet d'initaliser toute l'interface
	 * graphique et la logique du jeu
	 * 
	 * @param refWin
	 *            Une référence vers le jeu pour pouvoir demander de quitter la
	 *            fenetre
	 */
	public GameBoard(Window refWin) {

		this.refWin = refWin;
		this.all_words = new Vector<String>();
		this.load_words();

		this.setLayout(new BorderLayout());

		create_UI();
		init();
	}

	/**
	 * Méthode privée qui permet de charger dans le via mots.txt les différents
	 * mots de l'application pour pouvoir en choisir aléatoirement
	 */
	private void load_words() {

		InputStream in = getClass().getResourceAsStream("mots.txt");
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(isr);

		String word;
		try {
			while ((word = reader.readLine()) != null) {
				all_words.add(word);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Méthode permettant de demander la difficulté du pendu via une JOptionPane
	 * à l'utilisateur
	 * 
	 * @return retourne la valeur de la difficulte sous forme de string
	 * @see JOptionPane
	 */
	public String get_difficulty() {
		String[] diffilculties = { "Easy", "Medium", "Hard" };

		String difficult = (String) JOptionPane.showInputDialog(null, "Indiquez la difficulté", "Lol",
				JOptionPane.QUESTION_MESSAGE, null, diffilculties, diffilculties[1]);

		return difficult;
	}

	/**
	 * Methode permettant de mettre la difficulté dans le modéle logique du jeu
	 * 
	 * @param s
	 *            String représentant la difficulté du pendu
	 */
	public void build_difficulty(String s) {
		this.difficulty_level = s;
		if (s == "Easy") {
			nb_life = 9;
		} else if (s == "Medium") {
			nb_life = 7;
		} else {
			nb_life = 5;
		}
	}

	/**
	 * Methode interne permettant de créer l'interface graphique
	 */
	private void create_UI() {
		/* DRAWING AREA */
		this.draw_area = new DrawArea();
		this.draw_area.setLayout(new BorderLayout());

		/* LETTERS AREA */
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		letters_area = new JPanel(new GridLayout(6, 5));
		letters_area.setPreferredSize(new Dimension(400, 350));
		letters_area.setBorder(border);
		char a = 'A';
		for (int i = 0; i < 26; i++, a++) {
			JButton button = new JButton(Character.toString(a));
			button.setEnabled(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button.setEnabled(false);
					selectionButtonPressed(button.getText());
				}
			});

			letters_area.add(button);
		}

		/* WORD AREA */
		this.word = new Word("");
		this.word.setPreferredSize(new Dimension(400, 450));
		this.word.repaint();

		/* LEFT PANEL AREA */
		this.letter_word_area = new JPanel(new BorderLayout());
		this.letter_word_area.setPreferredSize(new Dimension(400, 800));
		this.letter_word_area.setBorder(border);
		this.letter_word_area.add(letters_area, BorderLayout.NORTH);
		this.letter_word_area.add(word, BorderLayout.SOUTH);

		/* RIGHT PANEL AREA */
		this.draw_area = new DrawArea();
		this.draw_area.setPreferredSize(new Dimension(400, 800));
		this.draw_area.setBorder(border);
		this.draw_area.set_level_drawing(0);
		this.draw_area.repaint();

		JButton btnSurr = new JButton("Abandonner");
		btnSurr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nb_life = 0;
				word.reveal_all();
				word.repaint();
				JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Partie terminée",
						JOptionPane.INFORMATION_MESSAGE);
				int option = JOptionPane.showConfirmDialog(null, "Rejouer", "Voulez vous refaire une partie ?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (option == JOptionPane.OK_OPTION) {
					init();
					repaint();
				} else {
					refWin.exit();
				}
			}
		});
		this.draw_area.add(btnSurr);

		/* WINDOW ADDING */
		this.setLayout(new BorderLayout());
		this.add(this.letter_word_area, BorderLayout.WEST);
		this.add(this.draw_area, BorderLayout.EAST);

	}

	/**
	 * Methode permettant d'initialiser le jeu, elle peut etre rappelé pour
	 * lancer une nouvelle partie
	 */
	public void init() {

		build_difficulty(get_difficulty());

		for (int i = 0; i < 26; i++) {
			this.letters_area.getComponent(i).setEnabled(true);
		}

		Random rand = new Random();
		String word = this.all_words.get(rand.nextInt(this.all_words.size()));
		this.word.reinit(word);
		this.word.repaint();
		this.draw_area.set_level_drawing(0);
		this.draw_area.repaint();
	}

	/**
	 * La méthode est appelé par lors de l'appuie d'un bouton du clavier virtuel
	 * par l'utilisateur et demande à une autre fonction de tester cette touche
	 * 
	 * @param s
	 *            String représentant la touche du bouton appuyé par
	 *            l'utilisateur
	 */
	public void selectionButtonPressed(String s) {
		this.detected = s.charAt(0);
		test_button_detected();
	}

	/**
	 * Méthode qui réalise le test du bouton appuyé par l'utilisateur. La
	 * fonction s'occupe de gérer la révélation d'une lettre, de specifier la
	 * victoire ou la défaite et de relancer une partie si le joueur le veut
	 */
	public void test_button_detected() {

		boolean win = false;
		if (this.word.letter_exist(this.detected)) {
			this.word.reveal_letter(this.detected);
			if (this.word.all_revealed()) {
				win = true;
			}
			this.word.repaint();
			this.repaint();
		} else {
			this.nb_life--;
			if (difficulty_level == "Easy") {
				this.draw_area.set_level_drawing(this.draw_area.get_level_drawing() + 1);
			} else if (difficulty_level == "Medium") {

				if (nb_life % 2 == 0) {
					this.draw_area.set_level_drawing(this.draw_area.get_level_drawing() + 1);
				} else if (nb_life == 1) {
					this.draw_area.set_level_drawing(this.draw_area.get_level_drawing() + 1);
				} else if (nb_life == 0) {
					this.draw_area.set_level_drawing(9);
				} else {
					this.draw_area.set_level_drawing(this.draw_area.get_level_drawing() + 2);
				}
			} else {
				if (nb_life == 0) {
					this.draw_area.set_level_drawing(9);
				} else {
					this.draw_area.set_level_drawing(this.draw_area.get_level_drawing() + 2);
				}
			}
			// this.draw_area.set_level_drawing(this.max_life - this.nb_life);
			this.draw_area.repaint();
		}

		if (this.nb_life == 0) {
			this.word.reveal_all();
			this.word.repaint();
			JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Partie terminée",
					JOptionPane.INFORMATION_MESSAGE);
			int option = JOptionPane.showConfirmDialog(null, "Rejouer", "Voulez vous refaire une partie ?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				this.init();
				this.repaint();
			} else {
				this.refWin.exit();
			}
		} else if (win) {
			JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Partie terminée",
					JOptionPane.INFORMATION_MESSAGE);
			int option = JOptionPane.showConfirmDialog(null, "Rejouer", "Voulez vous refaire une partie ?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				this.init();
				this.repaint();
			} else {
				this.refWin.exit();
			}
		}
	}
}
