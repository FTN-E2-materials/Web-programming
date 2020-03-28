import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private List<String> users;

	public Server(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.users = new ArrayList<String>();
	}

	public void run() {
		System.out.println("Web server running on port: " + port);
		System.out.println("Document root is: " + new File("/static").getAbsolutePath() + "\n");

		Socket socket;

		while (true) {
			try {
				// prihvataj zahteve
				socket = serverSocket.accept();
				InetAddress addr = socket.getInetAddress();

				// dobavi resurs zahteva
				String resource = this.getResource(socket.getInputStream());

				// fail-safe
				if (resource == null)
					continue;

				if (resource.equals(""))
					resource = "static/index.html";

				if (resource.startsWith("registruj?ime=")) {
					String ime = resource.substring(14);
					ime = URLDecoder.decode(ime, "utf-8");
					System.out.println(ime);
					users.add(ime);
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					out.println(
							"<html><head><meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body><h1 align =\"/center\"/> Uspesna registracija </h1>");
					out.println("</body></html>");
					out.println("<a href=\"http://localhost/static/index.html\">Pocetna</a><br>");
					out.println("<a href=\"http://localhost/static/trazi.html\">Pretraga korisnika</a><br>");
					out.println("<a href=\"http://localhost/static/brisanje.html\">Brisanje korisnika</a><br>");
					out.println("<a href=\"http://localhost/static/registracija.html\">Registracija novih korisnika</a><br>");
					
					out.close();
				} else if (resource.startsWith("trazi?ime=")) {
					String ime = resource.substring(10);
					ime = URLDecoder.decode(ime, "utf-8");
					System.out.println(ime);
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					out.println("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style>\n" + "table, th, td {\n"
							+ "  border: 1px solid black;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "\n"
							+ "<h2>Korisnici</h2>\n" + "<p>Korisnici koji odgovaraju vasoj pretrazi su.</p>\n" + "\n"
							+ "<table style=\"width:100%\">\n" + "  <tr>\n" + "    <th>Username</th>\n"
							+ "    <th>Password</th> \n" + "    <th>Ime</th>\n" + "  </tr>");
					/*
					 * Dinamicko popunjavanje tabele 
					 */
					for (String user : users) {
						if(user.contains(ime)) {
							out.println("<tr>\n" + 
									"    <td>"+user+"</td>\n" + 
									"    <td>"+"********"+"</td>\n" + 
									"    <td>"+"zasticena info"+"</td>\n" + 
									"  </tr>");
						}
					}

					out.println("<a href=\"http://localhost/static/index.html\">Pocetna</a><br>");
					out.println("<a href=\"http://localhost/static/trazi.html\">Ponovna pretraga</a><br>");
					out.println("<a href=\"http://localhost/static/brisanje.html\">Brisanje korisnika</a><br>");
					out.println("<a href=\"http://localhost/static/registracija.html\">Registracija novih korisnika</a><br>");
					out.close();
				}else if(resource.startsWith("brisanje?ime=")){
					String ime= resource.substring(13);
					ime= URLDecoder.decode(ime,"utf-8");
					System.out.println(ime);
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					
					if(users.contains(ime)) {
						users.remove(ime);
						out.print("<html><head><meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body><h1 align =\"/center\"/> Uspesno brisanje </h1>");
						
					}else {
						out.print("<html><head><meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body><h1 align =\"/center\"/> Korisnik ne postoji. </h1>");
					}
					
					out.println("<a href=\"http://localhost/static/index.html\">Pocetna</a><br>");
					out.println("<a href=\"http://localhost/static/trazi.html\">Pretraga korisnika</a><br>");
					out.println("<a href=\"http://localhost/static/brisanje.html\">Brisanje korisnika</a><br>");
					out.println("<a href=\"http://localhost/static/registracija.html\">Registracija novih korisnika</a><br>");
					
					out.close();
					
					
				}else {
			
				

					System.out.println("Request from " + addr.getHostName() + ": " + resource);

					sendResponse(resource, socket.getOutputStream());
				}

//				System.out.println("Request from " + addr.getHostName() + ": " + resource);
//
//				// posalji odgovor
//				this.sendResponse(resource, socket.getOutputStream());
				socket.close();
				socket = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Metoda koja prima zahtev i vraca ciljanu stranicu.
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String getResource(InputStream is) throws IOException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = dis.readLine();

		// fail-safe
		if (s == null)
			return null;

		String[] tokens = s.split(" ");

		// prva linija HTTP zahteva: METOD /resurs HTTP/verzija
		// obradjujemo samo GET metodu
		String method = tokens[0];
		if (!method.equals("GET"))
			return null;

		// String resursa
		String resource = tokens[1];

		// izbacimo znak '/' sa pocetka
		resource = resource.substring(1);

		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = dis.readLine()).equals(""))
			System.out.println(s1);

		return resource;
	}

	/**
	 * Metoda koja salje odgovor klijentu.
	 * 
	 * 
	 * @param resource
	 * @param os
	 * @throws IOException
	 */
	private void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os);

		// zamenimo web separator sistemskim separatorom
		resource = resource.replace('/', File.separatorChar);
		File file = new File(resource);

		if (!file.exists()) {
			// ako datoteka ne postoji, vratimo kod za gresku
			String errorCode = "HTTP/1.0 404 File not found\r\n"
					+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Not found:" + file.getName() + "</b>";

			ps.print(errorCode);

//            ps.flush();
			System.out.println("Could not find resource: " + file);
			return;
		}

		// ispisemo zaglavlje HTTP odgovora
		ps.print("HTTP/1.0 200 OK\r\n\r\n");

		// a, zatim datoteku
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[8192];
		int len;

		while ((len = fis.read(data)) != -1)
			ps.write(data, 0, len);

		ps.flush();
		fis.close();
	}

}
