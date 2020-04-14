package tags.iftag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/** Tag koji sluzi kao else deo if naredbe (ako uslov nije zadovoljen) */
public class ElseTag extends BodyTagSupport {

  private static final long serialVersionUID = -5627772813408817234L;

  public int doStartTag() throws JspTagException {
    IfTag parent = (IfTag)findAncestorWithClass(this, IfTag.class);
    if (parent == null)
      throw new JspTagException("else tag must be inside if tag!");
    else if (!parent.hasCondition())
      throw new JspTagException("condition tag must be defined before else tag!");
    return(EVAL_BODY_BUFFERED);
  }

  public int doAfterBody() {
    IfTag parent = (IfTag)findAncestorWithClass(this, IfTag.class);
    if (!parent.getCondition()) {
      try {
        BodyContent body = getBodyContent();
        JspWriter out = body.getEnclosingWriter();
        out.print(body.getString());
      } catch (IOException ex) {
        System.out.println("Error in ElseTag: " + ex.toString());
      }
    }
    return SKIP_BODY;
  }
}
