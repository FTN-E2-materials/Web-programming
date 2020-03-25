<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="mytags"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">	
<title>Custom tag demo</title>
</head>
<body >
<h1>Dodatni tag koji vraća slučajan broj</h1>
<p>Broj je: <mytags:rnd /></p>

<p>Broj u rasponu do 100 je: <mytags:rnd max="100" /></p>

<p>Broj je: <mytags:rnd /></p>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
