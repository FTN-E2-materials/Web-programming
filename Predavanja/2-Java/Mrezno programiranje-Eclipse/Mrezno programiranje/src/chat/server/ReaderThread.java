package chat.server;

import java.io.BufferedReader;
import java.net.Socket;

/** Nit za slanje poruka klijentu. */
public class ReaderThread extends Thread {

	public ReaderThread(Socket sock, BufferedReader in, ActiveClient client) {
		this.sock = sock;
		this.in = in;
		this.client = client;
		start();
	}

	public void run() {
		try {
			String msg;
			while (!(msg = in.readLine()).equals("QUIT!"))
				ClientUtils.sendMessageToAll(client.getUsername(),
						"[" + client.getUsername() + "/" + client.getAddress()
								+ "] " + msg);
			in.close();
			sock.close();
			ClientUtils.removeClient(client.getUsername());
			System.out.println(ClientUtils.getClientList());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Socket sock;
	private BufferedReader in;
	private ActiveClient client;
}
