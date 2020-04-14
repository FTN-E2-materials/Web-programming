package servlets.session;

import java.io.IOException;
import java.io.PrintWriter;

import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;
import servletengine.HttpSession;
import beans.session.SessionCounter;

public class SessionTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><head><title>Session Counter Test</title></head>");
		out.println("<body>");

		HttpSession session = req.getSession();
		// podaci o sesiji
		out.println("<b>Sesija ID:" + session.getId() + "</b>");
		// probamo da pokupimo brojac pristupa u ovoj sesiji (koji je dodeljen
		// ovoj sesiji)
		SessionCounter sc = (SessionCounter) session.getAttribute("brojac");
		if (sc == null) {
			out.println(", prvi pristup.<br>");
			sc = new SessionCounter();
			session.setAttribute("brojac", sc);

		} 
		sc.inc();
		out.println(", ukupno pristupa:" + sc.getCount() + ".<br>");
		out.println("</body></html>");
	}
}
