package chat.client;

/** Predstavlja bafer za poruke koje se šalju serveru. */
public class ChatData {

	public synchronized void setMessage(String message) {
		this.message = message;
		notify();
	}

	public synchronized String getMessage() {
		try {
			wait();
		} catch (Exception ex) {
		}
		return message;
	}

	private String message;
}