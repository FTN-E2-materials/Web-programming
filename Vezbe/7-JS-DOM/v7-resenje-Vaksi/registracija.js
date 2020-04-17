// Ako funkcija vrati true, validacija je uspesna i forma šalje podatke
// Ako funkcija vrati false, validacija nije uspešna i browser ostaje na trenutnoj stranici

/*
    TODO 1: Validirati da forma nema prazna polja.
    TODO 3: Validirati da ime i prezime pocinju velikim slovom,
    i JMBG se satoji od 13 cifara.
*/
function validacija(forma){
    let uspesno = true;
    
    let imeEl = document.getElementsByName('ime')[0];
	let ime = imeEl.value;
	if(!ime || !isNaN(ime)) {
		imeEl.style.background = 'red';
		uspesno = false;
	} else if (ime[0] != ime[0].toUpperCase()) {
		imeEl.style.background = 'yellow';
		uspesno = false;
	} else {
		imeEl.style.background = 'white';
	}

	let prezimeEl = document.getElementsByName('prezime')[0];
	let prezime = prezimeEl.value;
	if(!prezime || !isNaN(prezime)) {
		prezimeEl.style.background = 'red';
		uspesno = false;
	} else if (prezime[0] != prezime[0].toUpperCase()) {
		prezimeEl.style.background = 'yellow';
		uspesno = false;
	} else {
		prezimeEl.style.background = 'white';
	}

	let jmbgEl = document.getElementsByName('jmbg')[0];
	let jmbg = jmbgEl.value;
	if(!jmbg) {
		jmbgEl.style.background = 'red';
		uspesno = false;
	} else if (jmbg.length != 13) {
		jmbgEl.style.background = 'yellow';
		uspesno = false;
	} else {
		jmbgEl.style.background = 'white';
	}

    let markaEl = document.getElementsByName('marka')[0];
	let marka = markaEl.value;
    if(marka == 1) {        // opcija koja ostaje prazna, pa zbog toga stavljam 1
        // mogli smo i na HC nacin promeniti u registracija.html da marka vozila odma nudi jednu vrstu
        // vozila, pa nece ni u jednom slucaju biti nepopunjeno polje
		markaEl.style.background = 'red';
		uspesno = false;
	} else {
		markaEl.style.background = 'white';
	}


    let tipEl = document.getElementsByName('tip')[0];
	let tip = tipEl.value;
	if(tip == 1) {
		tipEl.style.background = 'red';
		uspesno = false;
	} else {
		tipEl.style.background = 'white';
    }
    
    let registracijaEl = document.getElementsByName('registracija')[0];
	let registracija = registracijaEl.value;
	if(!registracija) {
		registracijaEl.style.background = 'red';
		uspesno = false;
	} else {
		registracijaEl.style.background = 'white';
	}

	return uspesno;
}


/*
     TODO 4:
    Ako se izabere motorno vozilo dodati polje kubikaža u formu. Dodati paragraf koji sadrži tekst: "EKO KLASA"
    Menjati boju u zavisnosti od vrednosti polja kubikaža (HINT: provera se vrši na događaj unosa sa tastature).
      Ako je izabrano elktrično ili hibridno boja teksta treba da bude u skladu sa kubikažom: 
       <600 - zelena 
       600-1200 - žuta 
       >1200 – crvena   
*/
function proveraTipa(forma){
    let tabela = document.getElementsByTagName('table')[0];

	let tipEl = document.getElementsByName('tip')[0];
	let tip = tipEl.value;
	if(tip == 2) {
		let row = tabela.insertRow(6);                      // ubacujemo posle tipa vozila
		row.setAttribute('name', 'kubikaza');                 
		let cell0 = row.insertCell(0);
		cell0.innerHTML = 'Kubikaza';                       // u celiju 0 ubacimo string kubikaza

		let inputField = document.createElement('input');   // kreiramo input
		inputField.setAttribute('id', 'kubikazaInput')
		inputField.setAttribute('type', 'number');          // kako bi u change color mogli koristiti kao broj
		inputField.setAttribute('onkeyup', 'changeColor()');// vrednost koju unosimo

		let cell1 = row.insertCell(1);
		cell1.appendChild(inputField);                      // dodajemo u celiju 1 input

		let eko = document.getElementById('eko');           // i ispisujemo EKO KLASA
		eko.innerHTML = 'EKO KLASA';

	} else {
		let delRow = document.getElementByName('kubikaza');   // brisemo red kubikaza ako nije motorno
		if(delRow) {                                        // vozilo u pitanju
			delRow.parentNode.removeChild(delRow);
		}

		let eko = document.getElementById('eko');
		eko.innerHTML = '';                                 // i ne ipisujemo EKO KLASA
	}
}

function changeColor() {
	let kubikazaInput = document.getElementById('kubikazaInput');
	if(kubikazaInput) {
		let kub = kubikazaInput.value;

		let eko = document.getElementById('eko');
		if (kub < 600) {
			eko.style.color = 'green';
		} else if (kub >= 600 && kub <= 1200) {
			eko.style.color = 'yellow';
		} else if (kub >= 1200) {
			eko.style.color = 'red';
		}
	}	
}