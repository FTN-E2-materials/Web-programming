package primer03;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UserServer {

	public static final int TCP_PORT = 80;

	public static void main(String[] args) {
		// lista stringova u kojoj se cuvaju imena korisnika
		// inicijalno je prazna
		ArrayList<String> users = new ArrayList<String>();
		try {
			// slusaj zahteve na datom portu
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);

			System.out.println("Server running...");
			while (true) {
				Socket sock = ss.accept();
				new UserServerThread(sock, users);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
