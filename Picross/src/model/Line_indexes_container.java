package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.DB;

public class Line_indexes_container {

	private Vector<Line_indexes> all_line_indexes;

	/**
	 * Constructeur de la classe qui a pour simple vocations de contenir les
	 * différents indicateurs de lignes de la base de données. La classe peut
	 * charger les différents indicateurs de lignes et fournir des sous
	 * ensembles cohérents en fonction d'identifiant
	 */
	public Line_indexes_container() {
		this.all_line_indexes = new Vector<Line_indexes>();
	}

	/**
	 * Méthode permettant de charger tout les indicateurs de lignes qui se
	 * trouvent dans la base de données
	 */
	public void load_all_lines(DB data_base) {
		try {
			ResultSet res = data_base
					.exec("SELECT * FROM Indication_line ORDER BY id,line ASC");
			if (res != null) {
				int current_line = -1;
				Line_indexes idxes = null;
				while (res.next()) {
					String id = res.getString(1);
					int line = res.getInt(2);
					int indication = res.getInt(3);

					if (line != current_line) {
						if (idxes != null) {
							this.add_line_indexes(idxes);
						}
						current_line = line;
						idxes = new Line_indexes(id, line);
						idxes.add_index(indication);
					} else {
						idxes.add_index(indication);
					}
				}
				add_line_indexes(idxes);
			}
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Méthode permettant d'ajouter des indicateurs de lignes dans le tableau
	 * dynamique contenant les différents indicateurs
	 * 
	 * @param li
	 *            l'indicateur de ligne que l'on souhaite ajouter
	 */
	public void add_line_indexes(Line_indexes li) {
		this.all_line_indexes.add(li);
	}

	/**
	 * Méthode permettant de récupérer sous forme de vecteur les différents
	 * indicateurs de lignes qui répondent à un identifiant qui est donné en
	 * paramètre
	 * 
	 * @param id
	 *            l'identifiant avec lequel on souhaite récupérer les différents
	 *            indicateurs
	 * @return un vecteur contenant tous les indicateurs d'un identifiant donné
	 */
	public Vector<Line_indexes> get_lines_indicator_from_id(String id) {
		Vector<Line_indexes> to_return = new Vector<Line_indexes>();
		for (Line_indexes li : all_line_indexes) {
			if (li.get_identifiant().equals(id)) {
				to_return.add(li);
			}
		}
		return to_return;
	}
}
