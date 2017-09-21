package view;

import javax.swing.JFrame;

import model.Board;
import Main.Client;
import controler.Board_controler;

public class Window extends JFrame {

	public Window(int width, int height) {

		this.setTitle("Client de la bataille navale");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.launch_menu(width, height);
	}

	public void launch_menu(int width, int height) {
		Connection_menu connection = new Connection_menu(width, height, this);
		this.setContentPane(connection);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		connection.setFocusable(true);
		connection.requestFocusInWindow();
	}

	public void launch_connexion_bis(Client client) {
		Client_console_view console = new Client_console_view(600, 300);
		client.bind_console(console);
		client.communication_connection();

		Board board = new Board(10, 10, 30);
		Board_controler controler = new Board_controler(board, client);
		Board_view view = new Board_view(controler, 1300, 750, console);

		board.add_observer(view);
		board.notify_creation(board.get_player_grid(), board.get_enemy_grid());

		this.setContentPane(view);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		view.setFocusable(true);
		view.requestFocusInWindow();
	}
}
