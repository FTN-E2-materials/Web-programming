package httpd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import jsp.ServletBuilder;
import servletengine.Authorization;
import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

/**
 * Single threaded HTTP server.
 */
public class Httpd {

	/**
	 * Asocijativna mapa servleta. Cuvaju se parovi (servletName, servletClass).
	 */
	private HashMap<String, HttpServlet> servletMap = new HashMap<String, HttpServlet>();

	/**
	 * Vraca port na kom slusa web server. Ako se ne navede, server slusa na
	 * standardnom portu (80).
	 */
	private int getPort(String args[]) {
		int port = 80;

		if (args.length == 0) {
			return port;
		}

		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
		}

		return port;
	}

	/** Pomocna promenljiva koja se koristi za generisanje cookie-a. */
	private int count = 0;

	/** Konstruktor i glavna petlja. */
	public Httpd(String[] args) {
		HttpServletRequest request;
		HttpServletResponse response;

		try {

			// iz inicijalizacione datoteke pokupimo spisak svih servleta
			collectServlets();

			// pokupimo port na kojem ce server da slusa
			int port = getPort(args);

			@SuppressWarnings("resource")
			ServerSocket srvr = new ServerSocket(port);

			System.out.println("httpd running on port: " + port);
			System.out.println("document root is: "
					+ new File(".").getAbsolutePath() + "\n");

			Socket skt = null;

			while (true) {
				try {
					// cekamo na klijenta
					skt = srvr.accept();
					// pokupimo adresu klijenta
					InetAddress addr = skt.getInetAddress();
					System.out.println("Client connected from: " + addr);

					// pripremimo objekat koji reprezentuje zahtev od klijenta
					request = new HttpServletRequest(skt);
					// izvucemo uri do resursa
					String resource = request.getResource();
					if (resource == null) {
						skt.close();
						skt = null;
						continue;
					}
					System.out.println();
					
					if (resource.endsWith(".jsp")) {
						// Ako se ime resursa završava na ".jsp"
						// probaj da napraviš servlet od njega
						String s = buildServlet(resource);
						if (s != null) {
							// ako smo uspeli da napravimo servlet
							// onda ćemo ga i pozvati
							resource = s;
						}
					}

					// pripremimo objekat koji reprezentuje odgovor servera
					response = new HttpServletResponse(skt.getOutputStream());
					// pripremimo pracenje sesije
					handleCookies(request, response);

					// potrazimo servlet na osnovu imena
					HttpServlet s = findServlet(resource);
					if (s != null) // ako smo ga nasli, startujemo ga
						s.service(request, response);
					else
						// ako ne, onda je to staticki web sadrzaj
						sendResponse(resource, request, response);

					skt.close();
					skt = null;
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Pravi servlet od JSP stranice. Ako uspešno kompajlira
	 * napravljen servlet, onda ga i doda u mapu servleta 
	 * (da ne bi ponovo kompajlirao).
	 * 
	 * @param resource Putanja do JSP fajla
	 * @return alias za servlet koji je nastao od JSP stranice
	 */
	private String buildServlet(String resource) {
		String sName = ServletBuilder.createFullServletName(resource);
		// ako napravljeni servlet već postoji i 
		// nije mlađi od originalne JSP stranice 
		if (servletMap.get(sName) != null && ServletBuilder.correctDate(resource)) {
			// ne moramo da pravimo ponovo servlet i 
			// imamo ga već učitanog i spremnog
			return sName;
		}
		ServletBuilder sb = new ServletBuilder(resource);
		// napravimo servlet od JSP stranice
		HttpServlet s = sb.buildServlet();
		if (s != null) {
			// ako je uspelo, dodamo servlet u mapu servleta
			// ako je servlet već postojao, ovo će ga zameniti
			servletMap.put(sName, s);
			System.out.println(sName + "->" + s.getClass().getName());
			return sName;
		}
		return null;
	}

	/**
	 * Trazi servlet u asocijativnoj mapi. Ako ga nadje, vrati referencu na
	 * njega.
	 */
	private HttpServlet findServlet(String resource) {
		HttpServlet servlet = servletMap.get(resource);
		return servlet;
	}

	/**
	 * Iz konfiguracione datoteke skuplja servlete i cuva njihova simbolicka
	 * imena i odgovarajuce nazive Java klasa u asocijativnoj mapi.
	 */
	private void collectServlets() {
		try {
			BufferedReader bin = new BufferedReader(
					new FileReader("httpd.conf"));

			String s, sName, sClass;
			int idx;
			while ((s = bin.readLine()) != null) {
				if (s.trim().equals(""))
					continue;
				idx = s.indexOf("=");
				sName = s.substring(0, idx);
				sClass = s.substring(idx + 1);
				HttpServlet srv = (HttpServlet) Class.forName(sClass)
						.newInstance();
				servletMap.put(sName, srv);
				System.out.println(sName + "->" + sClass);
			}
			bin.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Odradjuje pracenje sesije upotrebom cookie-ja. Ako u http zahtevu nema
	 * cookie-ja, generise nov i spremi ga u response objektu (da se posalje
	 * klijentu prilikom slanja odgovora). Ako cookie postoji, zapamti se za
	 * dalje pracenje sesije.
	 */
	private void handleCookies(HttpServletRequest request,
			HttpServletResponse response) {
		// pogledamo da li u http zaglavlju postoji parametar Cookie
		String cookieFromRequest = request.getHeader("Cookie");

		if (cookieFromRequest != null) { // ako cookie postoji u request-u,onda
											// je dosao od naseg servera, pa ga
											// treba zapamtiti
			response.setCookie(cookieFromRequest);
			request.getSession().setId(cookieFromRequest);
		} else { // nema cookie-a u request-u, pa ga teba izgenerisati
			count++;
			String cookie = "id" + System.currentTimeMillis() + "_" + count
					+ "=" + System.currentTimeMillis() + "_" + count;
			response.setCookie(cookie);
			request.getSession().setId(cookie);
		}

	}

	/**
	 * Salje odgovor koji se sastoji iz statickog sadrzaja (datoteke). Servleti
	 * se obradjuju posebno.
	 */
	private void sendResponse(String resource, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.headerSent = true;
		PrintStream ps = new PrintStream(response.getOutputStream());
		response.headerSent = false;

		if (resource.equals("")) {
			// ako resource nije naveden, podmetnu�emo default datoteku, tj.
			// "index.html" datoteku.
			resource = "index.html";
		} else {
			// ako je resource naveden, zamenimo separator '/' sistemskim
			// separatorom putanje
			resource = resource.replace('/', File.separatorChar);
		}

		if (Authorization.requiresBasicAuthorization(resource)) {
			String authorization = request.getHeader("Authorization");
			if (authorization != null) { // browser poslao username:password
				// preskocimo pocetak stringa koji je oblika: Basic xxxxxxxx
				try {
					authorization = authorization.split("Basic ")[1];
				} catch (Exception ex) {
					authorization = null;
				}
				if (!Authorization.validBasic(resource, authorization)) {
					// browser poslao username:password ALI NIJE DOBAR
					response.setResponseHeader("HTTP/1.0 401 Unauthorized");
					response.setHeader("WWW-Authenticate", "Basic realm=\""
							+ resource + "\"");
					response.sendHeader();
					// ps.print("HTTP/1.0 401 Unauthorized\r\nWWW-Authenticate: Basic realm=\""
					// + resource + "\"\r\n\r\n");
					ps.flush();
					System.out.println("Wrong uname/pass for this resource: "
							+ resource);
					return;
				}
			} else { // browser NIJE poslao username:password, pa mu saljemo
						// WWW-authenticate atribut
				response.setResponseHeader("HTTP/1.0 401 Unauthorized");
				response.setHeader("WWW-Authenticate", "Basic realm=\""
						+ resource + "\"");
				response.sendHeader();
				// ps.print("HTTP/1.0 401 Unauthorized\r\nWWW-Authenticate: Basic realm=\""
				// + resource + "\"\r\n\r\n");
				// ps.flush();
				System.out.println("This resource needs authorization: "
						+ resource);
				return;
			}
		} else if (Authorization.requiresDigestAuthorization(resource)) {
			String authorization = request.getHeader("Authorization");
			if (authorization != null) { // browser poslao username:password
				// preskocimo pocetak stringa koji je oblika: Digest xxxxxxxx
				try {
					authorization = authorization.split("Digest ")[1];
				} catch (Exception ex) {
					authorization = null;
				}
				if (!Authorization.validDigest(resource, authorization)) {
					// browser poslao username:password ALI NIJE DOBAR
					response.setResponseHeader("HTTP/1.0 401 Unauthorized");
					response.setHeader(
							"WWW-Authenticate",
							"Digest realm=\""
									+ resource
									+ "\", "
									+ "nonce=\""
									+ Authorization.getNonce()
									+ "\", qop=\"auth,auth-int\", opaque=\"5ccc069c403ebaf9f0171e9517f40e41\"");
					response.sendHeader();
					// ps.print("HTTP/1.0 401 Unauthorized\r\nWWW-Authenticate: Digest realm=\""
					// + resource + "\", " +
					// "nonce=\"" + Authorization.getNonce() +
					// "\", qop=\"auth,auth-int\", opaque=\"5ccc069c403ebaf9f0171e9517f40e41\"\r\n\r\n");
					// ps.flush();
					System.out.println("Wrong uname/pass for this resource: "
							+ resource);
					return;
				}
			} else { // browser NIJE poslao username:password, pa mu saljemo
						// WWW-authenticate atribut
				response.setResponseHeader("HTTP/1.0 401 Unauthorized");
				response.setHeader(
						"WWW-Authenticate",
						"Digest realm=\""
								+ resource
								+ "\", "
								+ "nonce=\""
								+ Authorization.getNonce()
								+ "\", qop=\"auth,auth-int\", opaque=\"5ccc069c403ebaf9f0171e9517f40e41\"");
				response.sendHeader();
				// ps.print("HTTP/1.0 401 Unauthorized\r\nWWW-Authenticate: Digest realm=\""
				// + resource + "\", " +
				// "nonce=\"" + Authorization.getNonce() +
				// "\", qop=\"auth,auth-int\", opaque=\"5ccc069c403ebaf9f0171e9517f40e41\"\r\n\r\n");
				// ps.flush();
				System.out.println("This resource needs authorization: "
						+ resource);
				return;
			}
		}

		// probamo da otvorimo datoteku na koju ukazuje resource
		File file = new File(resource);
		// ako resource ukazuje na folder,
		// preusmericemo na default datoteku, tj. index.html
		if (file.isDirectory())
			file = new File(resource + File.separatorChar + "index.html");

		if (!file.exists()) {
			// file not found error
			ps.print("HTTP/1.0 404 File not Found\r\n\r\n");
			ps.flush();
			System.out.println("Could not find resource: " + file);
			return;
		}

		String ext = "";
		int idx = resource.lastIndexOf(".");
		if (idx != -1)
			ext = resource.substring(idx);
		// pokusamo da podesimo content type
		// setContentType takodje automatski salje HTTP zaglavlje
		if (ext.equalsIgnoreCase(".html"))
			response.setContentType("text/html");
		else if (ext.equalsIgnoreCase(".gif"))
			response.setContentType("image/gif");
		else if (ext.equalsIgnoreCase(".jpg"))
			response.setContentType("image/jpeg");
		else
			response.setContentType(null);

		// otvorimo resurs...
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[8192];
		int len;
		// ... i posaljemo ga preko mreze do web citaca (browser-a)
		while ((len = fis.read(data)) != -1) {
			ps.write(data, 0, len);
		}

		ps.flush();
		fis.close();
	}

	public static void main(String args[]) {
		new Httpd(args);
	}

}
