package primer04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

	public void run() {
		String response = "";
		try {
			// procitaj zahtev
			String request = in.readLine();
			System.out.println(request);

			// formiraj odgovor
			File file = new File(request);
			if (file.exists()) {
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++)
						response += files[i].getName() + "\n";
				} else {
					response = "Error: " + request + " is a file\n";
				}
			} else {
				response = "Error: path does not exist\n";
			}
			response += "END";

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