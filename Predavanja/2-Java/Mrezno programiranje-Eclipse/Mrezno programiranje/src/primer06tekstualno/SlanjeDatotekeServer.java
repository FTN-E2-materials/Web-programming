package primer06tekstualno;

import java.net.ServerSocket;
import java.net.Socket;

public class SlanjeDatotekeServer {

	public SlanjeDatotekeServer() {
		try {
			ServerSocket ss = new ServerSocket(9000);
			System.out.println("Èekam klijente...");
			while (true) {
				Socket s = ss.accept();
				System.out.println("Spojio se klijent sa: " + s.getInetAddress());
				SlanjeDatotekeServerThread t = new SlanjeDatotekeServerThread(s);
				t.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SlanjeDatotekeServer();
	}
}
