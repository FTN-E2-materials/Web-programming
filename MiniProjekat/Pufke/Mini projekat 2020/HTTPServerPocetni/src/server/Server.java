package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import model.Porucioc;


/**
 * Jednostavan web server
 */
public class Server {
	private static List<Porucioc> porucioci = new ArrayList<>();

	public static void main(String args[]) throws IOException {
		int port = 80;

		@SuppressWarnings("resource")
		ServerSocket srvr = new ServerSocket(port);

		System.out.println("httpd running on port: " + port);
		System.out.println("document root is: " + new File(".").getAbsolutePath() + "\n");

		Socket skt = null;

		while (true) {
			try {
				skt = srvr.accept();
				InetAddress addr = skt.getInetAddress();

				String resource = getResource(skt.getInputStream());

				if (resource.equals(""))
					resource = "static/index.html";

				System.out.println("Request from " + addr.getHostName() + ": " + resource);

				sendResponse(resource, skt.getOutputStream());
				skt.close();
				skt = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	// Metoda koja obradjuje poruke koje stizu od klijenta
	static String getResource(InputStream is) throws IOException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = dis.readLine();// Liniju po liniju citamo sta nam je to klijent poslao
		if (s == null)// Specavamo null pointer exceptio da ne parsiramo ako je prazan odgovor
			s = "";

		System.out.println(s);

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
		while (!(s1 = dis.readLine()).equals(""))
			System.out.println(s1);

		return rsrc;
	}

//Metoda koja vraca nazad klijentu datoteku ili nesto drugo
	static void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os);
		
		if(resource.startsWith("fotografijeSuIzradjene")) {
			String brojIzrade = resource.substring(22);
			
			//CSS
			String response = "<html><head><style>\r\n" + 
					"table, th, td {\r\n" + 
					"  border: 3px solid black;\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"}\r\n" + 
					"#naslov{"+
					"color:#808080;}\r\n"+
					".pretragaElementi{ display:hidden;}\r\n"
					+ 
				    ".format10x15{"
				    + "background-color:#808080;"+
				    "}"
				    + 
				    ".statusIZRADJENE{"
				    + "display: none;"+
				    "}"+
					"</style></head><body>\r\n"
					+"<script src=\"myscripts.js\"></script>\r\n ";
					
			response += "<h1 id=\"naslov\">HTTP. Pregled izrade fotografije</h1>";
			//Tabela
			response += "<table id=\"tabelaPacijenata\">";
			
			response += "<tr>"
					  +"<td>" + "Broj izrade" + "</td>" 
					  +"<td>" + "Ime narucioca" + "</td>" 
					  +"<td>" + "Broj telefona" + "</td>"
					  +"<td>" + "Naziv foldera" + "</td>"
					  +"<td>" + "Format fotografije" + "</td>"
					  +"<td>" + "Cena izrade[rsd]" + "</td>"
					  +"<td>" + " " +  "</td>"
					  +"</tr>"; 
			

			for (Porucioc p : porucioci) {
				if(p.getBrojIzrade().equals(brojIzrade)) {
					p.setStatusIzrade("IZRADJENE");
				}
				response += "<tr" + " class=" +"\"" +"format"+ p.getFormatFotografija()+ "\"" +">"
						  + "<td>" + p.getBrojIzrade() + "</td>" 
						  + "<td>" + p.getImeNarucioca() + "</td>" 
						  + "<td>"  + p.getBrojTelefona()+"</td>"
						  + "<td>"  + p.getNazivFoldera()+ "</td>"
						  + "<td>" + p.getFormatFotografija()+"</td>"
						  + "<td>"  + p.getCenaIzrade() +"</td>" 
						  + "<td" + " class=" + "\"" + "status"+ p.getStatusIzrade()+ "\"" +">"
						  + "<a href=\"http://localhost:80/fotografijeSuIzradjene"
						  + p.getBrojIzrade() + "\"" 
						  +">Fotografije su izradjene</a>"  
						  + "</td>"
						  + "</tr>" ; 

			}
		
			
			response += "</table> <br>";
			
			response += "<h2 class=\"pretragaElementi\">Pretraga forografija za izradu:</h2> <br>";
			response += "<label class=\"pretragaElementi\" for=\"pretraga\">Unesite ime narucioca:</label>\r\n" + 
					"		<input class=\"pretragaElementi\" type=\"text\" id=\"pretraga\"/><br> <button class=\"pretragaElementi\" onclick=\"pretraga()\" type=\"button\">Pretrazi</button>";
			
			
			response += "</body></html>";
			
			ps.print("HTTP/1.1 200 OK\n\n");
			ps.print(response);
			return;
		}
		
		
		
		
		if(resource.startsWith("pregledIzradeFotografija")) {
			String[] parts = resource.split("\\?");
			String podaci = parts[1]; 
			
			String[] podaciParts = podaci.split("&"); 
			
			String brojIzrade = podaciParts[0];
			String[] brojIzradeParts = brojIzrade.split("=");
			String brID = brojIzradeParts[1]; 
			brID = URLDecoder.decode(brID, "utf-8");
			
			String imeNarucioca = podaciParts[1];
			String[] imeNaruciocaParts = imeNarucioca.split("=");
			String imeNar = imeNaruciocaParts[1]; 
			
			String brojTelefona = podaciParts[2];
			String[] brojTelefonaParts = brojTelefona.split("=");
			String brTel = brojTelefonaParts[1]; 
			
			String nazivFoldera = podaciParts[3];
			String[] nazivFolderaParts = nazivFoldera.split("=");
			String nazFoldera = nazivFolderaParts[1]; 
			
			String formatFotografija = podaciParts[4];
			String[] formatFotografijaParts = formatFotografija.split("=");
			String formatFoto = formatFotografijaParts[1]; 
			
			String cenaIzrade = podaciParts[5];
			String[] cenaIzradeParts = cenaIzrade.split("=");
			String cenaIzr = cenaIzradeParts[1]; 
			
			Porucioc porucioc = new Porucioc(brID, imeNar, brTel, nazFoldera,formatFoto, cenaIzr);
			
			//Provera da li vec psotoji pacijent
			for (Porucioc p : porucioci) {
				if(p.getBrojIzrade().equals(porucioc.getBrojIzrade())) {
					ps.print("HTTP/1.0 404 File not found\r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Izrada je već unesena!" + "</b>");
					return;
				}
			}
			
			porucioci.add(porucioc);
			
			//CSS
			String response = "<html><head><style>\r\n" + 
					"table, th, td {\r\n" + 
					"  border: 3px solid black;\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"}\r\n" + 
					"#naslov{"+
					"color:#808080;}\r\n"+
					".pretragaElementi{ display:hidden;}\r\n"
					+ 
				    ".format10x15{"
				    + "background-color:#808080;"+
				    "}"
				    + 
				    ".statusIZRADJENE{"
				    + "display: none;"+
				    "}"+
					"</style></head><body>\r\n"
					+"<script src=\"myscripts.js\"></script>\r\n ";
					
			response += "<h1 id=\"naslov\">HTTP. Pregled izrade fotografije</h1>";
			//Tabela
			response += "<table id=\"tabelaPacijenata\">";
			
			response += "<tr>"
					  +"<td>" + "Broj izrade" + "</td>" 
					  +"<td>" + "Ime narucioca" + "</td>" 
					  +"<td>" + "Broj telefona" + "</td>"
					  +"<td>" + "Naziv foldera" + "</td>"
					  +"<td>" + "Format fotografije" + "</td>"
					  +"<td>" + "Cena izrade[rsd]" + "</td>"
					  +"<td>" + " " +  "</td>"
					  +"</tr>"; 
			

			for (Porucioc p : porucioci) {
				response += "<tr" + " class=" +"\"" +"format"+ p.getFormatFotografija()+ "\"" +">"
						  + "<td>" + p.getBrojIzrade() + "</td>" 
						  + "<td>" + p.getImeNarucioca() + "</td>" 
						  + "<td>"  + p.getBrojTelefona()+"</td>"
						  + "<td>"  + p.getNazivFoldera()+ "</td>"
						  + "<td>" + p.getFormatFotografija()+"</td>"
						  + "<td>"  + p.getCenaIzrade() +"</td>" 
						  + "<td" + " class=" + "\"" + "status"+ p.getStatusIzrade()+ "\"" +">"
						  + "<a href=\"http://localhost:80/fotografijeSuIzradjene"
						  + p.getBrojIzrade() + "\"" 
						  +">Fotografije su izradjene</a>"  
						  + "</td>"
						  + "</tr>" ; 

			}
		
			
			response += "</table> <br>";
		    
			//Sekcija za pretragu
			
			response += "<h2 class=\"pretragaElementi\">Pretraga forografija za izradu:</h2> <br>";
			response += "<label class=\"pretragaElementi\" for=\"pretraga\">Unesite ime narucioca:</label>\r\n" + 
					"		<input class=\"pretragaElementi\" type=\"text\" id=\"pretraga\"/><br> <button class=\"pretragaElementi\" onclick=\"pretraga()\" type=\"button\">Pretrazi</button>";
			
				
			response += "</body></html>";
		
			ps.print("HTTP/1.1 200 OK\n\n");
			ps.print(response);
			return;
		}
	
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


	static HashMap<String, String> getParameter(String requestLine) {
		HashMap<String, String> retVal = new HashMap<String, String>();

		String request = requestLine.split("\\?")[0];
		retVal.put("request", request);
		String parameters = requestLine.substring(requestLine.indexOf("?") + 1);
		StringTokenizer st = new StringTokenizer(parameters, "&");
		while (st.hasMoreTokens()) {
			String key = "";
			String value = "";
			StringTokenizer pst = new StringTokenizer(st.nextToken(), "=");
			key = pst.nextToken();
			if (pst.hasMoreTokens())
				value = pst.nextToken();

			retVal.put(key, value);
		}

		return retVal;
	}
}
