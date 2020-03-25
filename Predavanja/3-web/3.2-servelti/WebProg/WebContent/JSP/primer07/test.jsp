<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=utf-8">
<title>Primer EL</title>
</head>
<body>
<p>Ako se na stranici ne ispisuju vrednosti, prijavite se na sistem
na stranici <a href="../primer06/login.jsp">za prijavu </a>
(proba/proba)</p>

<h3>Pristup bean-ovima i njihovim atributima</h3>
<p>Adresa trenutno prijavljenog korisnika je:
${user.address.street} ${user.address.number}<br>
Adresa trenutno prijavljenog korisnika je: ${user.address}<br>

Prvi string iz niza <code>user.niz1</code> je: ${user.niz1[0]}<br>
Prvi string iz kolekcije <code>user.niz2</code> je: ${user.niz2[0]}<br>
v1: Vrednost za kljuc <code>jedan</code> HashMape <code>user.mapa</code>
je: ${user.mapa["jedan"]}<br>
v2: Vrednost za kljuc <code>jedan</code> HashMape <code>user.mapa</code>
je: ${user.mapa.jedan}<br>
</p>

<h3>Predefinisane promenljive</h3>
<p>Vrednost parametra <code>proba</code> je: ${param.proba}<br>
Vrednost parametra <code>proba</code> je: ${paramValues.proba[0]}<br>
<a href="test.jsp?proba=3">Link na stranicu sa parametrom proba=3</a><br>

Vrednost parametra <code>User-Agent</code> u zaglavlju HTTP zahteva je:
${header["User-Agent"]}<br>

Vrednost cookie-a <code>cookie.JSESSIONID.value</code> je:
${cookie.JSESSIONID.value}<br>

Vrednost inicijalizacionog parametra (tag <code>context-param</code> u <code>web.xml</code>)
fajlu je: ${initParam.inicijalizacioniParametar}<br>

<code>sessionScope</code> predefinisana varijala: prvi string iz niza <code>sessionScope.user.niz1</code>
je: ${sessionScope.user.niz1[0]}<br>
</p>
<h3>Funkcije</h3>
<p><a href="../primer08/test2.jsp">Funkcije</a> <br>
</p>

<h3>Operatori</h3>
<table border="1">
	<tr>
		<th>EL</th>
		<th>Rezultat</th>
	</tr>
	<tr>
		<td>\${1 &lt; 2}</td>
		<td>${1 < 2}</td>
	</tr>
	<tr>
		<td>\${1 lt 2}</td>
		<td>${1 lt 2}</td>
	</tr>
	<tr>
		<td>\${1 &gt; (4/2)}</td>
		<td>${1 > (4/2)}</td>
	</tr>
	<tr>
		<td>\${1 &gt; (4/2)}</td>
		<td>${1 > (4/2)}</td>
	</tr>
	<tr>
		<td>\${4.0 &gt;= 3}</td>
		<td>${4.0 >= 3}</td>
	</tr>
	<tr>
		<td>\${4.0 ge 3}</td>
		<td>${4.0 ge 3}</td>
	</tr>
	<tr>
		<td>\${4 &lt;= 3}</td>
		<td>${4 <= 3}</td>
	</tr>
	<tr>
		<td>\${4 le 3}</td>
		<td>${4 le 3}</td>
	</tr>
	<tr>
		<td>\${100.0 == 100}</td>
		<td>${100.0 == 100}</td>
	</tr>
	<tr>
		<td>\${100.0 eq 100}</td>
		<td>${100.0 eq 100}</td>
	</tr>
	<tr>
		<td>\${(10*10) != 100}</td>
		<td>${(10*10) != 100}</td>
	</tr>
	<tr>
		<td>\${(10*10) ne 100}</td>
		<td>${(10*10) != 100}</td>
	</tr>
</table>
<br/>

<p><a href="../index.html">Nazad</a></p>

</body>
</html>
