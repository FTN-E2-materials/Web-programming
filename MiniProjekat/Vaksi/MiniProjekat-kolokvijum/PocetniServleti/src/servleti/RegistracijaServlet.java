package servleti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDAO;
import model.Student;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

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
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	ServletContext context = getServletContext();
    	// Dodajem studente u context kako bi servleti mogli da rade s njim
    	context.setAttribute("studentiDAO", new StudentDAO());
    	context.setAttribute("studentiZaPretraguDAO", new StudentDAO());
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		StudentDAO studentiDAO = (StudentDAO) context.getAttribute("studentiDAO");
		StudentDAO studentiZaPretraguDAO = (StudentDAO) context.getAttribute("studentiZaPretraguDAO");
		
		String brojIndeksa = request.getParameter("brojIndeksa");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String bodoviStr = request.getParameter("bodovi");
		
		Integer bodovi = Integer.parseInt(bodoviStr);
		Student novi = new Student(brojIndeksa, ime, prezime, bodovi);
		
		studentiDAO.addStudent(novi);
		studentiZaPretraguDAO.addStudent(novi);
		
		// redirekcija na prikaz studenata
		RequestDispatcher disp = request.getRequestDispatcher("/studenti.jsp");
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
