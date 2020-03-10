package primer08;

public class Producer extends Thread {
	private Buffer buffer;
	private int count;

	public Producer(Buffer buffer, int count) {
		this.buffer = buffer;
		this.count = count;
	}

	public void run() {
		for (int i = 0; i < count; i++) {
			int broj = (int) Math.round(Math.random() * 100);
			buffer.write(broj);
			System.out.println("Producer has " + (count - i - 1) + " writes to do.");
		}
	}

}