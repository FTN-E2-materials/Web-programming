package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Pacijent;
import dao.PacijentDAO;

/**
 * Servlet implementation class IzmenaPacijenta
 */
@WebServlet("/IzmenaPacijenta")
public class IzmenaPacijenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IzmenaPacijenta() {
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
		ServletContext context = getServletContext();
		PacijentDAO products = (PacijentDAO) context.getAttribute("pacijenti");
		
		ArrayList<Pacijent> pacijenti = products.findAll();
		

		String brZdravstvenogOsig= request.getParameter("brZdravstvenogOsig");
		 
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.getBrZdravstvenogOsig().equals(brZdravstvenogOsig)) {
				pacijent.setZdravstveniStatus("ZARAZEN");
			}
		}
    	
		
    	RequestDispatcher disp = request.getRequestDispatcher("/pregledPacijenata.jsp");
    	disp.forward(request, response);
	}

	

}
