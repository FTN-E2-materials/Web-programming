<%@ page import="beans.Porucioc"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="porucioci" class="dao.PoruciocDAO" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>JSP. Pregled izrade fotografija</h1>
<table>

		<thead>
			<th>Broj izrade (id)</th>
			<th>Ime narucioca</th>
			<th>Broj telefona</th>
			<th>Naziv foldera</th>
			<th>Format fotografije</th>
			<th>Cena izrade</th>
		 	<th>  </th>
		</thead>
		<tbody>
			<c:forEach var="p" items="${porucioci.getPoruciociPretraga()}">
					<tr id="${p.formatFotografija}">
						<td>${p.brojIzrade}</td>	
						<td>${p.imeNarucioca}</td>	
						<td>${p.brojTelefona}</td>	
						<td>${p.nazivFoldera}</td>	
						<td>${p.formatFotografija}</td>	
						<td>${p.cenaIzrade}</td>
						<c:if test="${p.statusIzrade eq 'SPREMNEZAPREDAJU'}">
							<td> </td>	
						</c:if>
					
						<c:if test="${p.statusIzrade ne 'SPREMNEZAPREDAJU'}">
							<td><a href="http://localhost:8181/ServletJSP/IzmenaStatusa?brojIzrade=${p.brojIzrade}">Spremne za predaju</a></td>	
						</c:if>
										
					</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	
	
</body>
</html>