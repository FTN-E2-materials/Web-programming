package servletengine;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Klasa koja reprezentuje http zahtev (od klijenta).
 * 
 * @author Milan Vidakovic
 */
public class HttpServletRequest {

	/** Svi parametri iz forme se smestaju u asocijativnu mapu. */
	private HashMap<String, String> paramMap = new HashMap<String, String>();
	private HashMap<String, byte[]> multiParamMap = new HashMap<String, byte[]>();

	private String resource = null;

	private String method;

	private ArrayList<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

	/** Svi parametri iz http zaglavlja se smestaju u asocijativnu mapu. */
	private HashMap<String, String> headerMap = new HashMap<String, String>();
	private String remoteHost;

	/**
	 * Konstruktor. Izdvaja putanju do resursa (ako je ima) i smesta u atribut
	 * resource. Dok trazi putanju, inicijalizuje ovaj objekat podacima iz http
	 * zaglavlja.
	 */
	public HttpServletRequest(Socket sock) {
		this.remoteHost = sock.getRemoteSocketAddress().toString();
		InputStream is = null;
		try {
			is = sock.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			// kreiramo objekat koji reprezentuje sesiju.
			session = new HttpSession();
			// BufferedReader rdr = new BufferedReader(new
			// InputStreamReader(is));
			// // pokupimo prvi red iz http zahteva
			// String s = rdr.readLine();
			String s = readLine(is);
			// odstampamo prvi red http request-a
			System.out.println("\n" + s);
			if (!s.startsWith("GET ") && !s.startsWith("POST ")) {
				resource = null;
				return;
			}

			String[] tokens = s.split(" ");

			// HTTP first line format: METHOD /resource HTTP/version
			method = tokens[0];

			// preskocili smo METHOD, pa je sledeci string upravo putanja do
			// resursa
			String rsrc = tokens[1];

			// izbacimo vodeci '/' znak
			rsrc = rsrc.substring(1);

			// izdvojimo parametre Get metode forme (ako ih ima), a ostatak
			// je
			// uri do resursa
			resource = extractGetParameters(rsrc);

			// iscitamo zaglavlje http zahteva i
			// popunimo asocijativnu listu svih parametara iz zaglavlja
			readHeader(is);

			// ako je post metoda, izdvoj parametre u istu asoc. listu
			// paramMap
			if (method.equals("POST")) {
				extractPostParameters(is);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * String oblika ime1=vrednost1&ime2=vrednost2... parsira i smesta u asoc.
	 * mapu paramMap.
	 */
	private void putInParamMap(String params) {
		// izdelimo ih na pojedinacne parove "ime=vrednost"
		String[] tokens = params.split("&");
		for (String s : tokens) {
			int idx = s.indexOf("=");
			// levo od '=' je ime
			String pName = s.substring(0, idx).trim();
			// desno od '=' je vrednost
			String pValue = s.substring(idx + 1).trim();
			paramMap.put(pName, pValue);
		}
	}

	/**
	 * Iz prve linije elementa forme, oblika: <br>
	 * <code>Content-Disposition: form-data; name="filename"; filename="E:\Temp\proba.txt"</code>
	 * <br>
	 * izdvaja vrednost parametra datog imenom ("name").
	 */
	private String extractField(String fieldName, String s) {
		String retVal = null;
		// razbijemo deo na redove
		String[] lines = s.split("\\n");
		// izdvojimo prvi red
		String firstLine = lines[0].trim();
		// izdvojimo parametre razdvojene znakom ";"
		StringTokenizer st = new StringTokenizer(firstLine, ";");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int idx = token.indexOf("=");
			if (idx != -1) {
				String pName = token.substring(0, idx).trim(); // levo od '=' je
																// ime
				String pValue = token.substring(idx + 1).trim(); // desno od '='
																	// je
																	// vrednost
				if (pName.equals(fieldName)) {
					retVal = pValue.substring(1, pValue.length() - 1);
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * U ovom nizu se nalazi ostatak HTTP zaglavlja u kojem se nalaze parametri
	 * forme i datoteke koje su upload-ovane.
	 */
	private byte[] bytes;
	/** Ako je true, onda je POST multipart/form-data */
	private boolean multipart;

	public boolean isMultipart() {
		return multipart;
	}

	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}

	/** Izdvaja samo naziv fajla iz kompletne putanje. */
	private String extractFileName(String fileName) {
		int idx;
		idx = fileName.lastIndexOf("\\");
		if (idx != -1) { // ako imamo putanju
			fileName = fileName.substring(idx + 1); // izvucemo samo ime fajla
		} else if ((idx = fileName.lastIndexOf("/")) != -1) { // ako nije
																// Windows, onda
																// je Mac/Unix
																// separator?
			fileName = fileName.substring(idx + 1); // izvucemo samo ime fajla
		}
		return fileName;
	}

	/** Uklanja zaglavlje ispred datoteke. */
	private String stripHeader(String s) {
		// na kraju dela se nalazi prelazak u novi red, pa to treba preskociti
		int bytesToSkipAtTheEnd;
		int nl = s.indexOf("\r\n");
		nl += 4;
		bytesToSkipAtTheEnd = 2;
		String s1 = s.substring(nl, s.length() - bytesToSkipAtTheEnd);
		return s1;
	}

	private void dumpHeader(String s) {
		String[] lines = s.split("\\n");
		for (int i = 0; lines[i].trim().length() > 0; i++) {
			System.out.println(lines[i]);
		}
	}
	/**
	 * U zaglavlju HTTP zahteva stoji tip multipart/form-data, sa stringom za
	 * granicu izmedju polja koja se prenose. Primer jednog zaglavlja sa
	 * nekoliko polja (dva upload i jedno tekstualno): <br>
	 * 
	 * <pre>
	 *   Content-Type: multipart/form-data; boundary=---------------------------7d43b901104c6
	 * 
	 * 
	 *   -----------------------------7d43b901104c6
	 *   Content-Disposition: form-data; name="filename"; filename="E:\Temp\LS.COM"
	 *   Content-Type: application/octet-stream
	 * 
	 *   Tï¿½??[2J
	 *   ...
	 *   -----------------------------7d43b901104c6
	 *   Content-Disposition: form-data; name="filename"; filename="E:\Temp\proba.txt"
	 *   Content-Type: text/plain
	 * 
	 *   asdfasf
	 *   -----------------------------7d43b901104c6
	 *   Content-Disposition: form-data; name="TextField"
	 * 
	 *   asdf
	 *   -----------------------------7d43b901104c6
	 * </pre>
	 */
	private void processPart(String s, int pos) {
		// ako ne pocinje stringom "Content-Disposition"
		// onda je to ostatak koji ne predstavlja nista
		int idx = s.indexOf("Content-Disposition");
		if (idx == -1)
			return;
		/*
		 * U ovom trenutku, string "s" izgleda, na primer, ovako:
		 * Content-Disposition: form-data; name="filename";
		 * filename="E:\Temp\proba.txt" Content-Type: text/plain
		 * 
		 * asdfasf
		 */
		// pokusamo da izdvojimo naziv parametra "name", sto je ime polja u
		// formi
		String fieldName = extractField("name", s);
		if (fieldName == null) // ako ne postoji, nesto ne valja
			return;
		dumpHeader(s);
		// Zatim prelazimo na parametar ContentType.
		// Ako ga nema, u pitanju je polje sa tekstualnim sadrzajem;
		// ako ga ima, u pitanju je datoteka za upload.
		idx = s.indexOf("Content-Type");
		if (idx == -1) {
			// ako nema Content-Type, u pitanju je polje koje nije za upload
			// razbijemo deo na redove
			String[] lines = s.split("\\n");
			if (lines.length == 3) {
				// uzmemo treci red, koji predstavlja parametar, oblika:
				// ime=vrednost
				idx = s.indexOf(lines[2].trim());
				int len = lines[2].trim().length();
				byte[] buff = new byte[len];
				System.arraycopy(bytes, pos + idx, buff, 0, len);
				System.out.println("\n" + lines[2].trim());
				// posto se prilikom multipart POST zahteva tekstualna polja
				// kodiraju po zadatom enkodingu, a ne poput %XX, moramo da
				// ih smestimo u obliku bajtova koje cemo dekodirati na osnovu
				// encoding atributa
				multiParamMap.put(fieldName, buff);
			}
		} else { // datoteka za upload
			// uzmemo naziv polja za upload fajla
			String fileName = extractField("filename", s);
			if (fileName != null) {
				// ako ga nema, nije uneto nista u polju za datoteku, u formi
				if (fileName.equals(""))
					return;
				System.out.println("\n[BINARY_CONTENT]\n");
				// sada treba preskociti zaglavlje i otici direktno na fajl
				String s1 = stripHeader(s.substring(idx));
				pos += s.indexOf(s1);

				// izvucemo samo naziv fajla
				fileName = extractFileName(fileName);

				try {
					// pripremimo putanju za snimanje (u files poddirektorijum)
					File file = new File("files" + File.separatorChar
							+ fileName);
					FileOutputStream fs = new FileOutputStream(file);
					// pozicioniramo se u nizu bajtova u koji je ucitan ostatak
					// zaglavlja
					fs.write(bytes, pos, s1.length());
					fs.close();
					// dodamo u spisak ucitanih fajlova par
					// (naziv_polja_iz_forme, putanja_na_serveru)
					uploadedFiles.add(new UploadedFile(fieldName, file
							.getAbsolutePath()));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/** Razbije ostatak HTTP zaglavlja na pojedinacne elemente i procesira ih. */
	private void processMultiPart(String boundary, String s) {
		String parts[];
		/*
		 * - razbijemo ostatak HTTP zaglavlja upotrebom granicnika - granicnik
		 * razdvaja pojedinacne elemente zaglavlja - granicnik je oblika:
		 * "---...---boundary" - na kraju granicnika ide CRLF (ili samo LF), a
		 * kod poslednjeg granicnika na kraju ide "--" - HTTP zaglavlje delimo
		 * regularnim izrazom kao parametrom metode split()
		 */
		parts = s.split("-+" + boundary + "\\r?\\n?-*");
		for (int i = 0; i < parts.length; i++) {
			processPart(parts[i], s.indexOf(parts[i]));
		}
	}

	/**
	 * Izdvaja iz HTTP zaglavlja parametre forme date POST metodom i smesta ih u
	 * asocijativnu listu. Format parametara POST metode je:
	 * ime=vrednost&ime2=vrednost2...
	 * 
	 * @param rdr
	 *            Stream od klijenta koji ï¿½emo iskoristiti da izvuï¿½emo datoteke
	 *            koje se upload-uju.
	 * @param br
	 *            Reader koji ï¿½emo iskoristiti da izvuï¿½emo parametre forme koji
	 *            su poslati POST metodom, ali bez upload-a, tj.
	 *            "application/x-www-form-urlencoded" varijantom .
	 */
	private void extractPostParameters(InputStream dis) {
		try {
			String lengthStr = getHeader("Content-Length");
			String contentType = getHeader("Content-Type");
			// pokupili smo parametre Content-Length i Content-type
			String s1;
			if ((lengthStr != null) && (contentType != null)) {
				int len = 0;
				try {
					len = Integer.parseInt(lengthStr);
				} catch (Exception ex) {
					return;
				}
				if (len > 0
						&& contentType
								.equalsIgnoreCase("application/x-www-form-urlencoded")) {
					multipart = false;
					BufferedReader br = new BufferedReader(
							new InputStreamReader(dis));
					// ako nije multipart, tj. nema file upload
					// ucitamo ostatak zaglavlja
					char buff[] = new char[len];
					br.read(buff, 0, len);
					// napravimo string od ovoga
					s1 = new String(buff);
					System.out.println(s1);
					// parsiramo red i smestimo sve parametre u asoc. mapu
					putInParamMap(s1);
				} else if (len > 0
						&& contentType.startsWith("multipart/form-data")) {
					multipart = true;
					// ako imamo file upload
					bytes = new byte[len];
					// ucitamo ostatak zaglavlja u niz bajtova
					int total = 0;
					int l, razlika;
					do {
						razlika = len - total;
						l = dis.read(bytes, total, (razlika >= 1024 ? 1024
								: razlika));
						total += l;
					} while (total < len);
					// prevedemo celo zaglavlje u jedan veliki string, iz koga
					// necemo snimati
					// datoteku, ali cemo koristiti za analizu i izdvajanje
					// parametara
					s1 = new String(bytes, "US-ASCII");
					// System.out.println(s1);
					// string "boundary" oznacava granicu izmedju pojedinih
					// elemenata (multipart)
					int i = contentType.indexOf("boundary");
					if (i != -1) {
						String boundary = contentType.substring(i).trim();
						i = boundary.lastIndexOf("-");
						if (i != -1) {
							String boundaryText = boundary.substring(i + 1)
									.trim();
							// System.out.println(s1);
							// imamo izdvojen granicnik i zaglavlje; sada to
							// treba da procesiramo
							processMultiPart(boundaryText, s1.trim());
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Izdvaja iz resursa parametre forme date GET metodom i smesta ih u
	 * asocijativnu listu. Format je: putanja?ime=vrednost&ime2=vrednost2...
	 * 
	 * @param rsrc
	 *            Resurs, odn. putanja do resursa.
	 * @return Vraca samo resurs, bez parametara forme (oni su smesteni u
	 *         asocijativnu listu).
	 */
	private String extractGetParameters(String rsrc) {
		String[] tokens = rsrc.split("\\?");
		// ako imamo parametre forme
		if (tokens.length == 2) {
			// zapamtimo prvi deo, tj. "putanju", jer cemo to vratiti
			String retVal = tokens[0];
			// uzmemo parametre
			String s = tokens[1];
			paramMap.clear();
			putInParamMap(s);
			return retVal;
		} else
			return rsrc;
	}

	/** Èita jedan red iz ulaznog toka do entera. */
	private byte[] readLineBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(65536);
		try {
			do {
				byte b = (byte) is.read();
				if (b == '\r') {
					b = (byte) is.read(); // pokupimo '\n'
					break;
				} else {
					if (baos.size() > 1024)
						break;
					baos.write(b);
				}
			} while (true);
		} catch (Exception ex) {
			System.err.println("ReadLine Error: " + ex.getMessage());
		}
		return baos.toByteArray();
	}

	/**
	 * Èita jedan red iz ulaznog toka i konvertuje string po zadatom encoding-u.
	 */
	private String readLine(InputStream is) throws IOException {
		byte[] buff = readLineBytes(is);
		if (encoding != null)
			return new String(buff, encoding);
		else
			return new String(buff);
	}

	/** Cita parametre http zaglavlja i smesta ih u asocijativnu mapu. */
	private void readHeader(InputStream is) {
		try {
			String s1, name, value;
			do {
				s1 = readLine(is);
				System.out.println(s1);
				int idx = s1.indexOf(":");
				if (idx != -1) {
					// levo od ':' je ime
					name = s1.substring(0, idx);
					// desno od ':' je vrednost
					value = s1.substring(idx + 1).trim();
					headerMap.put(name.toUpperCase(), value);
				}
			} while (!s1.equals(""));
			System.out.println();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Vraca vrednost parametra http zaglavlja (ako ga ima). */
	public String getHeader(String name) {
		return headerMap.get(name.toUpperCase());
	}

	/** Objekat klase Session koji ï¿½uva podatke o trenutnoj sesiji. */
	private HttpSession session;

	public HttpSession getSession() {
		return session;
	}

	private String encoding = null;

	public void setCharacterEncoding(String e) {
		encoding = e;
	}

	public String getCharacterEncoding() {
		return encoding;
	}

	public String getMultiParameter(String string) {
		byte[] param = multiParamMap.get(string);
		if (param == null)
			return null;
		if (encoding != null)
			try {
				return new String(param, encoding);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		else
			return new String(param);
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getResource() {
		return resource;
	}

	public String getParameter(String name) {
		String s = paramMap.get(name);
		if (s == null)
			return null;

		try {
			if (encoding != null) {
				s = URLDecoder.decode(s, encoding);
			}
			return s;
		} catch (Exception ex) {
			return null;
		}
	}

	public String getMethod() {
		return method;
	}

	public int getUploadedFilesCount() {
		return uploadedFiles.size();
	}

	public UploadedFile getUploadedFile(int i) {
		return uploadedFiles.get(i);
	}

}