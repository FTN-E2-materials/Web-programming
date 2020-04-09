package beans;

import java.util.HashMap;

public class RegistrovaniKorisnici {
	private HashMap<String, Korisnik> regKorisnici;
	
	
	public RegistrovaniKorisnici() {
		regKorisnici = new HashMap<String, Korisnik>();
	}
	
	/*
	 * Ako korisnik ne postoji u kolekciji korisnika, dodajemo ga.
	 */
	public boolean dodajKorisnika(Korisnik korisnik) {
		if(!regKorisnici.containsKey(korisnik.getKorisnickoIme())) {
			regKorisnici.put(korisnik.getKorisnickoIme(), korisnik);
			return true;
		}	
		
		return false;
	}
	
	public void ukloniKorisnika(String korisnickoIme) {
		regKorisnici.remove(korisnickoIme);
	}

	public HashMap<String, Korisnik> getRegKorisnici() {
		return regKorisnici;
	}

	public void setRegKorisnici(HashMap<String, Korisnik> regKorisnici) {
		this.regKorisnici = regKorisnici;
	}
}
