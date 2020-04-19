window.onload = function(){
	let queryParams = window.location.search;
	if(!queryParams){
		window.location = 'login.html';
	}
	let korisnik = null;
	// Ubaciti logiku za kreiranje korisnika
	if (korisnik) {
		korisnik.stampaj();
	}
}