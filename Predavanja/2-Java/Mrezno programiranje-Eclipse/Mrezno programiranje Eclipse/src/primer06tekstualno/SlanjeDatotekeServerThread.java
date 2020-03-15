package primer06tekstualno;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SlanjeDatotekeServerThread extends Thread {
	
	Socket soc;
	BufferedReader in;
	PrintWriter out;

	public SlanjeDatotekeServerThread(Socket s) {
		this.soc = s;
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			PrintWriter fout = new PrintWriter(new OutputStreamWriter(new FileOutputStream("primljeno.txt")));
			String s;
			boolean imaJos = true;
			System.out.println("Cekam datoteku");
			while(true) {
				String s2 = in.readLine();
				imaJos = Boolean.parseBoolean(s2);
				System.out.println(imaJos);
				if (!imaJos)
					break;
				s = in.readLine();
				System.out.println("Primio: " + s);
				fout.println(s);
			} 
			fout.close();
			out.println("Primio datoteku.");
			System.out.println("Završio prijem, šaljem potvrdu klijentu.");
			out.flush(); // ako ne ukljuèimo auto flush kod PrintWriter-a, moramo ovako
			
			in.close();
			out.close();
			soc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
