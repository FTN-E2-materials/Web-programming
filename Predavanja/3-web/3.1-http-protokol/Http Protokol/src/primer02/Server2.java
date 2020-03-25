package primer02;

import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	public static final int TCP_PORT = 80;

	public static void main(String[] args) {
		try {
			// slusaj zahteve na datom portu
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);
			System.out.println("Server running...");
			while (true) {
				Socket sock = ss.accept();
				new ServerThread(sock);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}