package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Product;
import beans.WebCart;
import dao.ProductDAO;

/**
 * Servlet implementation class WebCartServlet
 */
@WebServlet("/WebCartServlet")
public class WebCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ServletContext context = getServletContext();
		ProductDAO products = (ProductDAO) context.getAttribute("products");
		Product p = products.findProduct(id);

		if (p == null) {
			request.setAttribute("err", "Proizvod ne postoji.");
			RequestDispatcher disp = request.getRequestDispatcher("/JSP/products.jsp");
			disp.forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		WebCart webcart = (WebCart) session.getAttribute("webcart");
		if (webcart == null) {
			webcart = new WebCart();
			webcart.dodajUKorpu(p);
			session.setAttribute("webcart", webcart);
		} else {
			if (!webcart.dodajUKorpu(p)) {
				request.setAttribute("err", "Proizvod je vec dodat u korpu.");
				RequestDispatcher disp = request.getRequestDispatcher("/JSP/products.jsp");
				disp.forward(request, response);
				return;
			}
		}
		
		RequestDispatcher disp = request.getRequestDispatcher("/JSP/webcart.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
