package primer06tekstualno;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SlanjeDatoteke {

	public static void main(String[] args) {
		try {
			Socket soc = new Socket("localhost", 9000);
			BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()));
			BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream("datoteka.txt")));

			String s;

			while ((s = fin.readLine()) != null) {
				out.println(true); // ima još da se prima
				out.println(s);
				System.out.println("Poslao: " + s);
			}
			out.println(false);
			out.println();
			out.flush(); // ako ne ukljuèimo auto flush kod PrintWriter-a, moramo ovako
			
			fin.close();

			System.out.println("Stiglo sa servera: " + in.readLine());
			in.close();
			out.close();
			soc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
