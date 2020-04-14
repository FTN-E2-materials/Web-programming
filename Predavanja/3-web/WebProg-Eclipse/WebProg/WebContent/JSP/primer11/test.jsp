<%@ taglib uri="mytags" prefix="mytags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-1250">
<title>Primer for taga</title>
</head>
<body>
<%! String a="3"; %>
<mytags:for from="1" to="<%= a %>">
  <mytags:value />. tekst <br>
  ${forValue}. tekst <br>
</mytags:for>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
