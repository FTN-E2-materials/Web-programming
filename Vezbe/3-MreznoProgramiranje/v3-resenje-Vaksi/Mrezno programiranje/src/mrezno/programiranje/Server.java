package mrezno.programiranje;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			int clientCounter = 0;
			ArrayList<String> users = new ArrayList<String>();
			
			// slusaj zahteve na datom portu
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);
			System.out.println("Server running...");
			while (true) {
				Socket sock = ss.accept();
				System.out.println("Client accepted: " + (++clientCounter));
				new UserThread(sock, clientCounter, users);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}