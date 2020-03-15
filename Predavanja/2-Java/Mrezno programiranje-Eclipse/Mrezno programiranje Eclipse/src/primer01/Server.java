package primer01;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			// slusaj zahteve na datom portu
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);
			System.out.println("Server running...");
			while (true) {
				Socket sock = ss.accept();
				System.out.println("Client accepted");
				new ServerThread(sock);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}