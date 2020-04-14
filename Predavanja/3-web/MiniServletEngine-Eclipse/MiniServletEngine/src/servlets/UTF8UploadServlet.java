package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import servletengine.HttpServlet;
import servletengine.HttpServletRequest;
import servletengine.HttpServletResponse;

public class UTF8UploadServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");

		if (request.getCharacterEncoding() == null)
			request.setCharacterEncoding("UTF-8");
		String param1;
		String param2;
		if (request.isMultipart()) {
			param1 = request.getMultiParameter("text_field");
			param2 = request.getMultiParameter("text_field2");
		} else {
			param1 = request.getParameter("text_field");
			param2 = request.getParameter("text_field2");
		}
		pout.println("Poslali ste ovo:" + param1 + "<br>");
		pout.println("Poslali ste ovo:" + param2 + "<br>");
		pout.println("Datoteke koje su poslate na server:<br>");
		for (int i = 0; i < request.getUploadedFilesCount(); i++) {
			pout.print(request.getUploadedFile(i).getFieldName() + "--->>");
			pout.println(request.getUploadedFile(i).getFilePath() + "<br>");
		}
		pout.println("</body>");
		pout.println("</html>");
		pout.flush();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");
		if (request.getCharacterEncoding() == null)
			request.setCharacterEncoding("UTF-8");
		pout.println("Poslali ste ovo:" + request.getParameter("text_field")
				+ "<br>");
		pout.println("Poslali ste ovo:" + request.getParameter("text_field2")
				+ "<br>");
		pout.println("</body>");
		pout.println("</html>");
		pout.flush();
	}

}