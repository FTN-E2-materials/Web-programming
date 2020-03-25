package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class for Servlet: UTF8UploadServlet
 * 
 */
public class UTF8UploadServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	private static final long serialVersionUID = 4833339643823108304L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UTF8UploadServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(response.getCharacterEncoding());
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");

		pout.println("Request character encoding: " + request.getCharacterEncoding() + "<br>");
		pout.println("Response character encoding: " + response.getCharacterEncoding() + "<br>");

		pout.println("Poslali ste ovo:" + request.getParameter("text_field") + "<br>");
		pout.println("Poslali ste ovo:" + request.getParameter("text_field2") + "<br>");
		pout.println("</body>");
		pout.println("</html>");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(response.getCharacterEncoding());

		PrintWriter pout = response.getWriter();

		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");

		pout.println("Request character encoding: " + request.getCharacterEncoding() + "<br>");
		pout.println("Response character encoding: " + response.getCharacterEncoding() + "<br>");

		ServletRequestContext ctx = new ServletRequestContext(request);

		if ( !ServletFileUpload.isMultipartContent(ctx) ) {
			pout.println("Poslali ste ovo:" + request.getParameter("text_field") + "<br>");
			pout.println("Poslali ste ovo:" + request.getParameter("text_field2") + "<br>");
		} else {
			try {
				// Create a factory for disk-based file items
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// preko ove veličine, fajlovi se snimaju direktno na disk
				factory.setSizeThreshold(2000000);
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				// ukupna veličina celog zahteva
				upload.setSizeMax(3000000);
				// Parse the request
				List<FileItem> items = upload.parseRequest(request);
				// Process the uploaded items
				for (FileItem item : items) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString(request
								.getCharacterEncoding());
						pout.println(name + " -> " + value + "<br>");
					} else {
						String fieldName = item.getFieldName();
						String fileName = item.getName();
						String contentType = item.getContentType();
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						pout.println(fieldName + "->" + fileName + " ("
								+ sizeInBytes + " bytes, Content-Type: "
								+ contentType + ", isInMemory: " + isInMemory
								+ ")<br>");
						int idx = fileName.lastIndexOf("\\"); // ako browser
						// daje puno ime fajla (sa putanjom)
						if (idx != -1)
							fileName = fileName.substring(idx + 1);
						if (sizeInBytes != 0) {
							System.out.println("<<"
									+ getServletContext().getRealPath("") + ">>");
							File uploadedFile = new File(getServletContext()
									.getRealPath("")
									+ "/files/" + fileName);
							System.out.println("{" + uploadedFile.getCanonicalPath() + "}");
							item.write(uploadedFile);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		pout.println("</body>");
		pout.println("</html>");
	}
}
