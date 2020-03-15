package chat.client;

import java.io.PrintWriter;

/** Nit za slanje poruka serveru. */
public class WriterThread extends Thread {

	public WriterThread(PrintWriter out, ChatData chatData) {
		this.out = out;
		this.chatData = chatData;
		start();
	}

	public void run() {
		try {
			String msg;
			while (true) {
				msg = chatData.getMessage();
				out.println(msg);
				if (msg.equals("QUIT!"))
					System.exit(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private PrintWriter out;
	private ChatData chatData;
}