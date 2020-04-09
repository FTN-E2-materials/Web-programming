package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.RegistrovaniKorisnici;

/**
 * Servlet implementation class PretragaServlet
 */
@WebServlet("/PretragaServlet")
public class PretragaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PretragaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Rezultat pretrage</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		ServletContext context = getServletContext();
		RegistrovaniKorisnici regKorisnici = (RegistrovaniKorisnici) context.getAttribute("regKorisnici");
		
		if(regKorisnici == null) {
			pw.println("<h2>Neuspesna pretraga</h2>");
			pw.println("<p>Jos uvek nema ni jednog registrovanog korisnika.</p>");
		} else {
			String korisnickoIme = request.getParameter("korisnickoIme");
			if(regKorisnici.getRegKorisnici().containsKey(korisnickoIme)) {
				pw.println("<h2>Uspesna pretraga</h2>");
				pw.println("<p>Postoji korisnik sa korisnickim imenom: <b>" + korisnickoIme + "</b>.</p>");
			} else {
				pw.println("<h2>Neuspesna pretraga</h2>");
				pw.println("<p>Ne postoji korisnik sa korisnickim imenom: <b>" + korisnickoIme + "</b>.</p>");
			}
		}
		
		pw.println("<a href='index.html'> Nazad na pocetnu </a><br>");
		pw.println("<a href='SpisakServlet'>Prikazi sve registrovane korisnike</a>");
		
		pw.println("</body>");
		pw.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
