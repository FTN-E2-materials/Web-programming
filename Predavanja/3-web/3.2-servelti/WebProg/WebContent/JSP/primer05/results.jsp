<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" class="beans.User" scope="session"/>
<jsp:useBean id="user2" class="beans.User" scope="application"/>
<jsp:useBean id="user3" class="beans.User" scope="request"/>
<jsp:useBean id="user4" class="beans.User" scope="page"/>
<jsp:setProperty name="user" property="username" param="username"/>
<jsp:setProperty name="user" property="password" param="password2"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Primer sa JavaBeans</title>
</head>
<body>

<% if (user.login()) { %>
  <p>
  Uspešno ste se prijavili:<br>
  Username: <b><jsp:getProperty name="user" property="username"/></b><br>
  Password: <b><%= user.getPassword() %></b>
  </p>
<% } else { %>
  <p>
  Niste se uspešno prijavili!
  </p>
<% } %>
<p>
[ <a href="login.jsp">login.jsp</a> ]
</p>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
