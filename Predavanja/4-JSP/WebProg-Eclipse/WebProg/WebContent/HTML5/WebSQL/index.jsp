<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<script src="../../jquery.js" ></script>
</head>
<body>
<h1>JavaScript WebSQL demo</h1>
<div id="container">
	<button id="init">init db</button> <br />
	<input type="text" id="searchJmbg" placeholder="JMBG" value="1234567890123"/><button id="bJmbg">search</button> <br />
	<input type="text" id="searchIme" placeholder="ime" value="Pera" /><button id="bIme">search</button> <br />
	<div id="result"></div>	<br />
</div>
<p></p>
<a href="../index.html">Nazad</a>

<script>
$(document).ready(function() {

	  if(!(window.openDatabase)){
	  	$('#result, input, button, #examples').fadeOut("fast");
	  	$('<p>Oh no, you need a browser that supports WebSQL API. How about <a href="http://www.google.com/chrome">Google/a>?</p>').appendTo('#container');
	  } else {
		try {
	        var shortName = 'studenti';
	        var version = '1.0';
	        var displayName = 'Studenti Database';
	        var maxSize = 65536; // in bytes
			var db = openDatabase(shortName, version, displayName, maxSize);
		} catch (e) {
			  alert("Ne mogu da otvorim bazu: " + e);
		}

		db.transaction(function (tx) {  
			   tx.executeSql(
			'CREATE TABLE IF NOT EXISTS studenti (jmbg unique, ime, prezime)');
		});
				  
	  	$("#init").click(function (e) {
			db.transaction(function (tx) {
				   tx.executeSql('INSERT INTO studenti (jmbg, ime, prezime) VALUES (?, ?, ?)', 
						   ["1234567890123", "Pera", "Perić" ]);
				   tx.executeSql('INSERT INTO studenti (jmbg, ime, prezime) VALUES (?, ?, ?)', 
						   ["9876543210123", "Mika", "Mikić" ]);
			});
		});
		
		$("#bJmbg").click(function(e) {
			db.transaction(function (tx) {
				   var jmbg = $("#searchJmbg").val();
				   tx.executeSql('SELECT * FROM studenti WHERE jmbg LIKE ?', [jmbg], function (tx, results) {
				   var len = results.rows.length, i;
				   $("#result").empty();
				   $("#result").append("<p>Found rows: " + len + "</p>");
				   for (i = 0; i < len; i++){
					   var student = results.rows.item(i);
					   $("#result").append("<li>" + student.ime + " " + student.prezime + "</li>");
				   }
				 }, null);
			});
		});

		$("#bIme").click(function(e) {
			db.transaction(function (tx) {
				   var ime = $("#searchIme").val();
				   tx.executeSql('SELECT * FROM studenti WHERE ime like ? OR prezime like ?', ['%'+ime+'%', '%'+ime+'%'], function (tx, results) {
				   var len = results.rows.length, i;
				   $("#result").empty();
				   $("#result").append("<p>Found rows: " + len + "</p>");
				   for (i = 0; i < len; i++){
					   var student = results.rows.item(i);
					   $("#result").append("<li>" + student.ime + " " + student.prezime + "</li>");
				   }
				 }, null);
			});
		}); 
		 
	  }
	});
</script>
</body>
</html>