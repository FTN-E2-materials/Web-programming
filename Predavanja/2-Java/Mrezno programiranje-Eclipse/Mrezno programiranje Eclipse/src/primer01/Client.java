package primer01;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			// odredi adresu racunara sa kojim se povezujemo
			InetAddress addr = InetAddress.getByName("127.0.0.1");

			// otvori socket prema drugom racunaru
			Socket sock = new Socket(addr, TCP_PORT);

			// inicijalizuj ulazni stream
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					sock.getInputStream()));

			// inicijalizuj izlazni stream
			DataOutputStream out = new DataOutputStream(
					new BufferedOutputStream(sock.getOutputStream()));

			// posalji zahtev
			System.out.println("[Client sent]: prvi_broj (3.14)");
			out.writeDouble(3.14);
			System.out.println("[Client sent]: drugi_broj (2.78)");
			out.writeDouble(2.78);

			// isprazni bafer
			out.flush();

			// procitaj odgovor
			double rez = in.readDouble();
			System.out.println("[Client received]: " + rez);

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

}