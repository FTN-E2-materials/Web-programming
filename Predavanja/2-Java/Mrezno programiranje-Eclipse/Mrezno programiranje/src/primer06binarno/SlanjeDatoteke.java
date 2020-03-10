package primer06binarno;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class SlanjeDatoteke {

	public static void main(String[] args) {
		try {
			Socket soc = new Socket("localhost", 9000);
			DataInputStream in = new DataInputStream(soc.getInputStream());
			DataOutputStream out = new DataOutputStream(soc.getOutputStream());
			
			File f = new File("datoteka.txt");
			FileInputStream fin = new FileInputStream(f);
			
			// šaljem velièinu datoteke
			out.writeLong(f.length());
			
			byte[] buff = new byte[1024];
			int procitao;
			while ((procitao = fin.read(buff)) != -1) {
				out.write(buff, 0, procitao);
				System.out.println("Poslao " + procitao + " bajtova.");
			}
			out.flush(); // tek ovim se bajtovi šalju 
			
			fin.close();

			System.out.println("Stiglo sa servera: " + in.readBoolean());
			in.close();
			out.close();
			soc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
