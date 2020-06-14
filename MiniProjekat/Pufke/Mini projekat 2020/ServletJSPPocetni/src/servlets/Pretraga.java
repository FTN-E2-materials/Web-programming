package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PoruciocDAO;

/**
 * Servlet implementation class Pretraga
 */
@WebServlet("/Pretraga")
public class Pretraga extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pretraga() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nazivFoldera = request.getParameter("pretraga_input");
		ServletContext context = getServletContext();
		PoruciocDAO poru = (PoruciocDAO) context.getAttribute("porucioci");
		poru.pretragaPorucioca(nazivFoldera);
		RequestDispatcher disp = request.getRequestDispatcher("/PretragaPorucioci.jsp");
    	disp.forward(request, response);
		doGet(request, response);
		
	}

}
