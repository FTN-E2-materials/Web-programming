package servlets.primer04;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: CP1250Servlet
 * 
 */
public class CP1250Servlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 452018400862176168L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CP1250Servlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// !!! PAZNJA !!! Obratiti paznju na setovanje charset-a na UTF-8
		// ovim se govori da se podaci koji se pisu u stream ka klijentu enkodiraju CP-1250 kodnom stranom
		response.setContentType("text/html; charset=Windows-1250");
		
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		
		// !!! Paznja !!!
		// Ovim se saopstava http klijentu (browseru) kako da interpretira podatke iz stream-a 
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=Windows-1250\">");
		pout.println("</head>");
		pout.println("<body>");
		
		try {
			pout.println("Ovo je stranica sa CP1250 karakterima: šðèæž\u0161\u0107<br>");
		} catch (Exception ex) {
			pout.println(ex.getMessage());
		}
		pout.println("</body>");
		pout.println("</html>");
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
