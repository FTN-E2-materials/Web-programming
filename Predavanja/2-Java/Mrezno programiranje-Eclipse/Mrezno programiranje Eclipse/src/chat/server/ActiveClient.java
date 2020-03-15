package chat.server;

/** Predstavlja jednog aktivnog klijenta. */
public class ActiveClient {

	public ActiveClient(String username, String address) {
		this.username = username;
		this.address = address;
		this.message = "";
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

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

	private String username;
	private String address;
	private String message;
}