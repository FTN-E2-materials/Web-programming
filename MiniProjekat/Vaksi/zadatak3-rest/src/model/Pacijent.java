package model;

/**
 * Predstavnica modela pacijenta.
 * @author Vaxi
 *
 */
public class Pacijent {
	
	private String brojZdravstvenogOsiguranja;
	private String ime;
	private String prezime;
	private String datumRodjenja;
	private String pol;
	private String zdravstveniStatus;
	private Boolean rezultatTestaPozitivan;
	
	public Pacijent() {
		this.brojZdravstvenogOsiguranja="0";
	}
	
	public Pacijent(String brojZdravstvenogOsiguranja, String ime, String prezime, String datumRodjenja, String pol,
			String zdravstveniStatus, Boolean rezultatTestaPozitivan) {
		super();
		this.brojZdravstvenogOsiguranja = brojZdravstvenogOsiguranja;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.pol = pol;
		this.zdravstveniStatus = zdravstveniStatus;
		this.rezultatTestaPozitivan = rezultatTestaPozitivan;
	}
	
	public Pacijent(String brojZdravstvenogOsiguranja, String ime, String prezime, String datumRodjenja, String pol,
			String zdravstveniStatus) {
		super();
		this.brojZdravstvenogOsiguranja = brojZdravstvenogOsiguranja;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.pol = pol;
		this.zdravstveniStatus = zdravstveniStatus;
	}
	
	public String getBrojZdravstvenogOsiguranja() {
		return brojZdravstvenogOsiguranja;
	}
	public void setBrojZdravstvenogOsiguranja(String brojZdravstvenogOsiguranja) {
		this.brojZdravstvenogOsiguranja = brojZdravstvenogOsiguranja;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getZdravstveniStatus() {
		return zdravstveniStatus;
	}
	public void setZdravstveniStatus(String zdravstveniStatus) {
		this.zdravstveniStatus = zdravstveniStatus;
	}
	public Boolean getRezultatTesta() {
		return rezultatTestaPozitivan;
	}
	public void setRezultatTesta(Boolean rezultatTestaPozitivan) {
		this.rezultatTestaPozitivan = rezultatTestaPozitivan;
	}
	
	

	

	
	
	
	
	
	
	
	
}
