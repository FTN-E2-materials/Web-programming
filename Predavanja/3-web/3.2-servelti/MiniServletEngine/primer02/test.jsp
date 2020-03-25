<html>
<head>
<title>JSP skriptleti</title>
</head>
<body>

<h3>Primer JSP skriptleta</h3>

<table border=1>
<tr>
  <th>R.br.</th>
  <th>Ime</th>
</tr>
<%
 String names[] = { "Bata", "Pera", "Mika", "Laza", "Sima" };
for (int i = 0; i < names.length; i++) {
%>
<tr>
  <td><%= i %></td>
  <td><%= names[i] %></td>
</tr>
<% } %>
</table>

<p><a href="../jsp.html">Nazad</a></p>

</body>
</html>