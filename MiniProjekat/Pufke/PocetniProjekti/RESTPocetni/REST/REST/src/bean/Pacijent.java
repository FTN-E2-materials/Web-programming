package bean;

public class Pacijent {
	private String brZdravstvenogOsig;
	private String datumRodjenja;
	private String ime;
	private String pol;
	private String prezime;
	private String zdravstveniStatus;
	
	
	public Pacijent(String brZdravstvenogOsig, String ime, String prezime, String datumRodjenja, String pol, String zdravstveniStatus) {
		super();
		this.brZdravstvenogOsig = brZdravstvenogOsig;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.pol = pol;
		this.zdravstveniStatus = zdravstveniStatus;
	}

	public String getBrZdravstvenogOsig() {
		return brZdravstvenogOsig;
	}

	public void setBrZdravstvenogOsig(String brZdravstvenogOsig) {
		this.brZdravstvenogOsig = brZdravstvenogOsig;
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

}