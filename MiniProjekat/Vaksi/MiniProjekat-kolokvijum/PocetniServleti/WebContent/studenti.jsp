<%@page import="model.Student" %>	
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="studentiDAO" class="dao.StudentDAO" scope="application"></jsp:useBean>

<html>
	<head>
		<style> table, th, td { border: 1px solid black; } 
		
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title> </title>
	</head>
	<body>
		<h1 align="center">JSP/Servlet Pregled studenata [PRETRAGA] </h1>
		<table align="center">
		<tr>
		
			<th> Broj indeksa </th>
			<th> Ime </th>
			<th> Prezime </th>
			<th> Bodovi </th>
			
		</tr>
		<c:forEach var="student" items="${studentiDAO.findAll()}">
		
		<c:choose>
			<c:when test="${ student.bodovi < '51' }">
				<tr style="background-color:red;">
				
				<td>${student.brojIndeksa}</td>
				<td>${student.ime}</td>
				<td>${student.prezime}</td>
				<td>${student.bodovi }</td>

				</tr>
			</c:when>
			<c:otherwise>
				<tr>
				
				<td>${student.brojIndeksa}</td>
				<td>${student.ime}</td>
				<td>${student.prezime}</td>
				<td>${student.bodovi }</td>

				</tr>
			</c:otherwise>
		</c:choose>
		
		</c:forEach>
		
	</table>
	
	<!--  Imam servlet koji bi trebao da hendluje pretragu, ali iz nekog razloga mi ne reaguje forma -->
	<form action = "htttp://localhost:8080/PocetniServleti/PretragaServlet" method="GET">
		<input type="text" name = "inputPretrage"/>
		<input type = "submit" />
		
	</form>
	
	</body>
</html>