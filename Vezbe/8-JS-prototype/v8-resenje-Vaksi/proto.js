
// Konstruktor klase Korisnik
function Korisnik(ime, prezime, lozinka){
	this.ime = ime;
	this.prezime = prezime;
	this.lozinka = lozinka;
};

Korisnik.prototype.ispis = function(tekst){
	// sa prototype smo rekli da svaki Korisnik videti tu funkciju
	var p = document.querySelector('#poruka');                  // daje nam prvi element koji ima ID 'poruka'
	p.innerText = tekst;                                        // da gadjamo klasu, stavili bi .poruka
};

Korisnik.prototype.stampaj = function(){
	// call metoda funkcije kao prvi parametar prima objekat koji će
	// biti referenca 'this' unutar funkcije. Npr. ako se kao prvi
	// argument prosledi {ime: 'pera'}, unutar funkcije ispis 'this'
	// će biti referenca na {ime: 'pera'}
	Korisnik.prototype.ispis.call(this, 'Hello ' + this.ime + '!');
};

// Maloprodajni korisnik nasledjuje Korisnika, ali za metode moramo preko Object.create()
function MaloprodajniKorisnik(ime, prezime, lozinka){
	Korisnik.call(this, ime, prezime, lozinka);	// kao super()
}

// Maloprodajni korisnik nasledjuje metode Korisnika
MaloprodajniKorisnik.prototype = Object.create(Korisnik.prototype);		// deep copy prosledjenog objekta

// Zatim za konstruktor klase MaloprodajniKorisnik postavljamo prethodno definisanu funkciju MaloprodajniKorisnik: 
MaloprodajniKorisnik.prototype.constructor = MaloprodajniKorisnik;

//redefinisanje metode ispis u MPKorisniku
MaloprodajniKorisnik.prototype.ispis = function(input){		// override f-je ispis
	let vrednost = 'TODO';
	let poruka = 'Cena narudzbine je: ' + vrednost;
	Korisnik.prototype.ispis.call(this, poruka);
}



// TODO 1: VPKorisnik, koji nasledjuje atribute i metode Korisnika.
function VeleprodajniKorisnik(ime, prezime, lozinka) {
	Korisnik.call(this, ime, prezime, lozinka);
}

VeleprodajniKorisnik.prototype = Object.create(Korisnik.prototype);
VeleprodajniKorisnik.prototype.constructor = VeleprodajniKorisnik;

//redefinisanje metode stampaj u VPKorisniku
VeleprodajniKorisnik.prototype.stampaj = function(input){		// override f-je stampaj
	let vrednost = 'TODO';
	let poruka = 'Cena narudzbine uz 15% iznosi: ' + vrednost;
	Korisnik.prototype.ispis.call(this, poruka);
}
