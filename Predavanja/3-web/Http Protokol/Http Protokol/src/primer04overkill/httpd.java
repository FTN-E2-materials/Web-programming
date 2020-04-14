package primer04overkill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Jednostavan web server
 */
public class httpd {
	public static void main(String args[]) throws IOException {
		int port = 80;

		@SuppressWarnings("resource")
		ServerSocket srvr = new ServerSocket(port);

		System.out.println("httpd running on port: " + port);
		System.out.println("document root is: " + new File(".").getAbsolutePath() + "\n");

		Socket skt = null;
		
		List<String> users = new ArrayList<String>();

		while (true) {
			try {
				skt = srvr.accept();
				InetAddress addr = skt.getInetAddress();

				String resource = getResource(skt.getInputStream());
				if (resource == null)
					continue;
				if (resource.equals(""))
					resource = "index.html";

				if (resource.startsWith("dodaj?ime=")) {
					String ime = resource.substring(10);
					ime = URLDecoder.decode(ime, "utf-8");
					System.out.println(ime);
					users.add(ime);
					PrintWriter out = new PrintWriter(new OutputStreamWriter(skt.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					out.println("<html><head><meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body><h1>Korisnici:</h1><ol>");
					for (String s : users) {
						out.println("<li>" + s + "</li>");
					}
					out.println("</ol></body></html>");
					out.close();
				} else if (resource.startsWith("trazi?ime=")) {
					String ime = resource.substring(10);
					ime = URLDecoder.decode(ime, "utf-8");
					System.out.println(ime);
					PrintWriter out = new PrintWriter(new OutputStreamWriter(skt.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					out.println("<html><head><meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body><h1>Korisnici:</h1><ol>");
					for (String s : users) {
						if (s.contains(ime))
							out.println("<li>" + s + "</li>");
					}
					out.println("</ol></body></html>");
					out.close();
				} else {

					System.out.println("Request from " + addr.getHostName() + ": " + resource);

					sendResponse(resource, skt.getOutputStream());
				}
				skt.close();
				skt = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	static String getResource(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String s = in.readLine();
		if (s == null) {
			return null;
		}
		System.out.println(">>" + s);

		String[] tokens = s.split(" ");

		// prva linija HTTP zahteva: METOD /resurs HTTP/verzija
		// obradjujemo samo GET metodu
		String method = tokens[0];
		if (!method.equals("GET")) {
			return null;
		}

		String rsrc = tokens[1];

		// izbacimo znak '/' sa pocetka
		rsrc = rsrc.substring(1);

		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = in.readLine()).equals(""))
			System.out.println(s1);

		return rsrc;
	}

	static void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os);
		// zamenimo web separator sistemskim separatorom
		resource = resource.replace('/', File.separatorChar);
		File file = new File(resource);

		if (!file.exists()) {
			// ako datoteka ne postoji, vratimo kod za gresku
			ps.print("HTTP/1.0 404 File not found\r\n"
					+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Нисам нашао:" + file.getName() + "</b>");
			// ps.flush();
			System.out.println("Could not find resource: " + file);
			return;
		}

		// ispisemo zaglavlje HTTP odgovora
		ps.print("HTTP/1.0 200 OK\r\n\r\n");

		// a, zatim datoteku
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[8192];
		int len;

		while ((len = fis.read(data)) != -1) {
			ps.write(data, 0, len);
		}

		ps.flush();
		fis.close();
	}
}
