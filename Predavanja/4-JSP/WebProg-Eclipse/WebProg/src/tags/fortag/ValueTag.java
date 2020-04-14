package tags.fortag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

public class ValueTag extends BodyTagSupport {

  private static final long serialVersionUID = -2326537207432461199L;

  public int doStartTag() throws JspTagException {
    ForTag parent = (ForTag)findAncestorWithClass(this, ForTag.class);
    if (parent == null)
      throw new JspTagException("value tag must be inside for tag!");
    try {
      JspWriter out = pageContext.getOut();
      out.print(parent.counter);
    } catch(IOException ex) {
      System.out.println("Error dumping value: " + ex);
    }
    return(SKIP_BODY);
  }
}