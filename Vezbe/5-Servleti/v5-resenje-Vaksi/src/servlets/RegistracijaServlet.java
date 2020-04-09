package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Korisnik;
import beans.RegistrovaniKorisnici;

/**
 * Servlet implementation class RegistracijaServlet
 */
@WebServlet("/RegistracijaServlet")
public class RegistracijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistracijaServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);		// bez poziva init() iz HttpServlet, getServletContext() vrati null
		
		ServletContext context = getServletContext();		
		if(context.getAttribute("regKorisnici") == null) {						// cuvam i prenosim referencu na RegistrovaneKorisnike
			context.setAttribute("regKorisnici", new RegistrovaniKorisnici());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw = response.getWriter();

		ServletContext context = getServletContext();
		RegistrovaniKorisnici regKorisnici = (RegistrovaniKorisnici) context.getAttribute("regKorisnici");		
		
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Registracija</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		Korisnik korisnik = new Korisnik();
		korisnik.setIme(request.getParameter("ime"));
		korisnik.setPrezime(request.getParameter("prezime"));
		korisnik.setKorisnickoIme(request.getParameter("korisnickoIme"));
		korisnik.setLozinka(request.getParameter("lozinka"));

		if (!regKorisnici.dodajKorisnika(korisnik)) {
			pw.println("<h1>Neuspesna registracija</h1>");
			pw.println("<p>Korisnik sa unetim korisnickim imenom vec postoji. Probajte sa drugim korisnickim imenom.</p>");
		} else {
			pw.println("<h1>Uspesna registracija</h1>");
			pw.println("<p>Uspsno ste se registrovali!</p>");
		}
		
		pw.println("<a href='index.html'> Nazad na pocetnu </a><br>");
		pw.println("<a href='SpisakServlet'>Prikazi sve registrovane korisnike</a>");
		
		pw.println("</body>");
		pw.println("</html>");
	}

}
