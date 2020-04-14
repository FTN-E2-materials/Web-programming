package servlets.primer01;
import java.io.IOException;
import java.io.PrintWriter;

import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("</head>");
		pout.println("<body>");
		pout.println("Hello World!");
		pout.println("<br>Klijent koji je pozvao ovaj servlet je: "
				+ request.getHeader("User-Agent"));
		pout.println("</body>");
		pout.println("</html>");
	}
}