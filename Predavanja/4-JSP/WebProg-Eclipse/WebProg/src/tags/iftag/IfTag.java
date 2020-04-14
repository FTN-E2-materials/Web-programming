package tags.iftag;

import javax.servlet.jsp.tagext.TagSupport;

/** Osnovni tag koji sluzi za if-then-else konstrukcije */
public class IfTag extends TagSupport {

  private static final long serialVersionUID = 7990863098708970332L;
  public int doStartTag() {
    return EVAL_BODY_INCLUDE;
  }

  public void setCondition(boolean condition) {
    this.condition = condition;
    hasCondition = true;
  }
  public boolean getCondition() {
    return condition;
  }

  public void setHasCondition(boolean flag) {
    this.hasCondition = flag;
  }
  public boolean hasCondition() {
    return hasCondition;
  }

  private boolean condition;
  private boolean hasCondition = false;
}