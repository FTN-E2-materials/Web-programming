<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
		<style type="text/css">
			h1 { 
				color: LightBlue;
			}
			label {
 			   display: inline-block;
  			   width: 200px;
  			   text-align: left;
			}
			.s{
			position:absolute;
			margin-left: 205px;
			width: 150px;
	
			  text-align: center;
			 }
		</style>
	<title>
		Prijava pacijenata [COVID 19]
	</title>
</head>
<body>
	 <form action="http://localhost:8181/ServletJSP/Servlet" method="post">
		<div>
		<h1> Prijava pacijenata [COVID-19] </h1>
	
		<label  for="brojZdravstvenogOsiguranja">Broj zdravstvenog osiguranja:</label>
		<input  type="text" name="brojZdravstvenogOsiguranja"/> <br>

		<label for="imePacijenta">Ime pacijenta:</label>
		<input type="text" name="imePacijenta"/> <br>
		
		
		<label for="prezimePacijenta">Prezime pacijenta:</label>
		<input type="text" name="prezimePacijenta"/><br>
		
		<label for="datumRodjenjaPacijenta">Datum rodjenja</label>
		<input type="text" name="datumRodjenjaPacijenta"/><br>
		
		<label for="pol">Pol</label>
  		<select name="pol">
		   <option value="muski">muski</option>
		   <option value="zenski">zenski</option>
 		</select><br>
 		
 		<label for="zdravstveniStatus">Zdravstveni status</label>
  		<select name="zdravstveniStatus">
  		   <option value="IZLECEN">IZLECEN</option>
  		   <option value="SA_SIMPTOMIMA">SA_SIMPTOMIMA</option>
		   <option value="ZARAZEN">ZARAZEN</option>
		   <option value="BEZ_SIMPTOMA">BEZ_SIMPTOMA</option>
 		</select><br>
		
		
		<input class="s" type="submit" Value="Posalji"/><br>
		
	
		</div>
	 </form>
	
		
		
</body>
</html>