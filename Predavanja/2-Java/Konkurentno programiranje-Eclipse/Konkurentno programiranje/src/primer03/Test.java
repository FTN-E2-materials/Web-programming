package primer03;

public class Test {

  public static final int BUFFER_SIZE = 5;
  public static final int PRODUCE_COUNT = 10;
  public static final int CONSUMER_COUNT = 2;

  public static void main(String[] args) {
    Buffer buffer = new Buffer(BUFFER_SIZE);
    Producer p = new Producer(buffer, PRODUCE_COUNT * CONSUMER_COUNT);
    p.start();
    for (int i = 0; i < CONSUMER_COUNT; i++)
      new Consumer(i+1, buffer, PRODUCE_COUNT).start();
  }
}