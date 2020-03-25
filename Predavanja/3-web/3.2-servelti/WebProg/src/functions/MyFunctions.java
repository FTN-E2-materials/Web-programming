package functions;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspTagException;

public class MyFunctions {
	@SuppressWarnings("rawtypes")
	public static int length(Object obj) throws JspTagException {
		if (obj == null)
			return 0;
		if (obj instanceof String)
			return ((String) obj).length();
		if (obj instanceof Collection)
			return ((Collection) obj).size();
		if (obj instanceof Map)
			return ((Map) obj).size();

		int count = 0;
		if (obj instanceof Iterator) {
			Iterator iter = (Iterator) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}

		if (obj instanceof Enumeration) {
			Enumeration en = (Enumeration) obj;
			count = 0;
			while (en.hasMoreElements()) {
				count++;
				en.nextElement();
			}
			return count;
		}

		try {
			count = Array.getLength(obj);
			return count;
		} catch (IllegalArgumentException ex) {
		}
		throw new JspTagException("Unsupported type");
	}
}