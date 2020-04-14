package tags.fancytag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

public class FancyTag extends TagSupport {

  private static final long serialVersionUID = 8828945110438837614L;

  public int doStartTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print("<font color=\"red\">");
    } catch(IOException ex) {
      System.out.println("Error working with fancy tag: " + ex);
    }

    return(EVAL_BODY_INCLUDE);
  }

  public int doEndTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print("</font>");
    } catch(IOException ex) {
      System.out.println("Error working with fancy tag: " + ex);
    }

    return(EVAL_PAGE);
  }
}