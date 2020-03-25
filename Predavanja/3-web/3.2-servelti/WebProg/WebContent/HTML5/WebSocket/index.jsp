<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<script src="../../jquery.js" ></script>
</head>
<body>
<h1>JavaScript web socket demo</h1>
<div id="container">
	<label>Enter text:</label><input type="text" id="text"/>
	<input type="button" id="send" value="send" />
	
	<p></p>
	
	<div id="chatLog" ></div>

</div>

<p></p>
<a href="../index.html">Nazad</a>

<script>
$(document).ready(function() {
	  var socket;
      function send(){
          var text = $('#text').val();

          if(text==""){
              message('<p>Please enter a message');
              return ;
          }
          try{
              socket.send(text);
              message('<p>Sent: '+text);
          } catch(exception){
             message('<p>Error: ' + exception);
          }
      }

      function message(msg){
      	console.log(msg);
        $('#chatLog').append(msg+'</p>');
      }

      $('#text').keypress(function(event) {
          if (event.keyCode == '13') {
            send();
          }
      });	

      $('#send').click(function(){
         send();
      });

      if(!("WebSocket" in window)){
	  	$('#chatLog, input, button, #examples').fadeOut("fast");
	  	$('<p>Oh no, you need a browser that supports WebSockets. How about <a href="http://www.google.com/chrome">Google/a>?</p>').appendTo('#container');
	  } else {
          var host = "ws://localhost:8080${pageContext.request.contextPath}/websocket/echoAnnotation";
          try{
              socket = new WebSocket(host);
              message('<p>connect: Socket Status: '+socket.readyState);

              socket.onopen = function(){
             	 message('<p>onopen: Socket Status: '+socket.readyState+' (open)');
              }

              socket.onmessage = function(msg){
             	 message('<p>onmessage: Received: '+msg.data);
              }

              socket.onclose = function(){
              	message('<p>onclose: Socket Status: '+socket.readyState+' (Closed)');
              	socket = null;
              }			

          } catch(exception){
             message('<p>Error'+exception);
          }

	  }//End else

	});
</script>
	
</body>
</html>