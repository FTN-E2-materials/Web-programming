
function clickClosure(){
	return function() {
		setTimeout(function(){
			location.reload();
		}, 500);
		
	};
}


$(document).ready(function() {

	$.get({
		url: 'rest/pacijenti/getPacijenti',
		success: function(pacijenti) {
			for (let p of pacijenti) {
				 addPacijentTr(p);
			}//Dobra fora za reload page-a samo jednom
			if (window.location.href.indexOf('reload')==-1) {
			     window.location.replace(window.location.href+'?reload');
			}
		}
	
	});
	
	function addPacijentTr(pacijent) {
		let tr = $('<tr id='+pacijent.zdravstveniStatus+'></tr>');
		let tdBR = $('<td>' + pacijent.brZdravstvenogOsig + '</td>');
		let tdIme = $('<td>' + pacijent.ime + '</td>');
		let tdPrezime = $('<td>' + pacijent.prezime + '</td>');
		let tddatumRodjenja = $('<td>' + pacijent.datumRodjenja + '</td>');
		let tdpol = $('<td>' + pacijent.pol + '</td>');
		let tdzdravstveniStatus = $('<td>' + pacijent.zdravstveniStatus + '</td>');
		let tdHref = $('<td> <a href="http://localhost:8181/PocetniREST/rest/pacijenti/TestPozitivan?brZdravstvenogOsig='+ pacijent.brZdravstvenogOsig +'">Test je pozitivan!</a></td>');
		tr.append(tdBR).append(tdIme).append(tdPrezime).append(tddatumRodjenja).append(tdpol).append(tdzdravstveniStatus).append(tdHref);
		tr.click(clickClosure());
		$('#tabela tbody').append(tr);
	}

});

function pretraga(){
	

	  var input, filter, table, tr, td, i, txtValue;
	  input = document.getElementById("pretraga");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tabela");
	  tr = table.getElementsByTagName("tr");

	  document.getElementById("naslov").innerHTML = "HTTP. Pretraga pacijenata [COVID-19]";
	  pretragaElementi =  document.getElementsByClassName("pretragaElementi");
	  document.getElementsByTagName("th")[6].style.display = "none";
	  
	  // Loop through all table rows, and hide those who don't match the search query
	
	  for (i = 1; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
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
