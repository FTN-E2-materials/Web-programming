package ws;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@WebSocket
public class WsHandler extends WebSocketHandler {

	private static final Queue<Session> allSessions = new ConcurrentLinkedQueue<>();
	
	@OnWebSocketConnect
	public void connected(Session session) {
		allSessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		allSessions.remove(session);
	}

	@OnWebSocketError
	public void error(Session session, Throwable t) {
		allSessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println("Got: " + message); // Print message
		postMessage(message, session);
	}
	
	public static void postMessage(String text, Session sess) {
		for (Session s : allSessions) {
			try {
				if (s.hashCode() != sess.hashCode())
					s.getRemote().sendString(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.getPolicy().setIdleTimeout(1000*60*60);
	}
	

}
