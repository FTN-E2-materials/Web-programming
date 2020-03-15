package primer02;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	public ServerThread(Socket sock) {
		this.sock = sock;
		try {
			// inicijalizuj ulazni stream
			in = new ObjectInputStream(sock.getInputStream());

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
			// ucitaj Auto preko mreze
			Object obj = in.readObject();
			Auto auto = (Auto) obj;
			System.out
					.println("[Server received] Auto, radi(): " + auto.radi());

			// odgovori na zahtev
			out.writeBoolean(auto.radi());
			System.out.println("[Server sent]: " + auto.radi());

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
	private ObjectInputStream in;
	private DataOutputStream out;
}