package servleti;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Klasa koja sluzi za promenu stanja izabranog pacijenta
 * iz bilo kog stanja u stanje BEZ_SIMPTOMA
 */
@WebServlet("/PromeniStatusServlet")
public class PromeniStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromeniStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EVO ME U PROMENI STATUS SERVLET");
		String brojZdravstvenog = request.getParameter("brojZdravstvenog");
		ServletContext context = getServletContext();
		
		PacijentDAO pacijentiDAO = (PacijentDAO) context.getAttribute("pacijenti");
		Pacijent pacijent = pacijentiDAO.find(brojZdravstvenog);
		pacijent.setRezultatTesta(true);
		pacijent.setZdravstveniStatus("BEZ_SIMPTOMA");
		
		// redirekcija na prikaz korisnika
		RequestDispatcher disp = request.getRequestDispatcher("/korisnici.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
