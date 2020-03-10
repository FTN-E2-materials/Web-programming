package primer01;

public class ThreadTest {

  /** Broj niti koje ce se pokrenuti */
  public static final int THREAD_COUNT = 10;

  /** Pokrece sve niti i zavrsava sa radom 
 * @throws InterruptedException */
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < THREAD_COUNT; i++) {
      PrviThread pt = new PrviThread(i);
      pt.start();
      /* Ako ovo stavimo, onda �e glavna nit da odspava, 
       * a za to vreme �e PrviThread da radi. 
       * Ako je pauza kratka, onda �e se niti pome�ati. 
       * Ako je pauza duga�ka, niti �e se izvr�avati jedna za drugom. 
       */
      //Thread.currentThread().sleep(100); 
      
      /* Ako ovo stavimo, onda �e glavna nit ovde sa�ekati da se PrviThread zavr�i, 
       * pa �e tek onda nastaviti petlju.
       */
      //pt.join(); 
    }
    System.out.println("Threads started.");
  }

}