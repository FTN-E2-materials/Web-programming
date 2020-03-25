package primer02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	public ServerThread(Socket sock) {
		this.sock = sock;
		try {
			// inicijalizuj ulazni stream
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream())), true);
			// pokreni thread
			start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Generisanje odgovora za web browser
	 * @throws IOException 
	 */
	private String browserResponse(String req) throws IOException {
		String retVal = "HTTP/1.0 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
		retVal += "<html><head><title>Spisak datoteka</title></head>\n";
		retVal += "<body><h1>Spisak datoteka:</h1>\n";
		String path = ".";
		// formiraj odgovor
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++)
					if (files[i].isDirectory())
						retVal += "<font color=\"red\"><b>&lt;"
								+ files[i].getName() + "&gt;"
								+ "</b></font><br />\n";
					else
						retVal += files[i].getName() + "<br />\n";
			}
		}

		retVal += "</body></html>\n";
		return retVal;
	}

	public void run() {
		String response = "";
		try {
			// procitaj zahtev
			String request = in.readLine();
			System.out.println("Request: " + request);

			response = browserResponse(request);

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

	private Socket sock;
	private BufferedReader in;
	private PrintWriter out;
}