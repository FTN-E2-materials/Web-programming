package primer02;

public class PrviThread extends Thread {

	/** ID niti */
	private int threadID;

	/** Brojac petlje unutar niti */
	private int counter;

	/**
	 * Ovaj objekat se prosleğuje svim nitima preko njega 
	 * æe se sinhronizovati.
	 */
	private Object objectLock;

	/**
	 * Konstruktor
	 * 
	 * @param threadID
	 *            Identifikator niti
	 */
	public PrviThread(int threadID, Object objectLock) {
		this.threadID = threadID;
		this.objectLock = objectLock;
		counter = 10000;
	}

	/**
	 * Nit: na svaki 1000-ti prolaz petlje ispisi poruku na konzolu.
	 */
	public void run() {
		synchronized (this.objectLock) {
			while (counter > 0) {
				if (counter % 1000 == 0)
					System.out.println("Thread[" + threadID + "]: " + (counter / 1000));
				counter--;
			}
		}
	}

}