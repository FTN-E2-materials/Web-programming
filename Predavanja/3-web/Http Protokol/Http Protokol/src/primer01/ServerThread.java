package primer01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	public ServerThread(Socket sock, int value) {
		this.sock = sock;
		this.value = value;
		try {
			// inicijalizuj ulazni stream
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream())), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		start();
	}

	/**
	 * Generisanje odgovora za web browser
	 */
	private String browserResponse() {
		String retVal = "HTTP/1.0 200 OK\r\nContent-Type: text/html\r\n\r\n";
		retVal += "<html><head><title>Broj odradjenih konekcija</title></head>\n";
		retVal += "<body><h1>Broj odradjenih konekcija:</h1>\n";
		// odgovori na zahtev
		retVal += ("<b>" + value + "</b>");
		retVal += "</body></html>\n";
		return retVal;
	}

	public void run() {
		try {
			// procitaj zahtev
			String request = in.readLine();
			System.out.println("Request: " + request);

			out.println(browserResponse());

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Socket sock;
	private int value;
	private BufferedReader in;
	private PrintWriter out;
}