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

import beans.Porucioc;
import dao.PoruciocDAO;



/**
 * Servlet implementation class IzmenaPacijenta
 */
@WebServlet("/IzmenaStatusa")
public class IzmenaStatusa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IzmenaStatusa() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException{
    	super.init();
    	ServletContext context = getServletContext();
    	String contextPath = context.getRealPath("");
    	context.setAttribute("porucioci", new PoruciocDAO(contextPath));
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext();
		PoruciocDAO products = (PoruciocDAO) context.getAttribute("porucioci");
		
		ArrayList<Porucioc> porucioci = products.findAll();
		

		String brojIzrade= request.getParameter("brojIzrade");
		 
		for (Porucioc pacijent : porucioci) {
			if(pacijent.getBrojIzrade().equals(brojIzrade)) {
				pacijent.setStatusIzrade("SPREMNEZAPREDAJU");
			}
		}
    	
		
    	RequestDispatcher disp = request.getRequestDispatcher("/pregledPorucioca.jsp");
    	disp.forward(request, response);
	}

	

}
