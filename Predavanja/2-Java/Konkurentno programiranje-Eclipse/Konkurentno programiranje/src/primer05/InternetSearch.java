package primer05;

import java.util.concurrent.CyclicBarrier;

public class InternetSearch {
  private int webBotCount;
  private CyclicBarrier barrier;
   
  
  public InternetSearch(String[] addrs) {
    webBotCount = addrs.length;
    barrier = new CyclicBarrier(webBotCount, 
                                new Runnable() {
                                  public void run() { 
                                    System.out.println("InternetSearch finished.");
                                  }
                                });
    for (int i = 0; i < webBotCount; ++i) 
      new Thread(new WebBot(barrier, addrs[i])).start();
  }
  
  public static void main(String[] args) {
    String[] addrs = { "www.abc.com", "www.abd.com", "www.abe.com", "www.abf.com"};
    new InternetSearch(addrs);                       
  }
 }