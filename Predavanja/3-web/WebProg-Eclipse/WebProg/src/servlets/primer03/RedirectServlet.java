package servlets.primer03;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: RedirectServlet
 * 
 */
public class RedirectServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = -8515377958318532049L;

	public RedirectServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ( request.getParameter("proba") == null ) {
			response.sendRedirect("index.html");
			return;		// da bi se izaslo iz metode odmah nakon redirekcije
		}
		else {
			response.setContentType("text/html");
			PrintWriter pout = response.getWriter();
			pout.println("<html>");
			pout.println("<head>");
			pout.println("</head>");
			pout.println("<body>");
			pout.println("Ovo je stranica koja se dobija ako je postavljen parametar <b>proba</b> na vrednost: "
							+ request.getParameter("proba") + "<br>");
			pout.println("Ovo je <a href=\"RedirectServlet\">link na ovaj isti servlet</a>, bez parametra, da bismo izazvali redirekciju.<br>");
			pout.println("</body>");
			pout.println("</html>");
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
