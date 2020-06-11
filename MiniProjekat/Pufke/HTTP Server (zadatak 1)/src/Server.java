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
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Jednostavan web server
 */
public class Server {
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
		if (s == null)
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
