
function pretraga(){
	
		  var input, filter, table, tr, td, i, txtValue;
		  input = document.getElementById("pretraga");
		  filter = input.value.toUpperCase();
		  table = document.getElementById("tabelaPacijenata");
		  tr = table.getElementsByTagName("tr");

		  pretragaElementi =  document.getElementsByClassName("pretragaElementi");
		  pretragaElementi[0].style.display = "none";
		  pretragaElementi[1].style.display = "none";
		  pretragaElementi[2].style.display = "none";
		  pretragaElementi[3].style.display = "none";
		  
		  // Loop through all table rows, and hide those who don't match the search query
		  tr[0].getElementsByTagName("td")[6].style.display = "none";
		  for (i = 1; i < tr.length; i++) {
		    td = tr[i].getElementsByTagName("td")[1];
		    if (td) {
		      txtValue = td.textContent || td.innerText;
		      if (txtValue.toUpperCase().indexOf(filter) > -1) {
		        tr[i].style.display = "";
		        tr[i].getElementsByTagName("td")[6].style.display = "none";
		   
		      } else {
		        tr[i].style.display = "none";
		        tr[i].getElementsByTagName("td")[6].style.display = "none";
		      
		      }
		    }
		  }

		
}