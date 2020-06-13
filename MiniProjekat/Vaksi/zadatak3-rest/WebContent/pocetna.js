function izmeniStatusZarazenosti(){
	alert("izmena statusa pocetna-index.html");
}

function dodajRedPacijenta(pacijent){
	let red = $('<tr></tr>');
	
	let tdBrojZdravstvenog = $('<td>' + pacijent.brojZdravstvenogOsiguranja+ '</td>');
	let tdIme = $('<td>' + pacijent.ime + '</td>');
	let tdPrezime = $('<td>' + pacijent.prezime + '</td>');
	let tdDatumaRodjenja = $('<td>' + pacijent.datumRodjenja+ '</td>');
	let tdPol = $('<td>' + pacijent.pol+ '</td>');
	let tdZdravstveniStatus = $('<td>' + pacijent.zdravstveniStatus+ '</td>');
	let tdStatusTesta = $('<td>' + '<button type="button" value = "promeniStatus" id="izmenaStatusa" onclick="izmeniStatusZarazenosti()"/>'+ '</td>');


	red.append(tdBrojZdravstvenog)
	.append(tdIme)
	.append(tdPrezime)
	.append(tdDatumaRodjenja)
	.append(tdPol)
	.append(tdZdravstveniStatus)
	.append(tdStatusTesta);
	
	$('#tabela tbody').append(red);
}


$(document).ready(function(){
	// Ucitavanje
	$('form#restFORMA').submit(function(event){
		event.preventDefault();

		let brojZdravstvenog = $('input[name="brojZdravstvenog"]').val();
		let ime = $('input[name="imePacijenta"]').val();
		let prezime = $('input[name="prezimePacijenta"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		let polPacijenta = $('select[name="polPacijenta"]').val();
		let zdravstveniStatus = $('select[name="zdravstveniStatus"]').val();
		
		let url = 'rest/pacijenti/dodaj';


		$.ajax({
		    type: 'PUT',
		    url: url,
		    contentType: 'application/json',
		    data: JSON.stringify(
				{
					brojZdravstvenogOsiguranja: brojZdravstvenog,
					ime: ime,
					prezime: prezime,
					datumRodjenja: datumRodjenja,
					pol:polPacijenta,
					zdravstveniStatus: zdravstveniStatus,
				}),
		    success: function(vraceniPacijent) {
				dodajRedPacijenta(vraceniPacijent);
				location.href = "http://localhost:8080/PocetniREST/pacijentiREST.html";
			}
		})

		//alert("stiglo je ime iz forme: " + ime);
	});
	
	$( "#izmenaStatusa").click(function(){
		alert("izmena statusa brale");
	});
});