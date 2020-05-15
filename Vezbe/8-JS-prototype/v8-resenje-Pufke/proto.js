function Korisnik(ime, prezime, lozinka){
    this.ime = ime;
    this.prezime = prezime;
    this.lozinka = lozinka;
};

Korisnik.prototype.ispis = function(tekst){
    var p = document.querySelector('#poruka');
    p.innerText = tekst;
    };
    Korisnik.prototype.stampaj = function(){
    // call metoda funkcije kao prvi parametar prima objekat koji će
    // biti referenca 'this' unutar funkcije. Npr. ako se kao prvi
    // argument prosledi {ime: 'pera'}, unutar funkcije ispis 'this'
    // će biti referenca na {ime: 'pera'}
    Korisnik.prototype.ispis.call(this, 'Hello ' + this.ime + '!');
    };

//Nasledjianje 

function MaloprodajniKorisnik(ime, prezime){
    Korisnik.call(this,ime,prezime);
}
//Da bi nasledili metode moramo postaviti i kopiju prototipa Korisnik
MaloprodajniKorisnik.prototype = Object.create(Korisnik.prototype);

MaloprodajniKorisnik.prototype.constructor = MaloprodajniKorisnik;

//Override metode 
MaloprodajniKorisnik.prototype.ispis = function(input){
    let vrednost = 'TODO';
    let poruka = 'Cena narudžbine je: ' + vrednost;
    Korisnik.prototype.ispis.call(this, poruka);
}

//Zadatak 1 
// Definisati klasu VeleprodajniKorisnik koja nasleđuje klasu Korisnik

function VeleprodajniKorisnik(ime ,prezime){
    Korisnik.call(this, ime, prezime);
}