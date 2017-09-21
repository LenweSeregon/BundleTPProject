package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client_entity {
	private String identifier;
	private boolean waiting_to_player;
	private boolean must_broadcast;

	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	public Client_entity(String identifier) {
		this.identifier = identifier;
		this.waiting_to_player = true;
		this.must_broadcast = false;

		this.socket = null;
		this.in = null;
		this.out = null;
	}

	public void set_must_broadcast(boolean must) {
		if (must) {
			this.set_waiting_to_player(false);
		}
		this.must_broadcast = must;
	}

	public boolean has_to_broadcast() {
		return this.must_broadcast;
	}

	public void send_identifier_to_client() {
		send_message(this.identifier);
	}

	public void bind_socket(Socket socket) {
		this.socket = socket;
	}

	public void send_message(String message) {
		if (socket != null) {
			try {
				out = new PrintWriter(socket.getOutputStream());
				out.println(message);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String receive_message() {
		if (socket != null) {
			try {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String s = in.readLine();
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "ERROR";
		} else {
			return "ERROR";
		}
	}

	public void set_waiting_to_player(boolean waiting) {
		if (waiting) {
			this.set_must_broadcast(false);
		}
		this.waiting_to_player = waiting;
	}

	public boolean get_is_waiting() {
		return this.waiting_to_player;
	}

	public String get_identifier() {
		return this.identifier;
	}
}
