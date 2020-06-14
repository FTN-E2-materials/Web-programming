package servleti;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDAO;
import model.Student;

/**
 * Servlet implementation class PretragaServlet
 */
@WebServlet("/PretragaServlet")
public class PretragaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PretragaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		StudentDAO studentiDAO = (StudentDAO) context.getAttribute("studentiDAO");
		StudentDAO studentiZaPretraguDAO = (StudentDAO) context.getAttribute("studentiZaPretraguDAO");
		
		String inputPretrage = request.getParameter("inputPretrage");
		
		if(inputPretrage.isEmpty()) {
			// uzmem od pomocnog kontesta i sve to prebacim u prave studente koje priakzujem na jsp-u
//			studentiDAO = new StudentDAO();
//			for (Student student : studentiZaPretraguDAO.findAll()) {
//				if(studentiDAO.)
//			}
		}
		
		
		/*
		 * Ideja je da uklonim one studente koji ne zadovoljavaju pretragu
		 * a kasnije ako je pretraga prazna, samo da vratim sve studente.
		 * 
		 * 
		 */
		ArrayList<Student> studentiZaUklanjanje = new ArrayList<Student>();
		for (Student student : studentiDAO.findAll()) {
			if(!student.getBrojIndeksa().startsWith(inputPretrage)) {
				studentiZaUklanjanje.add(student);
			}
		}
		
		studentiDAO.ukloniStudente(studentiZaUklanjanje);
		
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
