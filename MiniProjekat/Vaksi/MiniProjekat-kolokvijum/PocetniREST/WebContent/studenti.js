// globalna gde cuvam sve studente po ucitavanju
var sviStudenti = [];

function crtanjeTabele(studenti){
	// ukloniti moram prvo  sve redove iz tabele sa idom #tabela
	$("#tabela tbody").empty();

	for(let student of studenti){
		dodajRedStudenta(student);
	}
}


function dodajRedStudenta(student){
    let red;
    if(student.bodovi < 51){
        red = $('<tr style = \"background-color:red;\"></tr>');
    }else{
        red = $('<tr></tr>');
    }
    
	
	let tdBrojIndeksa= $('<td>' + student.brojIndeksa+ '</td>');
	let tdIme = $('<td>' + student.ime + '</td>');
    let tdPrezime = $('<td>' + student.prezime + '</td>');
    let tdBodovi = $('<td>' + student.bodovi + '</td>');

	red.append(tdBrojIndeksa)
	.append(tdIme)
	.append(tdPrezime)
	.append(tdBodovi)
	
	$('#tabela tbody').append(red);


}



$(document).ready(function(){
	
	// dobavljanje svih pacijenata i inicijalno ucitavanje
	$.get({
		url: 'rest/users/ucitavanje',
		contentType: 'application/json',
		success: function(vraceniStudenti){
			sviStudenti=vraceniStudenti;
			crtanjeTabele(vraceniStudenti);
		}
    });
});