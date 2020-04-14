package servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetHTMLServlet
 */
public class GetHTMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetHTMLServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.write("<div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget arcu rhoncus mauris condimentum vehicula. Aenean dolor orci, pellentesque id rhoncus vel, interdum ac justo. Sed et nisl sagittis, laoreet nisi dictum, viverra sem. Nunc at quam scelerisque, bibendum arcu et, accumsan sem. Nullam gravida tellus eu lobortis pretium. Nunc est metus, ornare sed ornare sed, consequat at odio. Morbi vel consectetur libero. Quisque vestibulum tincidunt risus, ut vestibulum libero vestibulum quis. Ut congue felis mauris, feugiat suscipit neque vehicula eu. Quisque quis sapien accumsan, tincidunt elit a, rhoncus massa. Sed ut sapien eget enim sagittis euismod. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nullam sit amet luctus dolor, a ornare leo. Maecenas laoreet hendrerit sapien nec porta. Vestibulum rutrum vestibulum urna sit amet auctor. Fusce ac tellus sodales nulla congue aliquam</div>");
		out.write("<table border=\"1\"><tr><td>Lorem</td><td>ipsum</td></tr><tr><td>dolor</td><td>sit</td></tr><tr><td>amet</td><td>consectetur</td></tr></table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
