package primer03;

/** Implementacija kruznog bafera
  */
public class Buffer {

  /** Konstruktor
    * @param size Velicina kruznog bafera
    */
  public Buffer(int size) {
    this.size = size;
    data = new int[size];
    readPos = 0;
    writePos = 0;
  }
  
  /** Upisuje novu vrednost u bafer.
    * @param value Nova vrednost koja se upisuje
    */
  public synchronized void write(int value) {
    while (isFull()) {
      System.out.println("Waiting to write...");
      try { wait(); } catch (Exception ex) { ex.printStackTrace(); }
    }
    data[writePos] = value;
    if (++writePos == size)
      writePos = 0;
    notify();
    System.out.println("Written: "+value);
  }
  
  /** Cita narednu vrednost iz bafera.
    * @return Procitana vrednost
    */
  public synchronized int read() {
	/*
	 * Bolje je da ispod piše while (isEmpty()), nego if (isEmpty()).
	 * U sluèaju da imamo više consumera, postoji moguænost da æe više
	 * njih biti u wait stanju. Onda notify() sa dna ove funkcije neæe 
	 * probuditi producera (koji najverovatnije ni nije u wait stanju), 
	 * veæ æe probuditi sledeæeg consumera, a bafer je još uvek prazan.
	 * Ovako, svaki consumer kada se probudi iz wait stanja, prvo
	 * proveri da li je bafer i dalje prazan. Ako jeste, onda opet ode 
	 * u wait. 
	 */
    while (isEmpty()) {
      System.out.println("Waiting to read...");
      try { wait(); } catch (Exception ex) { ex.printStackTrace(); }
    }
    int retVal = data[readPos];
    if (++readPos == size)
      readPos = 0;
    System.out.println("Read: "+retVal);
    notify();
    return retVal;
  }
  
  /** Ispituje da li je bafer prazan.
    * @return Vraca <code>true</code> ako je bafer prazan
    */
  public synchronized boolean isEmpty() {
    return readPos == writePos;
  }
  
  /** Ispituje da li je bafer pun.
    * @return Vraca <code>true</code> ako je bafer pun
    */
  public synchronized boolean isFull() {
    return readPos == (writePos + 1) % size;
  }
  
  /** Velicina kruznog bafera */
  private int size;
  /** Sadrzaj kruznog bafera */
  private int[] data;
  /** Naredna lokacija za citanje */
  private int readPos;
  /** Naredna lokacija za pisanje */
  private int writePos;
}
