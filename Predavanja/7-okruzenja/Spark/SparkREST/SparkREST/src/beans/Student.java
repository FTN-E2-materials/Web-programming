package beans;


public class Student {
	private String ime;
	private String prezime;
	private Address adresa;

	public Student() {
		
	}
	
	public Student(String ime, String prezime, Address adresa) {
		this();
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
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

	public Address getAdresa() {
		return adresa;
	}

	public void setAdresa(Address adresa) {
		this.adresa = adresa;
	}

	@Override
	public String toString() {
		return "Student [ime=" + ime + ", prezime=" + prezime + ", adresa=" + adresa + "]";
	}
}
