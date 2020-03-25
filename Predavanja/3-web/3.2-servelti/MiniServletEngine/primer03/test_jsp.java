import java.io.IOException;
import java.io.PrintWriter;

import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

public class test_jsp extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		System.out.println("JSP SERVLET");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
out.println("<html>");
out.println("<head>");
out.println("<title>JSP deklaracije</title>");
out.println("</head>");
out.println("<body>");
out.println("");
out.println("<h3>Primer JSP deklaracije</h3>");
out.println("");




out.println();
out.println("<p>");
out.println("Ovoj stranici je ukupno pristupano");
out.println("");out.println( ++hitCount );out.println(" puta.");
out.println("</p>");
out.println("<p>");
out.println("Slucajan broj od nula do 100: ");out.println( getRandom() );out.println(".");
out.println("</p>");
out.println("");
out.println("<p><a href=\"../jsp.html\">Nazad</a></p>");
out.println("");
out.println("</body>");
out.println("</html>");
	}
 int hitCount = 0; 
private int getRandom() {
      return (int)(Math.random()*100);
}


}
