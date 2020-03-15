package primer06binarno;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SlanjeDatotekeServerThread extends Thread {
	
	Socket soc;
	DataInputStream in;
	DataOutputStream out;

	public SlanjeDatotekeServerThread(Socket s) {
		this.soc = s;
		try {
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			FileOutputStream fout = new FileOutputStream("primljeno.txt");
			System.out.println("Cekam datoteku");
			
			// primim velièinu datoteke
			long len = in.readLong();
			long total = 0;
			
			int procitao;
			byte[] buff = new byte[1024];
			while(total < len) {
				procitao = in.read(buff);
				total += procitao;
				System.out.println("Primio: " + procitao + " bajtova.");
				fout.write(buff, 0, procitao);
			} 
			fout.close();
			System.out.println("Završio prijem, šaljem potvrdu klijentu.");
			out.writeBoolean(true); // šaljem true da sam primio datoteku
			out.flush(); // tek sada se šalju podaci na klijenta
			
			in.close();
			out.close();
			soc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
