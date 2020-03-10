package primer06;

import java.util.concurrent.CountDownLatch;

public class InternetSearch {
  private int webBotCount;
  CountDownLatch done;   
  
  public InternetSearch(String[] addrs) {
    webBotCount = addrs.length;
    done = new CountDownLatch(webBotCount);   
    for (int i = 0; i < webBotCount; ++i) 
      new Thread(new WebBot(done, addrs[i])).start();
    
    try {
      done.await();      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println("InternetSearch finished.");
  }
  
  public static void main(String[] args) {
    String[] addrs = { "www.abc.com", "www.abd.com", "www.abe.com", "www.abf.com"};
    new InternetSearch(addrs);                       
  }
 }