package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import model.Action_possible;
import model.Player_position;
import controler.Board_controler;

public class KeyboardManager implements KeyListener {

	private Board_controler controler;
	private Player_position attached_player;
	private Vector<Integer> events_available;
	private Vector<Action_possible> action_required;

	/**
	 * Constructeur de la carte de gestion du clavier
	 * 
	 * @param controler
	 *            une référence sur le controler
	 * @param attached_player
	 *            le joueur attaché à l'écouteur
	 */
	public KeyboardManager(Board_controler controler,
			Player_position attached_player) {
		this.controler = controler;
		this.attached_player = attached_player;
		this.events_available = new Vector<Integer>();
		this.action_required = new Vector<Action_possible>();
	}

	/**
	 * Méthode permettant d'ajouter un evenement a notre ecouteur
	 * 
	 * @param event
	 *            l'evemenet que l'on veut attacher à notre écouteur
	 * @param action
	 *            l'action que l'on veut réaliser sur event que l'on a trouvé
	 */
	public void add_event(Integer event, Action_possible action) {
		this.events_available.add(event);
		this.action_required.add(action);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing to do
	}

	@Override
	public void keyPressed(KeyEvent e) {

		for (int i = 0; i < events_available.size(); i++) {
			if (events_available.elementAt(i) == e.getKeyCode()) {
				controler.ask_action_go(action_required.elementAt(i),
						attached_player);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < events_available.size(); i++) {
			if (events_available.elementAt(i) == e.getKeyCode()) {
				controler.ask_action_stop(action_required.elementAt(i),
						attached_player);
			}
		}
	}

}
