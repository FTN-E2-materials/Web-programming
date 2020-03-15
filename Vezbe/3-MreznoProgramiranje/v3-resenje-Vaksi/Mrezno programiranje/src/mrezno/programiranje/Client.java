package mrezno.programiranje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static final int TCP_PORT = 9000;

	@SuppressWarnings("resource")
	public static void main(String[] args){
		try {
			// odredi adresu racunara sa kojim se povezujemo
			InetAddress addr = InetAddress.getByName("127.0.0.1");

			// otvori socket prema drugom racunaru
			Socket sock = new Socket(addr, TCP_PORT);

			// inicijalizuj ulazni stream
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			// inicijalizuj izlazni stream
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

			// posalji zahtev
			BufferedReader kIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("----------------- Meni -----------------");
			System.out.println(" Dodavanje korisnika [ ADD <username> ]");
			System.out.println(" Prikaz korisnika [ LIST ]");
			System.out.println(" Ukloni korisnika [ REMOVE <username> ]");
			System.out.println(" Nadji korisnika  [ FIND <username ]");
			System.out.println(" Visestruko dodavanje [ ADD <us1>,<us2>]");
			System.out.println("----------------------------------------");
			System.out.print("Unesite zeljenu komandu: ");
			String komanda = kIn.readLine();
			out.println(komanda);
			String[] niz = komanda.split(" ");
			
			// procitaj odgovor
			String response = in.readLine();
			if (niz[0].toLowerCase().equals("add") && response.equals("Success")) {
				System.out.println("Uspesno dodat korisnik");
			}
			if(komanda.toLowerCase().equals("list")) {
				System.out.println("----------------- Korisnici -----------------");
				System.out.println(response);
			}
			if(niz[0].toLowerCase().equals("remove")) {
				System.out.println(response);
			}
			
			
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