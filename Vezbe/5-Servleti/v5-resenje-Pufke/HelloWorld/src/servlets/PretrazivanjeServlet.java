package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PretrazivanjeServlet
 */
@WebServlet("/PretrazivanjeServlet")
public class PretrazivanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PretrazivanjeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("ime"); //Pokupimo ime korisnika kog zelimo da pretrazimo
		
		PrintWriter out =  response.getWriter();
		
		for(String oneUser: RegistrationServlet.getUsers()){
			if(user.equals(oneUser)){
				out.append("Korisnik " + user + " je pronadjen u bazi podataka.");
				return;
			}
		}
	
		out.append("Korisnik " + user + " nije pronadjen u bazi podataka!!!");
	}

}
