package chat.server;

import java.util.HashMap;

/** Implementira operacije nad kolekcijom aktivnih klijenata. */
public class ClientUtils {

	public static synchronized ActiveClient addClient(String username,
			String address) {
		ActiveClient test = (ActiveClient) clients.get(username);
		if (test == null) {
			ActiveClient client = new ActiveClient(username, address);
			clients.put(username, client);
			return client;
		} else
			return null;
	}

	public static synchronized boolean removeClient(String username) {
		ActiveClient test = (ActiveClient) clients.get(username);
		if (test == null)
			return false;
		else
			clients.remove(username);
		return true;
	}

	public static void sendMessageToAll(String sender, String message) {
		for (ActiveClient ac : clients.values()) {
			if (!ac.getUsername().equals(sender))
				ac.setMessage(message);
		}
	}

	public static String getClientList() {
		// StringBuffer umesto objekta String klase!
		StringBuilder retVal = new StringBuilder(500);
		retVal.append(tekst);
		for (ActiveClient ac : clients.values()) {
			retVal.append(ac.getUsername());
			retVal.append("\n");
		}
		return retVal.toString();
	}

	private static HashMap<String, ActiveClient> clients = new HashMap<String, ActiveClient>();
	private static String tekst = "Prijavljeni korisnici:\n";
}