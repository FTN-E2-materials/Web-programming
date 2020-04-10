package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.ProductDAO;

/***
 * Servlet zaduzen za prikazivanje proizvoda.
 * @author Vaxi
 *
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException { 
    	super.init();
    	ServletContext context = getServletContext();
    	String contextPath = context.getRealPath("");
    	// Dodaju se proizvodi u kontekst kako bi mogli servleti da rade sa njima
    	context.setAttribute("products", new ProductDAO(contextPath));
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 2: Implementirati listanje svih proizvoda
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			RequestDispatcher disp = request.getRequestDispatcher("/LoginServlet");
			disp.forward(request, response);
			return;
		}
		
		RequestDispatcher disp = request.getRequestDispatcher("/JSP/products.jsp");
		disp.forward(request, response);
	}


}
