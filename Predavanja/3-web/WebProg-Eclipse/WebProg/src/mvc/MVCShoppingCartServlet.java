package mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.webshop.Products;
import beans.webshop.ShoppingCart;

/**
 * Klasa koja obavlja listanje stavki u korpi, a ako je pozvana iz forme, dodaje
 * jednu stavku u korpu, pa onda lista).
 */
public class MVCShoppingCartServlet extends HttpServlet {

	private static final long serialVersionUID = -8628509500586512294L;

	private static final String SHOPPING_CART_KEY = "sc";
	
	// zasto ovako ne valja?
	// obratiti paznju na prirodu http protokola, koji je stateless
	// private ShoppingCart sc = new ShoppingCart();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// zasto ovako ne valja?
		// obratiti paznju na prirodu http protokola, koji je stateless
		// ShoppingCart sc = new ShoppingCart();

		// pogledamo da li u tekucoj sesiji postoji objekat ShoppingCart
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute(SHOPPING_CART_KEY);
		if ( sc == null ) {
			// ako ne postoji, kreiramo ga i dodelimo tekucoj sesiji. Na ovaj
			// nacin, objekat klase ShoppingCart ce pratiti sesiju.
			sc = new ShoppingCart();
			session.setAttribute(SHOPPING_CART_KEY, sc);
		}

		if ( request.getParameter("itemId") != null ) {
			// ako smo pozvali ovaj servlet sa parametrima za dodavanje proizvoda u korpu
			try {
				Products products = (Products) getServletContext().getAttribute("products");
				// probamo da ga dodamo
				sc.addItem(products.getProduct(request.getParameter("itemId")),
						Integer.parseInt(request.getParameter("itemCount")));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		RequestDispatcher disp = request
				.getRequestDispatcher("/primer14/shoppingCart.jsp");
		// redirektovacemo na shopping cart stranicu
		disp.forward(request, response);
	}
}
