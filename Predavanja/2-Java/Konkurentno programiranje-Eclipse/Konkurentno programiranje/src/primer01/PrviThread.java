package primer01;

public class PrviThread extends Thread {

	/** ID niti */
	private int threadID;
	
	/** Brojac petlje unutar niti */
	private int counter;

	/**
	 * Konstruktor
	 * 
	 * @param threadID
	 *            Identifikator niti
	 */
	public PrviThread(int threadID) {
		this.threadID = threadID;
		counter = 10000;
	}

	/**
	 * Nit: na svaki 1000-ti prolaz petlje ispisi poruku na konzolu.
	 */
	@Override
	public void run() {
		while (counter > 0) {
			if (counter % 1000 == 0)
				System.out.println("Thread[" + threadID + "]: " + (counter / 1000));
			counter--;
		}
	}

}