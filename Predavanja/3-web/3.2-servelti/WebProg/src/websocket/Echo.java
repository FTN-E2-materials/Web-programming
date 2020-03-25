package websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/echoAnnotation")
public class Echo {

	Logger log = Logger.getLogger("Websockets endpoint");
	
	static List<Session> sessions = new ArrayList<Session>();
	Timer t = new Timer();

	public Echo() {
//		t.schedule(new TimerTask() {
//			@Override
//			public void run() {
//		        for (Session s : sessions) {
//		        	try {
//						s.getBasicRemote().sendText("od servera: " + System.currentTimeMillis());
//		    			log.info("Poslao poruku od servera na: " + s.getId());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//		        }
//			}
//		}, 5000, 5000); 
	}
	
	@OnOpen
	public void onOpen(Session session) {
		if (!sessions.contains(session)) {
			sessions.add(session);
			log.info("Dodao sesiju: " + session.getId() + " u endpoint-u: " + this.hashCode() + ", ukupno sesija: " + sessions.size());
		}
	}
	
	@OnMessage
	public void echoTextMessage(Session session, String msg, boolean last) {
		try {
			if (session.isOpen()) {
				log.info("Websocket endpoint: " + this.hashCode() + " primio: " + msg + " u sesiji: " + session.getId());
		        for (Session s : sessions) {
		        	if (!s.getId().equals(session.getId())) {
		        		s.getBasicRemote().sendText(msg, last);
		        	}
		        }
			}
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@OnClose
	public void close(Session session) {
		sessions.remove(session);
		log.info("Zatvorio: " + session.getId() + " u endpoint-u: " + this.hashCode());
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		sessions.remove(session);
		log.log(Level.SEVERE, "Gre�ka u sessiji: " + session.getId() + " u endpoint-u: " + this.hashCode() + ", tekst: " + t.getMessage());
		t.printStackTrace();
	}
}
