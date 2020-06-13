package servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PacijentDAO;
import model.Pacijent;

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
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	ServletContext context = getServletContext();
    	String contextPath = context.getRealPath("");
    	// Dodajem pacijente u context kako bi servleti mogli da rade s njim
    	context.setAttribute("pacijenti", new PacijentDAO(contextPath));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest zahtev, HttpServletResponse odgovor) throws ServletException, IOException {
		RequestDispatcher usmerivac = zahtev.getRequestDispatcher("JSP/index.html");		// redirekcija na index html
		usmerivac.forward(zahtev, odgovor);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Name parametar iz registracije je parametar koji trazimo
		 */
		String brojZdravstvenog = request.getParameter("brojZdravstvenog");
		String imePacijenta = request.getParameter("imePacijenta");
		String prezimePacijenta = request.getParameter("prezimePacijenta");
		String datumRodjenja = request.getParameter("datumRodjenja");
		String polPacijenta = request.getParameter("polPacijenta");
		String zdravstveniStatus = request.getParameter("zdravstveniStatus");
		
		Pacijent pacijentZaDodavanje = new Pacijent(brojZdravstvenog, imePacijenta, prezimePacijenta, datumRodjenja, polPacijenta,zdravstveniStatus, false);
		
		ServletContext context = getServletContext();
		PacijentDAO pacijenti = (PacijentDAO) context.getAttribute("pacijenti");
		
		Boolean vecPostoji = false;
		for (Pacijent pacijent : pacijenti.findAll()) {
			if(pacijent.getBrojZdravstvenogOsiguranja().equals(pacijentZaDodavanje.getBrojZdravstvenogOsiguranja())) {
				vecPostoji = true; break;
			}
		}
		
		
		/**
		 * Ovo je samo u okviru zahteva, jer ako je pogresna prijava, treba samo da se vidi ova greska
		 */
		if(vecPostoji) {
			request.setAttribute("greska", "Izabrani pacijent je vec testiran na Covid19");
			doGet(request, response); // redirekcija
			return;
		}
		
		pacijenti.addPacijenta(pacijentZaDodavanje);
			
		// redirekcija na prikaz korisnika
		RequestDispatcher disp = request.getRequestDispatcher("/korisnici.jsp");
		disp.forward(request, response);
		
	}
	
	

}
