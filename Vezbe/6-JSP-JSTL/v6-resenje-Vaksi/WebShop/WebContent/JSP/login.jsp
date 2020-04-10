<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Dodajem JSTL deklaraciju, kako bi koristili JSTL i EL --%>
<html>
<head>
</head>
<body>
<form action="LoginServlet" method="POST">
<table>
	<tr>
		<td>Username</td>
		<td><input type="text" name ="username"/></td>
	</tr>
	<tr>
		<td>Password</td>
		<td><input type="password" name ="password"/></td>
	</tr>
	<tr><td><input type="submit" value="Login"></td></tr>
</table>
</form>
	<!-- TODO 5: Prikaži grešku, ako je bilo -->
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