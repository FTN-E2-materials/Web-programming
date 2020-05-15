window.onload = function(){
	let queryParams = window.location.search;
	if(!queryParams){
		window.location = 'login.html';
	}
	let korisnik = null;
	// Ubaciti logiku za kreiranje korisnika
	let prviParametar = queryParams.split('&')[0];
	let ime = prviParametar.split('=')[1];			// uzimamo vrednost posle jednako

	if (korisnik) {
		korisnik.stampaj();
	}
}