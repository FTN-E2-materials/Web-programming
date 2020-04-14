<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script src="../../jquery.js"></script>
</head>
<body>
	<h1>IndexedDB API demo</h1>
	<div id="container">
		<button id="init">init db</button>
		<br /> <input type="text" id="searchJmbg" placeholder="JMBG" value="1234567890123"/>
		<button id="bJmbg">search</button>
		<br /> <input type="text" id="searchIme" placeholder="ime" value="Pera" />
		<button id="bIme">search</button>
		<br />
		<div id="result"></div>
		<br />
	</div>
	<p></p>
	<a href="../index.html">Nazad</a>

	<script>
$(document).ready(function() {

	  if(!(window.indexedDB)){
	  	$('#result, input, button, #examples').fadeOut("fast");
	  	$('<p>Oh no, you need a browser that supports IndexedDB API. How about <a href="http://www.google.com/chrome">Google/a>?</p>').appendTo('#container');
	  } else {
		var request = window.indexedDB.open("Baza", 1);
		var db;
		request.onerror = function(e) {
			alert("Cannot open database!");
		};
		request.onupgradeneeded = function(e) {
			db = e.target.result;
			console.log("onupgradeneeded");
			if(!db.objectStoreNames.contains("studenti")) {
				console.log("onupgradeneeded, creating studenti");
                db.createObjectStore("studenti", { keyPath: "jmbg" });
            }
		};
		request.onsuccess = function(e) {
			db = e.target.result;
			console.log("onsuccess");
		};
	  
		$("#init").click(function (e) {
			const studenti = [
		                      { jmbg: "1234567890123", ime: "Pera", prezime: "Perić" },
		                      { jmbg: "9876543210123", ime: "Mika", prezime: "Mikić" }
		                 	 ];
            var studentiStore = db.transaction(["studenti"], "readwrite").objectStore("studenti");
			if (studentiStore != null) {
				for (var i in studenti) {
					studentiStore.add(studenti[i]);
				}
			} else {
				alert ("Ne mogu da dobijem store!");
			}
		});
		
		$("#bJmbg").click(function(e) {
               var studentiStore = db.transaction(["studenti"], "readwrite").objectStore("studenti");
               if (studentiStore != null) {
				 var searchText = $("#searchJmbg").val();
				 if (searchText != '') {
					 // pretraga po kljucu
					studentiStore.get(searchText).onsuccess = function (event) {
						$("#result").empty();
						var student = event.target.result;
						if (student != undefined) {
							$("#result").append('<li>' + student.ime + " " + student.prezime + '</li>');
						} else {
							$("#result").append('<li>nisam našao</li>');
						}
					};			 
				 }			 		
			   } else {
				 alert ("Ne mogu da dobijem store!");
			   }
		});

		$("#bIme").click(function(e) {
            var studentiStore = db.transaction(["studenti"], "readwrite").objectStore("studenti");
            if (studentiStore != null) {
				var searchText = $("#searchIme").val();
				if (searchText != '') {
					$("#result").empty();
					studentiStore.openCursor().onsuccess = function (event) {
						var cursor = event.target.result;
						if (cursor) {
							// iteriramo kroz bazu
							var student = cursor.value;
							if (student.ime.toUpperCase().indexOf(searchText.toUpperCase()) != -1 ||
								student.prezime.toUpperCase().indexOf(searchText.toUpperCase()) != -1
								) {
								$("#result").append('<li>' + student.ime + " " + student.prezime + '</li>');
							}					
							// nastaviće da iterira i pozvace ponovo onsuccess	
							cursor.continue();	
						} else {
							// kraj iteriranja
							$("#result").append('<li>-----</li>');
						}
					};			 
				}			 		
			} else {
				alert ("Ne mogu da dobijem store!");
			}
		}); 
		 
	  }
	});
</script>
</body>
</html>