package servlets.primer02;
import java.io.IOException;
import java.io.PrintWriter;

import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

public class FormServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("</head>");
		pout.println("<body>");
		pout.println("Poslali ste ovo:" + request.getParameter("tekst"));
		pout.println("</body>");
		pout.println("</html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("</head>");
		pout.println("<body>");
		pout.println("Poslali ste ovo:" + request.getParameter("tekst"));
		pout.println("</body>");
		pout.println("</html>");
	}

}