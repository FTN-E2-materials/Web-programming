package primer06;

import java.util.concurrent.CountDownLatch;

public class WebBot implements Runnable {
  String address;
  CountDownLatch done;
  
  public WebBot(CountDownLatch done, String addr) { 
    address = addr; 
    this.done = done;
  }
  
  public void run() {
      processAddress(address);
      try {
        System.out.println("Finished.");
        done.countDown(); 
      } catch (Exception ex) { 
        ex.printStackTrace(); 
      }
  }
  private void processAddress(String addr) {
    System.out.println("Processing address: " + addr);
    try { Thread.currentThread().sleep((int)(Math.random() * 1000)); } catch (Exception ex) {ex.printStackTrace();}
  }
}
