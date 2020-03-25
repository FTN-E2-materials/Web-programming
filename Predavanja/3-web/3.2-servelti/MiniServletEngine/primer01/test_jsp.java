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
out.println("<title>JSP izrazi</title>");
out.println("</head>");
out.println("<body>");
out.println("");
out.println("<h3>Primeri JSP izraza</h3>");
out.println("<p>");
out.println("Danasnji datum: ");out.println( new java.util.Date() );out.println("");
out.println("</p>");
out.println("");
out.println("<p>Vas racunar: ");out.println( request.getRemoteHost() );out.println("");
out.println("</p>");
out.println("<p><a href=\"../jsp.html\">Nazad</a></p>");
out.println("</body>");
out.println("</html>");
	}
}
