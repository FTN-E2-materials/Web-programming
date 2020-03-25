<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.ArrayList" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE> Demolition company</TITLE>
</HEAD>
<BODY>
<%@ include file="navbar.jsp"%>
<h1>NS Blaster Inc.</h1>
<p> We demolish, you pay! </p>
<p>
<%
  ArrayList <String> v = new ArrayList <String>();
  v.add("We");
  v.add("demolish,");
  v.add("you");
  v.add("pay!");

  for (int i = 0; i < v.size(); i++) {
%>
  <%= v.get(i) %>
<%
  }
%>
</p>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
