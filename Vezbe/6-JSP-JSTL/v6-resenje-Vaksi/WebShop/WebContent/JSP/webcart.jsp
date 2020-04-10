<%@page import="beans.Product" %>								<%-- Koristimo proizvod iz beans-a --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Dodajem JSTL deklaraciju, kako bi koristili JSTL i EL --%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="webcart" class="beans.WebCart" scope="session" /> <%-- Virtualnu korpu kacimo na sesiju --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Prikaz Vase korpe</title>
</head>
<body>
	<h1>VaSa korpa sa proizvodima</h1>
	<table border="1">
		<tr>
			<th>Naziv</th>
			<th>Cena</th>			
		</tr>
		<%-- Skriplet nacin 
		<% for (Product p : webcart.getProducts()) { %>
		<tr>
			<td><%= p.getName() %></td>
			<td><%= p.getPrice() %></td>
		</tr>
		<% } %>
		--%>
		<c:forEach var="p" items="${webcart.getProducts()}">
		<tr>
			<td>${p.name}</td>
			<td>${p.price}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>