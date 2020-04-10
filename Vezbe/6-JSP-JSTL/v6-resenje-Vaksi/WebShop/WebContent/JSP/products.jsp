<%@page import="beans.Product"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Dodajem JSTL deklaraciju, kako bi koristili JSTL i EL --%>
<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="products" class="dao.ProductDAO" scope="application"/> <%-- sve proizvode cemo cuvati u products i to u skoupu aplikacije--%>
<!-- Koristi objekat registrovan pod kljucem "user" iz sesije -->
<jsp:useBean id="user" class="beans.User" scope="session"/>
<html>
<head>
</head>
<body>
	<p>Dobrodosli, <%=user.getFirstName()%> <a href="LogoutServlet">Logout</a></p>
	<table border="1">
		<tr>
			<th>Naziv</th>
			<th>Cena</th>
			<th colspan="2">Kolicina</th>
		</tr>
		<!-- TODO 3: Izlistavanje proizvoda -->
		<%-- 
		<% for (Product p : products.findAll()) { %>
		<tr>
			<td><%=p.getName()%></td>
			<td><%=p.getPrice()%></td>
			<td>1</td>
			<td><a href="WebCartServlet?id=<%= p.getId() %>">Dodaj u korpu</a></td>
		</tr>
		<% } %>
		--%>
		<c:forEach var="p" items="${products.findAll()}">
		<tr>
			<td>${p.name}</td>
			<td>${p.price}</td>
			<td>1</td>
			<td><a href="WebCartServlet?id=${p.id}">Dodaj u korpu</a></td>
		</tr>
		</c:forEach>
	</table>
	<!-- Prikazi gresku, ako je bilo -->
	<%-- 
	<% if (request.getAttribute("err") != null) { %>
		<p style="color: red"><%=request.getAttribute("err")%></p>
	<% } %>
	--%>
	<c:if test="${not empty err}">
		<p style="color: red">${err}</p>
	</c:if>
</body>
</html>