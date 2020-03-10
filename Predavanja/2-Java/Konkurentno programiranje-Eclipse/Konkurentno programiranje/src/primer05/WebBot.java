package primer05;

import java.util.concurrent.CyclicBarrier;

public class WebBot implements Runnable {
  String address;
  CyclicBarrier barrier;
  
  public WebBot(CyclicBarrier barrier, String addr) { 
    address = addr; 
    this.barrier = barrier;
  }
  
  public void run() {
      processAddress(address);
      try {
        System.out.println("Waiting for all of us to finish...");
        barrier.await(); 
        System.out.println("Finished waiting.");
      } catch (Exception ex) { 
        ex.printStackTrace(); 
      }
  }
  private void processAddress(String addr) {
    System.out.println("Processing address: " + addr);
    try { Thread.currentThread().sleep((int)(Math.random() * 1000)); } catch (Exception ex) {ex.printStackTrace();}
  }
}
