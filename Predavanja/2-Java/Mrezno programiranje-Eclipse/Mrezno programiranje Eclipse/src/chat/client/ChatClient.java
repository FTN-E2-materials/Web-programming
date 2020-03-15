package chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/** Osnovna klasa klijentske aplikacije. */
public class ChatClient {

	public static final int TCP_PORT = 9000;
	private String address;

	public ChatClient(String[] args) {
		if (args.length == 0)
			address = "localhost";
		else
			address = args[0];

	}

	/** Šalje poruku serveru. */
	public void sendMessage(String s) {
		String message = s.trim();
		cd.setMessage(message);
	}

	/** Vrši prijavljivanje korisnika. */
	@SuppressWarnings("resource")
	public boolean login() {
		try {
			InetAddress addr = InetAddress.getByName(address);
			Socket sock = new Socket(addr, TCP_PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sock.getOutputStream())), true);

			BufferedReader kIn = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Username:");
			String username = kIn.readLine();
			out.println(username);
			String response = in.readLine();
			if (!response.equals("OK"))
				throw new Exception("Invalid user");
			cd = new ChatData();
			new ReaderThread(in);
			new WriterThread(out, cd);
			System.out.println("Chat Client [" + username + "]");
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void mainLoop() {
		BufferedReader kIn = new BufferedReader(
				new InputStreamReader(System.in));

		String command;
		while (true) {
			System.out.print(">");
			try {
				command = kIn.readLine();
			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
			if (!command.equals("quit"))
				sendMessage(command);
			else {
				sendMessage("QUIT!");
				break;
			}
		}
	}

	/**
	 * Prikazuje login dijalog i, ako je prijavljivanje uspešno, otvara osnovni
	 * prozor aplikacije.
	 */
	public static void main(String[] args) {
		ChatClient cc = new ChatClient(args);
		if (!cc.login())
			System.exit(0);
		else
			cc.mainLoop();
	}

	ChatData cd;
}