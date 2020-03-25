package servlets.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.session.SessionCounter;

/**
 * Servlet implementation class for Servlet: SessionTestServlet
 * 
 */
public class SessionTestServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 647174905764886727L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SessionTestServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Session Counter Test</title></head>");
		out.println("<body>");

		// uzimanje reference na objekat koji reprezentuje http sesiju
		HttpSession session = request.getSession();
		
		// podaci o sesiji
		out.println("<b>Sesija ID:" + session.getId() + "</b>");
		
		/*
		 * Probamo da pokupimo brojac pristupa u ovoj sesiji (koji je dodeljen ovoj sesiji).
		 * Ukoliko je objekat != od null, sledi da je objekat vec dodat sesiji, inace,
		 * kreira se novi objekat i dodaje se sesiji.
		 * Kljuc pod kojim se objekat dodaje sesiji (u ovom primeru) je "brojac".
		 */
		SessionCounter sc = (SessionCounter) session.getAttribute("brojac");
		if ( sc != null ) {
			sc.inc();
			out.println(", ukupno pristupa:" + sc.getCount() + ".<br>");
		}
		else {
			out.println(", prvi pristup.<br>");
			sc = new SessionCounter();
			sc.inc();
			session.setAttribute("brojac", sc);
		}
		out.println("</body></html>");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
