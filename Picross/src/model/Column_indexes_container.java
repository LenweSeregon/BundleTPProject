package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.DB;

public class Column_indexes_container {

	private Vector<Column_indexes> all_column_indexes;

	/**
	 * Constructeur de la classe qui a pour simple vocations de contenir les
	 * différents indicateurs de colonnes de la base de données. La classe peut
	 * charger les différents indicateurs de colonnes et fournir des sous
	 * ensembles cohérents en fonction d'identifiant
	 */
	public Column_indexes_container() {
		this.all_column_indexes = new Vector<Column_indexes>();
	}

	/**
	 * Méthode permettant de charger tout les indicateurs de colonnes qui se
	 * trouvent dans la base de données
	 */
	public void load_all_columns(DB data_base) {
		try {
			ResultSet res = data_base
					.exec("SELECT * FROM Indication_column ORDER BY id, column_p ASC");
			if (res != null) {
				int current_column = -1;
				Column_indexes idxes = null;
				while (res.next()) {
					String id = res.getString(1);
					int column = res.getInt(2);
					int indication = res.getInt(3);

					if (column != current_column) {
						if (idxes != null) {
							this.add_column_indexes(idxes);
						}
						current_column = column;
						idxes = new Column_indexes(id, column);
						idxes.add_index(indication);
					} else {
						idxes.add_index(indication);
					}
				}
				add_column_indexes(idxes);
			}
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Méthode permettant d'ajouter des indicateurs de colonnes dans le tableau
	 * dynamique contenant les différents indicateurs
	 * 
	 * @param li
	 *            l'indicateur de colonne que l'on souhaite ajouter
	 */
	public void add_column_indexes(Column_indexes ci) {
		this.all_column_indexes.add(ci);
	}

	/**
	 * Méthode permettant de récupérer sous forme de vecteur les différents
	 * indicateurs de colonnes qui répondent à un identifiant qui est donné en
	 * paramètre
	 * 
	 * @param id
	 *            l'identifiant avec lequel on souhaite récupérer les différents
	 *            indicateurs
	 * @return un vecteur contenant tous les indicateurs d'un identifiant donné
	 */
	public Vector<Column_indexes> get_columns_indicator_from_id(String id) {
		Vector<Column_indexes> to_return = new Vector<Column_indexes>();
		for (Column_indexes ci : all_column_indexes) {
			if (ci.get_identifiant().equals(id)) {
				to_return.add(ci);
			}
		}
		return to_return;
	}

}
