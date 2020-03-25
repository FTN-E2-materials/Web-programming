package servletengine;

import java.io.*;

/** Osnovna klasa koju nasledjuju svi servleti.
 *  @author Milan Vidakovic
 */
public abstract class HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {}
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {}
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {}
  public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {}
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {}
  public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {}
  public void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException {}

  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (request.getMethod().equals("GET"))
      doGet(request, response);
    else if (request.getMethod().equals("POST"))
      doPost(request, response);
    else if (request.getMethod().equals("PUT"))
      doPut(request, response);
    else if (request.getMethod().equals("HEAD"))
      doHead(request, response);
    else if (request.getMethod().equals("DELETE"))
      doDelete(request, response);
    else if (request.getMethod().equals("OPTIONS"))
      doOptions(request, response);
    else if (request.getMethod().equals("TRACE"))
      doTrace(request, response);
  }
}