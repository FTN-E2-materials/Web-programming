package servlets;

import java.io.IOException;
import java.net.URLDecoder;
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
;

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
    	context.setAttribute("porucioci", new PoruciocDAO(contextPath));
    	
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
		String brojIzrade = request.getParameter("brojIzrade");
		brojIzrade = URLDecoder.decode(brojIzrade, "utf-8");
    	String imeNarucioca = request.getParameter("imeNarucioca");
    	String brojTelefona = request.getParameter("brojTelefona");
    	String nazivFoldera = request.getParameter("nazivFoldera");
    	String formatFotografija = request.getParameter("formatFotografija");
    	String cenaIzrade = request.getParameter("cenaIzrade");
    	
    	ServletContext context = getServletContext();
		PoruciocDAO poru = (PoruciocDAO) context.getAttribute("porucioci");
		
		ArrayList<Porucioc> porucioci = poru.findAll();
		Porucioc noviPorucioc = new Porucioc(brojIzrade,imeNarucioca,brojTelefona,nazivFoldera,formatFotografija,cenaIzrade, "UIZRADI");
		
		for (Porucioc porucioc : porucioci) {
			if(porucioc.brojIzrade.equals(brojIzrade)) {
				RequestDispatcher disp = request.getRequestDispatcher("/PoruciocPostoji.jsp");
		    	disp.forward(request, response);
		    	return;
			}
		}
		
		porucioci.add(noviPorucioc);
		context.setAttribute("porucioci", poru);

    	RequestDispatcher disp = request.getRequestDispatcher("/pregledPorucioca.jsp");
    	disp.forward(request, response);
	}

}
