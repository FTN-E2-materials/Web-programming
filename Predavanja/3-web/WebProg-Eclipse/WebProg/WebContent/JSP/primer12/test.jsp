<%@ taglib uri="mytags" prefix="mytags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-1250">
<title>Primer if taga</title>
</head>
<body>
<mytags:if>
  <mytags:condition> <%= Math.random() > 0.5 %> </mytags:condition>
  <mytags:then> Prijavili ste se! </mytags:then>
  <mytags:else> Niste se prijavili! </mytags:else>
</mytags:if>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
