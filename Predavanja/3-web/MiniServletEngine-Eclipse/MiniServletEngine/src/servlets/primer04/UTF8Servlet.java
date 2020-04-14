package servlets.primer04;
import java.io.*;

import servletengine.*;

public class UTF8Servlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter pout = response.getWriter();
    pout.println("<html>");
    pout.println("<head>");
    pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
    pout.println("</head>");
    pout.println("<body>");
    try {
      pout.println("Ovo je stranica sa UTF-8 karakterima: \u0428 \u0429 ћирилица<br>");
    } catch(Exception ex) {
      pout.println(ex.getMessage());
    }
    pout.println("</body>");
    pout.println("</html>");
  }
}