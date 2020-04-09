package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Korisnik;
import beans.RegistrovaniKorisnici;

/**
 * Servlet implementation class SpisakServlet
 */
@WebServlet("/SpisakServlet")
public class SpisakServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SpisakServlet() {
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
		pw.println("<title>Registrovani</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h2>Spisak registrovanih korisnika</h2>");

		ServletContext context = getServletContext();
		RegistrovaniKorisnici regKorisnici = (RegistrovaniKorisnici) context.getAttribute("regKorisnici");

		if (regKorisnici == null) {
			pw.println("<p>Jos uvek nema ni jednog registrovanog korisnika.</p>");
		} else {
			pw.println("<table border='1'>");
			pw.println("<tr> <th>Ime</th> <th>Prezime</th> <th>Korisnicko ime</th> <th> Brisanje </th> </tr>");

			for (Korisnik k : regKorisnici.getRegKorisnici().values()) {
				pw.println("<tr>");
				pw.println("<td>" + k.getIme() + "</td>");
				pw.println("<td>" + k.getPrezime() + "</td>");
				pw.println("<td>" + k.getKorisnickoIme() + "</td>");
				pw.println("<td> <a href='UkloniServlet?korisnickoIme=" + k.getKorisnickoIme() + "'>Ukloni</a> </td>");
				// na ovaj nacin ce UkloniServlet preko doGet() uzeti ovo korisnicko ime
				pw.println("</tr>");
			}

			pw.println("</table>");
		}

		pw.println("<a href='index.html'> Nazad na pocetnu </a><br>");
		
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
