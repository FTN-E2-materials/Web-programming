//Ubaciti objekte za korisnike ovde. Kljuc je korisničko ime.
//Korisnici imaju ime, prezime i lozinku.

//TODO 1: Na stranici login.html omoguciti logovanje dva korisnika (lozinke proizvoljne):  
// - Pera Peric 
// - Mika Mikic


var usersMap = {
	"pera" : new Korisnik("Pera", "Perić", "peric"),
	"mika" : new Korisnik("Mika", "Mikić", "mikic")
};

function valid(){
	let poruka = document.querySelector("#poruka");
	
	let imeEl = document.getElementsByName('ime')[0];
	let ime = imeEl.value;
	if (!ime) {
		poruka.innerText = "Morate uneti korisničko ime.";
		return false;
	}

	if (!(ime in usersMap)) {
		poruka.innerText = "Ne postoji korisnik sa unetim korisničkim imenom.";
		return false;
	}

	let lozinkaEl = document.getElementsByName('lozinka')[0];
	let lozinka = lozinkaEl.value;
	if (!lozinka) {
		poruka.innerText = "Morate uneti lozinku.";
		return false;
	}

	let kor = usersMap[ime];
	if (kor.lozinka != lozinka) {
		poruka.innerText = "Neispravna lozinka.";
		return false;
	}


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