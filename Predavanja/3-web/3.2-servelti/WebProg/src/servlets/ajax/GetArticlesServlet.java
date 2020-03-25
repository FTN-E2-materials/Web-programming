package servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ajax.Article;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GetArticlesServlet
 */
public class GetArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetArticlesServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		List<Article> articles = new ArrayList<Article>();

		Article a = new Article();
		a.setTitle("prvi clanak");
		a.setDescription("primer clanka");
		a.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus pretium, sem sed sollicitudin hendrerit, velit massa euismod felis, vitae varius neque sem non ipsum. Etiam sit amet neque sollicitudin, ullamcorper velit et, porttitor arcu. Nunc et dapibus sapien. Fusce vel felis odio. Praesent tristique elit arcu, nec posuere est mattis nec.");
		articles.add(a);

		Article a1 = new Article();
		a1.setTitle("drugi clanak");
		// a.setDescription("primer clanka");
		a1.setText("Morbi vulputate pulvinar tristique. Nam fringilla quis eros eu accumsan. Donec hendrerit purus a mollis dapibus. In bibendum tortor ac diam consectetur convallis. Fusce feugiat felis sed gravida suscipit. ");
		articles.add(a1);
		getServletContext().setAttribute("articles", articles);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		@SuppressWarnings("unchecked")
		List<Article> articles = (List<Article>) getServletContext()
				.getAttribute("articles");
		ObjectMapper mapper = new ObjectMapper();
		String sArticles = mapper.writeValueAsString(articles);
		System.out.println(sArticles);
		out.write(sArticles);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
