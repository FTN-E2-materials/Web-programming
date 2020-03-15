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
      /* Ako ovo stavimo, onda ce glavna nit da odspava, 
       * a za to vreme ce PrviThread da radi. 
       * Ako je pauza kratka, onda ce se niti pomesati. 
       * Ako je pauza dugacka, niti ce se izvrsavati jedna za drugom. 
       */
      Thread.currentThread().sleep(1); 
      
      /* Ako ovo stavimo, onda ce glavna nit ovde sacekati da se PrviThread zavrsi, 
       * pa ce tek onda nastaviti petlju.
       */
      //pt.join(); 
    }
    System.out.println("Threads started.");
  }

}