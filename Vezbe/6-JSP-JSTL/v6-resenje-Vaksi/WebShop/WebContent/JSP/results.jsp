﻿<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" class="beans.User" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Primer sa JavaBeans</title>
</head>
<body>

<% if (user.isLoggedIn()) { %>
  <p>
  Uspešno ste se prijavili:<br>
  Username: <b><jsp:getProperty name="user" property="username"/></b><br>
  Password: <b><%= user.getPassword() %></b>
  </p>
[ <a href="ProductsServlet">Products</a> ]
  <p>
[ <a href="LoginServlet?logoff=true">logout</a> ]
  </p>

<% } else { %>
  <p>
  Niste se uspešno prijavili!
  </p>
  <p>
[ <a href="JSP/login.jsp">login.jsp</a> ]
  </p>

<% } %>

<p><a href="/WebShop/index.html">Nazad</a></p>

</body>
</html>
