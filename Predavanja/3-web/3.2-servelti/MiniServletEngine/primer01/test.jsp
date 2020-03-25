<html>
<head>
<title>JSP izrazi</title>
</head>
<body>

<h3>Primeri JSP izraza</h3>
<p>
Danasnji datum: <%= new java.util.Date() %>
</p>

<p>Vas racunar: <%= request.getRemoteHost() %>
</p>
<p><a href="../jsp.html">Nazad</a></p>
</body>
</html>