package http;

import java.io.IOException;

/**
 * 
 * Pokretacka klasa naseg servera,
 * sluzi za testiranje aplikacije i podesavanje
 * na kom portu ce da trci nasa app.
 * 
 * @author Vaxi
 *
 */
public class Pokretac {

	public static void main(String[] args) throws IOException {
		int port = 80;// SVUDA SAM DALJE KORISTIO 80, tako da u slucaju promene
		// porta, navigacija u html fajlovima se onda mora menjati !!!
		// kao i linkovanje, jer je svuda tipa localhost:80/bla bla
		Server server = new Server(port);
        server.run();
        

	}

}
