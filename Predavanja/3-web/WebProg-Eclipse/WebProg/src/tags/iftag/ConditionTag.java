package tags.iftag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/** Tag koji sluzi za definisanje uslova if naredbe */
public class ConditionTag extends BodyTagSupport {

  private static final long serialVersionUID = 4743529304334457469L;

  public int doStartTag() throws JspTagException {
    IfTag parent = (IfTag)findAncestorWithClass(this, IfTag.class);
    if (parent == null)
      throw new JspTagException("condition tag must be inside if tag!");
    return(EVAL_BODY_BUFFERED);
  }

  public int doAfterBody() {
    IfTag parent = (IfTag)findAncestorWithClass(this, IfTag.class);
    String body = getBodyContent().getString();
    if (body.trim().equals("true"))
      parent.setCondition(true);
    else
      parent.setCondition(false);
    return SKIP_BODY;
  }
}