<html>
<head>
<title>JSP deklaracije</title>
</head>
<body>

<h3>Primer JSP deklaracije</h3>

<%! int hitCount = 0; 
private int getRandom() {
      return (int)(Math.random()*100);
}
%>
<p>
Ovoj stranici je ukupno pristupano
<%= ++hitCount %> puta.
</p>
<p>
Slucajan broj od nula do 100: <%= getRandom() %>.
</p>

<p><a href="../jsp.html">Nazad</a></p>

</body>
</html>