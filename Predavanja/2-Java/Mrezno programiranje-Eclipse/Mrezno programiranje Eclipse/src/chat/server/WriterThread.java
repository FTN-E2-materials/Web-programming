package chat.server;

import java.io.PrintWriter;

/** Nit za slanje poruka klijentu. */
public class WriterThread extends Thread {

	public WriterThread(PrintWriter out, ActiveClient client) {
		this.out = out;
		this.client = client;
		start();
	}

	public void run() {
		try {
			String msg;
			while (!(msg = client.getMessage()).equals("QUIT!"))
				out.println(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private PrintWriter out;
	private ActiveClient client;
}
