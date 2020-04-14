package servlets.primer02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: FormServlet
 * 
 */
public class FormServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 6733195221530107303L;

	public FormServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter pout = response.getWriter();
		
		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta httpequiv=\"Content-Type\" value=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");
		String param = request.getParameter("tekst");
		pout.println("Poslali ste ovo:" + param);
		pout.println("</body>");
		pout.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
