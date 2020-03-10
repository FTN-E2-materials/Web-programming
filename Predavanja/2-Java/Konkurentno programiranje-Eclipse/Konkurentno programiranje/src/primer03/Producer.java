package primer03;

public class Producer extends Thread {
  private Buffer buffer;
  private int count;
  
  public Producer(Buffer buffer, int count) {
    this.buffer = buffer;
    this.count = count;
  }
  
  public void run() {
    for (int i = 0; i < count; i++)
      buffer.write((int)Math.round(Math.random() * 100));
  }
  

  
}