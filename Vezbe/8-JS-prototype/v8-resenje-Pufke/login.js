var usersMap = {
    "Pera" : "pera"
};


window.addEventListener('load', function(){
    let forma = document.forms[0];
    //Obradjivac dogadjaja slanja sa forme
    forma.addEventListener('submit', function(event){
        let valid = false;

        let ime = document.getElementById("ime").value; 
        let lozinka = document.getElementById("lozinka").value; 
        let poruka = document.getElementById("poruka");
        let korisnik = usersMap[ime];

        if (!ime) {
            poruka.innerText = "Morate uneti korisničko ime.";
            event.preventDefault();
            return;
        }else if(!(ime in usersMap)) {
            poruka.innerText = "Ne postoji korisnik sa unetim korisničkim imenom.";
            event.preventDefault();
            return;
        }else if(!(ime in usersMap)) {
            poruka.innerText = "Ne postoji korisnik sa unetim korisničkim imenom.";
            event.preventDefault();
            return;
        }else if(usersMap[ime] != lozinka) {
            poruka.innerText = "Neispravna lozinka.";
            event.preventDefault();
            return;
        }
   
        if(ime in usersMap){
            valid = usersMap[ime] == lozinka;
        }

        if(valid){
            return;
        }

        event.preventDefault();
        
    });
});