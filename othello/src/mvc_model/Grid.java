package mvc_model;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import observer_pattern.Observable;
import observer_pattern.Observer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import enums.Owner;

public class Grid implements Observable {

	private int nb_line;
	private int nb_column;
	private int size_tile;

	private Tile[][] tiles;
	private Owner owner_turn;
	private boolean ai_mode;

	private ArrayList<Observer> observers;

	/**
	 * Constructeur de la classe représentant la grille de notre plateau. cette
	 * classe va gérer toutes les données du jeu en créant une architecture
	 * solide qui va toujours etre maintenu à jour quelque soit les actions
	 * réalisés.
	 * 
	 * @param nb_line
	 *            le nombre de ligne de notre grille
	 * @param nb_column
	 *            le nombre de colonne de notre grille
	 * @param size_tile
	 *            la taille des cellules de notre grille
	 * @param ai
	 *            la présence d'une ia dans notre grille
	 */
	public Grid(int nb_line, int nb_column, int size_tile, boolean ai) {
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_tile = size_tile;
		this.owner_turn = Owner.BLACK;

		this.tiles = new Tile[nb_line][nb_column];
		this.observers = new ArrayList<Observer>();
		this.ai_mode = ai;

		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j] = new Tile(i, j, size_tile, Owner.NONE);
			}
		}
		tiles[3][3].set_owner(Owner.WHITE);
		tiles[3][4].set_owner(Owner.BLACK);
		tiles[4][3].set_owner(Owner.BLACK);
		tiles[4][4].set_owner(Owner.WHITE);

		calcul_possible_hit();

	}

	/**
	 * Deuxième constructeur de la classe représentant le plateau. Ce
	 * constructeur un peu plus spécial va être appelé lorsque l'on ne va pas
	 * lancer une partie mais en charger une.
	 * 
	 * @param nb_line
	 *            le nombre de ligne de notre grille
	 * @param nb_column
	 *            le nombre de colonne de notre grille
	 * @param size_tile
	 *            la taille des cellules de notre grille
	 * @param file_name
	 *            le nom du fichier dans lequel il faudra charger les données
	 */
	public Grid(int nb_line, int nb_column, int size_tile, String file_name) {
		this.nb_line = nb_line;
		this.nb_column = nb_column;
		this.size_tile = size_tile;

		this.tiles = new Tile[nb_line][nb_column];
		this.observers = new ArrayList<Observer>();
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j] = new Tile(i, j, size_tile, Owner.NONE);
			}
		}
	}

	/**
	 * Méthode permettant de charger complétement un niveau selon un fichier.
	 * Cette méthode va s'occuper de parser un fichier XML contenant la
	 * sauvegarde. Si le fichier n'est pas bon, on tombera sur une exception
	 * 
	 * @param file
	 *            le fichier que l'on souhaite charger
	 */
	public boolean load_game(String file) {
		final File folder = new File(System.getProperty("user.home"), "saves");
		if (!folder.exists()) {
			return false;
		}

		final File myFile = new File(folder, "save.xml");
		InputStream inp = null;
		try {
			inp = new FileInputStream(myFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}

		final DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(inp);
			final Element root = document.getDocumentElement();

			final NodeList root_nodes = root.getChildNodes();
			final int nb_root_nodes = root_nodes.getLength();
			for (int k = 0; k < nb_root_nodes; k++) {
				if (root_nodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
					final Element element = (Element) root_nodes.item(k);

					if (element.getNodeName() == "ai") {
						this.ai_mode = Boolean
								.valueOf(element.getTextContent());

					} else if (element.getNodeName() == "turn") {
						String ow = element.getTextContent();
						if (ow.equals(Owner.BLACK.toString())) {
							this.owner_turn = Owner.BLACK;
						} else if (ow.equals(Owner.WHITE.toString())) {
							this.owner_turn = Owner.WHITE;
						}

					} else {

						final Element i = (Element) element
								.getElementsByTagName("i").item(0);
						final Element j = (Element) element
								.getElementsByTagName("j").item(0);
						final Element owner = (Element) element
								.getElementsByTagName("owner").item(0);

						int val_i = Integer.valueOf(i.getTextContent());
						int val_j = Integer.valueOf(j.getTextContent());

						if (owner.getTextContent().equals(
								Owner.BLACK.toString())) {
							this.tiles[val_i][val_j].set_owner(Owner.BLACK);
						}
						if (owner.getTextContent().equals(
								Owner.WHITE.toString())) {
							this.tiles[val_i][val_j].set_owner(Owner.WHITE);
						}
						if (owner.getTextContent()
								.equals(Owner.NONE.toString())) {
							this.tiles[val_i][val_j].set_owner(Owner.NONE);
						}
					}
				}
			}
			return true;
		}

		catch (final ParserConfigurationException | SAXException | IOException e) {
			return false;
		}
	}

	/**
	 * Méthode permettant de sauvegarder l'état actuel de notre fenetre. Ici la
	 * meme régle que pour le chargement est appliqué pour garder un ensemble
	 * cohérent.
	 * 
	 * @param file
	 *            le nom du fichier dans lequel on souhaite sauvegarder
	 */
	public void save_options(String file) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder doc_builder = factory.newDocumentBuilder();

			// root elements
			Document doc = doc_builder.newDocument();
			Element root_element = doc.createElement("plateau");
			doc.appendChild(root_element);

			Element vs_ai = doc.createElement("ai");
			vs_ai.appendChild(doc.createTextNode(String.valueOf(ai_mode)));
			root_element.appendChild(vs_ai);

			Element turn = doc.createElement("turn");
			turn.appendChild(doc.createTextNode(owner_turn.toString()));
			root_element.appendChild(turn);

			for (int i = 0; i < nb_line; i++) {
				for (int j = 0; j < nb_column; j++) {

					// option elements
					Element element = doc.createElement("element");
					root_element.appendChild(element);

					// description of option
					Element i_val = doc.createElement("i");
					i_val.appendChild(doc.createTextNode(String
							.valueOf(tiles[i][j].get_index_x())));
					element.appendChild(i_val);

					// value of option
					Element j_val = doc.createElement("j");
					j_val.appendChild(doc.createTextNode(String
							.valueOf(tiles[i][j].get_index_y())));
					element.appendChild(j_val);

					Element owner = doc.createElement("owner");
					owner.appendChild(doc.createTextNode(tiles[i][j]
							.get_owner().toString()));
					element.appendChild(owner);

				}
			}

			// write the content into xml file
			TransformerFactory transformer_factory = TransformerFactory
					.newInstance();
			Transformer transformer = transformer_factory.newTransformer();
			DOMSource source = new DOMSource(doc);

			final File folder = new File(System.getProperty("user.home"),
					"saves");
			if (!folder.exists() && !folder.mkdir()) {
				System.err.println("Impossible");
			}
			final File myFile = new File(folder, "save.xml");

			// StreamResult result = new StreamResult(new File(file));
			StreamResult result = new StreamResult(myFile);

			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode permettant de savoir le nombre de possibilité possible du joueur
	 * à un instant T
	 * 
	 * @return le nombre de possibilité pour jouer
	 */
	public int get_nb_possiblity() {
		int nb = 0;
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j].get_possible_hit()) {
					nb++;
				}
			}
		}
		return nb;
	}

	/**
	 * Méthode permettant de calculer le nombre de possibilité dans notre grille
	 * par rapportt au joueur courant
	 */
	public void calcul_possible_hit() {
		Vector<Tile> tile_to_test = new Vector<Tile>();
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				tiles[i][j].set_possible_hit(false);
				if (tiles[i][j].get_owner() == this.owner_turn) {
					tile_to_test.add(tiles[i][j]);
				}
			}
		}

		for (Tile tile : tile_to_test) {
			test_up(tile);
			test_up_right(tile);
			test_up_left(tile);
			test_left(tile);
			test_right(tile);
			test_down(tile);
			test_down_right(tile);
			test_down_left(tile);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction haut
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_up(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, -1, 0);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction haut droite
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_up_right(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, -1, 1);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction haut gauche
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_up_left(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, -1, -1);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction gauche
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_left(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, 0, -1);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction droite
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_right(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, 0, 1);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction bas
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_down(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, 1, 0);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction bas droit
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_down_right(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, 1, 1);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode permettant de mettre à jour les combinaisons possibles par
	 * rapport à la direction bas gauche
	 * 
	 * @param tile
	 *            la cellule que l'on veut tester pour la combinaison
	 */
	private void test_down_left(Tile tile) {
		int x = tile.get_index_x();
		int y = tile.get_index_y();

		Dimension d = can_place_piece(x, y, 1, -1);
		if (d != null) {
			tiles[d.width][d.height].set_possible_hit(true);
		}
	}

	/**
	 * Méthode appellé lorsque l'on clique sur une cellule qui représente une
	 * possibilité. Cette méthode va s'assurer d'appliquer les régles de
	 * l'othello en retournant les différents pions.
	 * 
	 * @param i
	 *            index sur l'axe Y de la cellule
	 * @param j
	 *            index sur l'axe X de la cellule
	 */
	public void click_on_tile(int i, int j) {
		Owner counter = null;
		if (owner_turn == Owner.WHITE) {
			counter = Owner.BLACK;
		} else {
			counter = Owner.WHITE;
		}

		if (i > 0 && tiles[i - 1][j].get_owner() == counter) { // UP

			if (has_current_in_way(i, j, -1, 0)) {
				replace_to_current_player(i, j, -1, 0);
			}
		}
		if (i > 0 && j > 0 && tiles[i - 1][j - 1].get_owner() == counter) { // UPLEFT

			if (has_current_in_way(i, j, -1, -1)) {
				replace_to_current_player(i, j, -1, -1);
			}
		}
		if (i > 0 && j < nb_column - 1
				&& tiles[i - 1][j + 1].get_owner() == counter) { // UPRIGHT

			if (has_current_in_way(i, j, -1, 1)) {
				replace_to_current_player(i, j, -1, 1);
			}
		}
		if (j > 0 && tiles[i][j - 1].get_owner() == counter) { // LEFT
			if (has_current_in_way(i, j, 0, -1)) {
				replace_to_current_player(i, j, 0, -1);
			}
		}
		if (j < nb_column - 1 && tiles[i][j + 1].get_owner() == counter) { // RIGHT

			if (has_current_in_way(i, j, 0, 1)) {
				replace_to_current_player(i, j, 0, 1);
			}
		}
		if (i < nb_line - 1 && tiles[i + 1][j].get_owner() == counter) { // DOWN

			if (has_current_in_way(i, j, 1, 0)) {
				replace_to_current_player(i, j, 1, 0);
			}
		}
		if (i < nb_line - 1 && j > 0
				&& tiles[i + 1][j - 1].get_owner() == counter) { // DOWN LEFT

			if (has_current_in_way(i, j, 1, -1)) {
				replace_to_current_player(i, j, 1, -1);
			}
		}
		if (i < nb_line - 1 && j < nb_column - 1
				&& tiles[i + 1][j + 1].get_owner() == counter) { // DOWN RIGHT

			if (has_current_in_way(i, j, 1, 1)) {
				replace_to_current_player(i, j, 1, 1);
			}
		}
	}

	/**
	 * Méthode permettant de savoir si l'on peut placer une piece par rapport à
	 * un index X et Y sur la grille. Cette méthode va réaliser un parcours pour
	 * arriver jusqu'a une case vide ou le bord de la carte pour déterminer si
	 * elle peut
	 * 
	 * @param i
	 *            l'index sur l'axe Y
	 * @param j
	 *            l'index sur l'axe X
	 * @param i_mod
	 *            le modifier de I pour les tests
	 * @param j_mod
	 *            le modifier de J pour les tests
	 * @return une dimension ou il est possible de placer la piece si elle
	 *         existe, null sinon
	 */
	private Dimension can_place_piece(int i, int j, int i_mod, int j_mod) {

		Owner counter = null;
		if (owner_turn == Owner.WHITE) {
			counter = Owner.BLACK;
		} else {
			counter = Owner.WHITE;
		}

		int i_flw = i + i_mod;
		int j_flw = j + j_mod;
		if (i_flw < 0 || i_flw >= nb_line || j_flw < 0 || j_flw >= nb_column) {
			return null;
		}

		if (tiles[i_flw][j_flw].get_owner() != counter) {
			return null;
		}

		boolean done = true;
		while (true) {
			if (i_flw < 0 || i_flw >= nb_line || j_flw < 0
					|| j_flw >= nb_column) {
				done = false;
				break;
			}
			if (tiles[i_flw][j_flw].get_owner() == Owner.NONE) {
				done = true;
				break;
			}
			if (tiles[i_flw][j_flw].get_owner() == owner_turn) {
				done = false;
				break;
			}
			i_flw += i_mod;
			j_flw += j_mod;
		}

		return (done) ? (new Dimension(i_flw, j_flw)) : (null);
	}

	/**
	 * Méthode permettant de savoir si il existe sur la route d'un pion (i,j)
	 * une cellule pouvant être retourner pour faire l'objet d'un tour de jeu
	 * 
	 * @param i
	 *            l'index sur l'axe Y
	 * @param j
	 *            l'index sur l'axe X
	 * @param i_mod
	 *            le modifier de I pour les tests
	 * @param j_mod
	 *            le modifier de J pour les tests
	 * @return vrai si il y a un chemin, faux sinon
	 */
	private boolean has_current_in_way(int i, int j, int i_mod, int j_mod) {
		Owner counter = null;
		if (owner_turn == Owner.WHITE) {
			counter = Owner.BLACK;
		} else {
			counter = Owner.WHITE;
		}

		int i_flw = i + i_mod;
		int j_flw = j + j_mod;
		boolean done = true;
		while (tiles[i_flw][j_flw].get_owner() == counter) {
			i_flw += i_mod;
			j_flw += j_mod;
			if (i_flw < 0 || i_flw >= nb_line) {
				done = false;
				break;
			}
			if (j_flw < 0 || j_flw >= nb_column) {
				done = false;
				break;
			}

			if (tiles[i_flw][j_flw].get_owner() == owner_turn) {
				break;
			}
			if (tiles[i_flw][j_flw].get_owner() == Owner.NONE) {
				done = false;
				break;
			}

		}
		return done;
	}

	/**
	 * Méthode permettant de remplacer sur le chemin que l'on a trouvé tous les
	 * pions par la couleur du joueur courant
	 * 
	 * @param i
	 *            l'index sur l'axe Y
	 * @param j
	 *            l'index sur l'axe X
	 * @param i_mod
	 *            le modifier de I pour les tests
	 * @param j_mod
	 *            le modifier de J pour les tests
	 */
	private void replace_to_current_player(int i, int j, int i_mod, int j_mod) {
		Owner counter = null;
		if (owner_turn == Owner.WHITE) {
			counter = Owner.BLACK;
		} else {
			counter = Owner.WHITE;
		}

		int i_flw = i + i_mod;
		int j_flw = j + j_mod;
		while (tiles[i_flw][j_flw].get_owner() == counter) {
			tiles[i_flw][j_flw].set_owner(owner_turn);
			i_flw += i_mod;
			j_flw += j_mod;
		}
	}

	/**
	 * Méthode permettant de récuperer tous les couples d'index possibles pour
	 * les différents coups qui peuvent etre joué
	 * 
	 * @return un vector contenant tout les différents coups possibles
	 */
	public Vector<Dimension> get_all_possible_hit_indexes() {
		Vector<Dimension> dims = new Vector<Dimension>();

		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j].get_possible_hit()) {
					dims.add(new Dimension(i, j));
				}
			}
		}
		return dims;
	}

	public boolean is_ai_mode() {
		return ai_mode;
	}

	/**
	 * Méthode permettant de récupérer le nombre de piece du joueur blanc
	 * 
	 * @return le nombre de piece du joueur blanc
	 */
	public int get_nb_piece_white() {
		int nb = 0;
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j].get_owner() == Owner.WHITE) {
					nb++;
				}
			}
		}
		return nb;
	}

	/**
	 * Méthode permettant de récupérer le nombre de piece du joueur noir
	 * 
	 * @return le nombre de piece du joueur noir
	 */
	public int get_nb_piece_black() {
		int nb = 0;
		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				if (tiles[i][j].get_owner() == Owner.BLACK) {
					nb++;
				}
			}
		}
		return nb;
	}

	/**
	 * Méthode permettant de passer au joueur suivant dans la grille
	 */
	public void next_player() {
		if (owner_turn == Owner.BLACK) {
			owner_turn = Owner.WHITE;
		} else {
			owner_turn = Owner.BLACK;
		}
	}

	/**
	 * Méthode permettant de récuéprer le joueur courant dans la grille
	 * 
	 * @return le joueur courant
	 */
	public Owner get_player() {
		return owner_turn;
	}

	/**
	 * Méthode permettant de récupérer toutes les cellules de la grille tels
	 * quelles sont stockés
	 * 
	 * @return un tableau statique à deux dimension représentant les cellules
	 */
	public Tile[][] get_tiles() {
		return this.tiles;
	}

	/**
	 * Méthode permettant de récupérer une cellule par rapport à une positon
	 * (i,j) donnée
	 * 
	 * @param i
	 *            l'index sur l'axe Y
	 * @param j
	 *            l'index sur l'axe X
	 * @return
	 */
	public Tile get_tile(int i, int j) {
		return this.tiles[i][j];
	}

	/**
	 * Méthode permettant de récupérer le nombre de ligne qui se trouve dans la
	 * grille
	 * 
	 * @return le nombre de ligne
	 */
	public int get_nb_line() {
		return this.nb_line;
	}

	/**
	 * Méthode permettant de récupérer le nombre de colonne qui se trouve dans
	 * la grille
	 * 
	 * @return le nombre de colonne
	 */
	public int get_nb_column() {
		return this.nb_column;
	}

	/**
	 * Méthode permettant de récupérer la taille des cellules de la grille
	 * 
	 * @return la taille des cellules
	 */
	public int get_size_tile() {
		return this.size_tile;
	}

	public Grid get_copy() {
		Grid grid = new Grid(this.nb_line, this.nb_column, this.size_tile,
				this.ai_mode);
		grid.owner_turn = this.owner_turn;

		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				grid.tiles[i][j] = this.tiles[i][j].get_copy();
			}
		}

		return grid;
	}

	@Override
	public void add_observer(Observer ob) {
		observers.add(ob);
	}

	@Override
	public void remove_observers() {
		observers.clear();
	}

	@Override
	public void notify_creation(Grid grid) {
		for (Observer ob : observers) {
			ob.update_creation(grid);
		}
	}

	@Override
	public void notify_grid_change(Tile[][] grid) {
		for (Observer ob : observers) {
			ob.update_grid_change(grid);
		}
	}

	@Override
	public void notify_change_player(Owner owner) {
		for (Observer ob : observers) {
			ob.update_change_player(owner);
		}
	}

	@Override
	public void notify_no_hit(boolean display) {
		for (Observer ob : observers) {
			ob.update_no_hit(display);
		}
	}

	@Override
	public void notify_winner(Owner owner) {
		for (Observer ob : observers) {
			ob.update_winner(owner);
		}
	}

}
