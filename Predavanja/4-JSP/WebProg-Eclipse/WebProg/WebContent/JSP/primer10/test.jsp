<%@ taglib uri="mytags" prefix="mytags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-1250">
<title>Primer fancy taga</title>
</head>
<body>
<p>
Tekst, pa zatim
<mytags:fancy> malo zanimljivog teksta, sa malo JSP izraza <%= Math.sqrt(2) %>.
</mytags:fancy>
Tekst posle.
</p>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
