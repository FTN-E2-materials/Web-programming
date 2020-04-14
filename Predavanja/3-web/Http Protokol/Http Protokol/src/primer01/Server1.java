package primer01;

import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {

	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			int clientCounter = 0;
			// slusaj zahteve na datom portu
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);
			System.out.println("Server running...");
			while (true) {
				Socket sock = ss.accept();
				System.out.println("Client accepted: " + (++clientCounter));
				new ServerThread(sock, clientCounter);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}