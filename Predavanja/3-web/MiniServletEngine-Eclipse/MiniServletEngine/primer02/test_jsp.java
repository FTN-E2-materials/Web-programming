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
out.println("<title>JSP skriptleti</title>");
out.println("</head>");
out.println("<body>");
out.println("");
out.println("<h3>Primer JSP skriptleta</h3>");
out.println("");
out.println("<table border=1>");
out.println("<tr>");
out.println("  <th>R.br.</th>");
out.println("  <th>Ime</th>");
out.println("</tr>");

 String names[] = { "Bata", "Pera", "Mika", "Laza", "Sima" };
for (int i = 0; i < names.length; i++) {

out.println();
out.println("<tr>");
out.println("  <td>");out.println( i );out.println("</td>");
out.println("  <td>");out.println( names[i] );out.println("</td>");
out.println("</tr>");
out.println("");} out.println("");
out.println("</table>");
out.println("");
out.println("<p><a href=\"../jsp.html\">Nazad</a></p>");
out.println("");
out.println("</body>");
out.println("</html>");
	}
}
