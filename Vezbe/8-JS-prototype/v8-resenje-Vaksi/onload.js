window.onload = function(){
	let queryParams = window.location.search;

	// TODO 2.1: Ukoliko u URLu na stranici webshop.html ne postoje podaci o korisniku, prebaciti se login.html
	if(!queryParams){
		window.location = 'login.html';
	}

	// TODO 2.2: Prilikom učitavanja stranice preuzeti  korisnika iz URLa i u zavisnosti od logovanog korisnika, kreirati objekat korisnik tipa
	let korisnik = null;
	let prviParametar = queryParams.split('&')[0];
	let ime = prviParametar.split('=')[1];			// uzimamo vrednost posle jednako


	// if(ime === 'pera'){
	// 	korisnik = new MaloprodajniKorisnik('Pera','Peric');
	// }else if(ime === 'mika'){
	// 	korisnik = new VeleprodajniKorisnik('Mika', 'Mikic');
	// }


	// Ubaciti logiku za kreiranje korisnika
	if (korisnik) {
		korisnik.stampaj();
	}
}