// globalna gde cuvam sve pacijente po ucitavanju
var sviPacijenti = [];

function izmeniStatusZarazenosti(idx){
	$('table tbody tr#'+idx).css('background-color','lightgrey');
	//pacijenti[idx-1].brojZdravstvenogOsiguranja="9999999999999";
}

function crtanjeTabele(pacijenti){
	// ukloniti moram prvo  sve redove iz tabele sa idom #tabela
	$("#tabela tbody").empty();

	let index = 1;
	for(let pacijent of pacijenti){
		dodajRedPacijenta(pacijent,index);
		index+=1;
	}
}

function clickIzmeneStatusa(pacijent, idx){
	return function(){
		

		//napravim izmenu na beku, i dobavim sada kako izgledaju pacijenti
		
		let url = 'rest/pacijenti/izmena'

		$.ajax({
		    type: 'PUT',
		    url: url,
		    contentType: 'application/json',
		    data: JSON.stringify(
				{
					brojZdravstvenogOsiguranja: pacijent.brojZdravstvenogOsiguranja,
					ime: pacijent.ime,
					prezime: pacijent.prezime,
					datumRodjenja: pacijent.datumRodjenja,
					pol:pacijent.pol,
					zdravstveniStatus: pacijent.zdravstveniStatus,
				}),
		    success: function(vraceniPacijenti) {

				// onda nacrtam tu novu tabelu
				crtanjeTabele(vraceniPacijenti);
				$('table tbody tr#'+idx).css('background-color','lightgrey');
			}
		})

		// i na korespodentnom indeksu promenim boju
		// ovo ne radi jer je tabela prazna, ajax poziv je asinhron i nece uvek ovo da se odradi nakon poziva
		//$('table tbody tr#'+idx).css('background-color','lightgrey');
		

	}
}

function dodajRedPacijenta(pacijent,index){
	let red;
	if(pacijent.zdravstveniStatus == 'SA_SIMPTOMIMA'){
		red = $('<tr style=\"background-color:lightgrey;\" id="' + index +'" </tr> ');
	}else{
		red = $('<tr id="' + index + '"> </tr>');
	}
	
	let tdBrojZdravstvenog = $('<td>' + pacijent.brojZdravstvenogOsiguranja+ '</td>');
	let tdIme = $('<td>' + pacijent.ime + '</td>');
	let tdPrezime = $('<td>' + pacijent.prezime + '</td>');
	let tdDatumaRodjenja = $('<td>' + pacijent.datumRodjenja+ '</td>');
	let tdPol = $('<td>' + pacijent.pol+ '</td>');
	let tdZdravstveniStatus = $('<td>' + pacijent.zdravstveniStatus+ '</td>');
	//let tdStatusTesta = $('<td>' + '<button type="button" value = "promeniStatus" id="izmenaStatusa" onclick="izmeniStatusZarazenosti('+index+')" > Test je pozitivan </button>'+ '</td>');
	let tdStatusTesta = $('<td>' + '<button type="button" value = "promeniStatus" id="izmenaStatusa"> Test je pozitivan </button>'+ '</td>');
	tdStatusTesta.click(clickIzmeneStatusa(pacijent, index));


	red.append(tdBrojZdravstvenog)
	.append(tdIme)
	.append(tdPrezime)
	.append(tdDatumaRodjenja)
	.append(tdPol)
	.append(tdZdravstveniStatus)
	.append(tdStatusTesta);
	
	$('#tabela tbody').append(red);
}

function pretragaPacijenata(unetiKarakteri){
	let filtriraniPacijenti = []

	for(let i = 0; i < sviPacijenti.length; i++){
		unetiKarakteri = unetiKarakteri.toLowerCase();
		let brojZdravstvenogOsiguranja = sviPacijenti[i].brojZdravstvenogOsiguranja.toLowerCase();

		if(brojZdravstvenogOsiguranja.includes(unetiKarakteri)){
			filtriraniPacijenti.push(sviPacijenti[i])
		}
	}

	return filtriraniPacijenti;
}


$(document).ready(function(){
	
	// dobavljanje svih pacijenata i inicijalno ucitavanje
	$.get({
		url: 'rest/pacijenti/ucitavanje',
		contentType: 'application/json',
		success: function(vraceniPacijenti){
			sviPacijenti=vraceniPacijenti;
			crtanjeTabele(vraceniPacijenti);
		}
	});
	

	// event na keyup, kada neko kuca odma proveravamo
	//$('#inputPretrage').on('keyup',function(){
	
	// event na submit
	$('#submitPretrage').on('click',function(){	
		// bzv ispis kako bih video da li imam ovde pristup svim pacijentima
		// console.log('pacijenti[0]: ' + sviPacijenti[0].ime);

		let unetiKarakteri = $('input[name="imeKorisnika"]').val();
		console.log('Uneto: ' + unetiKarakteri);
		
		let filtriraniPacijenti = pretragaPacijenata(unetiKarakteri);
		crtanjeTabele(filtriraniPacijenti)
	});

});
