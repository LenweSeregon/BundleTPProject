package utils;

import model.Column_indexes_container;
import model.Game_level_container;
import model.Line_indexes_container;

public class Datas_container {

	private Game_level_container levels_container;
	private Line_indexes_container lines_container;
	private Column_indexes_container columns_container;

	private DB ref_DB;

	/**
	 * Constructeur de la classe qui a pour but de détenir tous les données du
	 * jeu qui se trouvent dans la base de données (IE : les niveaux et les
	 * différents indicateurs de lignes et colonnes. Cette classe fournit juste
	 * une petite interface pour charger toutes les données et récupérer les
	 * wrapper de données récrée pour l'application
	 */
	public Datas_container(DB db) {
		this.levels_container = new Game_level_container();
		this.lines_container = new Line_indexes_container();
		this.columns_container = new Column_indexes_container();

		this.ref_DB = db;
	}

	/**
	 * Méthode permettant de demandant aux différents wrapper de données de
	 * charger leurs données respective
	 */
	public void load_datas() {
		this.levels_container = new Game_level_container();
		this.lines_container = new Line_indexes_container();
		this.columns_container = new Column_indexes_container();

		levels_container.load_all_levels(ref_DB);
		lines_container.load_all_lines(ref_DB);
		columns_container.load_all_columns(ref_DB);
	}

	/**
	 * Méthode permettant de récupérer le conteneur qui détient les différents
	 * niveaux de la base de données
	 * 
	 * @return les différents niveaux de la base de données dans une classe qui
	 *         les contient
	 */
	public Game_level_container get_levels_container() {
		return levels_container;
	}

	/**
	 * Méthode permettant de récupérer le conteneur qui détient les différents
	 * indicateurs de lignes de la base de données
	 * 
	 * @return les différents indicateurs de lignes de la base de données dans
	 *         une classe qui les contient
	 */
	public Line_indexes_container get_line_indexes_container() {
		return lines_container;
	}

	/**
	 * Méthode permettant de récupérer le conteneur qui détient les différents
	 * indicateurs de colonnes de la base de données
	 * 
	 * @return les différents indicateurs de colonnes de la base de données dans
	 *         une classe qui les contient
	 */
	public Column_indexes_container get_column_indexes_container() {
		return columns_container;
	}
}
