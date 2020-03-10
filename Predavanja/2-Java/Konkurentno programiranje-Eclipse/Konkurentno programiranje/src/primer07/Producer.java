package primer07;

import java.util.concurrent.*;

public class Producer extends Thread {

  public Producer(BlockingQueue<Integer> buffer, int count) {
    this.buffer = buffer;
    this.count = count;
  }

  public void run() {
    for (int i = 0; i < count; i++) {
      int broj = (int)Math.round(Math.random() * 100);
      try {
        buffer.put(broj);
        System.out.println("Producer puts: " + broj);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private BlockingQueue<Integer> buffer;
  private int count;
}