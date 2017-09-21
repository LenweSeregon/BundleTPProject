package Network;

public enum Server_state {
	RUNNING("En marche"), PAUSED("En pause"), STOPPED("Arrêté");

	private String text;

	Server_state(String text) {
		this.text = text;
	}

	public String get_text() {
		return this.text;
	}
}
