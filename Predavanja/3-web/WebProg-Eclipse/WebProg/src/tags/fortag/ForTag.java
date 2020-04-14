package tags.fortag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.IterationTag;

public class ForTag extends BodyTagSupport {
  /**
   * 
   */
  private static final long serialVersionUID = -5954142606218079883L;
  int counter;

  public int doStartTag() {
    counter = from;
    // alternativni nacin smestanja trenutne vrednosti brojaca,
    // da bi se iz skriptleta moglo pristupiti vrednosti
    pageContext.setAttribute("forValue", new Integer(counter), PageContext.PAGE_SCOPE);
    return(EVAL_BODY_INCLUDE);
  }

  public int doAfterBody() {
    if (counter++ < to) {
      // alternativni nacin smestanja trenutne vrednosti brojaca,
      // da bi se iz skriptleta moglo pristupiti vrednosti
      pageContext.setAttribute("forValue", new Integer(counter), PageContext.PAGE_SCOPE);
      return IterationTag.EVAL_BODY_AGAIN;
    } else {
      return SKIP_BODY;
    }
  }

  private int from = 1;
  public void setFrom(String value) {
    try {
      this.from = Integer.parseInt(value);
    } catch (Exception ex) {
      this.from = 1;
    }
  }

  private int to = 1;
  public void setTo(String value) {
    try {
      this.to = Integer.parseInt(value);
    } catch (Exception ex) {
      this.to = 1;
    }
  }

  /** pageContext property setter method */
  public void setPageContext(PageContext aPageContext) {
System.out.println("Dodao pageContext");
    pageContext = aPageContext;
  }
 /** current page context */
  private PageContext pageContext;
}