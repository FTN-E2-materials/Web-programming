<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" class="beans.User" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Primer sa formom</title>
</head>
<body>
<% if (!user.isLoggedIn()) { %>
  <form action="/WebProg/LoginServlet" method="post">
  <table cellspacing=0 cellpadding=3 border=0>
    <tr>
      <td align=right>Username:</td>
      <td><input type="text" name="username"></td>
    </tr>
    <tr>
      <td align=right>Password:</td>
      <td><input type="password" name="password"></td>
    </tr>
    <tr>
      <td align=right>&nbsp;</td>
      <td><input type="submit" value=" Pošalji "></td>
    </tr>
  </table>
  </form>
<% } else { %>
  <p>
  Već ste se prijavili! <br>
  [<a href="/WebProg/LoginServlet?logoff=true">Odjavi se</a>]
  </p>
<% } %>

<p><a href="/WebProg/JSP/index.html">Nazad</a></p>

</body>
</html>
