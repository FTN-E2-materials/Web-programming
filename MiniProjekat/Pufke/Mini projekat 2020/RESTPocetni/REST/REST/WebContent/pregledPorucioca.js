
function clickClosure(){
	return function() {
		setTimeout(function(){
			location.reload();
		}, 500);
		
	};
}


$(document).ready(function() {

	$.get({
		url: 'rest/porucioci/getPorucioci',
		success: function(porucioci) {
			for (let p of porucioci) {
				addPoruciocTr(p);
			}//Dobra fora za reload page-a samo jednom
			if (window.location.href.indexOf('reload')==-1) {
			     window.location.replace(window.location.href+'?reload');
			}
		}
	
	});
	
	function addPoruciocTr(porucioc) {
		let tdCenaIzrade = $('<td>' + porucioc.cenaIzrade + '</td>');
		
		if(parseInt(porucioc.cenaIzrade)<100){
			var tr = $('<tr id="CENA"></tr>');
		}else{
			var tr = $('<tr></tr>');
		}
		
		
		let tdBR = $('<td>' + porucioc.brojIzrade + '</td>');
		let tdIme = $('<td>' + porucioc.imeNarucioca + '</td>');
		let tdBrtel = $('<td>' + porucioc.brojTelefona + '</td>');
		let tdNazFoldera = $('<td>' + porucioc.nazivFoldera + '</td>');
		let tdformatFoto = $('<td>' + porucioc.formatFotografija + '</td>');
	
		
		let tdHref = $('<td class="'+porucioc.statusIzrade +'"> <a href="http://localhost:8181/PocetniREST/rest/porucioci/Gotovo?porucioc.brojIzrade='+porucioc.brojIzrade+'">Gotovo</a></td>');
		tr.append(tdBR).append(tdIme).append(tdBrtel).append(tdNazFoldera).append(tdformatFoto).append(tdCenaIzrade).append(tdHref);
		tr.click(clickClosure());
		$('#tabela tbody').append(tr);
	}

});

function pretraga(){
	
	 var nijenasao = true;
	  var input, filter, table, tr, td, i, txtValue;
	  input = document.getElementById("pretraga");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tabela");
	  tr = table.getElementsByTagName("tr");

	  
	  pretragaElementi =  document.getElementsByClassName("pretragaElementi");
	  document.getElementsByTagName("th")[6].style.display = "none";
	  
	  // Loop through all table rows, and hide those who don't match the search query
	
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
	        nijenasao = false;
	      }
	    }
	  }
	  
	  if(nijenasao){
		  document.getElementById("nijeNasao").textContent   = "Ne postoji izrada sa trazenim naruciocem!";
	  
	  }


	
}
