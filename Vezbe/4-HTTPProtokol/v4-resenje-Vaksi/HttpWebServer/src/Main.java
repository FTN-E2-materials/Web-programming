import java.io.IOException;

/**
 * 
 * Klasa main u kojoj vrsim pokretanje servera, 
 * moje http aplikacije.
 * 
 * @author Vaxi
 *
 */
public class Main
{	
    public static void main(String args[])
            throws IOException
    {
        Server server = new Server(80);
        server.run();
    }
    
}
