package primer03;

public class Consumer extends Thread {
	private Buffer buffer;
	private int count;
	private int id;
  
	public Consumer(int id, Buffer buffer, int count) {
		this.buffer = buffer;
		this.count = count;
		this.id = id;
	}
  
  public void run() {
    for (int i = 0; i < count; i++)
      buffer.read();
  }
  
}