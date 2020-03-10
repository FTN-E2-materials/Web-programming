package primer01;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	public ServerThread(Socket sock) {
		this.sock = sock;
		try {
			// inicijalizuj ulazni stream
			in = new DataInputStream(new BufferedInputStream(
					sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new DataOutputStream(new BufferedOutputStream(
					sock.getOutputStream()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		start();
	}

	public void run() {
		try {
			// procitaj prvi broj
			double n1 = in.readDouble();
			// procitaj drugi broj
			double n2 = in.readDouble();
			System.out.println("[Server received]: " + n1);
			System.out.println("[Server received]: " + n2);

			// odgovori na zahtev
			out.writeDouble(n1 + n2);
			System.out.println("[Server sent]: " + (n1 + n2));

			// isprazni bafer
			out.flush();

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
}