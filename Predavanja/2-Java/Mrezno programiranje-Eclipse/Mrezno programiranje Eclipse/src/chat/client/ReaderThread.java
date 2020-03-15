package chat.client;

import java.io.BufferedReader;

/** Nit za èitanje poruka sa servera. */
public class ReaderThread extends Thread {

	public ReaderThread(BufferedReader in) {
		this.in = in;
		start();
	}

	public void run() {
		try {
			String msg;
			while (true) {
				msg = in.readLine();
				if (msg != null) {
					if (!msg.trim().equals("")) {
						System.out.println(msg);
						System.out.print(">");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private BufferedReader in;
}
