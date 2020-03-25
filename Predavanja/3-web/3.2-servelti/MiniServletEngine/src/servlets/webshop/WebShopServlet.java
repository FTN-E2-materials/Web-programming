package servlets.webshop;
import java.io.IOException;
import java.io.PrintWriter;

import beans.webshop.Product;
import beans.webshop.Products;
import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

/**
 * Osnovni servlet koji lista raspolozive proizvode i omogucuje njihovo
 * dodavanje u korpu.
 */
public class WebShopServlet extends HttpServlet {
	/**
	 * Atribut products je staticki da bi se video iz klase ShoppingCartServlet.
	 */
	public static Products products = new Products();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter pout = response.getWriter();
		response.setContentType("text/html");
		pout.println("<html>");
		pout.println("<head>");
		pout.println("</head>");
		pout.println("<body>");
		pout.println("Raspolozivi proizvodi:");
		pout.println("<table border=\"1\"><tr bgcolor=\"lightgrey\"><th>Naziv</th><th>Cena</th><th>&nbsp;</th></tr>");
		for (Product p : products.values()) {
			pout.println("<tr>");
			pout.println("<form method=\"get\" action=\"ShoppingCartServlet\">");
			pout.println("<td>" + p.getName() + "</td>");
			pout.println("<td>" + p.getPrice() + "</td>");
			pout.println("<td>");
			pout.println("<input type=\"text\" size=\"3\" name=\"itemCount\">");
			pout.println("<input type=\"hidden\" name=\"itemId\" value=\""
					+ p.getId() + "\">");
			pout.println("<input type=\"submit\" value=\"Dodaj\">");
			pout.println("</form>");
			pout.println("</td>");
			pout.println("</tr>");
		}
		pout.println("</table>");

		pout.println("<p>");
		pout.println("<a href=\"ShoppingCartServlet\">Pregled sadrzaja korpe</a>");
		pout.println("</p>");

		pout.println("</body>");
		pout.println("</html>");
	}
}