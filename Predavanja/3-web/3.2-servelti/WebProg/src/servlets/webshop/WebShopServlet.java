package servlets.webshop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.webshop.Product;
import beans.webshop.Products;

/**
 * Osnovni servlet koji lista raspolozive proizvode i omogucuje njihovo
 * dodavanje u korpu.
 */
public class WebShopServlet extends HttpServlet {

	private static final long serialVersionUID = 6593194247788949676L;
	
	/*
	 * Obratiti paznju da se metod init() zove samo jednom, prilikom prvo pokretanja (inicijalziacije)
	 * servleta.
	 * => Ukoliko bismo u products.txt dodali novi proizvod, bez restartovanja web servera, a prethodno
	 * je servlet vec bio pokrenut, novi proizvod se nece biti procitan.
	 */
	@Override
	public void init(ServletConfig cfg) {
		/**
		 * Atribut se dodaje u application scope, da bi se video iz klase ShoppingCartServlet.
		 */
		Products products;

		try {
			// obavezan poziv super metode, kako bi se korektno izvrsila inicijalizacija
			super.init(cfg);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		ServletContext ctx = getServletContext();
		products = new Products(ctx.getRealPath(""));
		ctx.setAttribute("products", products);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");
		
		PrintWriter pout = response.getWriter();
		
		pout.println("<html>");
		pout.println("<head>");
		pout.println("</head>");
		pout.println("<body>");
		pout.println("Raspolozivi proizvodi:");
		/**
		 * Atribut se dodaje u application scope, da bi se video iz klase ShoppingCartServlet.
		 */
		Products products = (Products) getServletContext().getAttribute("products");

		pout.println("<table border=\"1\"><tr bgcolor=\"lightgrey\"><th>Naziv</th><th>Cena</th><th>&nbsp;</th></tr>");
		for ( Product p : products.values() ) {
			pout.println("<tr>");
			pout.println("<form method=\"get\" action=\"ShoppingCartServlet\">");
			pout.println("<td>" + p.getName() + "</td>");
			pout.println("<td>" + p.getPrice() + "</td>");
			pout.println("<td>");
			pout.println("<input type=\"text\" size=\"3\" name=\"itemCount\">");
			pout.println("<input type=\"hidden\" name=\"itemId\" value=\"" + p.getId() + "\">");
			pout.println("<input type=\"submit\" value=\"Dodaj\">");
			pout.println("</form>");
			pout.println("</td>");
			pout.println("</tr>");
		}
		pout.println("</table>");

		pout.println("<p>");
		pout.println("<a href=\"ShoppingCartServlet\">Pregled sadrzaja korpe</a>");
		pout.println("</p>");
		pout.println("<p>");
		pout.println("<a href=\"servleti.html\">Nazad</a>");
		pout.println("</p>");

		pout.println("</body>");
		pout.println("</html>");
	}
}