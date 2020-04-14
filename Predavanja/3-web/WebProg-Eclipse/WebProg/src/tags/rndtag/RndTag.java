package tags.rndtag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

public class RndTag extends TagSupport {

  private static final long serialVersionUID = -4265955439231097718L;
  protected int len = 50;
  public int doStartTag() {
    try {
      JspWriter out = pageContext.getOut();
      double rnd = Math.random();
      out.print(rnd * max);
    } catch(IOException ex) {
      System.out.println("Error generating random: " + ex);
    }
    return(SKIP_BODY);
  }

  private int max = 1;
  public void setMax(String value) {
    try {
      this.max = Integer.parseInt(value);
    } catch (Exception ex) {
      this.max = 1;
    }
  }

  public void release() {
System.out.println("The Rnd tag released.");
    max = 1;
  }
}