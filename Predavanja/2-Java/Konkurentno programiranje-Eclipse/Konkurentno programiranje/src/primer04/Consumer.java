package primer04;


public class Consumer extends Thread {

	private ResourcePool pool;
	private int count;

	public Consumer(ResourcePool pool, int count) {
		this.pool = pool;
		this.count = count;
	}

	@Override
	public void run() {
		for (int i = 0; i < count; i++) {
			System.out.println(i + ". Fetching object from pool.");
			try {
				System.out.println(i + ". Fetched object from pool: " + pool.getItem().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
