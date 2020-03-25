package servletengine;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Klasa koja reprezentuje odgovor web servera. Ako nista dodatno nije
 * definisano, poslace string: "HTTP/1.0 200 OK" i prazan red. Od dodatnih
 * stvari, podrzava slanje sledecih parametara: <br>
 * 1. Content-type, koji definise tip resursa koji se salje ka klijentu, <br>
 * 2. Set-Cookie, koji definise cookie koji ce se poslati klijentu. <br>
 * 3. Location, koji definise adresu za redirekciju.
 * 
 * @author Milan Vidakovic
 */
public class HttpServletResponse {
	@SuppressWarnings("unused")
	private String contentType = null;
	private PrintWriter writer = null;
	private OutputStream outputStream = null;
	public boolean headerSent = false;
	private String responseHeader = null;

	@SuppressWarnings("unused")
	private String location;

	@SuppressWarnings("unused")
	private String cookie;

	private HashMap<String, String> headerContent = new HashMap<String, String>();

	public HttpServletResponse(OutputStream out) {
		outputStream = out;
		writer = new PrintWriter(new OutputStreamWriter(out), true);
	}

	private String getEncoding(String s) {
		String retVal = null;
		String[] tokens = s.split(";");
		if (tokens.length == 2) {
			String token = tokens[1].trim();
			int idx = token.indexOf("=");
			if (idx != -1 && token.substring(0, idx).equals("charset")) {
				retVal = token.substring(idx + 1);
			}
		}
		return retVal;
	}

	public void setContentType(String c) {
		// podesi tip povratne datoteke i...
		headerContent.put("Content-Type", c);
		contentType = c;
		if (c != null) {
			String encoding = getEncoding(c);
			if (encoding != null) {
				try {
					writer = new PrintWriter(new OutputStreamWriter(
							outputStream, encoding), true);
				} catch (Exception ex) {
				}
			}
		}
		// posalji zaglavlje HTTP protokola ka klijentu
		sendHeader();
	}

	public void setHeader(String name, String value) {
		headerContent.put(name, value);
	}
	
	public void setResponseHeader(String r) {
		responseHeader = r;
	}

	public void setCookie(String c) {
		cookie = c;
		headerContent.put("Set-Cookie", c);
	}

	public void sendRedirect(String url) {
		responseHeader = "HTTP/1.0 302 Object moved";
		headerContent.put("Location", url);
		location = url;
		// posalji zaglavlje HTTP protokola ka klijentu
		sendHeader();
	}

	public void sendHeader() {
		// posaljemo HTTP zahlavlje
		if (responseHeader == null)
			responseHeader = "HTTP/1.0 200 OK";

		writer.print(responseHeader + "\r\n");

		System.out.println("HTTP RESPONSE:");
		System.out.println("---------------------------------------");
		System.out.println(responseHeader);
		for (String key : headerContent.keySet()) {
			writer.print(key + ": " + headerContent.get(key) + "\r\n");
			System.out.println(key + ": " + headerContent.get(key));
		}
		System.out.println("---------------------------------------");
		writer.print("\r\n");
		writer.flush();
	}

	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	public OutputStream getOutputStream() throws IOException {
		return outputStream;
	}

}