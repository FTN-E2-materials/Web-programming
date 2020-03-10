package primer07;

import java.util.concurrent.*;

public class Consumer extends Thread {

  public Consumer(String id, BlockingQueue<Integer> buffer, int count) {
    this.buffer = buffer;
    this.count = count;
    this.id = id;
  }

  public void run() {
    for (int i = 0; i < count; i++) {
      try {
        // ako se ukljuci ovaj sleep, videce se zasto "ima"
        // count+1 a ne count poruka od producera
        // println ne garantuje redosled ispisivanja
        // zato posle prvih BUFFER_SIZE poruka
        // proizvodnja i potrosnja BUFER_SIZE+1 poruke
        // se ne ispisuju pravim redosledom
//        sleep(2000);
        System.out.println("Consumer " + id + " got: " + buffer.take());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private BlockingQueue<Integer> buffer;
  private int count;
  private String id;
}