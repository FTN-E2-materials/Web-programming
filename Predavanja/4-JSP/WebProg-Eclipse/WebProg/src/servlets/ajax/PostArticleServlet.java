package servlets.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ajax.Article;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PostArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5551894444616184019L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String jsonRequest = req.getParameter("jsonData");
		System.out.println(jsonRequest);
		ObjectMapper mapper = new ObjectMapper();
		Article a = mapper.readValue(jsonRequest, Article.class);
		@SuppressWarnings("unchecked")
		List<Article> articles = (List<Article>) getServletContext().getAttribute("articles");
		articles.add(a);
	}

}
