<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" class="beans.User" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Primer sa formom</title>
</head>
<body>
<%
  String logoff = request.getParameter("logoff");
  if ((logoff != null) && logoff.equals("true")) {
    user.logoff();
    session.invalidate();
  }

  if (!user.isLoggedIn()) { %>
  <form action="results.jsp" method="post">
  <table cellspacing=0 cellpadding=3 border=0>
    <tr>
      <td align=right>Username:</td>
      <td><input type="text" name="username"></td>
    </tr>
    <tr>
      <td align=right>Password:</td>
      <td><input type="password" name="password2"></td>
    </tr>
    <tr>
      <td align=right>&nbsp;</td>
      <td><input type="submit" value=" Pošalji "></td>
    </tr>
  </table>
  </form>
<% } else { %>
  <p>Već ste se prijavili! </p>
  <p>[<a href="login.jsp?logoff=true">Odjavi se</a>]</p>
<% } %>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
