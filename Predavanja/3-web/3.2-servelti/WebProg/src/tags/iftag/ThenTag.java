package tags.iftag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/** Tag koji sluzi kao then deo if naredbe (ako je uslov zadovoljen) */
public class ThenTag extends BodyTagSupport {

  private static final long serialVersionUID = 103910987307070460L;

  public int doStartTag() throws JspTagException {
    IfTag parent = (IfTag)findAncestorWithClass(this, IfTag.class);
    if (parent == null)
      throw new JspTagException("then tag must be inside if tag!");
    else if (!parent.hasCondition())
      throw new JspTagException("condition tag must be defined before then tag!");
    return(EVAL_BODY_BUFFERED);
  }

  public int doAfterBody() {
    IfTag parent = (IfTag)findAncestorWithClass(this, IfTag.class);
    if (parent.getCondition()) {
      try {
        BodyContent body = getBodyContent();
        JspWriter out = body.getEnclosingWriter();
        out.print(body.getString());
      } catch (IOException ex) {
        System.out.println("Error in ThenTag: " + ex.toString());
      }
    }
    return SKIP_BODY;
  }

}
