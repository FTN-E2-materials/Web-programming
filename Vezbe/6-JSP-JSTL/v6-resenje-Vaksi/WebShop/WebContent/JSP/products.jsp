<%@page import="beans.Product"%>
<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="products" class="dao.ProductDAO" scope="application"/>
<!-- Koristi objekat registrovan pod ključem "user" iz sesije -->
<jsp:useBean id="user" class="beans.User" scope="session"/>
<html>
<head>
</head>
<body>
	<p>Dobrodošli, <%=user.getFirstName()%> <a href="LogoutServlet">Logout</a></p>
	<table border="1">
		<thead>
			<th>Naziv</th>
			<th>Cena</th>
			<th colspan="2">Količina</th>
		</thead>
		<tbody>
		<!-- TODO 3: Izlistavanje proizvoda -->
			<% for(Product p : products.findAll()) {%>
				<tr>
					<td><%=p.getName() %></td>
					<td><%=p.getPrice()%></td>
					<td>1</td>
					<td><a href="">Dodaj u korpu</a></td>
				</tr>
			<%} %>
		</tbody>
	</table>
</body>
</html>