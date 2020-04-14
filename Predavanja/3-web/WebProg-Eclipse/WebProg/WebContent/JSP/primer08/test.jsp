<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Primer JSTL</title>
</head>
<body>

<h3>Funkcije</h3>
<a href="test2.jsp">Primer upotrebe EL funkcija </a> <br>

<h3>Grananje (if) </h3>
<c:if test='${user.address.street=="JSP Street"}'>
  Prijavili ste se kao proba/proba
</c:if>
<br>
Ako ovde ne vidite tekst, prijavite se <a href="../primer06/login.jsp">ovde</a>.


<h3>Grananje (choose) </h3>
<c:choose>
  <c:when test='${user.address.street=="JSP Street"}'>
    Prijavili ste se kao proba/proba
  </c:when>

  <c:when test='${user.address.street!="JSP Street"}'>
    Niste prijavljeni kao proba/proba
  </c:when>
</c:choose>


<h3>For petlja za brojanje</h3>
<c:forEach var="item" begin="1" end="10">
    ${item}
</c:forEach>

<h3>Foreach iteriranje kroz kolekciju</h3>

<%
String[] messagesddd =
{"Ovo", "je", "tekst", "poruke!"};
pageContext.setAttribute("messages", messagesddd);
%>
<ul>
<c:forEach var="message" items="${messages}">
<li><b>${message}</b></li>
</c:forEach>
</ul>

<h3>Foreach petlja za iteriranje kroz listu stavki odvojenih zarezom</h3>
<ul>
<c:forEach var="country"
items="Australia,Canada,Japan,USA,Spain">
  <li>${country}</li>
</c:forEach>
</ul>

<h3>ForTokens petlja za iteriranje kroz listu stavki odvojenih proizvoljnim delimiteorm</h3>
<ul>
<c:forTokens var="color"
items="(red (orange) yellow)(green)((blue# violet)"
delims="()#">
  <li><c:out value="${color}"/></li>
</c:forTokens>
</ul>


<h3>Ispis vrednosti varijable</h3>
<%
HashMap<String, String> mapa = new HashMap<String, String>();
mapa.put("jedan", "One");
pageContext.setAttribute("mapa", mapa);
%>
Vrednost za kljuc <code>jedan</code> HashMape <code>mapa</code> je: <br>
  ${mapa["jedan"]}<br>


<h3>DB tagovi </h3>

<sql:setDataSource 
driver="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost/test"
user="root"
password=""/>

<sql:query var="nastavnici" sql="SELECT * FROM nastavnici"/>

<c:forEach var="row" items="${nastavnici.rows}">
<ul>
<li><c:out value="${row.ime}"/>,
<c:out value="${row.prezime}"/> </li>
</ul>
</c:forEach>

<h4> Lista svih nastavnika koji imaju ime Pera</h4>

<sql:query  var="nastavnici" sql="SELECT * FROM nastavnici WHERE ime=?">
  <sql:param value="Pera"/>
</sql:query>

<c:forEach var="row" items="${nastavnici.rows}">
<ul>
<li><c:out value="${row.ime}"/>, ${row.prezime}</li>
</ul>
</c:forEach>

<h3>Formatiranje</h3>

<p>
<fmt:setLocale  value="en-US" />
<fmt:parseNumber value="1,034.5423" var="aNumber" pattern="#,###.##" /> <br/>
<fmt:formatNumber value="${aNumber}" type="number" minFractionDigits="2" maxFractionDigits="2" /> <br/>
<fmt:setLocale  value="sr" />
<fmt:formatNumber value="${aNumber}" type="number" minFractionDigits="2" maxFractionDigits="2" />
</p>

<p>
<fmt:formatNumber value="34.5423432426236" maxFractionDigits="5" minFractionDigits="3"/><br/>
<fmt:formatNumber value="34.5423432426236" type="currency" currencySymbol="€" />
</p>

<p>
<fmt:parseDate value="2/5/53" pattern="dd/MM/yy" var="homersBirthday"/>
<fmt:formatDate value="${homersBirthday}" dateStyle="full"/>
</p>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
