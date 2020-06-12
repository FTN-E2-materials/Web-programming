package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Pacijent;
import dao.PacijentDAO;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException{
    	super.init();
    	ServletContext context = getServletContext();
    	String contextPath = context.getRealPath("");
    	context.setAttribute("pacijenti", new PacijentDAO(contextPath));
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Hello world");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String brZdravstvenogOsig = request.getParameter("brojZdravstvenogOsiguranja");
    	String ime = request.getParameter("imePacijenta");
    	String prezime = request.getParameter("prezimePacijenta");
    	String datumRodjenja = request.getParameter("datumRodjenjaPacijenta");
    	String pol = request.getParameter("pol");
    	String zdravstveniStatus = request.getParameter("zdravstveniStatus");
    	
    	ServletContext context = getServletContext();
		PacijentDAO products = (PacijentDAO) context.getAttribute("pacijenti");
		
		ArrayList<Pacijent> pacijenti = products.findAll();
		Pacijent noviPacijent = new Pacijent(brZdravstvenogOsig,ime,prezime,datumRodjenja,pol,zdravstveniStatus);
		pacijenti.add(noviPacijent);
		 
		 
		for (Pacijent pacijent : pacijenti) {
			response.getWriter().append(pacijent.getBrZdravstvenogOsig());
		}
    	response.getWriter().append(pol);
		
    	RequestDispatcher disp = request.getRequestDispatcher("/pregledPacijenata.jsp");
    	disp.forward(request, response);
	}

}
