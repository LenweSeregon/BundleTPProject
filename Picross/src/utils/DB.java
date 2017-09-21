package utils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

	private String DB_url;
	private String DB_user;
	private String DB_password;
	private java.sql.Connection DB_connect;
	private java.sql.Statement DB_statement;

	/**
	 * Constructeur de la classe qui représente la connexion entre notre
	 * application et notre base de donnée
	 * 
	 * @param url
	 *            l'url sur laquelle on souhaite se connecter
	 * @param user
	 *            l'utilisateur avec lequel on veut se connecter
	 * @param pwd
	 *            le mot de passe de l'utilisateur avec lequel on veut se
	 *            connecter
	 */
	public DB(String url, String user, String pwd) {
		this.DB_url = url;
		this.DB_user = user;
		this.DB_password = pwd;
		this.DB_connect = null;
		this.DB_statement = null;
	}

	/**
	 * Méthode permettant de lancer la connexion à la base de données avec les
	 * informations qu'on a fournit au constructeur
	 * 
	 * @return vrai si la connexion a pu etre réalisé, faux sinon
	 */
	public boolean connect() {
		try {
			System.out.println("1");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.DB_connect = DriverManager.getConnection("jdbc:mysql:"
					+ this.DB_url, this.DB_user, this.DB_password);
			System.out.println("2");
			this.DB_statement = this.DB_connect.createStatement();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		} catch (ClassNotFoundException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		} catch (InstantiationException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		} catch (IllegalAccessException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	/**
	 * Méthode permettant de fermer toutes les connexions avec la base de
	 * données
	 */
	public void close() {
		try {
			this.DB_statement.close();
			this.DB_connect.close();
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Méthode permettant de récupérer sous la forme d'un tableau à 2 dimensions
	 * la solution d'une épreuve étant donné par son identifiant
	 * 
	 * @param id
	 *            l'identifiant du niveau que l'on souhaite connaitre la
	 *            solution
	 * @param nb_line
	 *            le nombre de ligne de ce niveau
	 * @param nb_column
	 *            le nombre de colonne de ce niveau
	 * @return un tableau à 2 dimensions de booléens représentant la solution
	 */
	public boolean[][] get_soluce_from_identifiant(String id, int nb_line,
			int nb_column) {

		boolean[][] to_return = new boolean[nb_line][nb_column];

		for (int i = 0; i < nb_line; i++) {
			for (int j = 0; j < nb_column; j++) {
				String sql = "SELECT pushed FROM soluce_level where id=\"" + id
						+ "\" && pos_i = " + i + " && pos_j = " + j;
				ResultSet res = this.exec(sql);
				try {
					if (res.next()) {
						to_return[i][j] = res.getBoolean(1);
					}
				} catch (SQLException e) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE,
							null, e);
				}
			}
		}

		return to_return;
	}

	/**
	 * Méthode permettant de réaliser une requete sql qui va nous retourner un
	 * resultat sous la forme d'un ResultSet
	 * 
	 * @param sql
	 *            la requete sql que l'on souhaite envoyer à la base de données
	 * @return la réponse de la base de données dans une classe dédié à contenir
	 *         ces données
	 */
	public ResultSet exec(String sql) {
		try {
			ResultSet rs = this.DB_statement.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	/**
	 * Méthode permettant d'envoyer à la base de données la demande de changer
	 * la réalisation d'un niveau en réussi
	 * 
	 * @param id
	 *            l'identifiant du niveau qui vient d'être réussi
	 */
	public void update_level_succeed(String id) {
		PreparedStatement prepare;
		try {
			prepare = DB_connect
					.prepareStatement("UPDATE Game_level set succeed = ? "
							+ "WHERE id = \"" + id + "\"");

			// On parametre notre requete preparee
			prepare.setBoolean(1, true);
			// On execute
			prepare.executeUpdate();
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	/**
	 * Méthode permettant de demander l'ajout d'un indicateur de ligne
	 * 
	 * @param id
	 *            l'identifiant du niveau auquel l'indicateur de ligne fait
	 *            référence
	 * @param line
	 *            la ligne à laquelle se trouve l'indicateur
	 * @param indication
	 *            l'indice
	 */
	public void insert_line_indication(String id, int line, int indication) {
		String sql = "INSERT INTO `Indication_line`(`id`, `line`, `indication`) VALUES ("
				+ "\""
				+ id
				+ "\","
				+ "\""
				+ line
				+ "\","
				+ "\""
				+ indication
				+ "\")";

		this.insert_values(sql);
	}

	/**
	 * Méthode permettant de demander d'ajouter une solution à un niveau donné
	 * et à une case donnée. Cette méthode va être appelé pour chaque case d'un
	 * niveau lorsqu'on enregistre sa solution
	 * 
	 * @param id
	 *            l'identifiant du niveau dont c'est la solution
	 * @param i
	 *            l'index I sur l'axe des X
	 * @param j
	 *            l'index J sur l'axe des J
	 * @param pushed
	 *            est ce que la case est appuyé ou non
	 */
	public void insert_soluce(String id, int i, int j, boolean pushed) {
		String sql = "INSERT INTO `soluce_level`(`id`, `pos_i`, `pos_j`, `pushed`) VALUES ("
				+ "\""
				+ id
				+ "\","
				+ "\""
				+ i
				+ "\","
				+ "\""
				+ j
				+ "\","
				+ "\"" + (pushed ? 1 : 0) + "\")";

		this.insert_values(sql);
	}

	/**
	 * Méthode permettant de demander l'ajout d'un indicateur de colonne
	 * 
	 * @param id
	 *            l'identifiant du niveau auquel l'indicateur de ligne fait
	 *            référence
	 * @param column
	 *            la colonne à laquelle se trouve l'indicateur
	 * @param indication
	 *            l'indice
	 */
	public void insert_column_indication(String id, int column, int indication) {
		String sql = "INSERT INTO `Indication_column`(`id`, `column_p`, `indication`) VALUES ("
				+ "\""
				+ id
				+ "\","
				+ "\""
				+ column
				+ "\","
				+ "\""
				+ indication
				+ "\")";

		this.insert_values(sql);
	}

	/**
	 * Méthode permettant de demander l'ajout d'un niveau dans notre base de
	 * données
	 * 
	 * @param id
	 *            l'identifiant du niveau
	 * @param name
	 *            le nom du niveau
	 * @param nb_line
	 *            le nombre de ligne dans le niveau
	 * @param nb_column
	 *            le nombre de colonne dans le niveau
	 * @param succeed
	 *            est ce que le niveau à été réussi
	 */
	public void insert_game_level(String id, String name, int nb_line,
			int nb_column, boolean succeed) {
		String sql = "INSERT INTO `Game_level`(`id`, `name`, `nb_line`, `nb_column`, `succeed`) VALUES ("
				+ "\""
				+ id
				+ "\","
				+ "\""
				+ name
				+ "\","
				+ "\""
				+ nb_line
				+ "\","
				+ "\""
				+ nb_column
				+ "\","
				+ "\""
				+ (succeed ? 1 : 0)
				+ "\")";

		this.insert_values(sql);
	}

	/**
	 * Méthode permettant d'inserer des valeurs dans notre base de données via
	 * une requete SQL.
	 * 
	 * @param sql
	 *            la requete sql d'insertion
	 */
	public void insert_values(String sql) {
		try {
			DB_statement.executeUpdate(sql);
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Méthode permettant de savoir si un nom est valide pour une insertion dans
	 * la base de données. Ainsi la fonction regarde simplement si avec une
	 * requete avec le nom donné en paramètre, on recoit une réponse ou non
	 * 
	 * @param name
	 *            le nom dont l'on veut tester la possibilité
	 * @return vrai si le nom est valide, faux sinon
	 */
	public boolean name_available(String name) {
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
				return false;
			}
		}
		String sql = "SELECT name FROM `Game_level` WHERE UPPER(name) LIKE UPPER('"
				+ name + "')";

		ResultSet res = this.exec(sql);
		try {
			return !res.next();
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	/**
	 * Méthode permettant d'obtenir un identifiant libre de notre base de
	 * données. La fonction lance de manière répété des appels SQL en demander
	 * de récupérer un niveau avec son identifiant, lorsque l'on ne trouve plus
	 * de résultat, c'est que notre identifiant est libre
	 * 
	 * @return le numéro de l'identifiant qui est libre
	 */
	public int get_free_identifier() {
		boolean find = false;
		int id = 1;
		while (!find) {
			try {
				String sql = "select id from Game_level where id=\"level" + id
						+ "\"";
				ResultSet res = this.exec(sql);
				if (!res.next()) {
					return id;
				}
				id++;
			} catch (SQLException e) {
				Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
			}

		}
		return -1;
	}

	/**
	 * Méthode permettant avec un id donné, de supprimer toutes données de la
	 * base de donnée qui sont raccordé à cette identifiant. Cette méthode
	 * s'occupe simplement de supprimer un niveau et toutes les indications et
	 * solutions qui y sont raccordés
	 * 
	 * @param id
	 *            l'identifiant dont l'on donné sont rattaché que l'on veut
	 *            supprimer
	 */
	public void delete_from_id(String id) {
		try {
			DB_statement.executeUpdate("DELETE FROM Game_level WHERE id = \""
					+ id + "\"");
			DB_statement
					.executeUpdate("DELETE FROM Indication_line WHERE id = \""
							+ id + "\"");
			DB_statement
					.executeUpdate("DELETE FROM Indication_column WHERE id = \""
							+ id + "\"");
			DB_statement.executeUpdate("DELETE FROM soluce_level WHERE id = \""
					+ id + "\"");
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}

	}
}
