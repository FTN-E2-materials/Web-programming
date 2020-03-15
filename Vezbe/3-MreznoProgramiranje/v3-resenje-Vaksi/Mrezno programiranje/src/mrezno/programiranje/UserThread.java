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
			// System.out.println(request);

			// razbiti prijemljenu poruku na koristan podatak
			String[] niz = request.split(" ");

			// primljenu poruku dodati u listu ako je u pitanju bila komanda dodavanja
			if (niz[0].toLowerCase().equals("add")) {
				korisnici.add(niz[1]);
				out.println("Success");// odgovori na zahtev
			}

			// ako je primljena poruka LIST komanda, vratiti sve korisnike
			if (niz[0].toLowerCase().equals("list")) {
				String returnString = "";
				for (String ime : korisnici) {
					returnString += ime;
					returnString += ", ";
				}
				returnString = returnString.substring(0, returnString.length() - 2);
				out.println(returnString); // odgovori na zahtev
			}

			// ako imamo primljenu komandu REMOVE, uklanjamo korisnika ako on postoji
			if (niz[0].toLowerCase().equals("remove")) {
				if (korisnici.contains(niz[1])) {
					String imeKorisnika = niz[1];
					if (korisnici.remove(niz[1])) {
						out.println(imeKorisnika);
					}
				} else {
					out.println("User not found");
				}
			}

			// ako imamo komandu FIND<usnm>, vracamo prvog nadjenog korisnika sa tim <usnm>
			// ( ako postoji )
			if (niz[0].toLowerCase().equals("find")) {
				if (korisnici.contains(niz[1])) {
					int indexKorisnika = korisnici.indexOf(niz[1]);
					String imeKorisnika = korisnici.get(indexKorisnika);
					out.println(imeKorisnika);
				} else {
					out.println("User not found");
				}
			}

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
