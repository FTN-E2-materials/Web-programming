package servlets.primer03;
import java.io.IOException;
import java.io.PrintWriter;

import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

public class RedirectServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (request.getParameter("proba") == null) {
      response.sendRedirect("index.html");
    } else {
      response.setContentType("text/html");
      PrintWriter pout = response.getWriter();
      pout.println("<html>");
      pout.println("<head>");
      pout.println("</head>");
      pout.println("<body>");
      pout.println("Ovo je stranica koja se dobija ako je postavljen parametar <b>proba</b> na vrednost: " +
                   request.getParameter("proba") + "<br>");
      pout.println("Ovo je <a href=\"RedirectServlet\">link na ovaj isti servlet</a>, bez parametra, da bismo izazvali redirekciju.<br>");
      pout.println("</body>");
      pout.println("</html>");
    }
  }
}