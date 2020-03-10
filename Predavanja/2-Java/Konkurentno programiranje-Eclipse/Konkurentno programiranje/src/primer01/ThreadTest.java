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
      /* Ako ovo stavimo, onda æe glavna nit da odspava, 
       * a za to vreme æe PrviThread da radi. 
       * Ako je pauza kratka, onda æe se niti pomešati. 
       * Ako je pauza dugaèka, niti æe se izvršavati jedna za drugom. 
       */
      //Thread.currentThread().sleep(100); 
      
      /* Ako ovo stavimo, onda æe glavna nit ovde saèekati da se PrviThread završi, 
       * pa æe tek onda nastaviti petlju.
       */
      //pt.join(); 
    }
    System.out.println("Threads started.");
  }

}