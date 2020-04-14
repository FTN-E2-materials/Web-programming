<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<script src="../../jquery.js" ></script>
</head>
<body>
<h1>JavaScript WebStorage API demo</h1>
<div id="container">

	<button id="init">init</button> <br />
	<button id="read">read</button> <br/>
	<div id="content" ></div>	
</div>
<p></p>
<a href="../index.html">Nazad</a>

<script>
$(document).ready(function() {

	  if(!(window.localStorage )){
	  	$('#content, input, button, #examples').fadeOut("fast");
	  	$('<p>Oh no, you need a browser that supports WebStorage API. How about <a href="http://www.google.com/chrome">Google/a>?</p>').appendTo('#container');
	  } else {
			$("#init").click(function() {
				// use localStorage for persistent storage
				// use sessionStorage for per tab storage
				window.localStorage.setItem('value', "TEKST");
				window.localStorage.setItem('timestamp', new Date());
	  		});
			$("#read").click(function() {
				$("#content").append("<li>"+ 
						'value: ' + window.localStorage.getItem('value') + ", " + 
						'timestamp: ' + window.localStorage.getItem('timestamp') +
						 "</li>");
			});	  
	  }
	});
</script>
<body>
</html>