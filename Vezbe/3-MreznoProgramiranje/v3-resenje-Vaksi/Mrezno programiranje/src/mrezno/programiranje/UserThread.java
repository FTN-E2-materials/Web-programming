package mrezno.programiranje;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {

	private Socket sock; // objekat soket za razmenu podataka
	private BufferedReader in;
	private PrintWriter out;
	private int value;

	/**
	 * Konstruktor klase UserThread.
	 */
	public UserThread(Socket sock, int value) {
		this.sock = sock;
		this.value = value;
		try {
//			// inicijalizuj ulazni stream
//			in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
//			// inicijalizuj izlazni stream
//			out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
//		
			// inicijalizuj ulazni stream
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		start(); // pokrecem tu nit

	}

	@Override
	public void run() {
		try {

			// procitaj zahtev
			String request = in.readLine();
			System.out.println(request);

			// odgovori na zahtev
			out.println("(" + value + ")");

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
