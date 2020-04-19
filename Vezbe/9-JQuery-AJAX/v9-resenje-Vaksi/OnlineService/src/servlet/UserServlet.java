package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

/**
 * Vra�a listu korisnika i dodaje nove korisnike u listu korisnika.
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ObjectMapper mapper = new ObjectMapper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	List<User> users = new ArrayList<>();
    	users.add(new User("Pera", "Peric", "pera", "123", "pera@email.com", 123));
    	getServletContext().setAttribute("users", users);
    }

	/**
	 * Vra�a listu korisnika kao JSON listu.
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users = (List<User>) getServletContext().getAttribute("users");
		// Pretvorimo korisnike u JSON oblik
		String usersJson = mapper.writeValueAsString(users);
		// Postavimo tip odgovora na JSON
		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter().print(usersJson);
	}

	/**
	 * Dodaje korisnika iz zahteva u listu svih korisnika.
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// U�itaj sadr�aj zahteva kao JSON i pretvori ga u User objekat
		User user = mapper.readValue(request.getReader(), User.class);
		List<User> users = (List<User>) getServletContext().getAttribute("users");
		users.add(user);
	}

}
