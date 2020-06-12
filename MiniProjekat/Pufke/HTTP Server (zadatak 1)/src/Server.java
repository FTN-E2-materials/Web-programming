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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import sun.security.util.Length;

/**
 * Jednostavan web server
 */
public class Server {
	private static List<Pacijent> pacijenti = new ArrayList<>();
	
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
	
   //Metoda koja obradjuje poruke koje stizu od klijenta
	static String getResource(InputStream is) throws IOException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = dis.readLine();//Liniju po liniju citamo sta nam je to klijent poslao
		if (s == null)//Specavamo null pointer exceptio da ne parsiramo ako je prazan odgovor
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
		
		if(resource.startsWith("testJePozitivan")) {
			String brojZdravstvenogOsiguranja = resource.substring(15);
			
			//CSS
			String response = "<html><head><style>\r\n" + 
					"table, th, td {\r\n" + 
					"  border: 3px solid black;\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"}\r\n" + 
					"#naslov{"+
					"color:red;}\r\n"+
					".pretragaElementi{ display:hidden;}\r\n"
					+ 
				    "#ZARAZEN{"
				    + "background-color:red;"+
				    "}"+
					"</style></head><body>\r\n"
					+"<script src=\"myscripts.js\"></script>\r\n ";
					
			response += "<h1 id=\"naslov\">HTTP. Pregled pacijenata [COVID-19]</h1>";
			//Tabela
			response += "<table id=\"tabelaPacijenata\">";
			response += "<tr>"
					  +"<td>" + "Broj zdravstvenog osiguranja" + "</td>" 
					  +"<td>" + "Ime pacijenta" + "</td>" 
					  +"<td>" + "Prezime pacijenta" + "</td>"
					  +"<td>" + "Datum rodjenja" + "</td>"
					  +"<td>" + "Pol" + "</td>"
					  +"<td>" + "Zdravstveni status" + "</td>"
					  +"<td>" + " " +  "</td>"
					  +"</tr>"; 
			

			for (Pacijent p : pacijenti) {
				if(p.getBrZdravstvenogOsig().equals(brojZdravstvenogOsiguranja)) {
					p.setZdravstveniStatus("ZARAZEN");
				}
				response += "<tr" + " id=" +"\""+ p.getZdravstveniStatus()+ "\"" +">"
						  + "<td>" + p.getBrZdravstvenogOsig() + "</td>" 
						  + "<td>" + p.getIme() + "</td>" 
						  + "<td>"  + p.getPrezime()+"</td>"
						  + "<td>"  + p.getDatumRodjenja()+ "</td>"
						  + "<td>" + p.getPol()+"</td>"
						  + "<td>"  + p.getZdravstveniStatus() +"</td>" 
						  + "<td>" 
						  + "<a href=\"http://localhost:80/testJePozitivan"
						  + p.getBrZdravstvenogOsig() + "\"" 
						  +">Test je pozitivan!</a>"  
						  + "</td>"
						  + "</tr>" ; 

			}
			
			response += "</table> <br>";
		    
			response += "<h2 class=\"pretragaElementi\">Pretraga pacijenta po prezimenu:</h2> <br>";
			response += "<label class=\"pretragaElementi\" for=\"pretraga\">Pretraga pacijenta:</label>\r\n" + 
					"		<input class=\"pretragaElementi\" type=\"text\" id=\"pretraga\"/><br> <button class=\"pretragaElementi\" onclick=\"pretraga()\" type=\"button\">Pretrazi</button>";
			
			
			ps.print("HTTP/1.1 200 OK\n\n");
			ps.print(response);
			return;
		}
		
		
		
		if(resource.startsWith("pregledPacijenata")) {
			String[] parts = resource.split("\\?");
			String podaci = parts[1]; // Otprilike izgleda ovako sada 
										//brojZdravstvenogOsiguranja=1234&imePacijenta=Nemanja&prezimePacijenta=Kljajic
			
			String[] podaciParts = podaci.split("&"); //Sada izgleda ovako brojZdravstvenogOsiguranja=1234 ... 
			
			String brojZdravstvenogOsiguranja = podaciParts[0];
			String[] brojZdravstvenogOsiguranjaParts = brojZdravstvenogOsiguranja.split("=");
			String brZdravOsig = brojZdravstvenogOsiguranjaParts[1]; 
			
			String imePacijenta = podaciParts[1];
			String[] imePacijentaParts = imePacijenta.split("=");
			String imeP = imePacijentaParts[1]; 
			
			String prezimePacijenta = podaciParts[2];
			String[] prezimePacijentaParts = prezimePacijenta.split("=");
			String prezimeP = prezimePacijentaParts[1]; 
			
			String datumRodjenjaPacijenta = podaciParts[3];
			String[] datumRodjenjaPacijentaParts = datumRodjenjaPacijenta.split("=");
			String datumRPac = datumRodjenjaPacijentaParts[1]; 
			
			String polPacijenta = podaciParts[4];
			String[] polPacijentaParts = polPacijenta.split("=");
			String polP = polPacijentaParts[1]; 
			
			String zdravstveniStatusPacijenta = podaciParts[5];
			String[] zdravstveniStatusPacijentaParts = zdravstveniStatusPacijenta.split("=");
			String zdravstveniStatus = zdravstveniStatusPacijentaParts[1]; 
			
			Pacijent pacijent = new Pacijent(brZdravOsig, imeP, prezimeP, datumRPac,polP, zdravstveniStatus);
			
			//Provera da li vec psotoji pacijent
			for (Pacijent pa : pacijenti) {
				if(pa.getBrZdravstvenogOsig().equals(pacijent.getBrZdravstvenogOsig())) {
					ps.print("HTTP/1.0 404 File not found\r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Pacijent je već testiran!" + "</b>");
					return;
				}
			}
			
			
			pacijenti.add(pacijent);
			
			

			
			//CSS
			String response = "<html><head><style>\r\n" + 
					"table, th, td {\r\n" + 
					"  border: 3px solid black;\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"}\r\n" + 
					"#naslov{"+
					"color:red;}\r\n"+
					".pretragaElementi{ display:hidden;}\r\n"
					+ 
				    "#ZARAZEN{"
				    + "background-color:red;"+
				    "}"+
					"</style></head><body>\r\n"
					+"<script src=\"myscripts.js\"></script>\r\n ";
					
			response += "<h1 id=\"naslov\">HTTP. Pregled pacijenata [COVID-19]</h1>";
			//Tabela
			response += "<table id=\"tabelaPacijenata\">";
			
			response += "<tr>"
					  +"<td>" + "Broj zdravstvenog osiguranja" + "</td>" 
					  +"<td>" + "Ime pacijenta" + "</td>" 
					  +"<td>" + "Prezime pacijenta" + "</td>"
					  +"<td>" + "Datum rodjenja" + "</td>"
					  +"<td>" + "Pol" + "</td>"
					  +"<td>" + "Zdravstveni status" + "</td>"
					  +"<td>" + " " +  "</td>"
					  +"</tr>"; 
			

			for (Pacijent p : pacijenti) {
				response += "<tr" + " id=" +"\""+ p.getZdravstveniStatus()+ "\"" +">"
						  + "<td>" + p.getBrZdravstvenogOsig() + "</td>" 
						  + "<td>" + p.getIme() + "</td>" 
						  + "<td>"  + p.getPrezime()+"</td>"
						  + "<td>"  + p.getDatumRodjenja()+ "</td>"
						  + "<td>" + p.getPol()+"</td>"
						  + "<td>"  + p.getZdravstveniStatus() +"</td>" 
						  + "<td>" 
						  + "<a href=\"http://localhost:80/testJePozitivan"
						  + p.getBrZdravstvenogOsig() + "\"" 
						  +">Test je pozitivan!</a>"  
						  + "</td>"
						  + "</tr>" ; 

			}
			
			response += "</table> <br>";
		    
			//Sekcija za pretragu
			
			response += "<h2 class=\"pretragaElementi\">Pretraga pacijenta po prezimenu:</h2> <br>";
			response += "<label class=\"pretragaElementi\" for=\"pretraga\">Pretraga pacijenta:</label>\r\n" + 
					"		<input class=\"pretragaElementi\" type=\"text\" id=\"pretraga\"/><br> <button class=\"pretragaElementi\" onclick=\"pretraga()\" type=\"button\">Pretrazi</button>";
			
			

			
			response += "</body></html>";
		
			ps.print("HTTP/1.1 200 OK\n\n");
			ps.print(response);
			return;
		}
		
		
		
		
		// zamenimo web separator sistemskim separatorom
		resource = resource.replace('/', File.separatorChar);
		
		// Ako je resource jednak stringu koji se dobija prilikom submita forme
		// oblika resurs?parametar=vrednost&... npr: dodaj?ime=pera&prezime=peric
		// getParameter(resource) - vratice HashMap objekat gde su kljucevi parametri (npr. ime, prezime),
		// a vrednosti su jednake unetim vrednostima: mapa.get("ime") vraca "pera"
		
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
	
	
	/**
	 * Funkcija koja prima string zahtev oblika: <br/>
	 *  {@literal "resurs?parametar1=vrednost1&parametar2=vrednost2&...&parametarN=vrednostN"} <br/>
	 * I vraća HashMap objekat sa Key - Value parovima:
	 * <pre>
	 * 	{ 
	 * 		request : resurs,
	 * 		parametar1 : vrednost1,
	 * 		parametar2 : vrednost2,
	 * 		...
	 * 		parametarN : vrednostN
	 *        } 
	 * </pre>
	 * @param requestLine - String oblika {@literal "resurs?parametar1=vrednost1&parametar2=vrednost2&...&parametarN=vrednostN"}
	 * @return - HashMap&lt;String, String&gt; {parametar: vrednost}
	 */
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
