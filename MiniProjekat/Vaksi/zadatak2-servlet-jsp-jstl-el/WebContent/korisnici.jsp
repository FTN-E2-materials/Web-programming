<%@page import="model.Pacijent" %>	
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Dodajem JSTL deklaraciju, kako bi koristili JSTL i EL --%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="pacijenti" class="dao.PacijentDAO" scope="application"></jsp:useBean>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Prikaz korisnika</title>
	</head>
	<body>
		<h1 style="color:#4a8fff;"> JSP.Pregled pacijenata [COVID-19] </h1>
		<table border="1">
		<tr>
		
			<th>Broj zdravstvenog kartona</th>
			<th>Ime pacijenta</th>
			<th> Prezime pacijenta </th>
			<th> Datum rodjenja </th>
			<th> Pol pacijenta </th>
			<th> Zdravstveni status </th>
			<th> </th>
			
		</tr>
		<c:forEach var="p" items="${pacijenti.findAll()}">
		<c:choose>
			<c:when test="${p.zdravstveniStatus.equals('BEZ_SIMPTOMA')}">
				<tr style="color:#4a8fff;">
				<td>${p.brojZdravstvenogOsiguranja}</td>
				<td>${p.ime}</td>
				<td>${p.prezime}</td>
				<td>${p.datumRodjenja }</td>
				<td>${p.pol }</td>
				<td>${p.zdravstveniStatus }</td>
				<td> <a href="http://localhost:8080/MiniProjekatS/PromeniStatusServlet?brojZdravstvenog=${p.brojZdravstvenogOsiguranja }"> Test je pozitivan</a></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
				<td>${p.brojZdravstvenogOsiguranja}</td>
				<td>${p.ime}</td>
				<td>${p.prezime}</td>
				<td>${p.datumRodjenja }</td>
				<td>${p.pol }</td>
				<td>${p.zdravstveniStatus }</td>
				<td> <a href="http://localhost:8080/MiniProjekatS/PromeniStatusServlet?brojZdravstvenog=${p.brojZdravstvenogOsiguranja }"> Test je pozitivan</a></td>
				</tr>
			</c:otherwise>
		</c:choose>
		</c:forEach>
		
		
	</table>
	</body>
</html>