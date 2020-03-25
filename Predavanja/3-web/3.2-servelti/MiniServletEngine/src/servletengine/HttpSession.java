package servletengine;

import java.util.*;

/**
 * Ova klasa reprezentuje sesiju korisnika.
 * 
 * @author Milan Vidakovic
 */
public class HttpSession {

	public HttpSession() {
		sessionId = null;
	}

	public HttpSession(String id) {
		sessionId = id;
	}

	/** Cuva id sesije. */
	private String sessionId;

	public void setId(String c) {
		sessionId = c;
	}

	public String getId() {
		return sessionId;
	}

	/**
	 * Asocijativna mapa objekata dodeljenih sesiji. Ako je potrebno da neki
	 * objekat prati sesiju, dodace se u ovu mapu. Kljuc je id sesije. Mapa je
	 * statièka da bi se iz svih zahteva moglo pristupiti dodeljenim objektima.
	 */
	private static HashMap<String, Object> sessionMap = new HashMap<String, Object>();

	public Object getAttribute(String key) {
		return sessionMap.get(sessionId + key);
	}

	public void setAttribute(String key, Object attr) {
		sessionMap.put(sessionId + key, attr);
	}

}