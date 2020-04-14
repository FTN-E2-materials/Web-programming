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
<p>Vas browser: <%= request.getHeader("User-agent") %>
</p>
<p><a href="../index.html">Nazad</a></p>
</body>
</html>