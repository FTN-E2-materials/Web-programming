//Ubaciti objekte za korisnike ovde. Kljuc je korisničko ime.
//Korisnici imaju ime, prezime i lozinku.
var usersMap = {
	"pera" : new Korisnik("Pera", "Perić", "p"),
	"mika" : new Korisnik("Mika", "Mikić", "m")
};

function valid(){

    return true;
}

// Sav kod koji obrađuje DOM stablo se mora nalaziti u ovoj funkciji, jer DOM objekti postoje tek kada se ucita stranica. 
window.addEventListener('load', function(){
	let form = document.forms[0];
	// TODO: Dodaj obrađivač događaja slanja sa forme.
	form.addEventListener('submit', function(event) {
		if(valid()) {
			return;
		}
		// Obustavlja podrazumevano ponašanje događaja, što je prelazak na
		// drugu stranicu.
		event.preventDefault();
	});
});