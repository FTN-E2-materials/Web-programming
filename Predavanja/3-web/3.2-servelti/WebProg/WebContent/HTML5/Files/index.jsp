<html>
<head>
	<script src="../../jQuery/jquery-1.11.0.js" ></script>
</head>
<body>
<h1>JavaScript File API demo</h1>
<div id="container">
	<label>Upload files to local repository:</label>
	<input type="file" id="uploaded" multiple="multiple"/>
	<div id="fileInfo" ></div>
	
</div>
<p></p>
<a href="../index.html">Nazad</a>

<script>
$(document).ready(function() {

	  if(!(window.File && window.FileReader && window.FileList && window.Blob)){
	  	$('#fileContent, input, button, #examples').fadeOut("fast");
	  	$('<p>Oh no, you need a browser that supports File API. How about <a href="http://www.google.com/chrome">Google/a>?</p>').appendTo('#container');
	  } else {
			  $("#uploaded").change(function (e) {
					var files = e.target.files; // FileList object
			    	// files is a FileList of File objects. List some properties.
				    var output = [];
				    for (var i = 0, f; f = files[i]; i++) {
				      output.push('<li><strong>', escape(f.name), 
						      	'</strong> (', f.type || 'n/a', ') - ',
				                f.size, ' bytes, last modified: ',
				                f.lastModifiedDate ? f.lastModifiedDate.toLocaleDateString() : 'n/a',
				                '</li>');



				      var reader = new FileReader();
				      reader.onload = function(e) {
				    	  $("#fileInfo").append("<li> Sadrzaj fajla: " + e.target.result + "</li>");
				      };
				      reader.readAsText(f);
				    }
				    $("#fileInfo").append('<ul>' + output.join('') + '</ul>');
			  });
	  }
	});
</script>
<body>
</html>