package chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/** Osnovna klasa servera: pokreæe listener. */
public class ChatServer {

	public static final int TCP_PORT = 9000;

	public ChatServer() {
		System.out.println("ChatServer startovan.");
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);
			while (true) {
				Socket sock = ss.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						sock.getInputStream()));
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(sock.getOutputStream())), true);

				String username = in.readLine();
				String address = sock.getInetAddress().getHostAddress();
				ActiveClient client = ClientUtils.addClient(username, address);
				if (client == null) {
					out.println("Bad user");
					in.close();
					out.close();
					sock.close();
					continue;
				}
				out.println("OK");
				new ReaderThread(sock, in, client);
				new WriterThread(out, client);
				System.out.println(ClientUtils.getClientList());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new ChatServer();
	}
}
