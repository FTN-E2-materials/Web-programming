package primer08;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementacija kruznog bafera
 */
public class Buffer {
	/** Velicina kruznog bafera */
	private int size;
	/** Sadrzaj kruznog bafera */
	private int[] data;
	/** Naredna lokacija za citanje */
	private int readPos;
	/** Naredna lokacija za pisanje */
	private int writePos;

	private ReentrantLock lock;
	private Condition readCondition;
	private Condition writeCondition;

	/**
	 * Konstruktor
	 * 
	 * @param size
	 *            Velicina kruznog bafera
	 */
	public Buffer(int size) {
		this.size = size;
		data = new int[size];
		readPos = 0;
		writePos = 0;
		lock = new ReentrantLock(true);
		readCondition = lock.newCondition();
		writeCondition = lock.newCondition();
	}

	/**
	 * Upisuje novu vrednost u bafer.
	 * 
	 * @param value
	 *            Nova vrednost koja se upisuje
	 */
	public void write(int value) {
		lock.lock();
		try {
			while (isFull()) {
				System.out.println("Producer FULL, waiting to write...");
				writeCondition.await();
			}
			data[writePos] = value;
			if (++writePos == size)
				writePos = 0;
			readCondition.signal();
			System.out.println("Producer wrote: " + value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Cita narednu vrednost iz bafera.
	 * 
	 * @return Procitana vrednost
	 */
	public int read(int id) {
		lock.lock();
		int retVal;
		try {

			while (isEmpty()) {
				System.out.println("Consumer " + id + " EMPTY, waiting to read...");
				readCondition.await();
			}
			retVal = data[readPos];
			if (++readPos == size)
				readPos = 0;
			System.out.println("Consumer " + id + " read: " + retVal);
			writeCondition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
			retVal = -1;
		} finally {
			lock.unlock();
		}

		return retVal;
	}

	/**
	 * Ispituje da li je bafer prazan.
	 * 
	 * @return Vraca <code>true</code> ako je bafer prazan
	 */
	public boolean isEmpty() {
		return readPos == writePos;
	}

	/**
	 * Ispituje da li je bafer pun.
	 * 
	 * @return Vraca <code>true</code> ako je bafer pun
	 */
	public boolean isFull() {
		return readPos == (writePos + 1) % size;
	}

}
