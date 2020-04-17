/* 
    Bitno je znati, da moramo sacekati da se DOM stablo prvo KREIRA
    da bi dalje mogli da radimo nesto sa njim.
*/

window.onload = function(event) {

	/* 
		TODO 2: Preuzeti parametre forme iz registracija.html i dodati ih
	 	u listu korisnika u tabeli registrovani_korisnici.html. 
	 	Trenutna putanja se nalazi u promenljivoj window.location.href i treba
	 	je parsirati kao URL kod HTTP zahteva. 
	*/
	let url = window.location.href;
	let urlParts = url.split('?');
	let parameters = urlParts[1].split('&');
	let ime = parameters[0].split('=')[1];
	let prezime = parameters[1].split('=')[1];
	let jmbg = parameters[2].split('=')[1];

	// Lista: [objekat1, objekat2 ...]
	// Objekat: {'polje': vrednost}
	let users = [
		{'ime': 'Petar', 'prezime': 'Petrović', 'jmbg': '123321'},
		{'ime': 'Marko', 'prezime': 'Marković', 'jmbg': '111111'},
		{'ime': 'Stevan', 'prezime': 'Stevanović', 'jmbg': '123456'},
		{'ime': 'Stevan', 'prezime': 'Stevanović', 'jmbg': '123456'},
		{'ime': ime, 'prezime': prezime, 'jmbg': jmbg}	
	];

	let tabela = document.getElementsByTagName('table')[0];
    /* Mogli smo i ovako*/
    // let tabela = document.getElementById('tabela')[0];

    /* Za svakog usera kreiramo novi red. */
	for(user of users) {
		let userTr = document.createElement('tr');                   // kreiramo prvo red
		let imeTd = document.createElement('td');
		let prezimeTd = document.createElement('td');
		let jmbgTd = document.createElement('td');

		imeTd.appendChild(document.createTextNode(user.ime));
		prezimeTd.appendChild(document.createTextNode(user.prezime));
		jmbgTd.innerHTML = user.jmbg;

		userTr.appendChild(imeTd);
		userTr.appendChild(prezimeTd);
		userTr.appendChild(jmbgTd);

		tabela.appendChild(userTr);                                 // na kraju u tabelu dodajemo red
	}
};

