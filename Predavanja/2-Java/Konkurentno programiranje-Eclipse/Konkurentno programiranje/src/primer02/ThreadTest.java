package primer02;

public class ThreadTest {

	/** Broj niti koje ce se pokrenuti */
	public static final int THREAD_COUNT = 10;

	/**
	 * Pokrece sve niti i zavrsava sa radom
	 * 
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Object objectLock = new Object();
		for (int i = 0; i < THREAD_COUNT; i++) {
			PrviThread pt = new PrviThread(i, objectLock);
			pt.start();
		}
		System.out.println("Threads started.");
	}

}