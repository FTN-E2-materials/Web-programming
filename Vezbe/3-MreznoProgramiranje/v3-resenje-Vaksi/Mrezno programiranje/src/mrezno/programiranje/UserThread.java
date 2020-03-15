package mrezno.programiranje;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread extends Thread {

	private Socket sock; // objekat soket za razmenu podataka
	private BufferedReader in;
	private PrintWriter out;
	private int value;
	ArrayList<String> korisnici = new ArrayList<String>();

	/**
	 * Konstruktor klase UserThread.
	 */
	public UserThread(Socket sock, int value, ArrayList<String> users) {
		this.sock = sock;
		this.value = value;
		this.korisnici = users;
		try {
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
			//System.out.println(request);
			
			// razbiti prijemljenu poruku na koristan podatak
			String[] niz = request.split(" ");
			
			// primljenu poruku dodati u listu ako je u pitanju bila komanda dodavanja
			if(niz[0].toLowerCase().equals("add")) {
				korisnici.add(niz[1]);
				out.println("Success");
			}
			
//			System.out.println("----------------- Korisnici -----------------");
//			for (String e : korisnici) {
//				System.out.println(e);
//			}
			
			
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
