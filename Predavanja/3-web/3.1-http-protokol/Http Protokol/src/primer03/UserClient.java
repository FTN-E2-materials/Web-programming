package primer03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClient {

	public static final int TCP_PORT = 80;

	public static void main(String[] args) {
		// direktorijum i adresa servera se zadaju iz komandne linije;
		// ako nisu definisani, ispisi poruku i prekini sa radom
//		if (args.length == 0) {
//			System.out.println("User Client v1.0");
//			System.out.println("Usage: UserClient <username> [<hostname>]");
//			System.out.println("Parameters:");
//			System.out.println("  <username> Username to use for login");
//			System.out
//					.println("  <hostname> Server name; default is localhost\n\n");
//			System.exit(0);
//		}
		String username = "mika"; //args[0];
		String hostname = (args.length > 1) ? args[1] : "localhost";
		try {
			// odredi adresu racunara sa kojim se povezujemo
			InetAddress addr = InetAddress.getByName(hostname);

			// otvori socket prema drugom racunaru
			Socket sock = new Socket(addr, TCP_PORT);

			// inicijalizuj ulazni stream
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));

			// inicijalizuj izlazni stream
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sock.getOutputStream())), true);

			// posalji zahtev
			System.out.println("Logging in...");
			out.println(username);

			// procitaj odgovor
			String response;
			String list = "";
			while (!(response = in.readLine()).equals("END")) {
				list += response + "\n";
			}
			System.out.println("\nCurrent users:\n" + list);

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
