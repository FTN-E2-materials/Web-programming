

















function dodajRedStudenta(student){
    let red = $('<tr></tr>');
	
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
    // Ucitavanje
	$('form#restFORMA').submit(function(event){
        event.preventDefault();
        
        let brojIndeksaInput = $('input[name="brojIndeksa"]').val();
        let imeInput = $('input[name="ime"]').val();
        let prezimeInput = $('input[name="prezime"]').val();
        let bodoviInput = $('input[name="bodovi"]').val();
        let bodoviIntInput = parseInt(bodoviInput, 10);

        let url = 'rest/users/dodaj';

        $.ajax({
		    type: 'PUT',
		    url: url,
		    contentType: 'application/json',
		    data: JSON.stringify(
				{
                    brojIndeksa: brojIndeksaInput,
                    ime: imeInput,
                    prezime: prezimeInput,
                    bodovi: bodoviIntInput
				}),
		    success: function(vraceniStudent) {
				dodajRedStudenta(vraceniStudent);
				location.href = "http://localhost:8080/PocetniREST/studentiREST.html";
			}
		})

    });

});