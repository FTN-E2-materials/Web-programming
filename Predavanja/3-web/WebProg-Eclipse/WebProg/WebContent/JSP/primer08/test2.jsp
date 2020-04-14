<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mytags" uri="mytags" %>

<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Primer JSTL</title>
</head>
<body>

<h1>Funkcije</h1>
<p>Duzina niza <code>user.niz1</code> je: ${fn:length(user.niz1)}</p>

<h3>Filtriranje kolekcije (function) </h3>

<%
String[] messages =
{"Ovo", "je", "tekst", "poruke!"};
pageContext.setAttribute("messages", messages);
%>
<ul>
<c:forEach var="message" items="${messages}">
<c:if test="${fn:containsIgnoreCase(message, param.filter) or empty param.filter}">
<li><b><c:out value="${message}"/></b></li>
</c:if>
</c:forEach>
</ul>

<form action="test2.jsp">
 <input type="text" name="filter">
 <input type="submit" value="Filtriraj">
</form>

Duzina niza messages je: ${fn:length(messages)} <br>

Duzina niza messages je: ${mytags:length(messages)} <br>

<a href="test.jsp">Nazad</a>

</body>
</html>
