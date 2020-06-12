<%@ page import="beans.Pacijent"%>
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
			<% for(Pacijent p : pacijenti.findAll()) {%>
					<tr id="<%= p.getZdravstveniStatus() %>">
						<td><%= p.getBrZdravstvenogOsig() %></td>	
						<td><%= p.getIme() %></td>	
						<td><%= p.getPrezime() %></td>	
						<td><%= p.getDatumRodjenja() %></td>	
						<td><%= p.getPol() %></td>	
						<td><%= p.getZdravstveniStatus() %></td>
						<% if(p.getZdravstveniStatus().equals("BEZ_SIMPTOMA"))	 {%>
							<td> </td>	
						<% }else{%>
							<td><a href="#">Test je pozitivan!</a></td>	
						<% }%>
						
					</tr>
			<% }%>
		</tbody>
	</table>
</body>
</html>