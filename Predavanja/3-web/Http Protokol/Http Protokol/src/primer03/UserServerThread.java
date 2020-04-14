package primer03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;

public class UserServerThread extends Thread {

	public UserServerThread(Socket sock, ArrayList<String> users) {
		this.sock = sock;
		this.users = users;
		try {
			// inicijalizuj ulazni stream
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream(), "UTF-8")), true);
			// pokreni thread
			start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void run() {
		String response = "";
		try {
			// procitaj zahtev
			String request = in.readLine();
			System.out.println("Request: " + request);

			// analiziraj zahtev: ako počinje sa GET, to je zahtev koga je
			// poslao web čitač. ako počinje nečim drugim, to je zahtev koga
			// je poslao konzolni klijent
			if (request.startsWith("GET /")) {
				response = browserResponse();
			} else {
				// dodaj u listu
				response = clientResponse(request);
			}

			// posalji odgovor
			out.println(response);

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Generisanje odgovora za web browser
	 */
	private String browserResponse() {
		String retVal = "HTTP/1.1 200 OK Ja sam svetski mega car\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
		retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		for (int i = 0; i < users.size(); i++) {
			String user = users.get(i);
			retVal += "<li>" + user + "</li>\n";
		}
		retVal += "</ol></body></html>\n";
		return retVal;
	}

	/**
	 * Generisanje odgovora za konzolnog klijenta
	 */
	private String clientResponse(String request) {
		String newUser = request;
		users.add(newUser);
		String retVal = "";
		for (int i = 0; i < users.size(); i++) {
			String user = users.get(i);
			retVal += user + "\n";
		}
		retVal += "END";
		return retVal;
	}

	private Socket sock;
	private ArrayList<String> users;
	private BufferedReader in;
	private PrintWriter out;
}
