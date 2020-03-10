package primer04;


public class Test {

	public static final int MAX_COUNT = 10;
	public static void main(String[] args) {
		ResourcePool pool = new ResourcePool(); 
		Consumer c1 = new Consumer(pool, MAX_COUNT/2);
		Consumer c2 = new Consumer(pool, MAX_COUNT/2);
		c1.start();
		c2.start();
	}

}
