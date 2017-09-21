package view;

import javax.swing.JFrame;

import Network.Communication_controler;
import Network.Connection_accepter;
import Network.Server;

public class Window extends JFrame {

	private Communication_view comm;
	private Configuration_menu config;

	private int width;
	private int height;

	Server server;

	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		config = new Configuration_menu(width, height, this);

		this.setTitle("Serveur de la bataille navale");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		launch_configuration_menu();
	}

	public void launch_configuration_menu() {

		this.setContentPane(config);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void launch_communication_menu(String nom, int port) {
		server = new Server(port, 4);

		Communication_controler ctl = new Communication_controler(server);
		Connection_accepter accepter = new Connection_accepter(ctl);
		ctl.bind_connection_accepter(accepter);

		comm = new Communication_view(width, height, this, ctl);
		comm.set_server_name(nom);
		comm.set_server_port(port);

		server.add_observer(comm);
		ctl.ask_to_open_server();

		this.setContentPane(comm);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void exit() {
		comm.get_console_view().add_message(
				"Le serveur va être coupé dans 5 secondes . . .");
		Thread last = null;
		for (int i = 5; i >= 0; i--) {

			if (i == 5) {
				last = new Thread(new Exit_printer(i, comm.get_console_view()));
				last.start();
			} else {
				new Thread(new Exit_printer(i, comm.get_console_view()))
						.start();
			}
		}

		new Thread(new Exit_window(last)).start();
	}

	public Server_console_view get_view() {
		return comm.get_console_view();
	}

	// Printer exit for exit loop
	public class Exit_printer implements Runnable {

		private int i;
		private Server_console_view v;

		public Exit_printer(int i, Server_console_view v) {
			this.i = i;
			this.v = v;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(i * 1000);
				v.add_message(5 - i + " secondes avant fermeture . . .");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public class Exit_window implements Runnable {
		private Thread wait_for;

		public Exit_window(Thread wait_for) {
			this.wait_for = wait_for;
		}

		@Override
		public void run() {
			try {
				wait_for.join();
				dispose();
				System.exit(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
