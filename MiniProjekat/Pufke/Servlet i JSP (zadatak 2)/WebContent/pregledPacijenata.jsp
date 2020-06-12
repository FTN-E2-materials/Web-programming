<%@ page import="beans.Pacijent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Dodajem JSTL deklaraciju, kako bi koristili JSTL i EL --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="pacijenti" class="dao.PacijentDAO" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<table>
		<thead>
			<th>Broj zdravstvenog osiguranja</th>
			<th>Ime pacijenta</th>
			<th>Prezime pacijenta</th>
			<th>Datum rodjenja</th>
			<th>Pol</th>
			<th>Zdravstveni status</th>
		 	<th>  </th>
		</thead>
		<tbody>
			<c:forEach var="p" items="${pacijenti.findAll()}">
					<tr id="${p.zdravstveniStatus}">
						<td>${p.brZdravstvenogOsig}</td>	
						<td>${p.ime}</td>	
						<td>${p.prezime}</td>	
						<td>${p.datumRodjenja}</td>	
						<td>${p.pol}</td>	
						<td>${p.zdravstveniStatus}</td>
						<c:if test="${p.zdravstveniStatus eq 'ZARAZEN'}">
							<td> </td>	
						</c:if>
					
						<c:if test="${p.zdravstveniStatus ne 'ZARAZEN'}">
							<td><a href="http://localhost:8181/ServletJSP/IzmenaPacijenta?brZdravstvenogOsig=${p.brZdravstvenogOsig}">Test je pozitivan!</a></td>	
						</c:if>
						
						
					</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>