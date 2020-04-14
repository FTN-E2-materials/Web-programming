package servlets.primer04;
import java.io.*;

import servletengine.*;

public class CP1250Servlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html; charset=Windows-1250");
    PrintWriter pout = response.getWriter();
    pout.println("<html>");
    pout.println("<head>");
    pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=Windows-1250\">");
    pout.println("</head>");
    pout.println("<body>");
    try {
      pout.println("Ovo je stranica sa CP1250 karakterima: šđčćž<br>");
    } catch(Exception ex) {
      pout.println(ex.getMessage());
    }
    pout.println("</body>");
    pout.println("</html>");
  }
}