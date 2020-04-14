package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<String> users = new ArrayList<>();
    //Sta god radili sa ovom listom, NAPOMENA NIJE TRAD SAFE, moramo 
	//sa njom da rukujemo u sinhronized bloku
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
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
		String user = request.getParameter("ime");
		
		getUsers().add(user);
		//Ovo mora da se odradi preko sigurnih reposzitorijuma, 
		//posto ovu staticku listu users ce da dele svi korisnici i ona nije
		//thread safety
		
		PrintWriter out =  response.getWriter();
		out.append("<html><body><table>");
		for(String oneUser: getUsers()){
			out.append("<tr><td>" + oneUser + "</td></tr>");
		}
		out.append("</html></body></table>");
	}

	public static List<String> getUsers() {
		return users;
	}

	public static void setUsers(List<String> users) {
		RegistrationServlet.users = users;
	}

}
