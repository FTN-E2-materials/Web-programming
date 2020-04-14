package servlets.rememberlogin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;

/**
 * Servlet implementation class RememberLoginServlet
 */
public class RememberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RememberLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		User u = (User) request.getSession().getAttribute("user");

		if (request.getParameter("logoff") != null) {
			if (u != null) {
				u.logoff();
				request.getSession().invalidate();
			}
			clearCookie(request, response);
			response.sendRedirect("login.html");
			return;
		}

		System.out.println("User bean: " + u);
		String userIdendificationKey = null;
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				System.out.println("Cookie: " + cookies[i].getName());
				if (cookies[i].getName().equals("userIdendificationKey")) {
					userIdendificationKey = cookies[i].getValue();
					System.out.println("User ID: >>" + userIdendificationKey);
					break;
				}
			}
		}

		if (u == null && userIdendificationKey != null) {
			System.out.println("u je NULL, a userIdKey nije NULL");
			if (!userIdendificationKey.equals("-1")) {
				System.out
						.println("u je NULL, a userIdKey nije NULL i razlicit je od -1");
				u = new User();
				// potrazimo u bazi na osnovu ID-a (userIdentificationKey)
				u.setUsername("proba");
				u.setPassword("proba");
				u.setLoggedIn(true);
			}
		}

		if (u == null) {
			out.println("<html><head></head><body>");
			out.println("NOT LOGGED IN");
			out.println("<a href=\"login.html\">Log in</a>");
			out.println("</body></html>");
			return;
		}
		if (u.isLoggedIn()) {
			response.setContentType("text/html");
			out.println("<html><head></head><body>");
			out.println("LOGGED IN");
			out.println("<a href=\"RememberLoginServlet?logoff\">Logoff</a>");
			out.println("</body></html>");
		} else {
			out.println("<html><head></head><body>");
			out.println("NOT LOGGED IN");
			out.println("<a href=\"login.html\">Log in</a>");
			out.println("</body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		String rememberStr = request.getParameter("remember");
		boolean remember = (rememberStr != null) ? rememberStr.equals("on")
				: false;
		System.out.println(rememberStr + "," + remember);

		User u = new User();
		u.setUsername(uname);
		u.setPassword(pwd);
		boolean success = u.login();

		if (success && remember) {
			// u.getId() - ID korisnika, iz baze podataka
			String userIdendificationKey = "1"; 
			Cookie cookie = new Cookie("userIdendificationKey",
					userIdendificationKey);
			// podesimo period validnosti
			cookie.setMaxAge(365 * 24 * 60 * 60);
			// dodamo cookie
			response.addCookie(cookie);
			System.out.println("Added cookie:" + cookie.getName());
		} else if (success && !remember) {
			clearCookie(request, response);
		}
		if (success) {
			request.getSession().setAttribute("user", u);
		}
		doGet(request, response);
	}

	private void clearCookie(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].getName());
				if (cookies[i].getName().equals("userIdendificationKey")) {
					cookies[i].setValue("-1");
					response.addCookie(cookies[i]);
					System.out
							.println("Cleared cookie:" + cookies[i].getName());
					break;
				}
			}
		}
	}

}
